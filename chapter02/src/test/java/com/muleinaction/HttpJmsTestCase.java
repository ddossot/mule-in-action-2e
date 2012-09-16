
package com.muleinaction;

public class HttpJmsTestCase extends AbstractConfigurationLoaderTestCase
{
    public HttpJmsTestCase()
    {
        super();
        setStartContext(false); // to prevent exceptions due to binding issue on api.prancingdonkey.com
    }

    @Override
    protected String getConfigResources()
    {
        return "http-jms-flow.xml";
    }
}
