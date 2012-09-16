import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;


public class ExpenseReportsCallbackFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/expenseReportsCallback.xml";
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testCanSubmitExpenseReportWhenMimeTypeIsXLS() throws Exception {

        Map properties = new HashMap();
        properties.put("mimeType","application/vnd.ms-excel");
        MuleClient client = muleContext.getClient();
        MuleMessage result = client.send("http://localhost:8081/expenses", "Foo", properties);
        Assert.assertNotNull(result);

        Thread.sleep(10000);


    }
}
