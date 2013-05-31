package com.muleinaction;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FTPFunctionalTestCase extends FunctionalTestCase {

    private FakeFtpServer fakeFtpServer;

    private CountDownLatch getFilesLatch;
    private CountDownLatch putFilesLatch;

    @Override
    protected String getConfigResources() {
        return "src/main/app/ftp-config.xml";
    }

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/sales/statistics").mkdirs();
        new File("./data/in").mkdirs();
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        startServer();
        getFilesLatch = new CountDownLatch(1);
        putFilesLatch = new CountDownLatch(1);

        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("retrieveSalesStatistics".equals(notification.getResourceIdentifier())
                        && "end dispatch".equals(notification.getActionName())) {
                    getFilesLatch.countDown();
                }
            }
        });

        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("ftpProductCatalog".equals(notification.getResourceIdentifier())
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
    public void testCanGetFiles() throws Exception {
        assertEquals(0, FileUtils.listFiles(new File("./data/sales/statistics"), new String[]{"dat"}, false).size());
        assertTrue(getFilesLatch.await(15, TimeUnit.SECONDS));
        assertEquals(1, FileUtils.listFiles(new File("./data/sales/statistics"), new String[]{"dat"}, false).size());
    }

    @Test
    public void testCanPutFiles() throws Exception {
        FileUtils.writeStringToFile(new File("./data/in/foo.txt"),"foo");
        assertTrue(putFilesLatch.await(15, TimeUnit.SECONDS));
    }


    void startServer() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(9879);
        fakeFtpServer.addUserAccount(new UserAccount("joe", "123456", "/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data/prancingdonkey/catalog"));
        fileSystem.add(new FileEntry("/ftp/incoming/file1.txt", "MULEINACTION"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.start();
    }

    void stopServer() {
        fakeFtpServer.stop();
    }

}
