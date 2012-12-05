package com.muleinaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class FTPFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/ftp-config.xml";
    }

    @Test
    public void testCanGetFiles() throws Exception {

    }
}
