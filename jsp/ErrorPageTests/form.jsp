<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
	<style>
	body, input { font-family:Tahoma; font-size:8pt; }
	</style>
</head>
<body>
<form action="formHandler.jsp" method="post">
<table align="center" border=1>
	<tr><td>Enter your first Number: </td>
	<td><input type="text" name="fno" /></td></tr>
	<tr><td>Enter your Second Number: </td>
	<td><input type="text" name="sno" /></td></tr> 
	<tr ><td colspan="2"><input type="submit" value="Submit" /></td></tr>
</table>
</form>

</body>
</html>