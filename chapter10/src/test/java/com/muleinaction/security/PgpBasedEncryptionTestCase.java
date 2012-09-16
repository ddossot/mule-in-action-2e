package com.muleinaction.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class PgpBasedEncryptionTestCase extends FunctionalTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "pgp-based-decryption.xml";
	}

	@Test
	public void testEndpointAuthenticated() throws Exception 
	{
		MuleClient client = new MuleClient(muleContext);
		
		String decryptedMessage = FileUtils.readFileToString(new File("src/test/resources/test.txt"));
        String encryptedMessage = FileUtils.readFileToString(new File("src/test/resources/test.txt.asc"));
        
        Map<String, String> messageProperties = new HashMap<String, String>();
        messageProperties.put("MULE_USER","Mule in Action <john.demic@gmail.com>");
        
		client.dispatch("jms:/messages.in", encryptedMessage, messageProperties);
		MuleMessage result = client.request("jms:/messages.out", 15000);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
		assertThat(result.getPayloadAsString(), equalTo(decryptedMessage));
	}

}
