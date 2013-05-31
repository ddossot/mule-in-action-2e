import com.prancingdonkey.domain.Order;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class NestedLogicalFunctionalTestCase extends FunctionalTestCase {

    CountDownLatch latch;
    @Override
    protected String getConfigResources() {
        return "src/main/app/nestedLogical.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        latch = new CountDownLatch(2);

        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                latch.countDown();

            }
        });
    }


    @Test
    public void testCanNestLogicalProcessors() throws Exception {
        Order order = new Order();
        order.setPriority("HIGH");
        muleContext.getClient().dispatch("jms://orders", order,null);
        Assert.assertTrue(latch.await(15, TimeUnit.SECONDS));
    }
}
