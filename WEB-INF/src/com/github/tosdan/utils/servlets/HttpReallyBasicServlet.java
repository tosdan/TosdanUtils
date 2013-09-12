package com.github.tosdan.utils.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings( "serial" )
public abstract class HttpReallyBasicServlet extends HttpServlet {
	
	protected ServletContext ctx;
	@Override protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService(req, resp); }
	@Override protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService(req, resp); }
	@Override
	public void init(ServletConfig config) throws ServletException {
		ctx = config.getServletContext();
		super.init();
	}
	public abstract void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException;
}
