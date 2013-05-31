package com.prancingdonkey.service;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;


public class OrderValidationService implements Callable {

    public Object onCall(MuleEventContext eventContext) throws Exception {
        return "Order Received";
    }
}
