import com.prancingdonkey.domain.Brew;
import com.prancingdonkey.domain.Order;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import static junit.framework.Assert.assertNotNull;


public class ExpenseReportCallbackWithDLQTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "./src/main/app/expenseReportsCallbackWithDLQ.xml";
    }

    @Test
    public void testCanRouteOrders() throws Exception {
        muleContext.getClient().dispatch("vm://orders",new Order(),null);
        MuleMessage response = muleContext.getClient().request("jms://brews.orders", 15000);
        assertNotNull(response);
    }

    @Test
    public void testCanRouteBrews() throws Exception {
        muleContext.getClient().dispatch("vm://orders",new Brew(),null);
        MuleMessage response = muleContext.getClient().request("jms://brews.definitions", 15000);
        assertNotNull(response);
    }

    @Test
    public void testCanRouteToDLQ() throws Exception {
        muleContext.getClient().dispatch("vm://orders","Foo.",null);
        MuleMessage response = muleContext.getClient().request("jms://DLQ", 15000);
        assertNotNull(response);
    }
}
