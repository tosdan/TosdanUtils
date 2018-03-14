package com.github.tosdan.utils.servlets;

import java.io.File;

public class DownloadServletAltParams {
	/**
	 * Nome (e eventualmente percorso relativo) del file da scaricare
	 */
	private String filename;
	 /**
	  * Nome file che verrà mostrato all'utente che effettua il download
	  */
	private String outputFileDisplayName;
	/**
	 * Mime type del file da scaricare.
	 */
	private String mimeType;
	/**
	 * Flag per mantenere o togliere il flag Content-Disposition: attachment, flag che cerca di forzare il download invece della preview nel browser
	 */
	private boolean attachment;
	/**
	 * Cartella alternativa alla cartella Download predefinita. E' sottocartella nel percorso assoluto contenuto nel parametro del context <code>WebAppsData</code>.
	 */
	private String folder;
	/**
	 * File da scaricare.
	 */
	private File absoluteFile;
	public DownloadServletAltParams(String filename, String outputFileDisplayName, String mimeType) {
		this(filename, outputFileDisplayName, mimeType, true);
	}
	public DownloadServletAltParams(String filename, String outputFileDisplayName, String mimeType, boolean attachment) {
		this(filename, outputFileDisplayName, mimeType, attachment, null);
	}
	public DownloadServletAltParams(String filename, String outputFileDisplayName, String mimeType, boolean attachment, String folder) {
		this(filename, outputFileDisplayName, mimeType, attachment, folder, null);
	}
	public DownloadServletAltParams(String filename, String outputFileDisplayName, String mimeType, boolean attachment, String folder, File absoluteFile) {
		this.filename = filename;
		this.outputFileDisplayName = outputFileDisplayName;
		this.mimeType = mimeType;
		this.attachment = attachment;
		this.folder = folder;
		this.absoluteFile = absoluteFile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOutputFileDisplayName() {
		return outputFileDisplayName;
	}
	public void setOutputFileDisplayName(String outputFileDisplayName) {
		this.outputFileDisplayName = outputFileDisplayName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public boolean getAttachment() {
		return attachment;
	}
	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public File getAbsoluteFile() {
		return absoluteFile;
	}
	public void setAbsoluteFile(File absoluteFile) {
		this.absoluteFile = absoluteFile;
	}
}