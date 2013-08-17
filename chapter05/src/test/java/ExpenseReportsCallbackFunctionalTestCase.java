import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;


public class ExpenseReportsCallbackFunctionalTestCase extends FunctionalTestCase {


    CountDownLatch latch = new CountDownLatch(1);
    @Override
    protected String getConfigResources() {
        return "src/main/app/expenseReportsCallback.xml";
    }


    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        latch = new CountDownLatch(1);
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/expenses/rejected").mkdirs();
        new File("./data/expenses/status").mkdirs();

        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("expenseReportCallback".equals(notification.getResourceIdentifier())
                        && "end dispatch".equals(notification.getActionName())) {
                    latch.countDown();
                }
            }
        });
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCannotSubmitExpenseReportWithIncorrectMIMEType() throws Exception {
        assertEquals(0,FileUtils.listFiles(new File("./data/expenses/rejected"), new String[]{"xls"}, false).size());
        Map properties = new HashMap();
        properties.put("Content-Type","application/foo");
        MuleClient client = muleContext.getClient();
        MuleMessage result = client.send("http://localhost:8081/expenses", "Foo", properties);
        assertNotNull(result);
        assertTrue(latch.await(15, TimeUnit.SECONDS));
        assertEquals(1,FileUtils.listFiles(new File("./data/expenses/rejected"), new String[]{"xls"}, false).size());
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCanSubmitExpenseReportWithCorrectMIMEType() throws Exception {
        assertEquals(0,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xls"}, false).size());
        Map properties = new HashMap();
        properties.put("Content-Type","application/vnd.ms-excel");
        MuleClient client = muleContext.getClient();
        MuleMessage result = client.send("http://localhost:8081/expenses", "Foo", properties);
        assertNotNull(result);
        assertTrue(latch.await(15, TimeUnit.SECONDS));
        assertEquals(1,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xls"}, false).size());
    }
}
