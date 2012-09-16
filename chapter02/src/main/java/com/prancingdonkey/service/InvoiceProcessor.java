
package com.prancingdonkey.service;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class InvoiceProcessor implements Callable
{
    @Override
    public Object onCall(final MuleEventContext eventContext) throws Exception
    {
        return eventContext.getMessage().getInboundProperty("currencyCode");
    }
}
