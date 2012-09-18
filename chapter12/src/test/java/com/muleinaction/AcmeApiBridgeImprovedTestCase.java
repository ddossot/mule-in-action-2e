
package com.muleinaction;

import static com.muleinaction.Constants.ACME_TEST_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.concurrent.Latch;

@SuppressWarnings("unchecked")
public class AcmeApiBridgeImprovedTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");

    @Override
    protected String getConfigResources()
    {
        return
           "functional-test-stubs.xml,acme-api-bridge.xml";
    }

    //<start id="lis_12_acme_api_bridge_improved_test"/>
    @Test
    public void testSuccessfulJdbcAndHttpDispatches() throws Exception
    {
        final Latch latch = new Latch();//<co id="lis_12_acme_api_bridge_improved_test-1"/>

        FunctionalTestComponent ftc =
                        getFunctionalTestComponent("acmeApiStub");

        ftc.setEventCallback(new EventCallback()//<co id="lis_12_acme_api_bridge_improved_test-2"/>
        {
            public void eventReceived(
                             MuleEventContext context,
                             Object component)
                     throws Exception
            {
                latch.countDown();//<co id="lis_12_acme_api_bridge_improved_test-3"/>
            }
        });

        MuleClient muleClient = muleContext.getClient();

        muleClient.dispatch("vm://invokeAcmeAmi",
                            ACME_TEST_MESSAGE,
                            null);

        latch.await(getTestTimeoutSecs(), TimeUnit.SECONDS);//<co id="lis_12_acme_api_bridge_improved_test-4"/>

        // DB and HTTP assertions unchanged...
        //<end id="lis_12_acme_api_bridge_improved_test"/>
        MuleMessage dbResponse =
                    muleClient.request("jdbc://retrieveData",
                                       1000 * getTestTimeoutSecs());

        List<Map<String, String>> resultSet =
                   (List<Map<String,String>>) dbResponse.getPayload();

        assertThat(resultSet.get(0).get("DATA"),
                   is(ACME_TEST_MESSAGE));

        assertThat(ftc.getReceivedMessagesCount(),
                   is(1));

        String lastReceivedMessage =
                   (String) ftc.getLastReceivedMessage();

        assertThat(lastReceivedMessage,
                   is(ACME_TEST_MESSAGE));
    }
}