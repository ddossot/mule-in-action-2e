
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import org.junit.Test;

public class MessageEnricherTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "message-enricher.xml";
    }

    @Test
    public void singleEnrichment() throws Exception
    {
        assertThat(new MuleClient(muleContext).send("vm://invoice-processor", "ignored", null)
            .getPayloadAsString(), is("USD"));
    }

    @Test
    public void multipleEnrichment() throws Exception
    {
        assertThat(new MuleClient(muleContext).send("vm://message-enricher-2", "ignored", null)
            .getPayloadAsString(), is("{USD,ABC123}"));
    }
}
