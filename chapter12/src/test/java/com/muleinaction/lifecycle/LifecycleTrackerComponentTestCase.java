
package com.muleinaction.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.module.client.MuleClient;

public class LifecycleTrackerComponentTestCase
{
    private MuleClient muleClient;
    private MuleContext muleContext;

    @Before
    public void bootMule() throws Exception
    {
        muleClient = new MuleClient("lifecycle-config.xml");
        muleContext = muleClient.getMuleContext();
        muleContext.start();
    }

    @Test
    public void trackSpringBeanFlowLifecycle() throws Exception
    {
        trackComponentLifecycle("SpringBeanFlow",
            "[setProperty, setMuleContext, springInitialize, setFlowConstruct, start, stop, springDestroy]");
    }

    @Test
    public void trackMuleSingletonFlowLifecycle() throws Exception
    {
        trackComponentLifecycle("MuleSingletonFlow",
            "[setProperty, setFlowConstruct, setMuleContext, initialise, start, stop, dispose]");
    }

    @Test
    public void trackMulePrototypeFlowLifecycle() throws Exception
    {
        trackComponentLifecycle("MulePrototypeFlow",
            "[setProperty, setFlowConstruct, setMuleContext, initialise, start, stop, dispose]");
    }

    @Test
    public void trackMulePooledPrototypeFlowLifecycle() throws Exception
    {
        trackComponentLifecycle("MulePooledPrototypeFlow",
            "[setProperty, setFlowConstruct, setMuleContext, initialise, start, stop, dispose]");
    }

    private void trackComponentLifecycle(final String flowName, final String expectedLifeCycle)
        throws Exception
    {

        final AbstractLifecycleTracker tracker = exerciseComponent(muleClient, flowName);

        muleContext.dispose();
        muleClient.dispose();

        assertEquals(flowName, expectedLifeCycle, tracker.getTracker().toString());
    }

    private AbstractLifecycleTracker exerciseComponent(final MuleClient muleClient, final String componentName)
        throws Exception
    {

        final AbstractLifecycleTracker ltc = (AbstractLifecycleTracker) muleClient.send(
            "vm://" + componentName + ".In", null, null).getPayload();

        assertNotNull(ltc);

        return ltc;
    }
}
