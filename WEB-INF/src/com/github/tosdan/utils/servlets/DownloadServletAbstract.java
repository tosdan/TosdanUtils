package com.github.tosdan.utils.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * Servlet di download file.
 * 
 * @author Daniele
 * @version 1.0.0-r2015-05-10
 */
public abstract class DownloadServletAbstract extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1867142326405806235L;

	@Override protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	
	/**
	 * Esegue il Download del file.
	 * @param req Request 
	 * @param resp Response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doService(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String filename = getFilenameFromRequest(req);
		filename = URLDecoder.decode(filename, "UTF-8");
		
		File file = new File(filename);
		
		ServletOutputStream out = resp.getOutputStream();
		
		if (file.exists()) {
			if (file.canRead()) {
				
				this.transferFile(out, req, resp, file);
				
			} else {
				errorCantReadFile(out, req, resp, file);
				// Il file esiste ma non e' leggibile
			}				
			
		} else {
			errorFileDoesntExists(out, req, resp, file);
			// Il file non esiste.
		}		
	}

	/**
	 * Produce il percorso assoluto del file da scaricare.
	 * @param req Request
	 * @return Percorso assoluto del file.
	 */
	protected abstract String getFilenameFromRequest(HttpServletRequest req);
	protected abstract void errorFileDoesntExists(ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException;
	protected abstract void errorCantReadFile(ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException;

	/**
	 * Imposta la response per il download, effettua il recupero del file e invia il file al browser.
	 * @param out Outputstream della servlet
	 * @param resp Response
	 * @param file File fisico.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected void transferFile(ServletOutputStream out, HttpServletRequest req, HttpServletResponse resp, File file)
			throws IOException {
		
		setContentLenth(resp, file);
		
		setContentType(req, resp);
		
		setCookies(resp);
		
		setContentDisposition(req, resp, file);
		
		copyStream(out, file);
			
	}
	
	private void setContentType( HttpServletRequest req, HttpServletResponse resp ) {
		String contentType = getContentType(req);
		if (contentType == null || contentType.trim().isEmpty()) {
			contentType = getDefaultContentType();
		}
		resp.setContentType(contentType);
	}
	
	private String getDefaultContentType() {
		return "application/octect-stream";
	}

	/**
	 * Imposta l'header Content-Length che indica a chi effettua il download lo spazio occupato dal file.
	 * @param resp Response
	 * @param file File fisico.
	 */
	private void setContentLenth( HttpServletResponse resp, File file ) {
		resp.setContentLength( ( int ) file.length() );
	}
	
	/**
	 * Restituisce il tipo mime da utilizzare come content type nella response.
	 * @param req 
	 * @return stringa con mime type
	 */
	protected String getContentType(HttpServletRequest req) {
		return null;
	}

	/**
	 * Imposta l'header "Content-Disposition". Di default inserisce l'attributo attachment e filename.
	 * Il filename è recuperato chiamando il metodo getFilenameAlias() oppure dal nome del file fisico.
	 * @param resp Response
	 * @param file File fisico.
	 */
	protected void setContentDisposition(HttpServletRequest req, HttpServletResponse resp, File file) {
		String filename = getFilenameAlias(req, file);
		if (filename == null || filename.trim().isEmpty()) {
			filename = file.getName();					
		}
		String contentDisposition = getContentDisposition(file, filename);
		if (contentDisposition == null || contentDisposition.trim().isEmpty()) {
			contentDisposition = getDefaultContentDisposition(filename);
		}
		resp.setHeader("Content-Disposition", contentDisposition);
	}
	
	private String getDefaultContentDisposition(String filename) {
		return "attachment; filename=\"" + filename + "\"";
	}
	
	/**
	 * Restituisce il content-disposition da impostare nella response.
	 * @param file File fisico per il download
	 * @param filename Il nome definitivo del file (può essere l'alias o il nome del file fisico).
	 * @return
	 */
	protected String getContentDisposition(File file, String filename) {
		return null;
	}
	
	/**
	 * Sovrascrivere questo metodo per definire il nome che il file deve avere (solo se diverso da quello del file fisico)
	 * in fase di download.
	 * @param req Request
	 * @param resp Response
	 * @param file File fisico per il download
	 * @return Un alias per il nome del file che verrà proposto all'utente in fase di download
	 */
	protected String getFilenameAlias(HttpServletRequest req, File file) {
		return null;
	}

	/**
	 * Imposta i cookies ottenuti dal metodo getCookies()
	 * @param resp Response
	 */
	protected void setCookies(HttpServletResponse resp) {
		if (getCookies() != null) {
			for (Cookie cookie : getCookies()) {
				resp.addCookie( cookie );
			}
		}
	}
	
	/**
	 * Restituisce la lista di cookies da aggiungere alla Response.
	 * @return lista dei cookies
	 */
	protected List<Cookie> getCookies() {
		return new ArrayList<Cookie>();
	}

	/**
	 * Legge lo stream di input del file da scaricare e lo trasfreisce sull'Outputstream della servlet.
	 * @param out Outputstream della servlet.
	 * @param file File fisico per il download.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void copyStream(ServletOutputStream out, File file) 
			throws IOException {
		IOUtils.copy(getFileInputStream(file), out);
	}
	
	/**
	 * Produce lo stream per leggere il file da scaricare.
	 * @param file File fisico da scaricare.
	 * @return Flusso di lettura del file.
	 * @throws FileNotFoundException
	 */
	protected FileInputStream getFileInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}
	
}
