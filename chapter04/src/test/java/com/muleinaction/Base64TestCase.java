
package com.muleinaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class Base64TestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "base64.xml";
    }

    @Test
    public void testBase64() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://base64.in", "Hey Joe!", null);

        assertThat((String) result.getPayload(), is(equalTo("SGV5IEpvZSE=")));
    }

}
