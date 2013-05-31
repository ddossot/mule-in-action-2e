import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class RouteHighPriorityOrdersWithXPathFunctionalTestCase extends FunctionalTestCase {

    CountDownLatch latch = new CountDownLatch(2);

    @Override
    protected String getConfigResources() {
        return "src/main/app/routeHighPriorityOrdersWithXPath.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                latch.countDown();
            }
        });
    }


    @Test
    public void testCanRoute() throws Exception {
        MuleMessage request = new DefaultMuleMessage(FileUtils.readFileToString(
                new File("src/test/resources/orders.xml")),muleContext);

        muleContext.getClient().dispatch("jms://topic:orders", request);

        assertTrue(latch.await(15, TimeUnit.SECONDS));


    }
}
