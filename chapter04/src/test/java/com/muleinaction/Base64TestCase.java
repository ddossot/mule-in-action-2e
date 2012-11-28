
package com.muleinaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class Base64TestCase extends FunctionalTestCase
{

    private static final String ENCODED_STRING = "SGV5IEpvZSE=";
    
	private static final String CLEAN_STRING = "Hey Joe!";

	@Override
    protected String getConfigResources()
    {
        return "base64.xml";
    }

    @Test
    public void testBase64() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://base64.in", CLEAN_STRING, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat((String) result.getPayload(), is(equalTo(ENCODED_STRING)));
    }

}
