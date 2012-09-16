
package com.muleinaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.registry.MuleRegistry;
import org.mule.config.spring.SpringRegistry;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;

public class RegistryTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "registry-config.xml";
    }

    @Test
    public void testGetMuleObject() throws Exception
    {
        final Object muleObject = muleContext.getRegistry().lookupObject("MuleObject");
        assertTrue(muleObject.toString(), muleObject instanceof EndpointBuilder);
    }

    @Test
    public void testGetSpringBeanDirect() throws Exception
    {
        final Object springBean = muleContext.getRegistry().lookupObject("SpringBean");
        assertTrue(springBean instanceof BigInteger);
    }

    @Test
    public void testGetSpringBeanViaApplicationContext() throws Exception
    {
        final ApplicationContext ac = (ApplicationContext) muleContext.getRegistry().lookupObject(
            SpringRegistry.SPRING_APPLICATION_CONTEXT);
        final Object springBean = ac.getBean("SpringBean", BigInteger.class);
        assertNotNull(springBean);
    }

    @Test
    public void testPutInTransientRegistry() throws Exception
    {
        final MuleRegistry registry = muleContext.getRegistry();
        assertNull(registry.lookupObject("bar"));
        registry.registerObject("bar", 123L);
        assertEquals(123L, registry.lookupObject("bar"));
    }
}
