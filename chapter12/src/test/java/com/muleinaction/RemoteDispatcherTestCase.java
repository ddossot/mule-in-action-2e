
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.FutureMessageResult;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.module.client.RemoteDispatcher;
import org.mule.tck.junit4.FunctionalTestCase;

public class RemoteDispatcherTestCase extends FunctionalTestCase
{
    private static final long TEST_CLIENT_ID = 123L;

    @Override
    protected String getConfigResources()
    {
        return "remote-dispatcher.xml";
    }

    @Test
    public void invokeRemoteEndpoint() throws Exception
    {
        //<start id="lis_12_remote-dispatch-2"/>
        MuleClient muleClient = new MuleClient(true);//<co id="lis_12_remote-dispatch-2_1"/>
        RemoteDispatcher remoteDispatcher =
             muleClient.getRemoteDispatcher("tcp://localhost:5555");//<co id="lis_12_remote-dispatch-2_2"/>
        FutureMessageResult asyncResponse =
            remoteDispatcher.sendAsyncRemote(//<co id="lis_12_remote-dispatch-2_3"/>
            "clientServiceChannel",
            TEST_CLIENT_ID,
            null);
        //<end id="lis_12_remote-dispatch-2"/>

        MuleMessage result = asyncResponse.getMessage();
        muleClient.dispose();

        assertThat(result.getPayloadAsString(), is("fake_client_data"));

        // ensure that Mule client runs in a different context than Mule that is called remotely
        assertThat(muleClient.getMuleContext()
            .getRegistry()
            .lookupFlowConstruct("clientFlow"), is(nullValue()));

        // while the Mule instance has a context loaded from remote-dispatcher.xml
        assertThat(
            muleContext.getRegistry().lookupFlowConstruct("clientFlow"),
            is(notNullValue()));
    }
}
