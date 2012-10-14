
package com.muleinaction;

import static com.muleinaction.Constants.MESSAGE_XML_V1;
import static com.muleinaction.Constants.MESSAGE_XML_V2;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.jms.BytesMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.jms.JmsConnector;

public class TransformingBridgeJmsClientTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "functional-test-connectors.xml,jms-transforming-bridge.xml";
    }

    // <start id="lis_12_fun_testing_jms"/>
    @Test
    public void testJmsXmlV1V2Bridge() throws Exception
    {
        JmsConnector jmsConnector = // <co id="lis_12_fun_testing_jms-1"/>
            (JmsConnector) muleContext
                            .getRegistry()
                              .lookupConnector("amqConnector");

        Session session =
            jmsConnector
              .getConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue v1Queue = session.createQueue("messages.v1");
        MessageProducer producer = session.createProducer(v1Queue);
        TextMessage v1Message = session.createTextMessage(MESSAGE_XML_V1);
        producer.send(v1Message);// <co id="lis_12_fun_testing_jms-2"/>

        Queue v2Queue = session.createQueue("messages.v2");
        MessageConsumer consumer = session.createConsumer(v2Queue);
        BytesMessage v2Message =// <co id="lis_12_fun_testing_jms-3"/>
             (BytesMessage) consumer.receive(1000 * getTestTimeoutSecs());

        byte[] bytes = new byte[(int) v2Message.getBodyLength()];
        v2Message.readBytes(bytes);// <co id="lis_12_fun_testing_jms-4"/>

        assertThat(new String(bytes), is(MESSAGE_XML_V2));

        session.close();
    }
    // <end id="lis_12_fun_testing_jms"/>
}
