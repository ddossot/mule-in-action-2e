package com.muleinaction.lifecycle;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

public class LifecycleTrackerMessageProcessor extends AbstractLifecycleTracker implements MessageProcessor
{
    public MuleEvent process(MuleEvent event) throws MuleException
    {
        // dirty trick to get the component instance that was used for the
        // request
        event.getMessage().setPayload(this);
        return event;
    }
}
