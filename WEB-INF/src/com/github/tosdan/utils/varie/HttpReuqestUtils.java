package com.github.tosdan.utils.varie;

import javax.servlet.http.HttpServletRequest;

public class HttpReuqestUtils
{
	public final static String[] sources = 
		{"X-Forwarded-For", "X-FORWARDED-FOR", "x-forwarded-for", "Proxy-Client-IP"
			, "X-Forwarded-Server", "X-Forwarded-Host", "X-Pounded-For", "Proxy-Client-IP"
			, "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
	
    public static String getClientIpAddr(HttpServletRequest request) {  
    	return getClientIpAddrSource(request).getIp();  
    }  
    
    public static ClientIP getClientIpAddrSource(HttpServletRequest request) { 
    	String ip, source;
    	
    	int i = 0;
    	do {
    		source = sources[i];
    		ip = request.getHeader(source);
    		i += 1;
    	} while (isInvalidIp(ip) && i < sources.length);
    	
    	if (isInvalidIp(ip)) {
    		ip = request.getRemoteAddr();
    	}
    	
    	return (new HttpReuqestUtils())
    			.new ClientIP(ip, source);  
    }

	private static boolean isInvalidIp(String ip) {
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}
    
	public class ClientIP {
		public String getIp() 		{ return ip; }
		public String getSource() 	{ return source; }
		public ClientIP(String ip, String source) {
			this.ip = ip;
			this.source = source;
		}
		private String ip;
		private String source;
	}
	
}
