package com.muleinaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;


public class JDBCOutboundFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/jdbc-outbound-config.xml";
    }

    @Test
    public void testCanInsertProducts() {

    }
}
