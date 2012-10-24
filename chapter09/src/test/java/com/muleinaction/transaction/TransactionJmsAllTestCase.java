package com.muleinaction.transaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;


public class TransactionJmsAllTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
    	return "transaction/transaction-jms-all.xml";
    }

    @Test
    public void testConfigLoads() throws Exception
    {
    }
    
}
