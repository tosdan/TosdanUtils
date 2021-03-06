package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 
 * @author Daniele
 * @version 0.1.4-r2013-08-05
 */
@SuppressWarnings( "serial" )
public class DownloadServlet extends HttpServlet {
	
	public static final String FILENAME_PARAM = "BetaDSFileName";
	private static final String DEBUG_PARAM = "debug";

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		this.doService( req, resp );
	}
	
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
//		String uri = req.getRequestURI();		
//		String percorsoDownloadServlet = req.getContextPath() + req.getServletPath();	
//		this.nomeFile = uri.replaceAll( (percorsoDownloadServlet + "/"), "" );
		
		String nomeFile = getNomeFile(req);
		nomeFile = URLDecoder.decode( nomeFile, "UTF-8" );		
		nomeFile = StringEscapeUtils.unescapeHtml4( nomeFile );
		this.doService( req, resp );
//		System.out.println( "nome file : " + nomeFile + "\npercorso servlet: "+ percorsoDownloadServlet );
				
		//String[] tokens = uri.split( "/" );
		//String filesDir = ( String ) getServletContext().getInitParameter( "filesDir" );
		//String filePath = filesDir + tokens[ tokens.length - 1 ];
	}
	
	
	
	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		File file = new File( getNomeFile(req) );		
		if (isDebug(req)) {
			System.out.println( "file.getAbsolutePath(): " +file.getAbsolutePath() + "\nfile.getName(): " + file.getName() );
		}
		
		ServletOutputStream out = resp.getOutputStream();
		
		if ( !file.exists() || !file.canRead() ) {
			String msg = file.exists() 
					? "Impossibile leggere il file: " +file.getAbsolutePath() 	// entrato nell'IF perche' !file.canRead()
					: "File non trovato: " + file.getAbsolutePath();			// entrato nell'IF perche' !file.exists()
					
			req.getSession().setAttribute( "DownloadServletBeta/Error", msg );
			
			if (isDebug(req)) {
				System.out.println( msg );
			}
			
			if (req.getParameter("returnToCaller") != null) {
				this.returnToCaller( req, resp );
				
			} else { // per chiamate ajax (ramo di esecuzione di default)
				resp.setHeader("content-type","text/html; charset=UTF-8");
				resp.setContentType( "text/html" );
//				msg = "{ \"name\": \"error\", \"message\": \""+msg+"\" }";
				msg = "<span>"+msg+"</span>";
				out.print( msg );
				
			}
			
		} else { // il file esiste ed e' leggibile
			this.transferFile( out, resp, file );
			
		}		
		
	}

	/**
	 * 
	 * @param out
	 * @param resp
	 * @param file
	 */
	private void transferFile(ServletOutputStream out, HttpServletResponse resp, File file) {
		try {
			resp.setContentLength( ( int ) file.length() );
			resp.setContentType( "application/octect-stream" ); // octect-stream | x-download . Il browser non deve riconoscere il contenuto come qualcosa di associato ad una operazione speciale
//			response.setContentType( getMimeType( file ) );
			
			/**/
			// Il coockie e' necessario per comunicare ad jQuery.fileDownload.js che il download e' avvenuto
			// Va inserito prima di iniziare a trasmettere il flusso di output
			Cookie cookie = new Cookie( "fileDownload", "true" );
			cookie.setPath( "/" );
			resp.addCookie( cookie );
			/**/
			
			resp.setHeader( "Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );
			
			IOUtils.copy( new FileInputStream( file ), out );
//			ReadToOutputStream.readInputStream( new FileInputStream( file ), out );
			
		} catch ( Exception e ) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public String getMimeType( File file ) {
		MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
		return mime.getContentType( file );
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	private String getNomeFile(HttpServletRequest req) {
		if (req.getAttribute(FILENAME_PARAM) != null) {
			return (String) req.getAttribute(FILENAME_PARAM);
		} else if (req.getParameter(FILENAME_PARAM) != null) {
			return req.getParameter( FILENAME_PARAM );
		} else {
			throw new RuntimeException("Parametro "+FILENAME_PARAM+" mancante.");
		}
	}
	
	private boolean isDebug(HttpServletRequest req) {
		return req.getParameter(DEBUG_PARAM) != null && req.getParameter(DEBUG_PARAM).equalsIgnoreCase("true");
				
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void returnToCaller(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String jspChiamante = req.getHeader("referer");	
//			System.out.println( jspChiamante );		
		boolean tagliaQueryString = BooleanUtils.toBoolean( req.getParameter("tagliaQueryString") );
		
		if (tagliaQueryString && jspChiamante.lastIndexOf( "?" ) > -1) 
			jspChiamante = jspChiamante.substring( 0, jspChiamante.lastIndexOf( "?" ) );
		
//		System.out.println( jspChiamante );
		resp.sendRedirect( jspChiamante );
//		throw new RuntimeException("File "+file.getAbsoluteFile()+" non trovato.");
	}
}
