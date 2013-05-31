import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ForeachFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/foreach.xml";
    }

    CountDownLatch latch = new CountDownLatch(6);

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        // ToDo this needs two latches to count down high and low
        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                latch.countDown();
            }
        });
    }

    @Test
    public void testCanProcessMessagesInLoop() throws Exception {

        String orders = FileUtils.readFileToString(new File("src/test/resources/orders.xml"));
        muleContext.getClient().dispatch("jms://orders.audit", orders, null);

        assertTrue(latch.await(15, TimeUnit.SECONDS));
        Thread.sleep(15000);

    }
}
