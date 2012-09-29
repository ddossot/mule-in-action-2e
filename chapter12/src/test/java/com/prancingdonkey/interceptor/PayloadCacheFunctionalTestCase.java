package com.prancingdonkey.interceptor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
public class PayloadCacheFunctionalTestCase extends FunctionalTestCase {

    private String expectedHash;

    private String tempFileName;

    @Override
    protected String getConfigResources() {
        return "md5fh-cache-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        // prepare test file for MD5 File Hasher Service
        final String fileData = RandomStringUtils.randomAscii(100);

        final File tempFile = File.createTempFile("mia-", null);
        tempFile.deleteOnExit();

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(tempFile);
            fos.write(fileData.getBytes("US-ASCII"));
            fos.flush();
        } finally {
            fos.close();
        }

        expectedHash = DigestUtils.md5Hex(fileData);
        tempFileName = tempFile.getName();
    }

    @Test
    public void testMd5FileHasher() throws Exception {
        final MuleClient muleClient = new MuleClient(muleContext);

        assertEquals(expectedHash, muleClient.send("vm://Md5FileHasher.In",
                tempFileName, null).getPayload());

        // a pretty harsh way to demonstrate that the hasher component does not
        // hit the file transport anymore
        muleClient.getMuleContext().getRegistry().lookupConnector(
                "NonDeletingFileConnector").dispose();

        assertEquals(expectedHash, muleClient.send("vm://Md5FileHasher.In",
                tempFileName, null).getPayload());

        muleClient.dispose();
    }
}
