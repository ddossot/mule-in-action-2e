package com.muleinaction.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class TransactionReliabilityTestCase extends FunctionalTestCase {
	
	MuleClient muleClient;

	@Override
	protected String getConfigResources() {
		return "transaction/transaction-reliability.xml";
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		muleClient = new MuleClient(muleContext);
	}

	@Test
	public void testConfigLoads() throws Exception {
		MuleMessage result = muleClient.send("http://127.0.0.1:8081/inbound", null, null);
		assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        // Let the business logic finish
        TimeUnit.SECONDS.sleep(3);
	}

}
