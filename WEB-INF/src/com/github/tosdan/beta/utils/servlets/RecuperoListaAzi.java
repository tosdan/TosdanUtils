package com.github.tosdan.beta.utils.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.utils.servlets.BasicHttpServlet;
import com.github.tosdan.utils.sql.BasicDAO;
import com.github.tosdan.utils.sql.BasicDAOException;
import com.github.tosdan.utils.sql.ConnectionProviderImplV2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings( "serial" )
public class RecuperoListaAzi extends BasicHttpServlet
{
	private BasicDAO dao;
	private ConnectionProviderImplV2 provider;

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	
	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		String richiestaAjax = req.getParameter( "richiesta" );
		String sqlName = req.getParameter("sqlName"); // nome prima query da usare
		String sqlName2 = req.getParameter( "sqlName2" ); // nome query della seconda passata
		
		String sDBConf = this.getInitParameter( "sDBConf" );
		this.provider = new ConnectionProviderImplV2(sDBConf);
		this.dao = new BasicDAO( provider );
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		PrintWriter out = resp.getWriter();		
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */		
		
		// richiesta='listaAziUnicaPassata' + sqlName='listaAziUnicaPassata'
		if ( ("listaAziUnicaPassata").equalsIgnoreCase(richiestaAjax) ) {
			String query = req.getAttribute( sqlName ).toString(); // contiene la query: attributo creato da sqlManagerFilter a partire dal parametro della rquest 'sqlName'
			out.print( gson.toJson(this.getListaAzi(query)) );
		}

		/* * * * * * * * * * * * * * * * */
		
		// richiestaServlet='listaAziSecondaPassata' + nomeSqlOverride='datacompanySecca'. Parametri ignorati: richiesta='listaAziDuePassate' + sqlName='gruppiUtente'
		else if ( ("listaAziSecondaPassata").equalsIgnoreCase((String) req.getAttribute("richiestaServlet")) ) {			
			// query su datacompany per prelevare le aziende con groupID compreso nei gruppi dell'utente
			// se il parametro GruppiUtente e' nullo e' stato eseguito un filtro sui gruppi quindi sputa fuori la lista completa
			String query = req.getAttribute( sqlName2 ).toString(); // contiene la query: attributo creato da sqlManagerFilter a partire dal parametro della rquest 'sqlName'
			out.print( gson.toJson(this.getListaAzi(query)) );
			
		}
		
		/* * * * * * * * * * * * * * * * */
		
		// richiesta = 'listaAziDuePassate' + sqlName= 'gruppiUtente' + sqlName2='datacompanySecca' 
		else if ( ("listaAziDuePassate").equalsIgnoreCase(richiestaAjax) ) {
			String query = req.getAttribute( sqlName ).toString(); // contiene la query: attributo creato da sqlManagerFilter a partire dal parametro della rquest 'sqlName'
			List<Object> gruppiUtente = this.getGruppiUtente(query);
			
			if ( !gruppiUtente.contains("1") ) {
				req.setAttribute( "GruppiUtente", gruppiUtente );	// Costituira' il parametro IN della WHERE della query sulla datacompany
																	// Senza questo parametro la lista delle aziende non sara' filtrata
			}
			req.setAttribute( "nomeSqlOverride", sqlName2 );		// in SqlManagerFilter nomeSqlOverride ha la precedenza su sqlName che viene ignorato
			req.setAttribute( "richiestaServlet", "listaAziSecondaPassata" );
			
			String thisUrl = req.getParameter( "this-url-pattern" ); //= "/filter/sqlmanager/rlistazi/go"; // richiama questa stessa servlet passando attraverso sqlManagerFilter
			RequestDispatcher dispatcher = this._ctx.getRequestDispatcher( thisUrl );
			dispatcher.forward( req, resp );
			
		}		
	} //doService()
	
	/**
	 * Esegue la query per il recupero della lista di aziende
	 * @param query 
	 * @param dbConfFile 
	 * @return
	 */
	private List<Map<String, Object>> getListaAzi(String query)
	{
		List<Map<String, Object>> lista = new ArrayList<Map<String,Object>>();
		try {
			lista = this.dao.runAndGetMapList( query );
		} catch ( BasicDAOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Esegue la query per il recupero della lista dei gruppi dell'utente
	 * @param query 
	 * @param dbConfFile 
	 * @return
	 */
	private List<Object> getGruppiUtente(String query)
	{
		List<Object> lista = new ArrayList<Object>();
		try {
			lista = Arrays.asList( this.dao.runAndGetArray(query) );
		} catch ( BasicDAOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
}
