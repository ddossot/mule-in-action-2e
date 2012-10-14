package com.prancingdonkey.objectstore;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.mule.api.store.ListableObjectStore;
import org.mule.api.store.ObjectDoesNotExistException;
import org.mule.api.store.ObjectStoreException;
import org.mule.util.store.AbstractObjectStore;

@SuppressWarnings("deprecation")
//<start id="lis_12_ehcache_object_store"/>
public class EhCacheObjectStore<T extends Serializable>
        extends AbstractObjectStore<T>
        implements ListableObjectStore<T>
{
    private Ehcache cache;

    public void setCache(final Ehcache cache)
    {
        this.cache = cache;
    }

    public boolean isPersistent()
    {
        return cache.getCacheConfiguration().isDiskPersistent();
    }

    public void open() throws ObjectStoreException
    {
        // NOOP
    }

    public void close() throws ObjectStoreException
    {
     // NOOP
    }

    @SuppressWarnings("unchecked")
    public List<Serializable> allKeys()
                    throws ObjectStoreException
    {
        return cache.getKeys();
    }

    @Override
    protected boolean doContains(Serializable key)
                    throws ObjectStoreException
    {
        return cache.isKeyInCache(key);
    }

    @Override
    protected void doStore(Serializable key, T value)
                    throws ObjectStoreException
    {
        cache.put(new Element(key, value));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T doRetrieve(Serializable key)
                    throws ObjectStoreException
    {
        Element element = cache.get(key);
        return element == null ? null : (T) element.getValue();
    }

    @Override
    protected T doRemove(Serializable key)
                    throws ObjectStoreException
    {
        T removedValue = doRetrieve(key);
        if (removedValue == null) {
            throw new ObjectDoesNotExistException();
        }
        boolean removed = cache.remove(key);
        if (!removed) {
            throw new ObjectDoesNotExistException();
        }
        return removedValue;
    }
}
//<end id="lis_12_ehcache_object_store"/>