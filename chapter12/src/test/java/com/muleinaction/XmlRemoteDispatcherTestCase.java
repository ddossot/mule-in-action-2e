
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

public class XmlRemoteDispatcherTestCase extends FunctionalTestCase
{
    private static final long TEST_CLIENT_ID = 123L;

    @Override
    protected String getConfigResources()
    {
        return "xml-remote-dispatcher.xml";
    }

    @Test
    public void invokeRemoteEndpoint() throws Exception
    {
        //<start id="lis_12_xml-remote-dispatch-2"/>
        MuleClient muleClient = new MuleClient(true);
        RemoteDispatcher remoteDispatcher =
             muleClient.getRemoteDispatcher("http://localhost:8080/_remoting");
        //<end id="lis_12_xml-remote-dispatch-2"/>
        FutureMessageResult asyncResponse = remoteDispatcher.sendAsyncRemote(
            "clientServiceChannel", TEST_CLIENT_ID, null);

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
