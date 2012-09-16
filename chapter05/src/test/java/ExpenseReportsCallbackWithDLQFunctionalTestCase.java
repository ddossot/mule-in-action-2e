import com.prancingdonkey.domain.Brew;
import com.prancingdonkey.domain.Order;
import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;


public class ExpenseReportsCallbackWithDLQFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/expenseReportsCallbackWithDLQ.xml";
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCannotSubmitNonBrew() throws Exception {
        MuleClient client = muleContext.getClient();
        client.dispatch("vm://orders", "Foo", null);
        MuleMessage result = client.request("jms://DLQ", 5000);
        Assert.assertNotNull(result);
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCannotSubmitOrder() throws Exception {
        MuleClient client = muleContext.getClient();
        client.dispatch("vm://orders", new Order(), null);
        MuleMessage result = client.request("jms://brews.orders", 5000);
        Assert.assertNotNull(result);
    }
}
