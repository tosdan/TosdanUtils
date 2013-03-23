package com.github.tosdan.beta.utils.servlets;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
//import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

/**
 * Servlet per upload file
 * 
 * @param upload-path : Cartella di destinazione dei file caricati. Viene cercata nel web.xml e da querystring. Quesrystrin ha precedenza.
 * @param returnQueryString : QueryString che verrà agganciata all'indirizzo per il ritorno alla pagina richiedente.
 * 		Il parametro può esser passato da con un campo input (magari hidden) o da query string, ma in questo caso deve
 * 		esser tradotto con URLEncoder.encode() con codifica UTF-8. Esempio: "returnQueryString=parametro%3DvaloreParametro"
 * @return upload-result : Attributo che viene memorizzato in sessione e contiene il risultato dell'upload
 * @author tosdan
 */
public class UploadServletR01 extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// La dimensione massima dei file che verranno mantenuti in memoria. File più grandi vengono salvati nella cartella temporanea
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	//La dimensione massima di ogni singolo file
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	// Dimensione massima della request
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	// cartella di appoggio temporanea se il file supera i requisiti per esser cricato e mantenuto in memoria
	private static final String TEMP_FOLDER = System.getProperty( "java.io.tmpdir" );
	@SuppressWarnings( "unused" )
	private FileCleaningTracker fileCleaningTracker;
	private String returnQueryString = "";
	private String uploadPath = "";
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException
	{		
//		System.out.println( TEMP_FOLDER );
		Map<String, String> reqParams = new HashMap<String, String>();

		// Controllo se effettivamente il form era impostato su multipart
		if ( !ServletFileUpload.isMultipartContent( request ) )
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
		
		
		// cartella di destinazione dei file caricati da web.xml
		uploadPath = getServletContext().getInitParameter( "upload-path" );
		
		// Se viene passato un parametro nella queryString sovrascrive il parametro da web.xml
		if ( request.getParameter( "upload-path" ) != null ) {
			uploadPath = request.getParameter( "upload-path" );
		}
		
//		System.out.println( "upload path " + uploadPath );
		
		// creates the directory if it does not exist
		File uploadDir = new File( this.uploadPath );
		if ( !uploadDir.exists() ) {
			uploadDir.mkdirs();
		}

		try { // Parse della request
			@SuppressWarnings( "rawtypes" )
			List formItems = upload.parseRequest( request );
			@SuppressWarnings( "rawtypes" )
			Iterator iter = formItems.iterator();

			// iterates over form's fields
			while ( iter.hasNext() ) {
				FileItem item = ( FileItem ) iter.next();
				
				if ( !item.isFormField() && !item.getName().equals("") ) { // processes only fields that are not form fields
					String fileName = new File( item.getName() ).getName();
					String filePath = this.uploadPath + "/" + fileName;
					File storeFile = new File( filePath );
					
					item.write( storeFile ); // saves the file on disk
				} else if ( item.isFormField() ) {
					String fieldName = item.getFieldName();
					reqParams.put( fieldName, item.getString() );
//					System.out.println( "Nome campo: " + fieldName + " - Valore: " + item.getString() );
					
					if ( fieldName.equalsIgnoreCase( "returnQueryString" ) ) { // returnQueryString prelevato da campo input del form
						this.returnQueryString = "?" + item.getString();
					}
				} else {
//					System.out.println( "Campo file che non e' stato compilato: " + item.getFieldName() + " ->'" + item.getString()+"'" );
				}
			}
			request.getSession().setAttribute( "UploadServlet-result", "Caricamento effettuato con successo!" );

		} catch ( Exception ex ) {
			ex.printStackTrace();
			request.getSession().setAttribute( "UploadServlet-result", "Errore nel caricamento: " + ex.getMessage() );

		}

		String urlChiamante = request.getHeader( "referer" );

		// returnQueryString prelevato da query string
		if ( request.getParameter( "returnQueryString" ) != null ) {
			this.returnQueryString = "?" + URLDecoder.decode( request.getParameter( "returnQueryString" ), "UTF-8" );
		}
		
//		System.out.println( "returnQueryString -> "+returnQueryString );
//		System.out.println( "query string -> " + request.getQueryString() );
//		System.out.println( "query string decodificata -> " + URLDecoder.decode( request.getQueryString(), "UTF-8"));
		
		if ( urlChiamante.lastIndexOf( "?" ) > -1 ) {
			urlChiamante = urlChiamante.substring( 0, urlChiamante.lastIndexOf( "?" ) );
		}

		response.sendRedirect( urlChiamante + this.returnQueryString );
		
	}
	
}
