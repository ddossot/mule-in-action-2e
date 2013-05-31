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

import static org.junit.Assert.*;


public class IdempotentOrderRouterFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/idempotentOrderRouter.xml";
    }

    CountDownLatch latch = new CountDownLatch(2);

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
    public void testCannotProcessMultipleMessages() throws Exception {
        MuleMessage request = new DefaultMuleMessage(FileUtils.readFileToString(
                new File("src/test/resources/orders.xml")),muleContext);

        muleContext.getClient().dispatch("jms://topic:orders", request);

        assertTrue(latch.await(15, TimeUnit.SECONDS));


    }

}
