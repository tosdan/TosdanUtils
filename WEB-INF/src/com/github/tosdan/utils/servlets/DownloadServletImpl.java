package com.github.tosdan.utils.servlets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniele
 *
 */
public class DownloadServletImpl extends DownloadServletAbstract {

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
		req.setAttribute(DownloadServletImpl.FILENAME_PARAM, filename);
		req.setAttribute(DownloadServletImpl.DISPLAY_NAME_PARAM, outputFileDisplayName);
		req.setAttribute(DownloadServletImpl.MIME_TYPE, mimeType);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -387567930206759932L;
	/**
	 * Attributo (o parametro) della request che deve contenere il percorso del file da scaricare.
	 * Il percorso deve essere relativo rispetto al parametro del context 'GlobalDownloadFolder'
	 */
	public static final String FILENAME_PARAM 		= "com.github.tosdan.utils.servlets.DownloadServletImpl.Filename";
	public static final String DISPLAY_NAME_PARAM 	= "com.github.tosdan.utils.servlets.DownloadServletImpl.DisplayName";
	public static final String ABSOLUTE_PATH 		= "com.github.tosdan.utils.servlets.DownloadServletImpl.AbsolutePath";
	public static final String MIME_TYPE 			= "com.github.tosdan.utils.servlets.DownloadServletImpl.MimeType";

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
		if (req.getAttribute(name) != null) {
			retval = (String) req.getAttribute(name);
		} else if (req.getParameter(name) != null) {
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
