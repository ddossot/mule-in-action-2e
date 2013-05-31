package com.prancingdonkey;

import com.prancingdonkey.model.Address;
import com.prancingdonkey.model.Customer;
import com.prancingdonkey.model.Order;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;


public class DroolsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/drools-config.xml";
    }

    // ToDo something broke in 3.4 need to fix
    @Ignore
    @Test
    public void testCanEvaluateRules() throws Exception {

        Order order = new Order();

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Foo");
        customer.setTotalOrders(1200);
        customer.setTotalSpendForYear(new BigDecimal(50000));

        Address address = new Address();
        address.setAddress1("Foo Street");
        address.setCity("Brooklyn");
        address.setState("NY");

        customer.setAddress(address);

        customer.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1978"));

        order.setCustomer(customer);

        muleContext.getClient().dispatch("vm://order.processing.loyalty", order, null);
        MuleMessage response = muleContext.getClient().request("vm://loyalty",15000);
        Assert.assertNotNull(response);

        Order result = (Order) response.getPayload();
        Assert.assertEquals(2,result.getRewards().size());
    }
}
