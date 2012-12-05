package com.muleinaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class JDBCInboundFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/jdbc-inbound-config.xml";
    }

    @Test
    public void testCanPollForStuckOrders() throws Exception {

    }
}
