<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page isErrorPage="true" import="java.io.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<style>
	body, p { font-family:Tahoma; font-size:10pt;
        padding-left:30; }
	pre { font-size:8pt; }
</style>
</head>
<body>
<%-- Exception Handler --%>
<font color="red">
<%= exception.toString() %><br />
</font>
<%
out.println("<!--");
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
exception.printStackTrace(pw);
out.print(sw);
sw.close();
pw.close();
out.println("-->");
%>
</body>
</html>