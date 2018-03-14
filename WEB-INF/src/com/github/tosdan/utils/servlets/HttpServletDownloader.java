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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class HttpServletDownloader {

	private List<Cookie> cookies;
	private String contentType;
	private String contentDisposition;
	private String outputFilename;
	private boolean showContentLength;
	private boolean contentDispositionAttachment;

	public HttpServletDownloader() {
		this.showContentLength = false;
		this.contentDispositionAttachment = true;
		this.cookies = new ArrayList<Cookie>();
	}
	

	/**
	 * 
	 * @param resp
	 * @param filename Nome del file da scaricare comprensivo di percorso assoluto.
	 * @throws FileNotFoundException "File not found"
	 * @throws IOException "Can't read input file"
	 */
	public void download(HttpServletResponse resp, String filename) throws FileNotFoundException, IOException {
		this.download(resp, filename, null);
	}
	
	
	/**
	 * 
	 * @param resp Oggetto {@link HttpServletResponse} corrente
	 * @param filename Nome del file da scaricare. Opzionalmente può essere comprensivo di percorso relativo (alla cartella indicata con <code>defaultDownloadFolder</code>). 
	 * @param defaultDownloadFolder Cartella di base per tutti i download (Es. /tomcat/webapp1/WEB-INF/downloads).
	 * @throws FileNotFoundException "File not found"
	 * @throws IOException "Can't read input file"
	 */
	public void download(HttpServletResponse resp, String filename, String defaultDownloadFolder) throws FileNotFoundException, IOException {

		try {
			filename = URLDecoder.decode(filename, "UTF-8");
			
		} catch ( UnsupportedEncodingException e ) {			
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
		
		String path = defaultDownloadFolder == null ? "" : defaultDownloadFolder;		
		String fileAbsolutePath = path + filename;
		
		File file = new File(fileAbsolutePath);
		
		download(resp, file);
	}
	
	
	/**
	 * 
	 * @param resp Oggetto {@link HttpServletResponse} corrente
	 * @param file File da scaricare. 
	 * @throws FileNotFoundException "File not found"
	 * @throws IOException "Can't read input file"
	 */
	public void download(HttpServletResponse resp, File file) throws FileNotFoundException, IOException {
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath() + " (File not found)");
			
		} else if (!file.canRead()) {
			
			throw new IOException(file.getAbsolutePath() + " (Can't read input file)");
			
		} else {
			
			this.transferFile(resp.getOutputStream(), resp, file);
			
		}
	}

	
	/**
	 * Imposta la response per il download, effettua il recupero del file e invia il file al browser.
	 * @param out Outputstream della servlet
	 * @param resp Response corrente
	 * @param file File fisico.
	 * @throws IOException 
	 */
	private void transferFile(ServletOutputStream out, HttpServletResponse resp, File file)
			throws IOException {
		
		this.setContentLenth(resp, file);
		
		this.setContentType(resp);
		
		this.setCookies(resp);
		
		this.setContentDisposition(resp, file);
		
		this.copyStream(out, file);
			
	}
	
	/**
	 * Imposta il content type della response
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
			contentDisposition = this.getDefaultContentDisposition( this.getOutputFilename(file) );
		}
		resp.setHeader("Content-Disposition", contentDisposition);
	}

	/**
	 * Recupera il filename come verrà mostrato in fase di download 
	 * @param file
	 * @return
	 */
	private String getOutputFilename(File file) {
		String filename = this.outputFilename;
		if (filename == null || filename.trim().isEmpty()) {
			filename = file.getName();
		}
		return filename;
	}
	
	private String getDefaultContentDisposition(String filename) {
		String attachment = this.contentDispositionAttachment ? "attachment; " : "";
		return attachment + "filename=\"" + filename + "\"";
	}
	
	/**
	 * Flag per mantenere o rimuovere il flag <code>attachment</code>. <br>
	 * La presenza di questo flag impedisce l'anteprima dei PDF nei browser.
	 * @param contentDispositionAttachment
	 */
	public void setContentDispositionAttachment(boolean contentDispositionAttachment) {
		this.contentDispositionAttachment = contentDispositionAttachment;
	}

	/**
	 * Legge lo stream di input del file da scaricare e lo trasfreisce sull'Outputstream della servlet.
	 * @param out Outputstream della servlet.
	 * @param file File fisico per il download.
	 * @throws IOException
	 */
	private void copyStream(ServletOutputStream out, File file) 
			throws IOException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			IOUtils.copy(fileInputStream, out);
		} finally {
			IOUtils.closeQuietly(fileInputStream);			
		}
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
}
