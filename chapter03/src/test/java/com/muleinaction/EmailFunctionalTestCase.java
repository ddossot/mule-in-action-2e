
package com.muleinaction;

import com.icegreen.greenmail.user.GreenMailUser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.EndpointMessageNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.email.ImapConnector;
import org.mule.transport.email.SmtpConnector;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.mule.util.FileUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class EmailFunctionalTestCase extends FunctionalTestCase {
    private static GreenMail smtpServer;
    private static GreenMail imapServer;

    private static String EMAIL_TEXT = "This is an alert from the cooling systems";

    private static CountDownLatch latch = new CountDownLatch(1);

    @Override
    protected String getConfigResources() {
        return "src/main/app/email-config.xml";
    }

    @BeforeClass
    public static void startServers() throws Exception {

        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();

        new File("./data/cooling/reports").mkdirs();


        final ServerSetup smtpSetup = new ServerSetup(2525, "127.0.0.1", SmtpConnector.SMTP);
        smtpServer = new GreenMail(smtpSetup);

        smtpServer.start();

        final ServerSetup imapSetup = new ServerSetup(2626, "127.0.0.1", ImapConnector.IMAP);
        imapServer = new GreenMail(imapSetup);
        imapServer.start();
        GreenMailUser user = imapServer.getManagers()
                .getUserManager()
                .createUser("cooling@prancingdonkey.com", "cooling", "password");

        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));

        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText(EMAIL_TEXT);

        message.setRecipient(Message.RecipientType.TO, new InternetAddress("cooling@prancingdonkey.com"));

        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource fileDataSource = new FileDataSource("./src/test/resources/cooling.report.pdf") {
            @Override
            public String getContentType() {
                return "application/octet-stream";
            }
        };
        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
        attachmentPart.setFileName("report");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        user.deliver(message);
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("sendExpenseReportEmails".equals(notification.getResourceIdentifier())
                        && "end dispatch".equals(notification.getActionName())) {
                    latch.countDown();
                }
            }
        });
    }

    @Test
    public void testCanReceiveEmail() throws Exception {
        assertEquals(0, FileUtils.listFiles(new File("./data/cooling/reports"), new String[]{"dat"}, false).size());
        MuleMessage coolingAlert = muleContext.getClient().request("jms://topic:cooling.alerts", 15000);
        assertNotNull(coolingAlert);
        assertNotNull(coolingAlert.getPayload());
        assertEquals(EMAIL_TEXT, coolingAlert.getPayloadAsString());
        assertEquals(1,FileUtils.listFiles(new File("./data/cooling/reports"), new String[]{"dat"}, false).size());
    }

    @Test
    public void testCanSendEmail() throws Exception {
        assertEquals(0,smtpServer.getReceivedMessages().length);
        muleContext.getClient().dispatch("jms://topic:expenses.status","Expense Report",null);
        assertTrue(latch.await(15, TimeUnit.SECONDS));
        assertEquals(1,smtpServer.getReceivedMessages().length);

    }
}
