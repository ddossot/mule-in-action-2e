import org.junit.Ignore;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;


public class ProcessSalesForceAlertsTest extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/processSalesForceAlert.xml";
    }

    @Ignore
    @Test
    public void testCanProcessSalesForceAlerts() throws Exception {
        // ToDo Implement when Cloud Connectors can participate in notifications
    }
}
