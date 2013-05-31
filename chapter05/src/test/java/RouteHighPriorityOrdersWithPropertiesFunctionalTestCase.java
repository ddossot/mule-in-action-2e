import com.prancingdonkey.domain.Order;
import org.junit.Test;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class RouteHighPriorityOrdersWithPropertiesFunctionalTestCase  extends FunctionalTestCase {

    CountDownLatch latch;

    @Override
    protected String getConfigResources() {
        return "src/main/app/routeHighPriorityOrdersOfTheCorrectType.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        latch = new CountDownLatch(2);

        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                latch.countDown();
            }
        });
    }

    @Test
    public void testCanRouteOrders() throws Exception {
        Order order = new Order();
        order.setPriority("HIGH");
        muleContext.getClient().dispatch("jms://topic:orders", order, null);

        assertTrue(latch.await(15, TimeUnit.SECONDS));
    }
}
