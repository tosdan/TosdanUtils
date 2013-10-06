package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.tosdan.dismesse.utils.servlets.BasicHttpServletV2;
import com.github.tosdan.utils.sql.BasicDAO;
import com.github.tosdan.utils.sql.BasicDAOException;
import com.github.tosdan.utils.sql.ConnectionProviderImplV2;

/**
 * 
 * @author Daniele
 *
 */
@SuppressWarnings( "serial" )
public class QueryRunnerServlet extends BasicHttpServletV2
{
	private boolean printStackTrace;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws QueryRunnerServletException, IOException { this.doPost( req, resp ); }
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws QueryRunnerServletException, IOException
	{
		// inizializza la mappa contenente i parametri della request
		String reqLog = getRequestParamsProcessLog(req);
		if ( BooleanUtils.toBoolean(req.getParameter("logQueryRunner")) && getInitParameter("logFileName") != null ) 
			// crea un file di log con il nome passato come parametro nella sottocartella della webapp
			FileUtils.writeStringToFile(new File(ctx.getRealPath(getInitParameter("logFileName"))), reqLog);

		// flag per verbose stacktrace delle eccezioni catturate da questa servlet
		this.printStackTrace = BooleanUtils.toBoolean( req.getParameter("printStackTrace") );
		
		// query da eseguire		
		String querySql = "";
		try {
			querySql = req.getAttribute("queryRecuperata").toString();
		} catch ( NullPointerException e ) {
			throw new QueryRunnerServletException( "Servlet " + this.getServletName() + 
					": errore, attributo queryRecuperata mancante/nullo nella request." );
		}
		
		int esito = this.eseguiQuery( querySql );

		if ( StringUtils.defaultString(req.getParameter("reqMethod")).equalsIgnoreCase("ajax") )
		{
			ServletOutputStream out = resp.getOutputStream();
			out.print(esito);
			
		} else {
			if ( esito > 0 ) {
				req.getSession().setAttribute( "QueryRunnerServlet_OK", "Operazione eseguita con successo." );
			} else {
				req.getSession().setAttribute( "QueryRunnerServlet_Error", "Rchiesta fallita." );
				// TODO
			}
			
			String jspChiamante = req.getHeader("referer");	
			resp.sendRedirect( jspChiamante );
		}
		
	}
	

	/**
	 * 
	 * @param sql stringa contenente la query sql
	 * @return il numero di record aggiornati/inseriti/eliminati
	 * @throws QueryRunnerServletException 
	 */
	private int eseguiQuery( String sql )
			throws QueryRunnerServletException
	{
		String fileDBConf = realPath + getInitParameter( "file_dbConf" );
		BasicDAO dao = new BasicDAO( new ConnectionProviderImplV2( fileDBConf ) );
		try {
			return dao.update( sql );
			
		} catch ( BasicDAOException e1 ) {
			if ( printStackTrace )
				e1.printStackTrace();
			throw new QueryRunnerServletException(  "Servlet " + this.getServletName() 
					+ ": errore di accesso al database. Classe: "+this.getClass().getName(), e1 );
		}
	}
} //***Chiusura classe
