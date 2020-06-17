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
	public static final String WEB_APP_FOLDER_PARAM = "WebAppsData";
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
	public static final String MIME_TYPE 			= DownloadServletAlt.class.getName() + ".MimeType";
	public static final String ATTACHMENT 			= DownloadServletAlt.class.getName() + ".Attachment";
	public static final String SHOW_CONTENT_LENGTH 	= DownloadServletAlt.class.getName() + ".ShowContentLength";
	public static final String FOLDER	 			= DownloadServletAlt.class.getName() + ".Folder";
	public static final String ABSOLUTE_FILE	 	= DownloadServletAlt.class.getName() + ".AbsoluteFile";

	@Override protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doGet(req, resp); }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpServletDownloader downloader = getDownloader(req);

		File absoluteFile = getAbsoluteFile(req);
		if (absoluteFile != null) {
			download(downloader, resp, absoluteFile);
			
		} else {
			download(downloader, resp, getFilename(req), getDownloadPath(req));
			
		}
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
	protected void download(HttpServletDownloader downloader, HttpServletResponse resp, File file)
					throws FileNotFoundException, IOException {

		downloader.download(resp, file);
		
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
	 * 
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 * @param mimeType Mime type del file da scaricare.
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName, String mimeType) {
		prepareDownload(req, filename, outputFileDisplayName, mimeType, true);
	}
	
	/**
	 * 
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 * @param mimeType Mime type del file da scaricare.
	 * @param attachment Flag per mantenere o togliere il flag Content-Disposition: attachment, flag che cerca di forzare il download invece della preview nel browser
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName, String mimeType, boolean attachment) {
		prepareDownload(req, filename, outputFileDisplayName, mimeType, attachment, null);
	}
	
	/**
	 * Per poter sfruttare la servlet è necessario inserire alcuni attributi nella request. Questo metodo semplifica questa operazione.
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 * @param mimeType Mime type del file da scaricare.
	 * @param attachment Flag per mantenere o togliere il flag Content-Disposition: attachment, flag che cerca di forzare il download invece della preview nel browser
	 * @param folder Cartella alternativa alla cartella Download predefinita. E' sottocartella nel percorso assoluto contenuto nel parametro del ceontex <code>WebAppsData</code>.
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName, String mimeType, boolean attachment, String folder) {
		prepareDownload(req, filename, outputFileDisplayName, mimeType, attachment, folder, null);
	}
	
	/**
	 * Per poter sfruttare la servlet è necessario inserire alcuni attributi nella request. Questo metodo semplifica questa operazione.
	 * @param req
	 * @param filename Nome (e eventualmente percorso relativo) del file da scaricare
	 * @param outputFileDisplayName Nome file che verrà mostrato all'utente che effettua il download
	 * @param mimeType Mime type del file da scaricare.
	 * @param attachment Flag per mantenere o togliere il flag Content-Disposition: attachment, flag che cerca di forzare il download invece della preview nel browser
	 * @param folder Cartella alternativa alla cartella Download predefinita. E' sottocartella nel percorso assoluto contenuto nel parametro del ceontex <code>WebAppsData</code>.
	 * @param absoluteFile Cartella (con percorso assoluto) alternativa alla cartella Download predefinita.
	 */
	public static void prepareDownload(HttpServletRequest req, String filename, String outputFileDisplayName, String mimeType, boolean attachment, String folder, File absoluteFile) {
		DownloadServletAltParams params = new DownloadServletAltParams(filename, outputFileDisplayName, mimeType, null, attachment, folder, absoluteFile);
		prepareDownload(req, params);
	}
	
	/**
	 * Per poter sfruttare la servlet è necessario inserire alcuni attributi nella request. Questo metodo semplifica questa operazione.
	 * @param req
	 * @param params
	 */
	public static void prepareDownload(HttpServletRequest req, DownloadServletAltParams params) {
		req.setAttribute(FILENAME_PARAM, params.getFilename());
		req.setAttribute(DISPLAY_NAME_PARAM, params.getOutputFileDisplayName());
		req.setAttribute(MIME_TYPE, params.getMimeType());
		req.setAttribute(ATTACHMENT, params.getAttachment());
		req.setAttribute(SHOW_CONTENT_LENGTH, params.getShowContentLength());
		req.setAttribute(FOLDER, params.getFolder());
		req.setAttribute(ABSOLUTE_FILE, params.getAbsoluteFile());
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
	 * 
	 * @param ctx
	 * @param altFolder Cartella alternativa alla canonica Download
	 * @return
	 */
	public static File getDownloadDir(ServletContext ctx, String altFolder) {
		return new File(ctx.getInitParameter(WEB_APP_FOLDER_PARAM) + "/" +altFolder + "/");
	}

	private File getAbsoluteFile(HttpServletRequest req) {
		return (File) getAttrOrParam(req, ABSOLUTE_FILE, false);
	}
	
	
	

	/**
	 * Recupera dai parametri del contesto il percorso della cartella globale di download
	 * @param req 
	 * @return
	 */
	protected String getDownloadPath(HttpServletRequest req) {
		
		String alternativeDownloadFolder = (String) getAttrOrParam(req, FOLDER, false);
		String paramName = GLOBAL_DOWNLOAD_FOLDER_PARAM;
		String altFolder = "";
		
		if (alternativeDownloadFolder != null) {
			paramName = WEB_APP_FOLDER_PARAM;
			altFolder = "/" + alternativeDownloadFolder;		
		}
		
		String rootFolder = this.getServletContext().getInitParameter(paramName);
		String absolutePath = rootFolder + altFolder + "/";
		
		return absolutePath;
	}
	
	/**
	 * Recupera dalla request il nome del file da scaricare (putrebbe essere comprensivo anche di path)
	 * @param req
	 * @return
	 */
	protected String getFilename( HttpServletRequest req ) {
		return (String) getAttrOrParam(req, FILENAME_PARAM, true);
	}
	
	/**
	 * Produce un oggetto {@link HttpServletDownloader}
	 * @param req
	 * @return
	 */
	protected HttpServletDownloader getDownloader(HttpServletRequest req) {
		Object attachmentParamValue = getAttrOrParam(req, ATTACHMENT, false);
		// attachment è true di default a meno che non sia presente un valore per il parametro ATTACHMENT
		Boolean attachment = attachmentParamValue == null || (Boolean) attachmentParamValue;
		
		HttpServletDownloader downloader = new HttpServletDownloader();
		downloader.setContentDispositionAttachment(attachment);

		Cookie cookie = new Cookie( "fileDownload", "true" );
		cookie.setPath( "/" );
		downloader.addCookie(cookie);
		
		downloader.setContentType(this.getContentType(req));
		downloader.setOutputFilename(this.getOutputFilename(req));
		downloader.setShowContentLength(this.isShowContentLength(req));
		
		return downloader;
	}
	
	/**
	 * Recupera dalla request il content type da imposatre
	 * @param req
	 * @return
	 */
	protected String getContentType(HttpServletRequest req) {
		return (String) getAttrOrParam(req, MIME_TYPE, false);
	}
	
	/**
	 * Recupera dalla request il flag per determinare se mostrare l'header content-length
	 * @param req
	 * @return
	 */
	protected Boolean isShowContentLength(HttpServletRequest req) {
		Object value = getAttrOrParam(req, SHOW_CONTENT_LENGTH, false);
		
		if (value == null) {
			return false;
			
		} else {
			if (value instanceof Boolean) {
				return (Boolean) value;
				
			} else {
				return new Boolean(value.toString().toLowerCase());
			}
		}
	}
	
	/**
	 * Recupera dalla request il filename che verrà mostrato in fase di download
	 * @param req
	 * @return
	 */
	protected String getOutputFilename(HttpServletRequest req) {
		return (String) getAttrOrParam(req, DISPLAY_NAME_PARAM, false);
	}

	/**
	 * Recupera un parametro dalla request
	 * @param req Oggetto {@link HttpServletRequest} corrente
	 * @param name Nome del parametro da recuperare
	 * @param checkExists Impone un controllo sull'effettiva presenza del parametro: se fosse assente viene lanciata un'eccezione
	 * @return
	 */
	protected Object getAttrOrParam(HttpServletRequest req, String name, boolean checkExists) {
		Object retval = null;
		boolean paramsFromRequest = "true".equalsIgnoreCase(this.getInitParameter(DIRECT_DOWNLOAD_FROM_REQUEST));
		
		if (req.getAttribute(name) != null) {
			retval = req.getAttribute(name);
			
		} else if (paramsFromRequest && req.getParameter(name) != null) {
			retval = req.getParameter(name);
			
		}
		
		if (checkExists && retval == null) {
			throw new IllegalArgumentException("Parametro ["+name+"] mancante nella request.");
		}
		
		return retval;
	}
	
}
