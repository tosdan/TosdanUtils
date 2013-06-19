<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="org.apache.commons.fileupload.servlet.FileCleanerCleanup"%>
<%@page import="org.apache.commons.io.FileCleaningTracker"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%

// La dimensione massima dei file che verranno mantenuti in memoria. File più grandi vengono salvati nella cartella temporanea
final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
//La dimensione massima di ogni singolo file
final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
// Dimensione massima della request
final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
// cartella di appoggio temporanea se il file supera i requisiti per esser cricato e mantenuto in memoria
// final String TEMP_FOLDER = System.getProperty( "java.io.tmpdir" );
//Cartella temporanea
final File TEMP_DIR = new File("d:\\tmp");
final FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(request.getSession().getServletContext());

//Check that we have a file upload request
boolean isMultipart = ServletFileUpload.isMultipartContent(request);

if (isMultipart)
{
	
	DiskFileItemFactory factory = new DiskFileItemFactory(); 
	
	//Dimensione massima di ogni file in memoria, opzionale
	factory.setSizeThreshold(MAX_FILE_SIZE);
	//Cartella temporanea, Opzionale
	factory.setRepository(TEMP_DIR);
	
	// Associazione del file cleaner
	factory.setFileCleaningTracker(fileCleaningTracker);

	//Classe per l'upload
	ServletFileUpload upload = new ServletFileUpload(factory); 
	
	//Dimensione massima della request, opzionale
	upload.setSizeMax(MAX_REQUEST_SIZE); 

	//Parse della request
	List items = upload.parseRequest(request); 
	
	Iterator iter = items.iterator();

	while ( iter.hasNext() )
	{
		FileItem item = (FileItem) iter.next();
		
		if ( item.isFormField() )
		{
			 String a = "field name: "+ item.getFieldName()
						+ "<br />\nget String : " + item.getString()
						+ "<br />\nget size: " + item.getSize()
						+ "<br />\n****************\n<br />" ;
			 
// 			System.out.println( a );
			out.println( a );
		}
		else
		{
			 String a = "field name: "+ item.getFieldName()
						+ "<br />\nget size: " + item.getSize()
						+ "<br />\nget content type: " + item.getContentType()
						+ "<br />\nget name: " + item.getName()
						+ "<br />\nin in memory: " + item.isInMemory()
						+ "<br />\n****************\n<br />" ;

			File uploaded = new File("d:/temp/test_apache_uploader/"+item.getName(  ));
			item.write( uploaded );
			
// 			System.out.println( a );
			out.println( a );
		}
		
	}
	out.println( "" );
}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<style type="text/css">
 #campo1 {
 	width: 300px;
 }
</style>
</head>
<body>

<form action="NewFile.jsp" enctype="multipart/form-data" method="post">
	<label for="campo1">Campo di testo:</label>
	<input type="text" name="campo1" id="campo1" value="Anche questo testo viene intercettato dalla servlet"/>
	<br />
	<input type="file" name="file1" id="file1" />
	<br />
	<input type="submit" />
</form>

</body>
</html>