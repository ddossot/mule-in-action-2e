package com.muleinaction;

import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.config.MuleProperties;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

public class JettyTestCase extends FunctionalTestCase
{
	
	@Rule
	public DynamicPort jettyPort = new DynamicPort("jettyPort");
	
	@Rule
	public DynamicPort jettySslPort = new DynamicPort("jettySslPort");
	
    @Override
    protected String getConfigResources()
    {
        return "jetty.xml";
    }

    @Override
    protected Properties getStartUpProperties()
    {
        Properties startupProps = new Properties();
        startupProps.put(MuleProperties.APP_HOME_DIRECTORY_PROPERTY, "src/test/resources");
        return  startupProps;
    }
    
    @Test
    public void testConfigLoads() throws Exception
    {
    }
    
}
