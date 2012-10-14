
package com.muleinaction.objectstore;

public class EhCacheObjectStoreTestCase extends AbstractObjectStoreTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "ehcache-object-store-config.xml";
    }
}
