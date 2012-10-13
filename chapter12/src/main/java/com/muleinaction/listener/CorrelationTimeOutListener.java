package com.muleinaction.listener;

import java.util.List;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.RoutingNotificationListener;
import org.mule.context.notification.RoutingNotification;

// <start id="lis_12_correl_to_listener"/>
public final class CorrelationTimeOutListener implements//<co id="lis_12_correl_to_listener-1"/>
        RoutingNotificationListener<RoutingNotification> {

    public void onNotification(final RoutingNotification notification) {
        if (notification.getAction()
                        != RoutingNotification.CORRELATION_TIMEOUT) {
            return;//<co id="lis_12_correl_to_listener-2"/>
        }

        final MuleMessage uncorrelatedMessage =
                        (MuleMessage) notification.getSource();//<co id="lis_12_correl_to_listener-3"/>
// <end id="lis_12_correl_to_listener"/>

        try {
            // we assume here that we care only about the first message of the
            // aggregation collection
            @SuppressWarnings("unchecked")
            final Object uncorrelatedPayload = ((List<Object>)uncorrelatedMessage.getPayload()).toArray()[0];
            
            MuleContext muleContext = uncorrelatedMessage.getMuleContext();

            muleContext.getClient().send(dlqAddress,
                                         new DefaultMuleMessage(uncorrelatedPayload, uncorrelatedMessage, muleContext));

        } catch (final MuleException me) {
            // here we should log a serialized form of the message, using a
            // specific file appender that target a DLQ log file
        }
    }

    private String dlqAddress;

    public void setDlqAddress(final String dlqAddress) {
        this.dlqAddress = dlqAddress;
    }
}
