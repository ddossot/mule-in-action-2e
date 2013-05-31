import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.email.ImapConnector;
import org.mule.util.FileUtils;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

import static junit.framework.Assert.assertNotNull;

public class WildcardFunctionalTestCase extends FunctionalTestCase {

    private static GreenMail imapServer;

    @Override
    protected String getConfigResources() {
        return "src/main/app/wildcard.xml";
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


    @Test
    public void testCanDoWildcardRouting() throws Exception {
        MuleMessage response = muleContext.getClient().request("jms://topic:cooling.alerts",15000);
        assertNotNull(response);
    }
}
