package com.github.tosdan.beta.utils.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import com.github.tosdan.utils.filters.BasicFilter;

public class TxtFileUploadFilter extends BasicFilter
{
	// La dimensione massima dei file che verranno mantenuti in memoria. File più grandi vengono salvati nella cartella temporanea
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	//La dimensione massima di ogni singolo file
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10MB
	// Dimensione massima della request
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 15; // 15MB
	// cartella di appoggio temporanea se il file supera i requisiti per esser cricato e mantenuto in memoria
	private static final String TEMP_FOLDER = System.getProperty( "java.io.tmpdir" );
	@SuppressWarnings( "unused" )
	private FileCleaningTracker fileCleaningTracker;
	
	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
	{
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse resp = ( HttpServletResponse ) response;
		
		// Controllo se effettivamente il form era impostato su multipart
		if ( !ServletFileUpload.isMultipartContent(req) )
			return;		

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Dimensione massima dei file tenuti temporaneamente in memoria. 
		// Superata la soglia vengon salvati temporaneamente su disco. Opzionale
		factory.setSizeThreshold( THRESHOLD_SIZE );
		// Cartella temporanea. Opzionale
		factory.setRepository( new File( TEMP_FOLDER ) );
		
		/*  ** Non si e' rivelato molto utile **
		fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(request.getSession().getServletContext());
			// Associazione del file cleaner
		factory.setFileCleaningTracker(fileCleaningTracker);
		*/
		
		// Crea l'oggetto che gestisce l'upload
		ServletFileUpload upload = new ServletFileUpload( factory );
		upload.setFileSizeMax( MAX_FILE_SIZE );
		// Setta la dimensione massima della request. Opzionale
		upload.setSizeMax( MAX_REQUEST_SIZE );
		
//		Map<String, String> mappaDatiForm = new HashMap<String, String>();
		
		try { // Parse della request
			@SuppressWarnings( "unchecked" )
			List<FileItem> formItems = upload.parseRequest( req );

			for( FileItem oggettoRequest : formItems ) {
				if ( !oggettoRequest.isFormField() && !oggettoRequest.getName().equals("") ) { // processes only fields that are not form fields
					ByteArrayInputStream bais = new ByteArrayInputStream( oggettoRequest.get() );
					InputStreamReader bis = new InputStreamReader( bais );
					BufferedReader reader = new BufferedReader( bis );
					String riga;
					String contenutoFileCaricato = "";
					while ( (riga = reader.readLine()) != null ) {
						contenutoFileCaricato += riga +"\n";
					}
					req.setAttribute( oggettoRequest.getName(), contenutoFileCaricato );
//					mappaDatiForm.put( oggettoRequest.getName(), contenutoFileCaricato );
//					System.out.println( "Nome campo: " + item.getName() + " - Valore: " + item.getString() );
				} else if ( oggettoRequest.isFormField() ) {
					req.setAttribute( oggettoRequest.getFieldName(), oggettoRequest.getString() );
//					mappaDatiForm.put( oggettoRequest.getFieldName(), oggettoRequest.getString() );
//					System.out.println( "Nome campo: " + item.getFieldName() + " - Valore: " + item.getString() );
				} else {
//					System.out.println( "Campo file che non e' stato compilato: " + item.getFieldName() + " ->'" + item.getString()+"'" );
				}
			}

		} catch ( FileUploadException ex ) {
			ex.printStackTrace();

		}
		
//		req.setAttribute( "mappaDatiForm", mappaDatiForm );
		chain.doFilter( req, resp );		

	}

}
