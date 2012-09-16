package com.muleinaction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.ObjectName;

import org.junit.Test;
import org.mule.context.notification.MuleContextNotification;
import org.mule.module.management.agent.JmxAgent;
import org.mule.module.management.agent.JmxServerNotificationAgent;
import org.mule.module.management.support.JmxSupport;
import org.mule.tck.junit4.FunctionalTestCase;

public class JmxListenerTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "jmx-listener-config.xml";
    }

    @Test
    public void testJmxListenerMBean() throws Exception {
        //<start id="lis_12_registry_lookupagent"/>
        JmxAgent jmxAgent = (JmxAgent) muleContext.getRegistry()
                .lookupAgent("jmx-agent");

        MBeanServer mBeanServer = jmxAgent.getMBeanServer();
        //<end id="lis_12_registry_lookupagent"/>

        //<start id="lis_12_context_configuration"/>
        String serverId = muleContext.getConfiguration().getId();

        ObjectName listenerObjectName = ObjectName
                .getInstance(JmxSupport.DEFAULT_JMX_DOMAIN_PREFIX + "."
                       + serverId + ":"
                       + JmxServerNotificationAgent.LISTENER_JMX_OBJECT_NAME);
        //<end id="lis_12_context_configuration"/>

        @SuppressWarnings("unchecked")
        final List<Notification> bootNotifications = (List<Notification>) mBeanServer
                .getAttribute(listenerObjectName, "NotificationsList");

        assertNotNull(bootNotifications);
        assertTrue(bootNotifications.size() > 0);
        assertTrue(bootNotifications.get(0).getSource() instanceof MuleContextNotification);
    }
}
