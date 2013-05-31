import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;

import static org.junit.Assert.*;

public class SplitLineItemsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/splitLineItems.xml";
    }

    @Test
    public void testCanSplitLineItems() throws Exception {
        String orders = FileUtils.readFileToString(new File("src/test/resources/orders.xml"));
        muleContext.getClient().dispatch("jms://order.submission", orders, null);

        for (int i = 0; i < 3; i++) {
            MuleMessage response = muleContext.getClient().request("jms://lineitem.processing", 15000);
            assertNotNull(response);
            assertNotNull(response.getPayload());
        }
    }
}
