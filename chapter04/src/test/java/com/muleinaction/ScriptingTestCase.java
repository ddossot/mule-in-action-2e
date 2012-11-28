
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.module.xml.util.XMLUtils;
import org.mule.tck.junit4.FunctionalTestCase;

public class ScriptingTestCase extends FunctionalTestCase
{

	private static final String INPUT_DIRECTORY = "./data";
	
	private static final String INPUT_FILE = INPUT_DIRECTORY + "/testFile";

    @Override
    protected String getConfigResources()
    {
        return "scripting.xml";
    }
    
    @Test
    public void testScripting() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        String lowercaseString = "hello";
        MuleMessage result = muleClient.send("vm://groovy-uppercase.in", lowercaseString, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayloadAsString(), is(lowercaseString.toUpperCase()));
    }
    
    @Test
    public void testExternalScripting() throws Exception
    {
    	createTempDirectory();
    	createAndPopulateTempFile();
    	
    	MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.request("jms://orders", 5000);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));

        String resultString = result.getPayloadAsString();
        Document resultDocument = XMLUtils.toDocument(resultString, muleContext);
        assertThat(resultDocument, is(notNullValue()));
    }
    
    private void createTempDirectory() throws Exception
    {
    	File tmpDir = new File(INPUT_DIRECTORY);
        tmpDir.delete();
        tmpDir.mkdir();
    }

    private void createAndPopulateTempFile() throws Exception
    {
        File target = new File(INPUT_FILE);

        Writer out = new FileWriter(target);
        out.write("409,1234,PENDING\n410,1234,PENDING\n411,1235,PENDING");
        out.flush();
        out.close();

        target.deleteOnExit();
    }

}
