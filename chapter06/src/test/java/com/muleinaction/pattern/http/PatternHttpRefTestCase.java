
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

public class PatternHttpRefTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/http/pattern-http-ref.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage result = muleClient.request("http://localhost:8888?v=1.0&q=hi", 5000);
        assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
    }
    
}
