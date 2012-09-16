
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.util.Collections;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;

public class MessageAttachmentsTestCase extends FunctionalTestCase
{
    private static final String TEST_PAYLOAD = "ignored";
    private static final String TEST_USAGE_REPORT_PDF = "fake-usage-report.pdf";
    private static final String TEST_ORDER_PDF = "fake-order.pdf";

    @Override
    protected String getConfigResources()
    {
        return "message-attachments.xml";
    }

    @Test
    public void attachPdfUsageReport() throws Exception
    {
        final String usageReportPdfFilePath = getPdfResourceUrl(TEST_USAGE_REPORT_PDF).getFile();

        //<start id="lis_12_mule_client-1"/>
        MuleClient muleClient = muleContext.getClient();

        MuleMessage result = muleClient.send("vm://add-attachments.in",
            TEST_PAYLOAD,
            Collections.<String,Object>singletonMap(
                "pdfFilePath",
                usageReportPdfFilePath));

        assertThat(
            result.getInboundAttachmentNames().contains(TEST_USAGE_REPORT_PDF),
            is(true));
        //<end id="lis_12_mule_client-1"/>
    }
    
    @Test
    public void processAttachments() throws Exception
    {
        MuleMessage message = new DefaultMuleMessage("fake email content",
            muleContext);
        File orderFile = new File(
            getPdfResourceUrl(TEST_ORDER_PDF).toURI());
        message.addOutboundAttachment("1-" + orderFile.getName(),
            orderFile, "application/pdf");
        message.addOutboundAttachment("2-" + orderFile.getName(),
            orderFile, "application/pdf");
        muleContext.getClient().dispatch("vm://email.orders.in",
            message);

        FunctionalTestComponent ftc = getFunctionalTestComponent("pdf-order-handler");
        while (ftc.getReceivedMessagesCount() < 2)
        {
            Thread.yield();
            Thread.sleep(100L);
        }

        assertThat(
            ftc.getReceivedMessage(1) instanceof javax.activation.DataHandler,
            is(true));
        
        assertThat(
            ftc.getReceivedMessage(2) instanceof javax.activation.DataHandler,
            is(true));
    }

    private static URL getPdfResourceUrl(String pdfResourceName)
    {
        return Thread.currentThread()
            .getContextClassLoader()
            .getResource(pdfResourceName);
    }
}
