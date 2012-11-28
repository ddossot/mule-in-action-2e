
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ExpressionTransformerTestCase extends FunctionalTestCase
{

    private static final String HOSTNAME = "localhost";

    @Override
    protected String getConfigResources()
    {
        return "expression-transformer.xml";
    }

    @Test
    public void testExpressionArrayResult() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Inet4Address inetAddress = (Inet4Address) InetAddress.getByName(HOSTNAME);
        MuleMessage result = muleClient.send("vm://expression-transformer.in", inetAddress, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), instanceOf(Object[].class));
        
        Object[] payload = (Object[]) result.getPayload();

        assertThat(payload[0], instanceOf(String.class));

        assertThat((Boolean) payload[1], is(false));
    }

    @Test
    public void testExpressionSingleResult() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Inet4Address inetAddress = (Inet4Address) InetAddress.getByName(HOSTNAME);
        MuleMessage result = muleClient.send("vm://expression-transformer-single.in", inetAddress, null);

        Object payload = (Object) result.getPayload();
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(payload, instanceOf(String.class));
    }

}
