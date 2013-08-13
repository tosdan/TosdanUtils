package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ResourcesRedirect implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
//		StringBuffer url = req.getRequestURL();
//		System.out.println(url);
		String ctxPath = req.getContextPath();

		if (uri.contains( ctxPath )) {
			System.out.println("IGNORATO -> '" + uri + "'" );
			chain.doFilter(req, response);
		} else {
			String redirPath = ctxPath + "/" + uri;
			System.out.println("Reindirizzato -> '" + uri + "'  -- VERSO -->  '" + redirPath  +"'" );
			req.getRequestDispatcher( ctxPath + "/" + uri ).forward(req, response);
		}
	}

	@Override
	public void init( FilterConfig config ) throws ServletException {
		// TODO Auto-generated method stub

	}

}
