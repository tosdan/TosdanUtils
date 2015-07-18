package com.github.tosdan.utils.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Daniele
 *
 */
public class DownloadServletImpl extends DownloadServletAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -387567930206759932L;
	/**
	 * Attributo (o parametro) della request che deve contenere il percorso del file da scaricare.
	 * Il percorso deve essere relativo rispetto al parametro del context 'GlobalDownloadFolder'
	 */
	public static final String FILENAME_PARAM 		= "tosdan.DownloadServletImpl.Filename";
	public static final String DISPLAY_NAME_PARAM 	= "tosdan.DownloadServletImpl.DisplayName";
	public static final String ABSOLUTE_PATH 		= "tosdan.DownloadServletImpl.AbsolutePath";
	public static final String MIME_TYPE 			= "tosdan.DownloadServletImpl.MimeType";

	@Override
	protected String getContentType(HttpServletRequest req) {
		return getAttrOrParam(req, MIME_TYPE);
	}
	
	@Override
	protected String getFilenameAlias(HttpServletRequest req, File file) {
		return getAttrOrParam(req, DISPLAY_NAME_PARAM);
	}
	
	@Override
	protected String getFilenameFromRequest(HttpServletRequest req) {
		String fileAbsolutePath = getAttrOrParam(req, FILENAME_PARAM);

		if (fileAbsolutePath == null) {
			throw new RuntimeException("Parametro ["+FILENAME_PARAM+"] mancante nella request.");
		}
		
		String path = this.getServletContext().getInitParameter("GlobalDownloadFolder");
		
		if (req.getAttribute(ABSOLUTE_PATH) != null) {
			path = "";
		}
		
		fileAbsolutePath = path + fileAbsolutePath;
		
		return fileAbsolutePath;
	}
	
	protected String getAttrOrParam(HttpServletRequest req, String name) {
		String retval = null;
		String allowRewParams = this.getInitParameter("AllowRequestParameter");
		
		if (req.getAttribute(name) != null) {
			retval = (String) req.getAttribute(name);
			
		} else if ("true".equalsIgnoreCase(allowRewParams) && req.getParameter(name) != null) {
			retval = req.getParameter(name);
			
		}
		return retval;
	}
	/**
	 * Questo coockie e' necessario per comunicare a jQuery.fileDownload.js che il download e' avvenuto. 
	 * (Va inserito prima di iniziare a trasmettere il flusso di output)
	 */
	@Override
	protected List<Cookie> getCookies() {
		List<Cookie> retval = new ArrayList<Cookie>();

		Cookie cookie = new Cookie( "fileDownload", "true" );
		cookie.setPath( "/" );
		retval.add(cookie);
		
		return retval;
	}
	
	@Override protected void errorFileDoesntExists( ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file )
			 throws IOException, ServletException {
		sendError(out, req, resp, file, "File non trovato: " + file.getName());
	}
	@Override protected void errorCantReadFile( ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file ) 
			throws IOException, ServletException {
		sendError(out, req, resp, file, "Impossibile leggere il file: " +file.getName());
	}

	protected void sendError(ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file, String msg) 
			throws IOException, ServletException {
//		req.getSession().setAttribute( "DownloadServletBeta/Error", msg );
		
		resp.setHeader("content-type","text/html; charset=UTF-8");
		resp.setContentType( "text/html" );
//		msg = "{ \"name\": \"error\", \"message\": \""+msg+"\" }";
		msg = "<span>"+msg+"</span>";
		out.print( msg );
		
	}

	
	/**
	 * L'idea è di poter impostare il content type dinamicamente in questo modo: resp.setContentType(getMimeType(file));
	 * Al momento è inutilizzato
	 * @param file
	 * @return
	 */
	protected String getMimeType( File file ) {
		MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
		return mime.getContentType( file );
	}
	
}
