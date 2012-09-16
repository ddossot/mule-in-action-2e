
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;

public class TransRef extends FunctionalTestCase
{

    String catalogXml;

    @Override
    protected String getConfigResources()
    {
        return "trans-refs.xml";
    }

    @Before
    public void setup() throws IOException
    {
        catalogXml = "<catalog><cd><title>Wish you were here</title><artist>Pink Floyd</artist></cd></catalog>";
    }

    @Test
    public void testRefs() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8080", catalogXml, null);
        assertThat(result.getPayload(), is(not(instanceOf(NullPayload.class))));
    }

    @Test
    public void testMPs() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8081", catalogXml, null);
        assertThat(result.getPayload(), is(not(instanceOf(NullPayload.class))));
    }
}
