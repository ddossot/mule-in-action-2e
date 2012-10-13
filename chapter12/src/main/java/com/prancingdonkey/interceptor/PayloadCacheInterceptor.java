
package com.prancingdonkey.interceptor;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.interceptor.Interceptor;
import org.mule.api.processor.MessageProcessor;

// <start id="lis_12_interceptor_cache"/>
public class PayloadCacheInterceptor implements Interceptor//<co id="lis_12_interceptor_cache-1"/>
{
    private MessageProcessor next;
    private Ehcache cache;

    public void setListener(MessageProcessor listener)//<co id="lis_12_interceptor_cache-5"/>
    {
        next = listener;
    }

    public void setCache(final Ehcache cache)//<co id="lis_12_interceptor_cache-6"/>
    {
        this.cache = cache;
    }

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        MuleMessage currentMessage = event.getMessage();
        Object key = currentMessage.getPayload();//<co id="lis_12_interceptor_cache-2"/>
        Element cachedElement = cache.get(key);

        if (cachedElement != null)//<co id="lis_12_interceptor_cache-3"/>
        {
            DefaultMuleMessage cachedMessage =
                new DefaultMuleMessage(cachedElement.getObjectValue(),
                                       currentMessage,
                                       event.getMuleContext());

            return new DefaultMuleEvent(cachedMessage, event);
        }

        // we don't synchronize so several threads can compete to fill
        // the cache for the same key: this is rare enough to be
        // acceptable
        MuleEvent result = next.process(event);//<co id="lis_12_interceptor_cache-4"/>
        cache.put(new Element(key, result.getMessage().getPayload()));
        return result;
    }
}
// <end id="lis_12_interceptor_cache"/>
