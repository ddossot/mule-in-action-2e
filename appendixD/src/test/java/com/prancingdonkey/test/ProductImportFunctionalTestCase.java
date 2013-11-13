package com.prancingdonkey.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

import com.prancingdonkey.product.event.ProductIngestCompleted;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.
	   EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sql.DataSource;

//<start id="lis_appf_test-case"/>
public class ProductImportFunctionalTestCase extends 
		FunctionalTestCase {

    private FakeFtpServer fakeFtpServer;//<co id="lis_appf_test-case-1"/>

    JdbcTemplate jdbcTemplate;
    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        startServer();
        jdbcTemplate = new JdbcTemplate(
                muleContext.getRegistry()
                        .lookupObject(DataSource.class));//<co id="lis_appf_test-case-5"/>
    }

    @Override
    protected String getConfigResources() {
        return "prancing-donkey-product-service.xml";
    }

    @Test
    public void testCanLoadAndQueryForProducts() throws Exception {

        MuleMessage completedEvent = muleContext.getClient()
                .request("jms://topic:events.product",
                        15000);
        assertNotNull(completedEvent);//<co id="lis_appf_test-case-6"/>
        assertEquals(2,
                jdbcTemplate.queryForInt(
                        "select count(*) from product"));//<co id="lis_appf_test-case-7"/>
        Map<String, Object> properties =
                new HashMap<String, Object>();
        properties.put("http.method", "GET");
        MuleMessage products =
                muleContext.getClient()
                        .send("http://localhost:8081/products", "",
                                properties); //<co id="lis_appf_test-case-8"/>
        assertNotNull(products);
        String expected = FileUtils.readFileToString(
                new File("src/test/files/expectedProducts.json"));
        assertEquals(expected, products.getPayloadAsString());
    }

    void startServer() throws IOException {
      fakeFtpServer = new FakeFtpServer();
      fakeFtpServer.setServerControlPort(9879);
      fakeFtpServer.addUserAccount(
                new UserAccount("foo", "foo", "/"));

      FileSystem fileSystem = new UnixFakeFileSystem();
      fileSystem.add(new FileEntry("/products/file1.xml",
         FileUtils.readFileToString(
            new File(
                "src/test/files/suppliers/supplier1/products.xml"))));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.start();
    }
}

//<end id="lis_appf_test-case"/>

