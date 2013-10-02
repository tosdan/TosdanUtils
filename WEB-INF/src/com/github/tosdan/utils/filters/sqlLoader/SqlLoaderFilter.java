package com.github.tosdan.utils.filters.sqlLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import com.github.tosdan.utils.filters.BasicFilterV2;
import com.github.tosdan.utils.servlets.ServletUtils;
import com.github.tosdan.utils.sql.MassiveQueryCompiler;
import com.github.tosdan.utils.stringhe.TemplateCompilerException;
import com.github.tosdan.utils.stringhe.TemplatePickerSections;
/**
 * 
 * @author Daniele
 * @version 0.2.6-b2013-08-25
 */
public class SqlLoaderFilter extends BasicFilterV2
{
	// Contenitore configurazione filtro
	private Map<String, String> settings;
	// Configurazione specifica per recupero/compilazione queries
	private Map<String, String> queriesRepositoryIndexMap;
	private String queriesRepositoryIndexFilename;
	private String queriesRepoFolderPath;
	// Opzioni
	private boolean isLogEnabled;
	private boolean lasciaParametrica;
	private boolean printStackTrace;
	private boolean retroCompatibilitaTemplate;

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )	throws IOException, ServletException
	{
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse resp = ( HttpServletResponse ) response;

		String[] nomiQueriesDaCompilare = getNomiQueriesDaCompilare( req );
		
		getSqlLoaderOptions(req);
		loadConfiguration();
		logParametersToFile( req );
		
		if ( ! this.lasciaParametrica ) {
			// Lista eventuali parametri per query di update (anche piu' di un update per volta essendo appunto una lista di parametri)
			@SuppressWarnings( "unchecked" )
			List<Map<String, Object>> updateParamsMapsList = (List<Map<String, Object>>) req.getAttribute("sqlLoaderParametriUpdate");
			MassiveQueryCompiler mqc = new MassiveQueryCompiler( this.queriesRepositoryIndexMap, this.queriesRepoFolderPath, updateParamsMapsList );
			
			if ( retroCompatibilitaTemplate  )
				mqc.setPicker( new TemplatePickerSections() ); // TODO convertire i template delle query vecchie in yaml

			try {
				Map<String, List<String>> queries = mqc.getQueriesListMap( nomiQueriesDaCompilare, getAllParamsMap(req) );
				
				// Ogni query e' recuperabile usando il nome specificato in sqlName nella request ajax
				for( int i = 0 ; i < nomiQueriesDaCompilare.length ; i++ ) {
					
					List<String> listaQueryTemp = queries.get(nomiQueriesDaCompilare[i]);
					
					if (listaQueryTemp.size() == 1) {
						
						req.setAttribute( nomiQueriesDaCompilare[i], listaQueryTemp.get(0) );
					} else {
						
						req.setAttribute( nomiQueriesDaCompilare[i], listaQueryTemp );
					}
					
				}
				doOldCompatilityStuff(req, queries, nomiQueriesDaCompilare);
				
			} catch ( TemplateCompilerException e ) {
				if ( printStackTrace )
					e.printStackTrace();
				throw new SqlLoaderFilterException(  "Filtro " + this.filterConfig.getFilterName()
						+ ": errore compilazione query. Classe: "+this.getClass().getName(), e );
			}
			
		} else {
			// TODO recupero query senza compilarla
			
		}
		
		
		chain.doFilter( req, resp );
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param req
	 * @throws IOException 
	 */
	private void logParametersToFile(HttpServletRequest req) throws IOException {
		String reqLog = getRequestParamsProcessLog(req);
		if ( isLogEnabled && settings.get("logFileName") != null ) 
			FileUtils.writeStringToFile( new File(realPath + settings.get("logFileName")), reqLog);// crea un file di log con il nome passato come parametro nella sottocartella della webapp

	}
	
	
	
	/**
	 * 
	 * @param req
	 * @param queries
	 * @param nomiQueriesDaCompilare
	 */
	private void doOldCompatilityStuff(HttpServletRequest req, Map<String, List<String>> queries, String[] nomiQueriesDaCompilare) {

		// Nome dell'attributo inserito nella request, inviato alla servlet cliente, che conterra' le queries richieste.
		// sqlLoaderMappaQueries e' la chiave di default per recuperare (nella servlet cliente) la mappa con le queries compilate
		String contenitoreMultiQueries = StringUtils.defaultString( req.getParameter("MultiQueryContainer"), "sqlLoaderMappaQueries" );
		
		// Essendo possibile specificare piu' di un valore per il parametro sqlName
		// viene creata anche una mappa con chiave sqlName[i] e valore Query[i]. 
		// Tale mappa finisce in un attribute con chiave: var contenitoreMultiQueries oppure  "sqlManagerMappaQueries"
		// nel caso sia fornito il parametro 'sqlLoaderParametriUpdate' la mappa avra' chiave sqlName[i] e valore List<Query[i]>
		req.setAttribute( contenitoreMultiQueries, queries ); // TODO deprecato: ogni query e' inserita come attributo usando il nome della request ajax (prima implementazione per query multiple)
		req.setAttribute( "sqlManagerMappaQueries", queries ); // TODO deprecato: retro compatibilita' con sqlManagerFilter (prima implementazione per query multiple)
		req.setAttribute( "queryRecuperata", (queries.get(nomiQueriesDaCompilare[0])).get(0) ); // TODO deprecato: retro compatibilita' con sqlManagerFilter (primissima implementazione)
		
	}
	
	
	
	/**
	 * Dalla request recupera 'sqlName' (parametro) oppure 'sqlOverride' (attributo)
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
			
			nomiQueriesDaCompilare = new String[] { sqlOverride.toString() };
		}		
		
		if (nomiQueriesDaCompilare == null)
			throw new SqlLoaderFilterException( "Filtro " + this.filterConfig.getFilterName() + ": errore, parametro sqlName mancante nella request." );
		
		return nomiQueriesDaCompilare;

	}
	
	
	
	/**
	 * Raccoglie tutti i <code>parameters</code> della <code>request</code> compresi <code>attributes</code> eventualmente aggiunti da servlet o filtri precedenti e parametri custom contenuti in mappe inserite in sessione o in un attribute della request
	 * @param req
	 * @return
	 */
	private Map<String, Object> getAllParamsMap(HttpServletRequest req) {	
		Map<String, Object> allParams = new HashMap<String, Object>();
		allParams.putAll( ServletUtils.getReqParameters(req) );
		allParams.putAll( ServletUtils.getReqAttributes(req) );
		// TODO prevedere caricamento parametri costanti da file, magari su base di sqlName
		allParams.putAll( getParametriAggiuntivi(req) );
		return allParams;
	}
	
	
	
	/**
	 * 
	 * @throws SqlLoaderFilterException 
	 */
	private void loadConfiguration() throws SqlLoaderFilterException {
		// Cerca nel web.xml il nome del file contenente la configurazione di questo Filter (completo di percorso relativo) 
		String sqlLoaderConfigFile = initConfigParamsMap.get("SqlLoaderConf_File");
		// Carica la configurazione per SqlLoaderFilter da file.
		settings = getMapFromConfFile( sqlLoaderConfigFile );
		
		// Percorso relativo del file contenente l'index delle queries
		queriesRepositoryIndexFilename = settings.get("SqlLoaderQueriesReposIndex_File").toString(); //TODO sotto try/catch + rethrow
		// Crea una mappa con l'index delle queries
		queriesRepositoryIndexMap = getMapFromConfFile( queriesRepositoryIndexFilename );
		// Percorso assoluto dei file contenenti i templates delle queries
		queriesRepoFolderPath = realPath + settings.get("SqlLoaderConf_Path");
		
	}
	
	
	
	/**
	 * Legge dalla request eventuali opzioni inserite nella chiamata e imposta le relative variabili di istanza con i valori recuperati
	 * @param req
	 */
	private void getSqlLoaderOptions(HttpServletRequest req) {
		// Opzione per verbose stacktrace delle eccezioni catturate
		printStackTrace = BooleanUtils.toBoolean(req.getParameter("printStackTrace"));
		// Opzione per abilitare il log
		isLogEnabled =  BooleanUtils.toBoolean(req.getParameter("logSqlLoader"));
		// Opzione per evitare la compilazione automatica della query. Fondamentalmente recupera la query e la serve immutata TODO
		lasciaParametrica = BooleanUtils.toBoolean(req.getParameter("lasciaQueryParametrica"));
		// 
		retroCompatibilitaTemplate = BooleanUtils.toBoolean(req.getParameter("noYamlPicker"));
	}
	
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private Map<String, Object> getParametriAggiuntivi(HttpServletRequest req) {
		Map<String, Object> mappaParametriAggiuntivi = new HashMap<String, Object>();
		
		// Se nella chiamata del jsp sono presenti uno o entrambi i seguenti parametri viene cercata nella session e/o nella request
		// un attributo con chiave corrispondente a quella/e recuperata/e attraverso questi due parametri.
		String[] idParametriAggiuntiviDaSessione = req.getParameterValues("LoaderSessionCustomParamsMap");
		String idParametriAggiuntiviOnCallingChain = (String) req.getParameter("LoaderCustomParamsMap"); // aggiunti da servlet nella catena di chiamate 
		
		// Tenta il recupero dalla sessione di una (o piu') mappa(e) con eventuali parametri custom che non e' stato possibile (o consigliabile) inserire nel jsp
		// Es. chiamata JSP: /servlet/blabla?do=something&LoaderSessionCustomParamsMap=miaMappaParametriCustom
		// specificando piu' parametri 'LoaderSessionCustomParamsMap' si possono caricare piu' mappe con parametri custom. 
		if (idParametriAggiuntiviDaSessione != null ) { // cerchera' in sessione un attribute con chiave 'miaMappaParametriCustom'
			HttpSession session = req.getSession();
			for( int i = 0 ; i < idParametriAggiuntiviDaSessione.length ; i++ ) {
				mappaParametriAggiuntivi.putAll( (Map<String, Object>)session.getAttribute(idParametriAggiuntiviDaSessione[i]) );
			}
		}
		
		// Tenta il recupero dalla request di una mappa con eventuali parametri custom che non e' stato possibile (o consigliabile) inserire nel jsp
		if (idParametriAggiuntiviOnCallingChain != null) {
			Map<String, Object> mappaParametriAggiuntiviFromCallingChain = (Map<String, Object>) req.getAttribute(idParametriAggiuntiviOnCallingChain);
			
			if (mappaParametriAggiuntiviFromCallingChain != null)
				mappaParametriAggiuntivi.putAll( mappaParametriAggiuntiviFromCallingChain ); // unisce le due mappe 
		}
		
		return mappaParametriAggiuntivi;
	}
	
	
	
	/**
	 * Carica e restituisce una mappa con i dati di configurazione contenuti nel file passato.
	 * @param configFile
	 * @throws SqlLoaderFilterException
	 */
	@SuppressWarnings( "unchecked" )
	private Map<String, String> getMapFromConfFile(String configFile) throws SqlLoaderFilterException
	{
		Map<String, String> contentMap;
		Yaml yaml = new Yaml();
		InputStream is = null;
		try {
			is =  ctx.getResourceAsStream( configFile );
			if (is == null)
				throw new SqlLoaderFilterException( "Filtro " + this.filterConfig.getFilterName()
						+ ": errore, file configurazione: '"+configFile+"' mancante. Classe: "+this.getClass().getName());
			
			String configFileContent = IOUtils.toString( is );
			contentMap = (Map<String, String>) yaml.load( configFileContent );
			
		} catch ( IOException e ) {
			if ( printStackTrace )
				e.printStackTrace();
			throw new SqlLoaderFilterException( "Filtro " + this.filterConfig.getFilterName()
					+ ": errore caricamento file configurazione: '"+configFile+"'. Classe: "+this.getClass().getName(), e );
		} finally {
			IOUtils.closeQuietly( is );
		}
		
		return contentMap;
	}
}
