package com.github.tosdan.beta.utils.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletRequestAttribParams extends HttpServletRequestWrapper
{

	private boolean prioriryToAttribute;

	public HttpServletRequestAttribParams( HttpServletRequest request ) {
		super( request );
		prioriryToAttribute = false;
	}
	
	public void setPrioriryToAttribute( boolean prioriryToAttribute ) {
		this.prioriryToAttribute = prioriryToAttribute;
	}

	@Override
	public String getParameter(String name) {
		
		Object attribute = getAttribute(name);
		String retVal = super.getParameter(name);
		
		if (retVal == null || prioriryToAttribute) 
			retVal = (String) attribute;
		
		return retVal;
	}
	
}
