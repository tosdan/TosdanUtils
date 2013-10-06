package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.dismesse.utils.servlets.BasicHttpServletV2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings( "serial" )
public class FolderTreeServlet extends BasicHttpServletV2
{
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	
	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		PrintWriter w = resp.getWriter();
		System.out.println(  getRequestParamsProcessLog(req) );
		String idfolder = req.getParameter("idfolder");
		Map<String, Object> output = new HashMap<String, Object>();
		List<Object> listaFolder = new ArrayList<Object>();
		if ( idfolder == null ) {
			listaFolder.add( this.getFolderMap( "Test Folder 1", "folder", "F1" ) );
			listaFolder.add( this.getFolderMap( "Test Folder 2", "folder", "F2" ) );
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 1", "item", "L1" ) );
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 2", "item", "L2" ) );
			
		} else if ( idfolder.equalsIgnoreCase("F1") ) {
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 3", "item", "L3" ) );
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 4", "item", "L4" ) );
			
		} else if ( idfolder.equalsIgnoreCase("F2") ) {
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 5", "item", "L5" ) );
			listaFolder.add( this.getFolderMap( "Test Leaf Folder 6", "item", "L6" ) );
			
		}
		
		output.put( "result", listaFolder );
		w.print( gson.toJson(output) );
		
		w.close();
	}
	
	private Map<String, Object> getFolderMap(String name, String type, String idfolder) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put( "name", name );
		result.put( "type", type );
		Map<String, String> additParams = new HashMap<String, String>();
		additParams.put( "idfolder", idfolder );
		result.put( "additionalParameters", additParams );
		
		return result;
	}
	
}
