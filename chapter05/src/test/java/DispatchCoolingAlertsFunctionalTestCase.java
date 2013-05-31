import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.email.ImapConnector;
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

import static junit.framework.Assert.assertNotNull;


public class DispatchCoolingAlertsFunctionalTestCase extends FunctionalTestCase {

    private static GreenMail imapServer;

    CountDownLatch latch;

    @Override
    protected String getConfigResources() {
        return "src/main/app/dispatchCoolingAlerts.xml";
    }

    @BeforeClass
    public static void startServers() throws Exception {

        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();

        new File("./data/cooling/reports").mkdirs();


        final ServerSetup imapSetup = new ServerSetup(2626, "127.0.0.1", ImapConnector.IMAP);
        imapServer = new GreenMail(imapSetup);
        imapServer.start();
        GreenMailUser user = imapServer.getManagers()
                .getUserManager()
                .createUser("cooling@prancingdonkey.com", "cooling", "password");

        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));

        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText("SEVERE");

        message.setRecipient(Message.RecipientType.TO, new InternetAddress("cooling@prancingdonkey.com"));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);

        message.setContent(multipart);

        user.deliver(message);
    }

    @AfterClass
    public static void stopServers() throws Exception {
        imapServer.stop();
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        latch = new CountDownLatch(1);
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
    public void testCanDispatchCoolingAlerts() throws Exception {
        MuleMessage response =  muleContext.getClient().request("jms://topic:cooling.alerts", 15000);
        assertNotNull(response);
        // ToDo test for other condition
    }
}


