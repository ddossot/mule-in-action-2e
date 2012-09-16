
package com.muleinaction.pattern.http;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class PatternHttpCacheTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/http/pattern-http-cache.xml";
    }

    @Test
    @Ignore
    public void testSimpleValidator() throws Exception
    {
        //MuleClient muleClient = new MuleClient(muleContext);
        //MuleMessage result = muleClient.request("http://localhost:8888?v=1.0&q=hi", 5000);
        //assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
    }
    
}
