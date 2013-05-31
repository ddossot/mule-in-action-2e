package com.prancingdonkey;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;


public class MediationFunctionalTestCase extends FunctionalTestCase  {

    @Override
    protected String getConfigResources() {
        return "src/main/app/mediation-config.xml";
    }


    @Test
    public void testCanProxyMessages() throws Exception {

    }
}
