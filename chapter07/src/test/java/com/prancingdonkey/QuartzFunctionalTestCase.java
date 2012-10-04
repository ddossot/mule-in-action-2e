package com.prancingdonkey;

import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: johndemic
 * Date: 10/3/12
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuartzFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/quartz-config.xml";
    }

    @Test
    public void testCanGenerateEvent() throws Exception {

    }
}
