package com.muleinaction.security;

public class LdapDelegateTestCase extends AbstractConfigurationLoaderTestCase 
{

	@Override
	protected String getConfigResources() 
	{
		return "ldap-delegate.xml";
	}

}
