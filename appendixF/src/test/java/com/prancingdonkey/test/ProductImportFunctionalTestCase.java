package com.prancingdonkey.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class ProductImportFunctionalTestCase extends FunctionalTestCase {
	
    private FakeFtpServer fakeFtpServer;
    private CountDownLatch parseFilesLatch;
    
    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./products").mkdirs();
        new File("./data/in").mkdirs();
    }
        
    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        startServer();
        parseFilesLatch = new CountDownLatch(1);
        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("pollProducts".equals(notification.getResourceIdentifier())
                        && "receive".equals(notification.getActionName())) {
                	parseFilesLatch.countDown();
                }
            }
        });
    }

	@Override
	protected String getConfigResources() {
		// TODO Auto-generated method stub
		return "src/main/app/prancing-donkey-product-service.xml";
	}
		
	@Test
	public void testCanLoadAndQueryForProducts() throws Exception {
        assertTrue(parseFilesLatch.await(15, TimeUnit.SECONDS));		
	}
	
	void startServer() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(9879);
        fakeFtpServer.addUserAccount(new UserAccount("foo", "foo", "/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new FileEntry("/products/file1.xml", 
        		FileUtils.readFileToString(new File("src/test/files/suppliers/supplier1/products.xml"))));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.start();
    }

    void stopServer() {
        fakeFtpServer.stop();
    }
    
	

}
