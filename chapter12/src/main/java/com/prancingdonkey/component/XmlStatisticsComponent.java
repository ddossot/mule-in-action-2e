package com.prancingdonkey.component;

import java.io.StringWriter;
import java.util.Collections;

import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.lifecycle.Callable;
import org.mule.management.stats.AllStatistics;
import org.mule.management.stats.FlowConstructStatistics;
import org.mule.management.stats.printers.XMLPrinter;
import org.mule.transport.NullPayload;

/**
 * Returns all the statistics of a Mule instance as a string.
 */
public class XmlStatisticsComponent implements Callable {
    public Object onCall(final MuleEventContext eventContext) throws Exception {
        final StringWriter xmlStatisticsWriter = new StringWriter();

        final Object payload = eventContext.getMessage().getPayload();

        final MuleContext muleContext = eventContext.getMuleContext();

        if (payload instanceof NullPayload) {
            // <start id="lis_12_context_statistics"/>
            final AllStatistics allStatistics = muleContext.getStatistics();

            allStatistics.logSummary(new XMLPrinter(xmlStatisticsWriter));
            // <end id="lis_12_context_statistics"/>
        } else {
            final String flowName = payload.toString();

            // <start id="lis_12_registry_flow_statistics"/>
            final FlowConstruct flow = muleContext.getRegistry()
                    .lookupFlowConstruct(flowName);

            FlowConstructStatistics flowStatistics = flow.getStatistics();
            new XMLPrinter(xmlStatisticsWriter)
                    .print(Collections.singleton(flowStatistics));
            // <end id="lis_12_registry_flow_statistics"/>
        }

        return xmlStatisticsWriter.toString();
    }

}
