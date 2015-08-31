package com.github.tosdan.utils.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Daniele
 *
 */
public class DownloadServletAlt extends HttpServlet {
	public static final String GLOBAL_DOWNLOAD_FOLDER_PARAM = "GlobalDownloadFolder";
	public static final String DIRECT_DOWNLOAD_FROM_REQUEST = "DIRECT_DOWNLOAD_FROM_REQUEST";
	/**
	 * 
	 */
	private static final long serialVersionUID = -387567930206759932L;
	/**
	 * Attributo (o parametro) della request che deve contenere il percorso del file da scaricare.
	 * Il percorso deve essere relativo rispetto al parametro del context 'GlobalDownloadFolder'
	 */
	public static final String FILENAME_PARAM 		= DownloadServletAlt.class.getName() + ".Filename";
	public static final String DISPLAY_NAME_PARAM 	= DownloadServletAlt.class.getName() + ".DisplayName";
	public static final String ABSOLUTE_PATH 		= DownloadServletAlt.class.getName() + ".AbsolutePath";
	public static final String MIME_TYPE 			= DownloadServletAlt.class.getName() + ".MimeType";

	@Override protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doGet(req, resp); }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpServletDownloader downloader = getDownloader(req);
		downloader.download(req, resp);
	}

	
	/**
	 * Per poter sfruttare la servlet è necessario inserire alcuni attributi nella request. Questo metodo semplifica questa operazione.
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName) {
		prepareDownload(req, filename, outputFileDisplayName, null);
	}
	/**
	 * Per poter sfruttare la servlet è necessario inserire alcuni attributi nella request. Questo metodo semplifica questa operazione.
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 * @param mimeType Mime type del file da scaricare.
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName, String mimeType) {
		req.setAttribute(FILENAME_PARAM, filename);
		req.setAttribute(DISPLAY_NAME_PARAM, outputFileDisplayName);
		req.setAttribute(MIME_TYPE, mimeType);
	}

	/**
	 * Permette di ottenere la cartella globale in cui la servlet di download cerca i file da scaricare.
	 * @param ctx Contesto della webapp corrente.
	 * @return File della cartella di download
	 */
	public static File getDownloadDir(ServletContext ctx) {
		return new File(ctx.getInitParameter(GLOBAL_DOWNLOAD_FOLDER_PARAM));
	}
	
	
	private HttpServletDownloader getDownloader(HttpServletRequest req) {
		String path = this.getServletContext().getInitParameter(GLOBAL_DOWNLOAD_FOLDER_PARAM);
		
		HttpServletDownloader downloader = new HttpServletDownloader(FILENAME_PARAM, path);

		Cookie cookie = new Cookie( "fileDownload", "true" );
		cookie.setPath( "/" );
		downloader.addCookie(cookie);
		
		downloader.setContentType(this.getContentType(req));
		downloader.setOutputFilename(this.getOutputFilename(req));
		
		boolean requestDirectDownload = "true".equalsIgnoreCase(this.getInitParameter(DIRECT_DOWNLOAD_FROM_REQUEST));
		downloader.setDirectFromRequest(requestDirectDownload);
		
		return downloader;
	}
	
	
	protected String getContentType(HttpServletRequest req) {
		return getAttrOrParam(req, MIME_TYPE);
	}
	
	
	protected String getOutputFilename(HttpServletRequest req) {
		return getAttrOrParam(req, DISPLAY_NAME_PARAM);
	}

	
	protected String getAttrOrParam(HttpServletRequest req, String name) {
		String retval = null;
		
		if (req.getAttribute(name) != null) {
			retval = (String) req.getAttribute(name);
			
		} else if (req.getParameter(name) != null) {
			retval = req.getParameter(name);
			
		}		
		return retval;
	}
	
}
