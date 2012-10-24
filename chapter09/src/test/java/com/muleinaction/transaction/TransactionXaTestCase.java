package com.muleinaction.transaction;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransactionXaTestCase extends FunctionalTestCase {
	JdbcTemplate templateOperational;

	JdbcTemplate templateWarehouse;

	MuleClient muleClient;

	@Override
	protected String getConfigResources() {
		return "transaction/transaction-xa.xml,transaction/transaction-xa-spring.xml";
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		templateOperational = (JdbcTemplate) muleContext.getRegistry().get(
				"operationalTemplate");
		templateWarehouse = (JdbcTemplate) muleContext.getRegistry().get(
				"warehouseTemplate");
		muleClient = new MuleClient(muleContext);
	}

	protected void emptyOrCreateTables() throws Exception {
		try {
			templateOperational.execute("DELETE FROM BILLING_STATS");
		} catch (Exception e) {
			templateOperational
					.execute("CREATE TABLE BILLING_STATS (id BIGINT NOT NULL, stat VARCHAR(255))");
		}

		try {
			templateWarehouse.execute("DELETE FROM BILLING_STATS");
		} catch (Exception e) {
			templateWarehouse
					.execute("CREATE TABLE BILLING_STATS (id BIGINT NOT NULL, stat VARCHAR(255))");
		}
	}

	@Test
	public void testMessageConsumedTransactionally() throws Exception {
		emptyOrCreateTables();

		muleClient.dispatch("jms://billingData", "STATUS: OK", null);
		TimeUnit.SECONDS.sleep(5);

		int operationalSize = templateOperational.queryForList(
				"SELECT * FROM BILLING_STATS").size();
		assertThat(operationalSize, is(1));

		int warehouseSize = templateWarehouse.queryForList(
				"SELECT * FROM BILLING_STATS").size();
		assertThat(warehouseSize, is(1));
	}

}
