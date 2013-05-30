<?xml version="1.0" encoding="ISO-8859-15" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
    pageEncoding="ISO-8859-15"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
<title>Fake Login</title>
<link rel="stylesheet" href="css-3rd-party/bootstrap.css" />
</head>
<body>

<% // *** Questa pagina non deve finire in produzione *** 
String id = request.getParameter( "fakeID" );
String pagina = request.getParameter("pagina");

String pwd = request.getParameter( "pwd" );
if ( pwd != null && pwd.equals( "tosdan" ) ) {	
	if ( id == null || pagina == null ) {
		pagina = application.getContextPath() + "/jsp/AuthFilter/authfilter.jsp";
		id = "1";
		%>		
		<div class="row span12">
			<form action="" method="post">
				<div>
					<label class="lead muted">ID utente per il fake login.</label>
					<input class="input-mini" type="text" name="fakeID" value="<%= id %>" />
				</div>
				
				<div>
					<label class="lead muted">Pagina per il redirect.</label>
					<input class="span7" type="text" name="pagina" value="<%= pagina %>" />
				</div>
				<input type="hidden" name="pwd" value="<%= pwd %>" />
				<div><input class="btn" type="submit" /></div>		
			</form>
		</div>	
		<%
	} else {		
		session.setAttribute("UserID", id);
		
		if ( session.getAttribute( "UserID" ) != null ) {
			System.out.println( "user id "+id+" inserito correttamente in sessione" );
			response.sendRedirect( pagina );
		} else {
			out.println( "inserimento in sessione forzato fallito" );
		}
	}
}


%>
</body>
</html>