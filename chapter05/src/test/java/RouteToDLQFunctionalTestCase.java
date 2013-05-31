import com.prancingdonkey.domain.Order;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;


public class RouteToDLQFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/routeToDLQ.xml";
    }

    @Test
    public void testCanRouteOnUnaccepted() throws Exception {
        muleContext.getClient().dispatch("jms://topic:orders", new Order(),null);
        MuleMessage result = muleContext.getClient().request("jms://DLQ",15000);
        Assert.assertNotNull(result);
    }
}
