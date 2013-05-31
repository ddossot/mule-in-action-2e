package com.prancingdonkey;

import org.junit.Test;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;


public class CRMFunctionalTestCase extends FunctionalTestCase {

	@Override
	protected String getConfigResources() {		
		return "src/main/app/salesforce-config.xml";
	}

	@Test
	public void testCanCreateContact() throws Exception {
		MuleClient client = muleContext.getClient();
		com.prancingdonkey.model.Customer customer = new com.prancingdonkey.model.Customer();
		customer.setFirstName("Mule");
		customer.setLastName("InAction");

		client.dispatch("vm://crm.contact.create", customer,null);
	}


    @Test
    public void testCanGetContact() throws Exception {

        // ToDo Re-enable
//        MuleClient client = muleContext.getClient();
//
//        MuleMessage result = client.send("vm://crm.contact.get", "Mule InAction",null);
//        assertNotNull(result);
//        Customer customer = new Customer();
//        customer.setFirstName("Mule");
//        customer.setLastName("InAction");
//
//        assertEquals(customer, result.getPayload());

    }


}
