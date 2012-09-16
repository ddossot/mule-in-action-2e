
package com.muleinaction.pattern.ws;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PatternWsInheritanceTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/ws/pattern-ws-inheritance.xml";
    }

    @Test
    public void testWsProxy() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        MuleMessage result = muleClient.send("http://localhost:8080/public/BrewService/?wsdl", null, null);
        assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
    }
    
}
