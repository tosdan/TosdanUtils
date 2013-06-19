package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.github.tosdan.utils.servlets.BasicHttpServlet;
import com.github.tosdan.utils.stringhe.MapFormat;
import com.github.tosdan.utils.stringhe.MapFormatTypeValidatorSQL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings( "serial" )
public class QueriesFormatterServlet extends BasicHttpServlet
{
	private Gson gson;
	
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	public void init() throws ServletException {
		super.init();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		// da filtro
		
		
		// ajax diretta
		String jsonString = req.getParameter( "jsonParams" );
		String jsonStringUnescaped = StringEscapeUtils.unescapeHtml4( jsonString );
		@SuppressWarnings( "unchecked" )
		Map<String, Object> mappaParametriSostitutivi = this.gson.fromJson( jsonStringUnescaped, HashMap.class );
		String testo = ( String ) mappaParametriSostitutivi.get("testoOriginaleReservedKeyWord");
		boolean flagValidator = this._booleanSafeParse( mappaParametriSostitutivi.get("flagPerUtilizzoMapFormatSQLValidator") );
		
		
		
		
		String result = "";
		if ( flagValidator ) {
			result = MapFormat.format( testo , mappaParametriSostitutivi, new MapFormatTypeValidatorSQL() );
		} else {
			result = MapFormat.format( testo , mappaParametriSostitutivi );
		}
		
		PrintWriter out = resp.getWriter();
//		System.out.println( result );
		out.print( result );
	}
	
	@Override
	public void destroy() {
		this.gson = null;
		super.destroy();
	}
}
