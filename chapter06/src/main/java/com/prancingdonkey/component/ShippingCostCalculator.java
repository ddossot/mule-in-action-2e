package com.prancingdonkey.component;

import org.mule.api.MuleEventContext;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.EndpointException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;

//<start id="lis_06_component-lifecycle"/>
public class ShippingCostCalculator 
  implements Initialisable, Callable {
	boolean initialized = false;
	EndpointBuilder errorProcessorChannelBuilder;
	OutboundEndpoint errorProcessorChannel;
	
	public void setErrorProcessorChannel(
	        EndpointBuilder errorProcessorChannelBuilder) {//<co id="lis_06_component-lifecycle-1_co"/> 
	    this.errorProcessorChannelBuilder = 
	    		errorProcessorChannelBuilder;
	}
	
	public void initialise() throws InitialisationException {
	    if (initialized) {//<co id="lis_06_component-lifecycle-2_co"/> 
	        return;
	    }
	    try {
	        errorProcessorChannel = errorProcessorChannelBuilder//<co id="lis_06_component-lifecycle-3_co"/> 
	                .buildOutboundEndpoint();
	        initialized = true;
	    } catch (final EndpointException ee) {
	        throw new InitialisationException(ee, this);
	    }
	}

	/* Other logic here */
	
// <end id="lis_06_component-lifecycle"/>
	
	public Object onCall(MuleEventContext eventContext)
			throws Exception {
		return "";
	}
}

