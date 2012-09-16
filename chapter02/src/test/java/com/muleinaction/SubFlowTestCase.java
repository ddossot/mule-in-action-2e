
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class SubFlowTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "choice-dispatcher-stubs.xml sub-flow.xml";
    }

    @Test
    public void requestDispatcherSubFlow() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        assertThat(
            muleClient.send("vm://test-request-dispatcher.in", "foo",
                Collections.singletonMap("valid", true))
                .getPayloadAsString(), is("foo:valid"));
        assertThat(
            muleClient.send("vm://test-request-dispatcher.in", "foo",
                Collections.singletonMap("valid", false))
                .getPayloadAsString(), is("foo:invalid"));
    }
}
