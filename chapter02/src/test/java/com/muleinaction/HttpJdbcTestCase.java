
package com.muleinaction;

public class HttpJdbcTestCase extends AbstractConfigurationLoaderTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "http-jdbc-flow.xml";
    }
}
