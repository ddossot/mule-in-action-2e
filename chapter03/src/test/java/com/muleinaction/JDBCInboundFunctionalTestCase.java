package com.muleinaction;

import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Map;

import static junit.framework.Assert.*;

public class JDBCInboundFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/jdbc-inbound-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        createDatabase();
    }

    @Test
    public void testCanPollForStuckOrders() throws Exception {
        MuleMessage results = muleContext.getClient().request("jms://topic:orders.status.stuck",15000);
        assertNotNull(results);
        assertNotNull(results.getPayload());
        assertEquals(1, ((Map)results.getPayload()).keySet().size());
    }


    private void createDatabase() {
        DataSource dataSource = (DataSource) muleContext.getRegistry().lookupObject("dataSource");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        try {
            template.update("DROP TABLE orders");
        } catch (BadSqlGrammarException ex) {
            logger.error(ex);
        }
        template.update("CREATE TABLE orders " +
                "(id BIGINT NOT NULL, details VARCHAR(4096), timestamp TIMESTAMP)");

        Date now = new Date();
        template.update("INSERT INTO orders VALUES (0,?,?)",
                new Object[]{"foo", new Date(now.getTime() - (86400000 * 2))});
    }
}
