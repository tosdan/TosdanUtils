package com.github.tosdan.utils.servlets;


import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import lime49.lockcrypt.WildcardURL;

/**
 * 
 * @author Daniele
 * @version 0.1.0-b2013-08-11
 */
public class ServletUtils
{
	private HttpServletRequest req;
	private WildcardURL wurl;
	private boolean validReferer;
	
	public ServletUtils(HttpServletRequest request) {
		setRequest(request);
	}
	
	private void setRequest(HttpServletRequest req) {
		this.req = req;
		validReferer = getReferer() != null;
		if (validReferer)
			wurl = new WildcardURL(getReferer());
	}
	
	public ServletUtils resetRequest(HttpServletRequest req) {
		setRequest(req);
		return this;
	}
	
	public String getCtxPath() {
		if (validReferer)
			return req.getContextPath();
		return null;
	}

	public String getReferer() {
		return req.getHeader("referer");
	}
	
	public String getPath() {
		if (validReferer)
			return wurl.getPath();
		return null;
	}
	
	public String getRequestedPage() {
		if (validReferer)
			return wurl.getFile();
		return null;
	}
	
	public Integer getPort() {
		if (validReferer)
			return wurl.getPort();
		return null;
	}
	
	public String getProtocol() {
		if (validReferer)
			return wurl.getProtocol();
		return null;
	}
	
	public String getAuthority() {
		if (validReferer)
			return wurl.getAuthority();
		return null;				
	}

	public String getDomain() {
		if (validReferer)
			return wurl.getHost();
		return null;
	}
	
	public String getDirectory() {
		if (validReferer)
			return wurl.getDirectory();
		return null;
	}
	
	public String getRef() {
		if (validReferer)
			return wurl.getRef();
		return null;
	}
	
	public String getQuery() {
		if (validReferer)
			return wurl.getQuery();
		return null;
	}
	
	public String getUserInfo() {
		if (validReferer)
			return wurl.getUserInfo();
		return null;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
								.append("getAuthority", getAuthority())
								.append("getCtxPath", getCtxPath())
								.append("getDirectory", getDirectory())
								.append("getDomain", getDomain())
								.append("getPath", getPath())
								.append("getPort", getPort())
								.append("getProtocol", getProtocol())
								.append("getQuery", getQuery())
								.append("getRef", getRef())
								.append("getReferer", getReferer())
								.append("getRequestedPage", getRequestedPage())
								.append("getUserInfo", getUserInfo())
								.toString();
	}
	
	/*******************************************************/
	/*************		Metodi statici		****************/
	/*******************************************************/

	private final static int VALORE_SINGOLO = 0; 
	private final static int VALORE_MULTIPLO = 1; 
	private final static int VALORE_SINGOLO_E_MULTIPLO = 2; 

	/**
	 * Interfaccia per filtri su parametri e attributi
	 */
	public interface ParamsFilter {
		public boolean accept(String name, Object value);
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public static Map<String, Object> getReqParameters(HttpServletRequest req) {
		return getReqParameters( req, null);
	}
	
	/**
	 * 
	 * @param req
	 * @param filter
	 * @return
	 */
	public static Map<String, Object> getReqParameters(HttpServletRequest req, ParamsFilter filter) {
		return reqParamsParser( req, filter, VALORE_SINGOLO_E_MULTIPLO );
	}
	
	/**
	 * 
	 * @param req oggetto request da processare
	 * @return 
	 */
	public static Map<String, String> getReqSingleValueParams(HttpServletRequest req) {
		return getReqSingleValueParams( req, null );
	}
	/**
	 * 
	 * @param req
	 * @param filter
	 * @return
	 */
	public static Map<String, String> getReqSingleValueParams(HttpServletRequest req, ParamsFilter filter) {
		return reqParamsParser( req, filter, VALORE_SINGOLO );
	}
	
	/**
	 * 
	 * @param req oggetto request da processare
	 * @return 
	 */
	public static Map<String, List<String>> getReqMultipleValuesParams(HttpServletRequest req) { 
		return getReqMultipleValuesParams( req, null );
	}
	
	/**
	 * 
	 * @param req
	 * @param filter
	 * @return
	 */
	public static Map<String, List<String>> getReqMultipleValuesParams(HttpServletRequest req, ParamsFilter filter) {
		return reqParamsParser( req, filter, VALORE_MULTIPLO );
	}
	
	/**
	 * 
	 * @param req
	 * @param filter
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private static <T> Map<String, T> reqParamsParser(HttpServletRequest req, ParamsFilter filter, int tipo)
	{
		Map<String, T> requestParamsMap = new HashMap<String, T>();
		
		Enumeration<String> paramsNames = req.getParameterNames();
		while ( paramsNames.hasMoreElements() ) {
			String name = paramsNames.nextElement();
			String[] values = req.getParameterValues(name);
			
			if ( values.length == 1 ) {
				
				if ( (tipo == VALORE_SINGOLO || tipo == VALORE_SINGOLO_E_MULTIPLO) ) {
					
					if ( filter == null )
						requestParamsMap.put( name, ( T ) values[ 0 ] );
					else if ( filter.accept( name, values ) )
						requestParamsMap.put( name, ( T ) values[ 0 ] );
				}
				
				
			} else if ( values.length > 1 ) {
				
				if	(tipo == VALORE_MULTIPLO || tipo == VALORE_SINGOLO_E_MULTIPLO) {
					
					if (filter == null)
						requestParamsMap.put(name, ( T ) Arrays.asList(values));
					else if ( filter.accept(name, values) )
						requestParamsMap.put(name, ( T ) Arrays.asList(values));
				}
			}
			
			
		}
		return requestParamsMap;
	}

	/**
	 * 
	 * @param req oggetto request da processare
	 * @return 
	 */
	public static Map<String, Object> getReqAttributes(HttpServletRequest req) {
		return getReqAttributes( req, null );
	}
	/**
	 * 
	 * @param req oggetto request da processare
	 * @return 
	 */
	public static Map<String, Object> getReqAttributes(HttpServletRequest req, ParamsFilter filter)
	{
		Map<String, Object> requestAttributes = new HashMap<String, Object>();
		@SuppressWarnings( "unchecked" )
		Enumeration<String> attributes = req.getAttributeNames();
		while ( attributes.hasMoreElements() ) {
			String attribName = (String) attributes.nextElement();
			Object attribValue = req.getAttribute(attribName);
			
			if (filter == null)
				requestAttributes.put( attribName, attribValue );
			 else if ( filter.accept(attribName, attribValue) )
				requestAttributes.put( attribName, attribValue );
		}
		return requestAttributes;
	}

	
}
