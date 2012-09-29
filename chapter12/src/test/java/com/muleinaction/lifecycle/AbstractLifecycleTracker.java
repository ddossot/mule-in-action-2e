
package com.muleinaction.lifecycle;

import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Lifecycle;

public abstract class AbstractLifecycleTracker implements Lifecycle, MuleContextAware, FlowConstructAware
{
    private final List<String> tracker = new ArrayList<String>();

    public List<String> getTracker()
    {
        return tracker;
    }

    public void setProperty(final String value)
    {
        tracker.add("setProperty");
    }

    public void setMuleContext(final MuleContext context)
    {
        tracker.add("setMuleContext");
    }

    public void setFlowConstruct(FlowConstruct flowConstruct)
    {
        tracker.add("setFlowConstruct");
    }

    public void initialise() throws InitialisationException
    {
        tracker.add("initialise");
    }

    public void start() throws MuleException
    {
        tracker.add("start");
    }

    public void stop() throws MuleException
    {
        tracker.add("stop");
    }

    public void dispose()
    {
        tracker.add("dispose");
    }
}
