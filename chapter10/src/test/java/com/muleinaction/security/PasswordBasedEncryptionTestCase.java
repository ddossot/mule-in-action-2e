package com.muleinaction.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PasswordBasedEncryptionTestCase extends FunctionalTestCase 
{

	private static String DECRYPTED_MESSAGE = "test";

	@Override
	protected String getConfigResources() 
	{
		return "password-based-encryption.xml";
	}

	@Test
	public void testEndpointAuthenticated() throws Exception 
	{
		MuleClient client = new MuleClient(muleContext);

		client.dispatch("jms:/messages.in", DECRYPTED_MESSAGE, null);
		MuleMessage result = client.request("jms:/messages.out", 15000);
		assertThat(result.getPayload(),
				is(not(instanceOf(ExceptionPayload.class))));
		assertThat(result.getPayloadAsString(), equalTo(DECRYPTED_MESSAGE));
	}

}
