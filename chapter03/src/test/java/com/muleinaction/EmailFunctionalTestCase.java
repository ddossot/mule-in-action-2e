
package com.muleinaction;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.email.ImapConnector;
import org.mule.transport.email.SmtpConnector;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class EmailFunctionalTestCase extends FunctionalTestCase
{
    private static GreenMail smtpServer;
    private static GreenMail imapServer;

    @Override
    protected String getConfigResources()
    {
        return "src/test/resources/email-config.xml";
    }

    @BeforeClass
    public static void startServers() throws Exception
    {
        final ServerSetup smtpSetup = new ServerSetup(2525, null, SmtpConnector.SMTP);
        smtpServer = new GreenMail(smtpSetup);
        smtpServer.start();

        final ServerSetup imapSetup = new ServerSetup(2626, null, ImapConnector.IMAP);
        imapServer = new GreenMail(imapSetup);
        imapServer.start();
        imapServer.getManagers()
            .getUserManager()
            .createUser("cooling@prancingdonkey.com", "cooling", "password");

    }

    @Test
    public void testCanGetEmail() throws Exception
    {
        // TODO implement
    }
}
