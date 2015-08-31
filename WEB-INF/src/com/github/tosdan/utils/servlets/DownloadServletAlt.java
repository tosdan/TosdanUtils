package com.github.tosdan.utils.servlets;

import java.io.File;
import java.io.FileNotFoundException;
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
		
		download(getDownloader(req), resp, getFilename(req), getDownloadPath());
	}

	/**
	 * 
	 * @param resp Oggetto {@link HttpServletResponse}
	 * @param downloader Oggetto {@link HttpServletDownloader}
	 * @param downloadPath Cartella di base per tutti i download (Es. /tomcat/webapp1/WEB-INF/downloads).
	 * @param filename Nome del file da scaricare. Opzionalmente può essere comprensivo di percorso relativo. 
	 * @throws FileNotFoundException "File not found"
	 * @throws IOException "Can't read input file"
	 */
	protected void download(HttpServletDownloader downloader, HttpServletResponse resp, String filename, String downloadPath)
					throws FileNotFoundException, IOException {
		
		downloader.download(resp, filename, downloadPath);
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
	
	
	

	/**
	 * Recupera dai parametri del contesto il percorso della cartella globale di download
	 * @return
	 */
	protected String getDownloadPath() {
		return this.getServletContext().getInitParameter(GLOBAL_DOWNLOAD_FOLDER_PARAM);
	}
	
	/**
	 * Recupera dalla request il nome del file da scaricare (putrebbe essere comprensivo anche di path)
	 * @param req
	 * @return
	 */
	protected String getFilename( HttpServletRequest req ) {
		return getAttrOrParam(req, FILENAME_PARAM, true);
	}
	
	/**
	 * Produce un oggetto {@link HttpServletDownloader}
	 * @param req
	 * @return
	 */
	protected HttpServletDownloader getDownloader(HttpServletRequest req) {
		
		HttpServletDownloader downloader = new HttpServletDownloader();

		Cookie cookie = new Cookie( "fileDownload", "true" );
		cookie.setPath( "/" );
		downloader.addCookie(cookie);
		
		downloader.setContentType(this.getContentType(req));
		downloader.setOutputFilename(this.getOutputFilename(req));
		
		return downloader;
	}
	
	/**
	 * Recupera dalla request il content type da imposatre
	 * @param req
	 * @return
	 */
	protected String getContentType(HttpServletRequest req) {
		return getAttrOrParam(req, MIME_TYPE, false);
	}
	
	/**
	 * Recupera dalla request il filename che verrà mostrato in fase di download
	 * @param req
	 * @return
	 */
	protected String getOutputFilename(HttpServletRequest req) {
		return getAttrOrParam(req, DISPLAY_NAME_PARAM, false);
	}

	/**
	 * Recupera un parametro dalla request
	 * @param req Oggetto {@link HttpServletRequest} corrente
	 * @param name Nome del parametro da recuperare
	 * @param checkExists Impone un controllo sull'effettiva presenza del parametro: se fosse assente viene lanciata un'eccezione
	 * @return
	 */
	protected String getAttrOrParam(HttpServletRequest req, String name, boolean checkExists) {
		String retval = null;
		boolean paramsFromRequest = "true".equalsIgnoreCase(this.getInitParameter(DIRECT_DOWNLOAD_FROM_REQUEST));
		
		if (req.getAttribute(name) != null) {
			retval = (String) req.getAttribute(name);
			
		} else if (paramsFromRequest && req.getParameter(name) != null) {
			retval = req.getParameter(name);
			
		}
		
		if (checkExists && retval == null) {
			throw new IllegalArgumentException("Parametro ["+name+"] mancante nella request.");
		}
		
		return retval;
	}
	
}
