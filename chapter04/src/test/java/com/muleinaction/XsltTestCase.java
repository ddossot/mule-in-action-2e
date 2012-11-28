
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class XsltTestCase extends FunctionalTestCase
{
    
    private static final String PAYLOAD = 
    		"<products><product><id>1234</id>" +
    		"<type>Imported Beer</type>" +
    		"<name>Mordor's Pale Lager</name>" +
    		"<price>10.90</price></product></products>";

    @Override
    protected String getConfigResources()
    {
        return "xslt.xml";
    }

    @Test
    public void testSimple() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://xsl-simple.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testParam() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://xsl-param.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testParamExpr() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("discount", "10");

        MuleMessage result = muleClient.send("vm://xsl-param-expr.in", PAYLOAD, headers);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testIdle() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://xsl-idle.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

}
