package com.prancingdonkey;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class CEPFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/order-cep-config.xml";
    }

    @Test
    public void testCanDetermineAbnormalStreamOfOrders() throws Exception {

    }
}
