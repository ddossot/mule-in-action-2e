
package com.prancingdonkey.interceptor;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.interceptor.Interceptor;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.NotificationException;
import org.mule.processor.AbstractInterceptingMessageProcessor;

// <start id="lis_12_interceptor_broker_not_ready"/>
public final class BrokerNotReadyInterceptor
  extends AbstractInterceptingMessageProcessor
  implements Interceptor,
              MuleContextNotificationListener<MuleContextNotification>,
              Initialisable
{
    private volatile boolean brokerReady = false;

    public void initialise() throws InitialisationException
    {
        try
        {
            muleContext.registerListener(this);//<co id="lis_12_interceptor_broker_not_ready-1"/>
        }
        catch (final NotificationException ne)
        {
            throw new RuntimeException(ne);
        }
    }

    public void onNotification(MuleContextNotification notification)
    {
        int action = notification.getAction();

        if (action == MuleContextNotification.CONTEXT_STARTED)
        {
            brokerReady = true;//<co id="lis_12_interceptor_broker_not_ready-2"/>
        }
        else if (action == MuleContextNotification.CONTEXT_STOPPED)
        {
            brokerReady = false;//<co id="lis_12_interceptor_broker_not_ready-3"/>
        }
    }

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        if (!brokerReady)//<co id="lis_12_interceptor_broker_not_ready-4"/>
        {
            throw new IllegalStateException(
                "Invocation of service "
              + event.getFlowConstruct().getName()
              + " impossible at this time!");
        }

        return next.process(event);
    }
}
// <end id="lis_12_interceptor_broker_not_ready"/>
