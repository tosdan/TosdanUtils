package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
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
	@Override
	public void init() throws ServletException 	{ 
		super.init();
		this.prop = new Properties();
		this.queriesReposList = new ArrayList<String>();
	}
	
	private String configPath;
	private String configFileName;
	private File configDir;
	private Properties prop;
	private List<String> queriesReposList;

	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
//		this.stampaMappa(this._initConfigParamsMap);
		this.configPath = this._initConfigParamsMap.get( "SqlManagerServletConf_Path" );	
		this.configFileName =  this._initConfigParamsMap.get( "SqlManagerServletConf_File" );
		this.configDir = new File( this._appRealPath + this.configPath );
		
		this.loadPropFile();
		this.readQueriesRepoList();
		
		this.printQueriesRepoFile(); 
		
		PrintWriter out = resp.getWriter();
		out.print( "test" );
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	protected void loadPropFile() throws IOException {
//		System.out.println(confDir.getAbsolutePath());
		File propFile = new File( this._appRealPath + this.configFileName );
		FileReader reader = new FileReader( propFile );
		this.prop.load( reader );
		reader.close();
	}
	
	/**
	 * 
	 */
	protected void readQueriesRepoList() {		
		Set<Object> propSet = this.prop.keySet();
		for( Object object : propSet ) {
			this.queriesReposList.add( (String) object );
			
		}		
	}
	
	/**
	 * 
	 */
	protected void printQueriesRepoFile() {
		File f;
		for ( String el : this.queriesReposList) {			
			String queriesRepoName = (String) this.prop.get( el );
			String queriesRepoFullPath = this.configDir.getAbsolutePath() + queriesRepoName;
			f = new File( queriesRepoFullPath );
			System.out.println( el + (f.exists() ? " - esistente" : " - inesistente") + " - " + f.getAbsolutePath() );
			
		}
	}
	
	/**
	 * 
	 * @param mappa
	 */
	protected void stampaMappa(HashMap<String, String> mappa) {
		Set<Entry<String, String>> mappaEntrySet = mappa.entrySet();		
		for( Entry<String, String> entry : mappaEntrySet ) 
			System.out.println( entry.getKey() + " - " + entry.getValue());
		
	}
	
}
