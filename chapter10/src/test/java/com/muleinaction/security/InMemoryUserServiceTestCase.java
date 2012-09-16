package com.muleinaction.security;

public class InMemoryUserServiceTestCase extends AbstractConfigurationLoaderTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "in-memory-user-service.xml";
	}

}
