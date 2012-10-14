package com.muleinaction.component;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class DodgyComponent implements Callable {

    public Object onCall(final MuleEventContext eventContext) throws Exception {
        throw new RuntimeException("Simulated service error");
    }
}
