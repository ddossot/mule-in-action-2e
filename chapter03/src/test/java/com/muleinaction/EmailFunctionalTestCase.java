package com.muleinaction;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Before;
import org.junit.Test;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.email.ImapConnector;
import org.mule.transport.email.SmtpConnector;

public class EmailFunctionalTestCase extends FunctionalTestCase {

    private GreenMail smtpServer;
    private GreenMail imapServer;

    @Override
    protected String getConfigResources() {
        return "src/test/resources/email-config.xml";
    }

    @Override
    protected void suitePreSetUp() throws Exception {
        ServerSetup smtpSetup = new ServerSetup(2525, null, SmtpConnector.SMTP);
        smtpServer = new GreenMail(smtpSetup);
        smtpServer.start();

        ServerSetup imapSetup = new ServerSetup(2626, null, ImapConnector.IMAP);
        imapServer = new GreenMail(imapSetup);
        imapServer.start();
        imapServer.getManagers().getUserManager()
                .createUser("cooling@prancingdonkey.com", "cooling", "password");

    }


    @Test
    public void testCanGetEmail() throws Exception {

    }
}
