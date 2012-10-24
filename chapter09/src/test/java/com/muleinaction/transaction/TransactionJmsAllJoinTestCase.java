package com.muleinaction.transaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;


public class TransactionJmsAllJoinTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
    	return "transaction/transaction-jms-all-join.xml";
    }

    @Test
    public void testConfigLoads() throws Exception
    {
    }
    
}
