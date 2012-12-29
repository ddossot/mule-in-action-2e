package com.muleinaction.security;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.NullPayload;

public class HttpsClientSimpleTestCase extends AbstractConfigurationLoaderTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "https-client-simple.xml";
	}
	
	
	@Test
	public void testEndpointAuthenticated() throws Exception 
	{
		MuleClient client = new MuleClient(muleContext);

		MuleMessage result = client.send("vm://getGoogle.in", null, null);
		assertThat(result.getPayload(),
				is(not(instanceOf(ExceptionPayload.class))));
		assertThat(result.getPayload(),
				is(not(instanceOf(NullPayload.class))));
	}

}
