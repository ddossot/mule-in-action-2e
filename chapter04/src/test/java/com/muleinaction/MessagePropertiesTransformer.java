
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class MessagePropertiesTransformer extends FunctionalTestCase
{

	private static final String SHOULD_SURVIVE_PROPERTY_NAME = "iwillsurvive";

	private static final String SHOULD_SURVIVE_PROPERTY_VALUE = "oh yes";

	@Override
    protected String getConfigResources()
    {
        return "message-properties-transformer.xml";
    }

    @Test
    public void testDeleteSimple() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.send("vm://expression-transformer-delete-simple.in", null, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getInboundProperty(SHOULD_SURVIVE_PROPERTY_NAME), is(instanceOf(String.class)));
        assertThat((String) result.getInboundProperty(SHOULD_SURVIVE_PROPERTY_NAME), is(SHOULD_SURVIVE_PROPERTY_VALUE));
    }

    @Test
    public void testDeleteExpr() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.send("vm://expression-transformer-delete-expr.in", null, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getInboundProperty(SHOULD_SURVIVE_PROPERTY_NAME), is(instanceOf(String.class)));
        assertThat((String) result.getInboundProperty(SHOULD_SURVIVE_PROPERTY_NAME), is(SHOULD_SURVIVE_PROPERTY_VALUE));
    }
    
    @Test
    public void testAdd() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.send("vm://expression-transformer-add-multiple.in", null, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat((String) result.getInboundProperty("Content-Type"), is("application/vnd.ms-excel"));
        assertThat((String) result.getInboundProperty("Content-Disposition"), is("attachment; filename=stats.csv"));
    }
    
    @Test
    public void testRename() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        MuleMessage result = muleClient.send("vm://expression-transformer-rename.in", null, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getInboundProperty("prancing.isbn"), is(instanceOf(String.class)));
        assertThat((String) result.getInboundProperty("prancing.isbn"), is("123"));
    }
    
    @Test
    public void testPropertyCopy() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("PrancingDonkeyRequestId", "123");
        
        MuleMessage result = muleClient.send("vm://expression-transformer-copy.in", null, headers);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat((String) result.getInboundProperty("PrancingDonkeyRequestId"), is("123"));
    }
    
    @Test
    public void testVariableSet() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.send("vm://expression-transformer-variable-set.in", null, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat((String) result.getInboundProperty("prancingVariable"), is("Value or expression"));
    }
    
}
