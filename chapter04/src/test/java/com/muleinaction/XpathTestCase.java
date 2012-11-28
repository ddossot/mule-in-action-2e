
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.w3c.dom.NodeList;

public class XpathTestCase extends FunctionalTestCase
{

    private static final String PAYLOAD = 
    		"<products><product><id>1234</id>" +
    		"<type>Imported Beer</type>" +
    		"<name>Mordor's Pale Lager</name>" +
    		"<price>10.90</price></product></products>";
    
    @Override
    protected String getConfigResources()
    {
        return "xpath.xml";
    }

    @Test
    public void testXpathExpression() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("vm://xpath-expression.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getInboundProperty("productId"), is(notNullValue()));
    }

    @Test
    public void testAlias() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("vm://xpath-extractor.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(NodeList.class)));
    }

}
