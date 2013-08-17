package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.context.MuleContextBuilder;
import org.mule.api.context.MuleContextFactory;
import org.mule.config.AnnotationsConfigurationBuilder;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextBuilder;
import org.mule.context.DefaultMuleContextFactory;

public class MuleBootstrapTest
{
    public static final String BROKER_URL = "tcp://localhost:52525";

    private BrokerService amqBroker;

    private String queueName;

    private String expectedPayload;

    @Before
    public void initializeActiveMQ() throws Exception
    {
        expectedPayload = RandomStringUtils.randomAlphanumeric(10);

        amqBroker = new BrokerService();
        amqBroker.addConnector(BROKER_URL);
        amqBroker.start();

        queueName = "queue." + RandomStringUtils.randomAlphanumeric(10);

        // load a message in the test queue so we can fetch it via the remote
        // client
        final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            BROKER_URL);

        final Connection connection = connectionFactory.createConnection();

        final Session session = connection.createSession(false,
            Session.AUTO_ACKNOWLEDGE);

        session.createProducer(session.createQueue(queueName)).send(
            session.createTextMessage(expectedPayload));

        connection.close();
    }

    @After
    public void disposeActiveMQ() throws Exception
    {
        amqBroker.stop();
    }

    @Test
    public void startMuleWithAnnotationsSupport() throws Exception
    {
        //<start id="lis_12_mule-full-start"/>
        List<ConfigurationBuilder> configurationBuilders =
          Arrays.<ConfigurationBuilder>asList(
            new AnnotationsConfigurationBuilder(),
            new SpringXmlConfigurationBuilder("raw-jms-muleclient-config.xml"));

        MuleContextBuilder muleContextBuilder = new DefaultMuleContextBuilder();
        MuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        MuleContext muleContext =
          muleContextFactory.createMuleContext(
            configurationBuilders,
            muleContextBuilder);

        muleContext.start();

        MuleClient muleClient = muleContext.getClient();
        //<end id="lis_12_mule-full-start"/>

        MuleMessage response = muleClient.request(
            "jms://" + queueName + "?connector=amqConnector", 1000);

        muleContext.dispose();

        final String actualPayload = response.getPayloadAsString();

        assertThat(expectedPayload, is(actualPayload));
    }
}
