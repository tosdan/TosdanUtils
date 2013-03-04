<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Demo UploadFlieServlet</title>
 <style type="text/css">
  #campo1 {
  	width: 300px;
  }
 </style>
</head>
<body>

	<form action="<%= application.getContextPath() %>/UploadServlet?returnQueryString=prova%3Dciao&upload-path=d:/temp/upsrvlt/" enctype="multipart/form-data" method="post">
		<div>
			<h2>UploadFlieServlet Demo</h2>
			<p>${ sessionScope["UploadServlet-result"] } &nbsp;</p>
		</div>
		
		<label for="check1">check1</label>
		<input id="check1" type="checkbox" name="check" value="check1"/>
		<label for="check2">check2</label>
		<input id="check2" type="checkbox" name="check" value="check2"/>
		<label for="check3">check3</label>
		<input id="check3" type="checkbox" name="check" value="check3"/>
		<br />
		<label for="campo1">Campo di testo:</label>
		<input type="text" name="campo1" id="campo1" value="Anche questo testo viene intercettato dalla servlet"/>
		<br />
		<input type="file" name="file1" id="file1" />
		<br />

<!-- 	<input type="hidden" value="prova=ciao" name="returnQueryString"/> -->

		<input type="submit" value="carica file" />

		<% 	session.removeAttribute( "upload-result" ); %>
	
	</form>

</body>
</html>