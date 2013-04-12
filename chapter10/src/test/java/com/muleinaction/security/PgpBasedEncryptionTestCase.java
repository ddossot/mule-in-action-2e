package com.muleinaction.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.springframework.util.ResourceUtils;

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
		MuleClient client = muleContext.getClient();

		String decryptedMessage = FileUtils.readFileToString(ResourceUtils.getFile(this.getClass().getResource("/test.txt")));
        String encryptedMessage = FileUtils.readFileToString(ResourceUtils.getFile(this.getClass().getResource("/test.txt.asc")));
        
        Map<String, Object> messageProperties = new HashMap<String, Object>();
        messageProperties.put("MULE_USER","Mule in Action <john.demic@gmail.com>");
        
		client.dispatch("jms:/messages.in", encryptedMessage, messageProperties);
		MuleMessage result = client.request("jms:/messages.out", 15000);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getPayload(), is(not(instanceOf(ExceptionPayload.class))));
		assertThat(result.getPayloadAsString(), equalTo(decryptedMessage));
	}

}
