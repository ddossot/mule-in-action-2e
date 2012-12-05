package com.muleinaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class VMFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/vm-config.xml";
    }

    @Test
    public void testCanPostExpenses() throws Exception {

    }
}
