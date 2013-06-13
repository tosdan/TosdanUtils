package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.utils.servlets.BasicHttpServlet;

@SuppressWarnings( "serial" )
public class FolderTreeServlet extends BasicHttpServlet
{
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	
	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		PrintWriter w = resp.getWriter();
		System.out.println(  this.get_requestParamsProcessLog( req ) );
		String idfolder = req.getParameter("idfolder");
		
		if ( idfolder == null ) {
			w.print( "{ \"result\": [ " +
		
				"	{ \"name\": \"Test Folder 1\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F1\" } }, \r\n " + 
				"	{ \"name\": \"Test Folder 2\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F2\" } }, \r\n " + 
				"	{ \"name\": \"Test Item 1\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I1\" } }, \r\n " + 
				"	{ \"name\": \"Test Item 2\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I2\" } } \r\n " +
				
			"] }" );
			
		} else if ( idfolder.equalsIgnoreCase("F1") ) {
			w.print( "{ \"result\": [ " +
			
//				"	{ \"name\": \"Test Folder 1\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F1\" } }, \r\n " + 
//				"	{ \"name\": \"Test Folder 2\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F2\" } }, \r\n " + 
				"	{ \"name\": \"Test Item 3\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I3\" } }, \r\n " + 
				"	{ \"name\": \"Test Item 4\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I4\" } } \r\n " +
			
			"] }" );
		} else if ( idfolder.equalsIgnoreCase("F2") ) {
			w.print( "{ \"result\": [ " +
					
	//				"	{ \"name\": \"Test Folder 1\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F1\" } }, \r\n " + 
	//				"	{ \"name\": \"Test Folder 2\", \"type\": \"folder\", \"additionalParameters\": { \"idfolder\": \"F2\" } }, \r\n " + 
	"	{ \"name\": \"Test Item 5\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I5\" } }, \r\n " + 
	"	{ \"name\": \"Test Item 6\", \"type\": \"item\", \"additionalParameters\": { \"idfolder\": \"I6\" } } \r\n " +
	
					"] }" );
		}
		
		w.close();
	}
}
