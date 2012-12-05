package com.muleinaction;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class FileFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/file-config.xml";
    }

    @Test
    public void testCanCopyExpenseReports() throws Exception {

    }
}
