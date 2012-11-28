
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ObjectToByteArrayTestCase extends FunctionalTestCase
{

    private static final String TEST_PAYLOAD = "I'm a test string.";

    @Override
    protected String getConfigResources()
    {
        return "object-to-byte-array.xml";
    }

    @Test
    public void testObjectToByteArray() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://object-to-byte-array.in", TEST_PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload().equals(TEST_PAYLOAD), is(true));
    }

}
