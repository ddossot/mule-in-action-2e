
package com.prancingdonkey.component;

import org.apache.commons.codec.digest.DigestUtils;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.transport.file.FileConnector;

public class Md5FileHasher implements Callable
{
    private String sourceFolder;

    private String fileConnectorName;

    public void setSourceFolder(final String sourceFolder)
    {
        this.sourceFolder = sourceFolder.replace('\\', '/');
    }

    public void setFileConnector(final FileConnector fileConnector)
    {
        this.fileConnectorName = fileConnector.getName();
    }

    public Object onCall(final MuleEventContext eventContext) throws Exception
    {

        final String fileName = eventContext.getMessageAsString();
        final String endpointUri = "file://" + sourceFolder + "/" + fileName + "?connector="
                                   + fileConnectorName;

        MuleMessage requestedFileMessage = eventContext.getMuleContext().getClient().request(endpointUri, 0L);

        return requestedFileMessage != null
                                           ? DigestUtils.md5Hex(requestedFileMessage.getPayloadAsBytes())
                                           : null;
    }
}
