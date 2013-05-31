import com.prancingdonkey.domain.Brew;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.MessageProcessorNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AcceptBrewDefinitionsFunctionalTestCase extends FunctionalTestCase {


    CountDownLatch callbackLatch;
    @Override
    protected String getConfigResources() {
        return "src/main/app/acceptBrewDefinitions.xml";
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
    public void testCanSubmitBrewDefinition() throws Exception {
        Brew brew = new Brew();
        muleContext.getClient().dispatch("jms://brews.definitions", brew, null);
        assertTrue(callbackLatch.await(15, TimeUnit.SECONDS));
    }
}

