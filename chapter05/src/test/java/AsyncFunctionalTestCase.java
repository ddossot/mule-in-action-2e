import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import static org.junit.Assert.*;


public class AsyncFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/async.xml";
    }

    @Test
    public void testCanProcessOrderAsynchronously() throws Exception {
        MuleMessage response = muleContext.getClient().send("http://localhost:8080/orders","order",null);
        assertNotNull(response);
        assertEquals("Order Received", response.getPayloadAsString());
        MuleMessage orderCompletedMessage = muleContext.getClient().request("jms://orders.completed",15000);
        assertNotNull(orderCompletedMessage);
    }
}
