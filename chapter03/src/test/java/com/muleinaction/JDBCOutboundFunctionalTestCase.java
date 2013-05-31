package com.muleinaction;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.EndpointMessageNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class JDBCOutboundFunctionalTestCase extends FunctionalTestCase {

    private FakeFtpServer fakeFtpServer;

    private CountDownLatch putFilesLatch;

    private  JdbcTemplate template;


    @Override
    protected String getConfigResources() {
        return "src/main/app/jdbc-outbound-config.xml";
    }

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        startServer();
        putFilesLatch = new CountDownLatch(1);


        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {

                EndpointMessageNotification messageNotification = (EndpointMessageNotification) notification;

                if (messageNotification.getEndpoint().contains("ftp")
                        && "end dispatch".equals(notification.getActionName())) {
                    putFilesLatch.countDown();
                }
            }
        });
    }

    @Override
    protected void doTearDown() throws Exception {
        stopServer();
    }

    @Test
    public void testCanInsertProducts() throws Exception {
        FileUtils.copyFileToDirectory(new File("src/test/resources/product.csv"),new File("./data"));
        assertTrue(putFilesLatch.await(15, TimeUnit.SECONDS));

        assertEquals(2, template.queryForList("SELECT * FROM products").size());
    }

    void startServer() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(9879);
        fakeFtpServer.addUserAccount(new UserAccount("admin", "123456", "/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data/prancingdonkey/catalog"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.start();

        DataSource dataSource =  muleContext.getRegistry().lookupObject("dataSource");
        template = new JdbcTemplate(dataSource);

        createDatabase();
    }

    void stopServer() {
        fakeFtpServer.stop();
    }

    private void createDatabase() {

        try {
            template.update("DROP TABLE  products");
        } catch (BadSqlGrammarException ex) {
            logger.error(ex);
        }
        template.update("CREATE TABLE products " +
                "(id BIGINT NOT NULL, name VARCHAR(256), acv DOUBLE, cost DOUBLE, description VARCHAR(4096))");

    }
}
