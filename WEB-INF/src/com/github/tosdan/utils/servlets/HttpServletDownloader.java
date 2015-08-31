package com.github.tosdan.utils.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class HttpServletDownloader {

	private String reqFilenameParam;
	private String filename;
	private String defaultDownloadFolder;
	/**
	 * Consente il download direttamente dalla request invece di passare per una servlet intermediaria.
	 * Ovvero di default il nome del file da scaricare viene cecato solo negli attributi della request invece 
	 * di controllare anche i parametri. Impostare a false per controllare anche i parametri.
	 */
	private boolean directFromRequest;
	private boolean showContentLength;
	
	private List<Cookie> cookies;
	private String contentType;
	private String contentDisposition;
	private String outputFilename;

	/**
	 * 
	 * @param reqFilenameParam Nome del parametro per recuperare il nome del file dalla request 
	 * o il nome del file più path relativa alla cartella base di download. (Es. myfolder/myfile.txt oppure /tomcat/webapp1/WEB-INF/downloads/myfolder/myfile.txt)
	 * <p>Diventa inutile se viene fornito il parametro <code><b>filename</b></code>
	 * @param filename Nome del file da scaricare. Può essere comprensivo di percorso assoluto, relativo o senza. Ovviamente,
	 * nel caso sia con percorso assoluto, non ha senso fornire il parametro <code><b>defaultDownloadFolder</b></code>.
	 * <p>Ha la priorità sul parametro <code><b>reqFilenameParam</b></code> che quindi può essere nullo.
	 */
	public HttpServletDownloader(String reqFilenameParam, String filename) {
		this(reqFilenameParam, null, filename);
	}
	
	/**
	 * 
	 * @param reqFilenameParam Nome del parametro per recuperare il nome del file dalla request 
	 * o il nome del file più path relativa alla cartella base di download. (Es. myfolder/myfile.txt oppure /tomcat/webapp1/WEB-INF/downloads/myfolder/myfile.txt)
	 * <p>Diventa inutile se viene fornito il parametro <code><b>filename</b></code>
	 * @param defaultDownloadFolder Cartella di base per tutti i download (Es. /tomcat/webapp1/WEB-INF/downloads)
	 * @param filename Nome del file da scaricare. Può essere comprensivo di percorso assoluto, relativo o senza. Ovviamente,
	 * nel caso sia con percorso assoluto, non ha senso fornire il parametro <code><b>defaultDownloadFolder</b></code>.
	 * <p>Ha la priorità sul parametro <code><b>reqFilenameParam</b></code> che quindi può essere nullo.
	 */
	public HttpServletDownloader(String reqFilenameParam, String defaultDownloadFolder, String filename) {
		this.reqFilenameParam = reqFilenameParam;
		this.defaultDownloadFolder = defaultDownloadFolder;
		this.directFromRequest = false;
		this.showContentLength = false;
		this.filename = filename;
		this.cookies = new ArrayList<Cookie>();
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws FileNotFoundException "File not found"
	 * @throws IOException "Can't read input file"
	 */
	public void download(HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException {

		this.filename = this.filename == null ? this.getFilenameFromRequest(req) : this.filename;
		try {
			this.filename = URLDecoder.decode(this.filename, "UTF-8");
			
		} catch ( UnsupportedEncodingException e ) {			
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
		
		File file = new File(this.filename);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath() + " (File not found)");
			
		} else if (!file.canRead()) {
			
			throw new IOException(file.getAbsolutePath() + " (Can't read input file)");
			
		} else {

			ServletOutputStream out = resp.getOutputStream();
			this.transferFile(out, req, resp, file);
			
		}
	}

	/**
	 * Imposta la response per il download, effettua il recupero del file e invia il file al browser.
	 * @param out Outputstream della servlet
	 * @param resp Response
	 * @param file File fisico.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void transferFile(ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file)
			throws IOException {
		
		this.setContentLenth(resp, file);
		
		this.setContentType(resp);
		
		this.setCookies(resp);
		
		this.setContentDisposition(resp, file);
		
		this.copyStream(out, file);
			
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 */
	private void setContentType(HttpServletResponse resp) {
		if (this.contentType == null || this.contentType.trim().isEmpty()) {
			this.contentType = "application/octect-stream";
		}
		resp.setContentType(this.contentType);
	}
	
	/**
	 * Imposta l'header Content-Length che indica a chi effettua il download lo spazio occupato dal file.
	 * @param resp Response
	 * @param file File fisico.
	 */
	private void setContentLenth(HttpServletResponse resp, File file) {
		if (this.showContentLength) {
			resp.addHeader("Content-Length", Long.toString(file.length()));
		}
	}

	/**
	 * Imposta l'header "Content-Disposition". Di default inserisce l'attributo attachment e filename.
	 * Il filename è recuperato chiamando il metodo getFilenameAlias() oppure dal nome del file fisico.
	 * @param resp Response
	 * @param file File fisico.
	 */
	private void setContentDisposition(HttpServletResponse resp, File file) {
		String contentDisposition = this.contentDisposition;
		if (contentDisposition == null || contentDisposition.trim().isEmpty()) {
			contentDisposition = this.getDefaultContentDisposition( this.getCurrentFilename(file) );
		}
		resp.setHeader("Content-Disposition", contentDisposition);
	}

	private String getCurrentFilename(File file) {
		String filename = this.outputFilename;
		if (filename == null || filename.trim().isEmpty()) {
			filename = file.getName();
		}
		return filename;
	}
	
	private String getDefaultContentDisposition(String filename) {
		return "attachment; filename=\"" + filename + "\"";
	}

	/**
	 * Legge lo stream di input del file da scaricare e lo trasfreisce sull'Outputstream della servlet.
	 * @param out Outputstream della servlet.
	 * @param file File fisico per il download.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void copyStream(ServletOutputStream out, File file) 
			throws IOException {
		IOUtils.copy(new FileInputStream(file), out);
	}
	
	/**
	 * Recupera il nome del file da scaricare
	 * @param req
	 * @return
	 */
	private String getFilenameFromRequest(HttpServletRequest req) {
		String fileAbsolutePath = this.getAttrOrParam(req);

		if (fileAbsolutePath == null) {
			throw new IllegalArgumentException("Parametro ["+this.reqFilenameParam+"] mancante nella request.");
		}
		
		String path = this.defaultDownloadFolder == null ? "" : this.defaultDownloadFolder;
		
		fileAbsolutePath = path + fileAbsolutePath;
		
		return fileAbsolutePath;
	}

	/**
	 * Recupera dagli attributi, o dai parametri della request, il valore del parametro contenente il nome del file da scaricare.
	 * @param req
	 * @return
	 */
	private String getAttrOrParam(HttpServletRequest req) {
		String retval = null;
		
		if (req.getAttribute(this.reqFilenameParam) != null) {
			retval = (String) req.getAttribute(this.reqFilenameParam);
			
		} else if (req.getParameter(this.reqFilenameParam) != null && this.directFromRequest) {
			retval = req.getParameter(this.reqFilenameParam);
			
		}
		
		return retval;
	}

	/**
	 * Aggiunge i cookies alla response.
	 * @param resp Response
	 */
	private void setCookies(HttpServletResponse resp) {
		for (Cookie cookie : this.cookies) {
			resp.addCookie( cookie );
		}
	}
	
	/**
	 * Aggiunge un cookie alla lista dei cookies inviati nella response (che di default è vuota) 
	 */
	public void addCookie(Cookie cookie) {
		this.cookies.add(cookie);
	}
	
	/**
	 * Consente di impostare l'header content-type che altrimenti di default è <code><strong>application/octect-stream</strong></code>
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * Consente di impostare l'header content-disposition. NB. Per impostare semplicemente il nome del file di output
	 * utilizzare il metodo <cpde><storng>setOutputFilename</strong></code>
	 * @param contentDisposition
	 */
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	
	/**
	 * Imposta il nome del file come verrà mostrato all'utente in fase di effettivo download.
	 * @param outputFilename
	 * @return
	 */
	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}
	
	/**
	 * <p>Imposta la possibilità di effettuare il download direttamente da request del browser, invece di imporre che  
	 * la chiamata arrivi tramite un'altra servlet. 
	 * <p>In pratica il parametro col nome del file viene letto anche da parameter e non solo da attribute della request.
	 * @param directFromRequest
	 */
	public void setDirectFromRequest( boolean directFromRequest ) {
		this.directFromRequest = directFromRequest;
	}
}
