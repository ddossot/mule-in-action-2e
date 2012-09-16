
package com.prancingdonkey.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;

import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.xml.sax.InputSource;

public class XmlStatisticsComponentTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "xml-statistics-config.xml";
    }

    @Test
    public void testRendersAllXmlStatistics() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final String xmlStatistics = muleClient.send("vm://XmlStats.IN", null, null).getPayloadAsString();

        muleClient.dispose();

        assertNotNull(xmlStatistics);

        assertEquals(
            "3",
            XPathFactory.newInstance()
                .newXPath()
                .evaluate("count(/Components/Service)", new InputSource(new StringReader(xmlStatistics))));

    }

    @Test
    public void testRendersOneFlowXmlStatistics() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final String xmlStatistics = muleClient.send("vm://XmlStats.IN", "Emailer", null)
            .getPayloadAsString();

        muleClient.dispose();

        assertNotNull(xmlStatistics);

        assertEquals(
            "1",
            XPathFactory.newInstance()
                .newXPath()
                .evaluate("count(/Components/Service)", new InputSource(new StringReader(xmlStatistics))));
    }
}
