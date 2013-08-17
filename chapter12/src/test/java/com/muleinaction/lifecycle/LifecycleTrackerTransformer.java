
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
import org.mule.api.service.Service;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

@SuppressWarnings("deprecation")
public class LifecycleTrackerTransformer extends AbstractTransformer
    implements Lifecycle, MuleContextAware, FlowConstructAware
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

    @Override
    public void setMuleContext(final MuleContext context)
    {
        tracker.add("setMuleContext");
    }

    public void setService(final Service service)
    {
        tracker.add("setService");
    }

    public void setFlowConstruct(FlowConstruct flowConstruct)
    {
        tracker.add("setFlowConstruct");
    }

    @Override
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

    @Override
    public void dispose()
    {
        tracker.add("dispose");
    }

    @Override
    protected Object doTransform(final Object src, final String encoding) throws TransformerException
    {
        // dirty trick to get the transformer instance that was used for the
        // request
        return this;
    }
}
