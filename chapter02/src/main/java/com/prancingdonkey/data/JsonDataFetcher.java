
package com.prancingdonkey.data;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class JsonDataFetcher implements Callable
{
    public Object onCall(final MuleEventContext eventContext) throws Exception
    {
        return "[1,2,3]";
    }
}
