package com.github.tosdan.utils.filters.sqlLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.utils.filters.BasicFilter;
import com.github.tosdan.utils.sql.QueriesUtils;
import com.github.tosdan.utils.sql.QueriesUtilsException;
import com.github.tosdan.utils.stringhe.MapFormatTypeValidator;
import com.github.tosdan.utils.stringhe.MapFormatTypeValidatorSQL;
/**
 * 
 * @author Daniele
 * @version 0.1.0-b2013.06.19
 */
public class SqlLoaderFilter extends BasicFilter
{
	private boolean printStackTrace;
	private String[]  nomiQueriesDaCompilare;
	private List<Map<String, Object>> updateParamsMapsList;
	private Properties queriesRepository;
	private String queriesRepoFolderFullPath;
	private boolean isLogEnabled;

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )	throws IOException, ServletException
	{
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse resp = ( HttpServletResponse ) response;

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

		// Recupera dalla request 'sqlName' (parametro) oppure 'sqlOverride' (attributo)
		this.nomiQueriesDaCompilare = this.getNomiQueriesDaCompilare( req );
		
		String contenitoreMultiQueries = (req.getParameter("ContenitoreMultiQueries") != null) 
										? req.getParameter("ContenitoreMultiQueries") 
										: "sqlLoaderMappaQueries"; // chiave di default per recuperare la mappa con le queries compilate
		
		// Lista eventuali parametri per query di update (anche piu' di un update per volta essendo appunto una lista di parametri)
		this.updateParamsMapsList = (List<Map<String, Object>>) req.getAttribute("sqlLoaderParametriUpdate");

		// Flag per verbose stacktrace delle eccezioni catturate
		this.printStackTrace = this._booleanSafeParse(req.getParameter("printStackTrace"));
		// Flag per abilitare il log
		this.isLogEnabled =  this._booleanSafeParse(req.getParameter("logSqlLoader"));
		// Flag per evitare la compilazione automatica della query. Fondamentalmente recupera la query e basta TODO
		boolean lasciaParametrica = _booleanSafeParse(req.getParameter("lasciaQueryParametrica"));
	
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		// Cerca nel web.xml il nome del file contenente la configurazione di questo Filter (completo di percorso relativo) 
		String thisConfigFile = this._initConfigParamsMap.get("SqlLoaderConf_File");
		// Carica la configurazione per SqlLoaderFilter da file.
		Properties thisProperties = this.loadProperties( thisConfigFile );
		
		// Percorso completo del file contenente il repository delle queries
		String queriesRepositoryFileName = this._appRealPath + thisProperties.get("SqlLoaderQueriesRepos_File").toString();
		// Crea l'oggetto Properties con il repository delle queries // TODO vista l'utilizzo forse è meglio trasformarlo in una mappa -> cambiare anche queruUtils
		this.queriesRepository = this.loadProperties( queriesRepositoryFileName );
		// Percorso dei file contenenti le queries
		this.queriesRepoFolderFullPath = this._appRealPath + thisProperties.get("SqlLoaderConf_Path");

		String reqLog = this.get_requestParamsProcessLog( req );
		if ( this.isLogEnabled && thisProperties.get("logFileName") != null ) 
			// crea un file di log con il nome passato come parametro nella sottocartella della webapp
			this._logOnFile( this._appRealPath + thisProperties.get("logFileName"), reqLog );

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

		if ( ! lasciaParametrica ) {
			// raccoglie i parametri della request, degli initConf della servlet e degli attributes eventualmente aggiunti da servlet o filtri precedenti
			Map<String, Object> allParams = this.getAllParamsMap( req );
			SqlLoaderQueriesProvider sqlQP = new SqlLoaderQueriesProvider( this.nomiQueriesDaCompilare, this.queriesRepository, this.queriesRepoFolderFullPath, this.updateParamsMapsList );

			try {
				Map<String, List<String>> queries = sqlQP.getQueriesListMap( allParams );
					
				// Se c'e' un solo valore per il parametro 'sqlName', allora la query e' anche recuperabile,  
				// direttamente come stringa, dall'attributo con chiave uguale ad 'sqlName'.
				req.setAttribute( this.nomiQueriesDaCompilare[0], (queries.get(this.nomiQueriesDaCompilare[0])).get(0) ); // giusto per comodita' in caso di un unica query da gestire
				
				// Essendo possibile specificare piu' di un valore per il parametro sqlName
				// viene creata anche una mappa con chiave sqlName[i] e valore Query[i]
				// nel caso sia fornito il parametro 'sqlLoaderParametriUpdate' la mappa avra' chiave sqlName[i] e valore List<Query[i]>
				req.setAttribute( contenitoreMultiQueries, queries );
			
			} catch ( QueriesUtilsException e ) {
				if ( this.printStackTrace )
					e.printStackTrace();
				throw new SqlLoaderFilterException(  "Filtro " + this._filterConfig.getFilterName()
						+ ": errore caricamento query da file. Classe: "+this.getClass().getName(), e );
			}
			
		} else {
			// TODO recupero query senza compilarla
			
		}
		
		chain.doFilter( req, resp );
//		System.out.println( this._filterConfig.getFilterName() +"\n" + req.getSession().getAttribute( "JsonDataTableString" ) + " - " + req.getSession().getAttribute( "DataTableQuery" ));
	}
	
	/**
	 * 
	 * @param req HttpServletRequest
	 * @return
	 * @throws SqlLoaderFilterException
	 */
	private String[] getNomiQueriesDaCompilare(HttpServletRequest req) throws SqlLoaderFilterException {
		
		String [] nomiQueriesDaCompilare = req.getParameterValues( "sqlName" );
		
		// sqlOverride puo' arrivare solo da una servlet e ha precedenza su sqlName
		Object sqlOverride = req.getAttribute("sqlOverride");
		
		if (sqlOverride instanceof String[]) {
			nomiQueriesDaCompilare = (String[]) sqlOverride;
		} else if (sqlOverride instanceof String) {
			nomiQueriesDaCompilare = new String[] { (String) sqlOverride };
		}		
		
		if (nomiQueriesDaCompilare == null)
			throw new SqlLoaderFilterException( "Filtro " + this._filterConfig.getFilterName() + ": errore, parametro sqlName mancante nella request." );
		
		return nomiQueriesDaCompilare;

	}
	
	/**
	 * 
	 * @param allParams
	 * @return
	 * @throws SqlLoaderFilterException
	 */
	/*
	private Map<String, List<String>> getQueriesListMap(Map<String, Object> allParams) throws SqlLoaderFilterException {

		Map<String, List<String>> queries = new HashMap<String, List<String>>();
		try {
			// Istanzia l'oggetto per la validazione dei parametri rispetto ai valori effettivamente passati per evitare problemi sui Tipi
			MapFormatTypeValidator validator = new MapFormatTypeValidatorSQL();
			// Compila la query parametrica sostituendo ai parametri i valori contenuti nella mappa 'allParams'
			for( int i = 0 ; i < this.nomiQueriesDaCompilare.length ; i++ ) {
				List<String> listaQueries = new ArrayList<String>();
				if (this.updateParamsMapsList != null) {
					for( Map<String, Object> updateParamsMap : this.updateParamsMapsList ) {
						listaQueries.add( QueriesUtils.compilaQueryDaFile( this.queriesRepository, this.queriesRepoFolderFullPath, this.nomiQueriesDaCompilare[i], updateParamsMap, validator ) );							
					}
				} else {
					listaQueries.add( QueriesUtils.compilaQueryDaFile( this.queriesRepository, this.queriesRepoFolderFullPath, this.nomiQueriesDaCompilare[i], allParams, validator ) );
				}
				queries.put( this.nomiQueriesDaCompilare[i], listaQueries );
			}
			
		} catch ( QueriesUtilsException e ) {
			if ( this.printStackTrace )
				e.printStackTrace();
			throw new SqlLoaderFilterException(  "Filtro " + this._filterConfig.getFilterName()
					+ ": errore caricamento query da file. Classe: "+this.getClass().getName(), e );
		}
		return queries;
	}
	*/
	
	private Map<String, Object> getAllParamsMap(HttpServletRequest req) {	
		Map<String, Object> allParams = new HashMap<String, Object>();
		allParams.putAll( this.get_requestParamsMap( req ) );
		allParams.putAll( this.get_requestMultipleValuesParamsMap( req ) );
//		allParams.putAll( this._initConfigParamsMap ); // TODO prevedere caricamento parametri da file, magari su base di sqlName
		allParams.putAll( this.get_requestAttributes( req ) );
		allParams.putAll( this.getParametriAggiuntivi(req) );
		return allParams;
	}
	
	@SuppressWarnings( "unchecked" )
	private Map<String, Object> getParametriAggiuntivi(HttpServletRequest req) {
		String idParametriAggiuntiviDaSessione = req.getParameter( "SqlMngrSessionCustomParamsMap" );
		String idParametriAggiuntiviOnCallingChain = req.getParameter( "SqlMngrCustomParamsMap" ); // aggiunti da servlet nella catena di chiamate 

		Map<String, Object> mappaParametriAggiuntivi = null;
		if (idParametriAggiuntiviDaSessione != null )		
			mappaParametriAggiuntivi = (Map<String, Object>) req.getSession().getAttribute(idParametriAggiuntiviDaSessione);

		Map<String, Object> mappaParametriAggiuntiviOnCallingChain = null;
		if (idParametriAggiuntiviOnCallingChain != null)
		mappaParametriAggiuntiviOnCallingChain = (Map<String, Object>) req.getAttribute(idParametriAggiuntiviOnCallingChain);

		if (mappaParametriAggiuntivi == null) 
			mappaParametriAggiuntivi = new HashMap<String, Object>();
		if (mappaParametriAggiuntiviOnCallingChain != null)
			mappaParametriAggiuntivi.putAll( mappaParametriAggiuntiviOnCallingChain );
		
		
		return mappaParametriAggiuntivi;
	}
	
	/**
	 * Carica e restituisce un oggetto {@link Properties}
	 * @param propertiesFile
	 * @throws SqlLoaderFilterException
	 */
	protected Properties loadProperties(String propertiesFile) throws SqlLoaderFilterException
	{
		Properties dtrSettings = new Properties();
		
		try {
			// carica, dal file passato, l'oggetto Properties salvandolo in un campo della servlet 
			dtrSettings.load( this._ctx.getResourceAsStream( propertiesFile ) );
			
		} catch ( IOException e ) {
			if ( printStackTrace )
				e.printStackTrace();
			throw new SqlLoaderFilterException( "Filtro " + this._filterConfig.getFilterName()
					+ ": errore caricamento file configurazione. Classe: "+this.getClass().getName(), e );
		}
		
		return dtrSettings;
	}
}
