import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;


public class LogExpenseReportsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/logExpenseReports.xml";
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCannotSubmitNonBrew() throws Exception {
        MuleClient client = muleContext.getClient();
        client.dispatch("jms://topic:expenses.status",
                "<expense><id>1234</id><status>PROCESSED</status></expense>", null);
    }
}
