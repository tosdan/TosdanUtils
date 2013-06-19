<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="exceptionHandler.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>

	<style>
	body, p { font-family:Tahoma; font-size:10pt; }
	</style>
	
</head>
<body>
<%
	int fno;
	int sno;
		fno = Integer.parseInt(request.getParameter("fno"));
		sno = Integer.parseInt(request.getParameter("sno"));
		
		int div=fno/sno;
%>

<p>Division is : <%= div %></p>
<p><a href="form.jsp">Back</a>.</p>


</body>
</html>