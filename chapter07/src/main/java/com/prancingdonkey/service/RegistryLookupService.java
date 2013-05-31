package com.prancingdonkey.service;


import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class RegistryLookupService implements Callable {

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        eventContext.getMessage().setInvocationProperty("resolvedEndpoint","foo");
        return eventContext.getMessage();
    }
}
