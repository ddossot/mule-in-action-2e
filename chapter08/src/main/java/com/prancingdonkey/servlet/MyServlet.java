package com.prancingdonkey.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.client.MuleClient;
import org.mule.api.config.MuleProperties;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = -1975294495629760037L;

	MuleContext muleContext;
	
	MuleClient client;
	
	@Override
	public void init() throws ServletException {
		try {
			initMule();
		} catch (MuleException e) {
			throw(new ServletException(e));
		}
    }
	
	public void initMule() throws MuleException {

//<start id="lis_08_mule-context-webapp"/>
muleContext = (MuleContext) getServletContext()
	.getAttribute(MuleProperties.MULE_CONTEXT_PROPERTY);

client = muleContext.getClient();
//<end id="lis_08_mule-context-webapp"/>
	
	}

}
