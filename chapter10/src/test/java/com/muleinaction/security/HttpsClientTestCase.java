package com.muleinaction.security;


public class HttpsClientTestCase extends AbstractConfigurationLoaderTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "https-client.xml";
	}

}
