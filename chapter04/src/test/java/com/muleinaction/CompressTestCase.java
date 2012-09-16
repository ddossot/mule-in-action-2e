
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class CompressTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "compress.xml";
    }

    @Test
    public void testCompress() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        final String payload = "The quick fox jumped over the lazy dog";

        muleClient.dispatch("vm://compress.in", payload, null);

        MuleMessage result = muleClient.request("jms://uncompressedDataQueue", 10000);

        assertThat((String) result.getPayload(), is(equalTo(payload)));
    }

}
