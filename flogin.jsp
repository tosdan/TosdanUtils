<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Fake Login</title>
<link rel="stylesheet" href="css-3rd-party/bootstrap.css" />
</head>
<body><!--  *** Questa pagina non deve essere lasciata in produzione ***  -->
<c:if test="${ param.pwd == 'tosdan'}">
	<c:choose>
		<c:when test="${ empty param.fakeID || empty param.pagina}">
		<c:set var="id" value="1" />
			<div class="row span12">
				<form action="" method="post">
					<div>
						<label class="lead muted">ID utente per il fake login.</label>
						<input class="input-mini" type="text" name="fakeID" value="${ id }" />
					</div>
					
					<div>
						<label class="lead muted">Pagina per il redirect.</label>
						<input class="span7" type="text" name="pagina" value="${param.pagina}" title="Percorso relativo senza il context della webapp" />
					</div>
					<input type="hidden" name="pwd" value="${ param.pwd }" />
					<div><input class="btn" type="submit" /></div>		
				</form>
			</div>	
		</c:when>
		<c:otherwise>
			<c:set value="${param.fakeID}" scope="session" var="UserID"/>
			<c:redirect url="/${param.pagina}" context="/${pageContext.request.contextPath}" />
		</c:otherwise>
	</c:choose>
</c:if>
</body>
</html>