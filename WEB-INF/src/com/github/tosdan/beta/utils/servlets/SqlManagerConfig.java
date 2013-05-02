package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.utils.servlets.BasicHttpServlet;

@SuppressWarnings( "serial" )
public class SqlManagerConfig extends BasicHttpServlet
{
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }

	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		out.print( "test" );
		HashMap<String, String> mappa = this._initConfigParamsMap;
		Set<Entry<String, String>> mappaEntrySet = mappa.entrySet();
		
		for( Entry<String, String> entry : mappaEntrySet ) {
			System.out.println( entry.getKey() + " - " + entry.getValue());
		}
		
	}
	
	
}
