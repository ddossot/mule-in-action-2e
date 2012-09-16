package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class RawJmsMuleClientTest
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
    public void tapJmsTransport() throws Exception
    {
        //<start id="lis_12_raw-jms"/>
        MuleClient muleClient = new MuleClient("raw-jms-muleclient-config.xml");//<co id="lis_12_raw-jms_1"/>

        muleClient.getMuleContext().start();//<co id="lis_12_raw-jms_2"/>

        MuleMessage response = muleClient.request(
            "jms://" + queueName + "?connector=amqConnector", 1000);//<co id="lis_12_raw-jms_3"/>

        muleClient.getMuleContext().dispose();//<co id="lis_12_raw-jms_4"/>
        muleClient.dispose();
        //<end id="lis_12_raw-jms"/>

        final String actualPayload = response.getPayloadAsString();

        assertThat(expectedPayload, is(actualPayload));
    }
}
