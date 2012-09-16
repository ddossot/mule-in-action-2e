
package com.muleinaction.pattern.http;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PatternHttpTransformerTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/http/pattern-http-transformer.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
    	String payload = "{\"type\" : \"cans\"}";
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.send("http://127.0.0.1:8888", payload, null);
        assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
    }
    
}
