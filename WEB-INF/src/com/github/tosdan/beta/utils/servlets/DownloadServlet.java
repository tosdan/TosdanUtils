package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;

@SuppressWarnings( "serial" )
public class DownloadServlet extends HttpServlet
{
	private String nomeFile;

	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		File file = new File(this.nomeFile);//( filePath );
		
//		System.out.println(  file.getAbsoluteFile() );
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream( file );
		
			resp.setContentLength( ( int ) file.length() );
			resp.setContentType( "application/x-download" ); // oppure octect-stream . Il browser non deve riconoscere il contenuto come qualcosa di associato ad una operazione speciale
//			response.setContentType( getMimeType( file ) );
			resp.setHeader( "Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );
			
			ServletOutputStream out = resp.getOutputStream();
			BufferedOutputStream bufferOut = new BufferedOutputStream( out );
			int b = 0;
			byte[] bufferData = new byte[ 8192 ];
			
			while ( ( b = fis.read( bufferData ) ) != -1 ) {
				bufferOut.write( bufferData, 0, b );
			}
			
			fis.close();
			bufferOut.flush();
			bufferOut.close();
//			out.close();
			
		} catch ( Exception e ) {
			// e.printStackTrace();
			req.getSession().setAttribute( "DownloadServletError", "File non trovato: " + file.getName() );			
			System.out.println( "Errore: impossibile trovare il file: " + file.getAbsolutePath() );
			
			String jspChiamante = req.getHeader("referer");	
//				System.out.println( jspChiamante );		
			boolean tagliaQueryString = BooleanUtils.toBoolean( req.getParameter("tagliaQueryString") );
			
			if (tagliaQueryString && jspChiamante.lastIndexOf( "?" ) > -1) 
				jspChiamante = jspChiamante.substring( 0, jspChiamante.lastIndexOf( "?" ) );
			
//			System.out.println( jspChiamante );
			resp.sendRedirect( jspChiamante );
			
		}
	}
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		this.nomeFile = req.getParameter( "nomeFile" );
		this.doService( req, resp );
	}
	
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
//		String uri = req.getRequestURI();		
//		String percorsoDownloadServlet = req.getContextPath() + req.getServletPath();	
//		this.nomeFile = uri.replaceAll( (percorsoDownloadServlet + "/"), "" );
		
		this.nomeFile = req.getParameter( "nomeFile" );
		this.nomeFile = URLDecoder.decode( nomeFile, "ISO-8859-1" );		
		this.nomeFile = StringEscapeUtils.unescapeHtml4( nomeFile );
		this.doService( req, resp );
//		System.out.println( "nome file : " + nomeFile + "\npercorso servlet: "+ percorsoDownloadServlet );
				
		//String[] tokens = uri.split( "/" );
		//String filesDir = ( String ) getServletContext().getInitParameter( "filesDir" );
		//String filePath = filesDir + tokens[ tokens.length - 1 ];
	}

	public String getMimeType( File file ) {
		MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
		return mime.getContentType( file );
	}
	
}
