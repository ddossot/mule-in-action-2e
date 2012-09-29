
package com.prancingdonkey.interceptor;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.NotificationException;
import org.mule.processor.AbstractInterceptingMessageProcessor;

// <start id="lis_12_interceptor_broker_not_ready"/>
public final class BrokerNotReadyInterceptor extends AbstractInterceptingMessageProcessor
    implements MuleContextNotificationListener<MuleContextNotification>
{
    private volatile boolean brokerReady = false;

    @Override
    public void setMuleContext(final MuleContext context)
    {
        try
        {
            context.registerListener(this);
        }
        catch (final NotificationException ne)
        {
            throw new RuntimeException(ne);
        }
    }

    public void onNotification(final MuleContextNotification notification)
    {
        int action = notification.getAction();

        if (action == MuleContextNotification.CONTEXT_STARTED)
        {
            brokerReady = true;
        }
        else if (action == MuleContextNotification.CONTEXT_STOPPED)
        {
            brokerReady = false;
        }
    }

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        if (!brokerReady)
        {
            throw new IllegalStateException("Invocation of service " + event.getFlowConstruct().getName()
                                            + " impossible at this time!");
        }

        return next.process(event);
    }
}
// <end id="lis_12_interceptor_broker_not_ready"/>
