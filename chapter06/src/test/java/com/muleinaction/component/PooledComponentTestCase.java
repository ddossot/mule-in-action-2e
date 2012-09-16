
package com.muleinaction.component;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PooledComponentTestCase extends FunctionalTestCase
{

	private String expectedHash;
	  
    private String tempFileName;

    @Before
    public void doSetUp() throws Exception 
    {
        // prepare test file for MD5 File Hasher Service
        final String fileData = RandomStringUtils.randomAscii(100);

        final File tempFile = File.createTempFile("mia-", null);
        tempFile.deleteOnExit();

        FileOutputStream fos = null;

        try 
        {
            fos = new FileOutputStream(tempFile);
            fos.write(fileData.getBytes("US-ASCII"));
            fos.flush();
        } finally {
            fos.close();
        }

        expectedHash = DigestUtils.md5Hex(fileData);
        tempFileName = tempFile.getName();
    }
    
    @Override
    protected String getConfigResources()
    {
        return "component/pooled-component.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://pooled-md5-service.in", tempFileName, null);

        assertThat(result.getPayloadAsString(), equalTo(expectedHash));
    }
    
}
