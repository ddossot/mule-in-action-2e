
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.dom4j.Document;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.module.xml.util.XMLUtils;
import org.mule.tck.junit4.FunctionalTestCase;

public class TransRef extends FunctionalTestCase
{

    String sampleXml = "<sample/>";

    @Override
    protected String getConfigResources()
    {
        return "trans-refs.xml";
    }

    @Test
    public void testRefs() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8080", sampleXml, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testMPs() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8081", sampleXml, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        
        String resultString = result.getPayloadAsString();
        Document resultDocument = XMLUtils.toDocument(resultString, muleContext);
        assertThat(resultDocument, is(notNullValue()));
    }
}
