import com.prancingdonkey.domain.LineItem;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.UUID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class AggregateLineItemsFunctionalTestCase extends FunctionalTestCase {

    CountDownLatch callbackLatch;

    @Override
    protected String getConfigResources() {
        return "src/main/app/aggregateLineItems.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        callbackLatch = new CountDownLatch(2);
        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                callbackLatch.countDown();
            }
        });
    }

    @Test
    public void testCanAggregateLineItems() throws Exception {

        String orderId = UUID.getUUID();
        for (int i=0; i<3; i++) {
            LineItem lineItem = new LineItem();
            Map properties = new HashMap();
            properties.put("MULE_CORRELATION_ID",orderId);
            properties.put("MULE_CORRELATION_GROUP_SIZE",3);
            MuleMessage message = new DefaultMuleMessage(lineItem,properties,muleContext);
            muleContext.getClient().dispatch("jms://lineitem.complete",message);
        }

        assertTrue(callbackLatch.await(15, TimeUnit.SECONDS));
    }
}
