package com.muleinaction.security;


public class HttpsServerTestCase extends AbstractConfigurationLoaderTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "https-server.xml";
	}
	
}
