package com.muleinaction.exception;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class ReconnectStrategiesTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "exception/reconnect-strategies.xml";
    }

    @Test
    public void testConfigLoads() throws Exception
    {
    }
    
}
