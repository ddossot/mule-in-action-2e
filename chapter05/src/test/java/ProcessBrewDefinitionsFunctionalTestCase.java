import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class ProcessBrewDefinitionsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/processBrewDefinitions.xml";
    }

    @Test
    public void testCanProcessBrewDefinitions() throws Exception {
        Map properties = new HashMap();
        properties.put("PD_PRIORITY","HIGH");
        muleContext.getClient().dispatch("http://localhost:8080/orders","<order/>",properties);
        MuleMessage response = muleContext.getClient().request("jms://orders.critical",15000);
        assertNotNull(response);
    }
}
