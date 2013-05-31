import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;


public class ValidateAndDispatchCoolingAlertsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/validateAndDispatchCoolingAlerts.xml";
    }

    @Test

    public void testCanValidate() throws Exception {
        String orders = FileUtils.readFileToString(new File("src/test/resources/orders.xml"));
        muleContext.getClient().dispatch("http://localhost:8080/orders",orders,null);
        MuleMessage result = muleContext.getClient().request("jms://topic:orders.submitted", 15000);
        Assert.assertNotNull(result);

    }
}
