package com.muleinaction;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleEventContext;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FileFunctionalTestCase extends FunctionalTestCase {

    CountDownLatch copyExpenseReportLatch;
    CountDownLatch callbackLatch;

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/expenses/1/in").mkdirs();
        new File("./data/expenses/out").mkdirs();
        new File("./data/expenses/2/in").mkdirs();
        new File("./data/expenses/status").mkdirs();
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        copyExpenseReportLatch = new CountDownLatch(1);
        callbackLatch = new CountDownLatch(1);
        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("copyExpenseReports".equals(notification.getResourceIdentifier())
                        && "receive".equals(notification.getActionName())) {
                    copyExpenseReportLatch.countDown();
                }

                if ("expenseReportCallback".equals(notification.getResourceIdentifier())
                        && "end dispatch".equals(notification.getActionName())) {
                    callbackLatch.countDown();
                }
            }
        });
    }

    @Override
    protected String getConfigResources() {
        return "src/main/app/file-config.xml";
    }

    @Test
    public void testCanCopyExpenseReports() throws Exception {
        FileUtils.writeStringToFile(new File("./data/expenses/1/in", "expenses.xls"), "a crazy bar tab");
        Collection files = FileUtils.listFiles(new File("./data/expenses"), new String[]{"xls"}, true);
        assertTrue(copyExpenseReportLatch.await(15000, TimeUnit.SECONDS));
        assertEquals(1, files.size());
    }

    @Test
    public void testCanPostExpenseReportsFromADirectory() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(MuleEventContext context, Object component)
                    throws Exception
            {
                latch.countDown();
            }
        };

        getFunctionalTestComponent("dummyHttpServer").setEventCallback(callback);

        FileUtils.writeStringToFile(new File("./data/expenses/2/in/foo.xls"),"Foo");
        assertTrue(latch.await(15000,TimeUnit.SECONDS));
    }

    @Test
    public void testCanPerformCallback() throws Exception {
        assertEquals(0,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xml"}, false).size());
        muleContext.getClient().dispatch("http://localhost:8080/expenseReportCallback","FOO",null);
        assertTrue(callbackLatch.await(15000,TimeUnit.MILLISECONDS));
        assertEquals(1,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xml"}, false).size());

    }


}
