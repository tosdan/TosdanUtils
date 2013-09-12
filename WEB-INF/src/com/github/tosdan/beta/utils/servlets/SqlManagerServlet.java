package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.github.tosdan.utils.servlets.BasicHttpServletV2;
import com.github.tosdan.utils.sql.QueriesUtils;
import com.github.tosdan.utils.sql.QueriesUtilsException;
import com.github.tosdan.utils.stringhe.MapFormatTypeValidator;
import com.github.tosdan.utils.stringhe.MapFormatTypeValidatorSQL;

/**
 * Carica dinamicamente una query da file ed esegue un update o, se specificato un gestore per le select, trasferisce il controllo ad un'altra servlet che se ne occupi.
 * @param NextHandlerServlet  <code>Request Parameter</code> in cui deve esser specificato il nome della <code>Serlvet</code> (come indicato nel <code>web.xml</code> nel sotto elemento <code>servlet-name</code>) verso la quale verra' re-inoltrata la request per l'esecuzione effettiva della query
 * @param logSqlManager <code>Request Parameter</code> Flag (<code>true</code>/<code>false</code>) per determinare se debbano essere stampati nel log i parametri letti nella request
 * @param printStackTrace <code>Request Parameter</code> Flag (<code>true</code>/<code>false</code>) per determinare se lo <code>stacktrace</code> delle eccezioni catturate debba esser stampato in console (<code>System.err</code>) o meno
 * @param sqlName <code>Request Parameter</code> Nome della query che deve esser caricata da file, il nome deve essere censito nel file di configurazione specificato nel web.xml tra gli init param della servlet stessa
 * @param lasciaQueryParametrica <code>Request Parameter</code> Flag (<code>true</code>/<code>false</code>) per determinare se la query non deve esser compilata in automatico con i parametri passati nella request ( inclusi i parametri init config)
 * @author Daniele
 * @version 0.9
 */
@SuppressWarnings( "serial" )
public class SqlManagerServlet extends BasicHttpServletV2
{
	private boolean printStackTrace;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doPost( req, resp ); }
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		// inizializza la mappa contenente i parametri della request
		String reqLog = getRequestParamsProcessLog(req);
		if ( BooleanUtils.toBoolean(req.getParameter("logSqlManager")) && getInitParameter("logFileName") != null ) 
			// crea un file di log con il nome passato come parametro nella sottocartella della webapp
			FileUtils.writeStringToFile(new File(ctx.getRealPath(getInitParameter("logFileName"))), reqLog);
			
		// flag per verbose stacktrace delle eccezioni catturate da questa servlet
		this.printStackTrace = BooleanUtils.toBoolean( req.getParameter("printStackTrace") );
		// percorso file settings
		String queriesRepoFolderFullPath = realPath + getInitParameter( "SqlManagerServletConf_Path" );
		// nome file settings
		String propertiesFile = getInitParameter( "SqlManagerServletConf_File" );
		// carica la configurazione dal file properties
		Properties dtrProperties = this.loadProperties( propertiesFile );

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

		// query da eseguire
		String querySql = "";
		// identificativo query nel file repository delle queries
		String nomeSQL = req.getParameter( "sqlName" );
		if (nomeSQL == null) 
			throw new SqlManagerServletException( "Servlet " + this.getServletName() + 
					": errore, parametro sqlName mancante nella request." );
		
		// raccoglie i parametri della request, degli initConf della servlet e degli attributes eventualmente aggiunti da servlet o filtri precedenti
		Map<String, Object> allParams = new HashMap<String, Object>();
		allParams.putAll( getRequestParamsMap(req) );
		allParams.putAll( getRequestMultipleValuesParamsMap(req) );
		allParams.putAll( getRequestAttributes(req) );

		if ( ! BooleanUtils.toBoolean(req.getParameter("lasciaQueryParametrica")) )
		{
			try {
				// istanza l'oggetto per la validazione dei parametri rispetto ai valori effettivamente passati per evitare problemi sui Tipi
				MapFormatTypeValidator validator = new MapFormatTypeValidatorSQL();
				// compila la query parametrica sostituendo ai parametri i valori contenuti nella request e nell'initConf della servlet 
				querySql = QueriesUtils.compilaQueryDaFile( dtrProperties, queriesRepoFolderFullPath , nomeSQL, allParams, validator );
				
			} catch ( QueriesUtilsException e1 ) {
				if ( printStackTrace )
					e1.printStackTrace();
				throw new SqlManagerServletException(  "Servlet " + this.getServletName() 
						+ ": errore caricamento query da file. Classe: "+this.getClass().getName(), e1 );
				
			}
		}
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		String nextHandlerServlet = req.getParameter( "NextHandlerServlet" );
		req.setAttribute( "queryRecuperata", querySql );
		
//		String destinazione = nextHandlerServlet + "/go"; // URL Relativo
//		RequestDispatcher dispatcher = req.getRequestDispatcher( destinazione );
		RequestDispatcher dispatcher = ctx.getNamedDispatcher( nextHandlerServlet );
		
		dispatcher.forward( req, resp );

	}

	/**
	 * Carica e restituisce un oggetto {@link Properties}
	 * @param propertiesFile
	 * @throws SqlManagerServletException
	 */
	protected Properties loadProperties(String propertiesFile) throws SqlManagerServletException
	{
		Properties dtrSettings = new Properties();
		
		try {
			// carica, dal file passato, l'oggetto Properties salvandolo in un campo della servlet 
			dtrSettings.load( ctx.getResourceAsStream( propertiesFile ) );
			
		} catch ( IOException e2 ) {
			if ( printStackTrace )
				e2.printStackTrace();
			throw new SqlManagerServletException( "Servlet " + this.getServletName()
					+ ": errore caricamento file configurazione. Classe: "+this.getClass().getName(), e2 );
		}
		
		return dtrSettings;
	}
}
