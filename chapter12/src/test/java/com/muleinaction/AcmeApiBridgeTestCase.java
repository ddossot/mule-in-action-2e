
package com.muleinaction;

import static com.muleinaction.Constants.ACME_TEST_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
@SuppressWarnings("unchecked")
//<start id="lis_12_acme_api_bridge_test"/>
public class AcmeApiBridgeTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");//<co id="lis_12_acme_api_bridge_test-0"/>

    @Override
    protected String getConfigResources()//<co id="lis_12_acme_api_bridge_test-1"/>
    {
        return
           "functional-test-stubs.xml,acme-api-bridge.xml";
    }

    @Test
    public void testSuccessfulJdbcAndHttpDispatches() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();//<co id="lis_12_acme_api_bridge_test-2"/>

        muleClient.dispatch("vm://invokeAcmeAmi",//<co id="lis_12_acme_api_bridge_test-3"/>
                            ACME_TEST_MESSAGE,
                            null);

        Thread.sleep(5000L);//<co id="lis_12_acme_api_bridge_test-4"/>

        MuleMessage dbResponse =
                    muleClient.request("jdbc://retrieveData",
                                       1000 * getTestTimeoutSecs());//<co id="lis_12_acme_api_bridge_test-5"/>

        List<Map<String, String>> resultSet =
                   (List<Map<String,String>>) dbResponse.getPayload();

        assertThat(resultSet.get(0).get("DATA"),//<co id="lis_12_acme_api_bridge_test-6"/>
                   is(ACME_TEST_MESSAGE));

        FunctionalTestComponent ftc =
                        getFunctionalTestComponent("acmeApiStub");//<co id="lis_12_acme_api_bridge_test-7"/>

        assertThat(ftc.getReceivedMessagesCount(),//<co id="lis_12_acme_api_bridge_test-8"/>
                   is(1));

        String lastReceivedMessage =
                   (String) ftc.getLastReceivedMessage();//<co id="lis_12_acme_api_bridge_test-9"/>

        assertThat(lastReceivedMessage,//<co id="lis_12_acme_api_bridge_test-10"/>
                   is(ACME_TEST_MESSAGE));
    }
}
//<end id="lis_12_acme_api_bridge_test"/>