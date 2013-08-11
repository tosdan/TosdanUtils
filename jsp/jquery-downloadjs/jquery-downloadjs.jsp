<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--	Per risolvere i problemi con IE9+ e la chiamata ajax di jquery.fileDownload effettuata con metodo post 
 		si puo' forzare IE9+ ad interpretare la pagina come IE8. Con il metodo get il problema non sussiste.	-->
<!-- <meta http-equiv="X-UA-Compatible" content="IE=8; IE=7; Chrome=1" />  -->   <!-- Forzatura ad IE8  -->
	<title>jQuery Download.js Tests</title>
	
	<%--
	<!--[if lt IE 9]>	<script src="../../js-3rd-party/html5shiv.js"></script>		<![endif]-->
	--%>
	
<!--		select[ivizr] emulates CSS3 selectors for IE 			-->
<!--[if (gte IE 6)&(lte IE 8)]>
	<script type="text/javascript" src="[JS library]"></script>
	<script type="text/javascript" src="../../js-3rd-party/selectivizr.js"></script>
	<noscript><link rel="stylesheet" href="[fallback css]" /></noscript>
<![endif]-->
	<script type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="../../js-3rd-party/bootstrap.js"></script> 
	<script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="../../js-3rd-party/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="script.js"></script>
	
	
	<link rel="stylesheet" href="../../css-3rd-party/bootstrap.css"/>
<!-- 			Fix per buttons di bootstrap su IE7 e IE8			 -->
	<!--[if lt IE 8]>	<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie7buttonfix.css"> 	<![endif]-->
	<!--[if IE 8]>		<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie8buttonfix.css"> 	<![endif]-->
<!-- 			Tema bootstrap per Jquery UI (per ora e' fermo alla 10.0.0)			  -->
	<link rel="stylesheet" href="../../css-3rd-party/jquery-ui-custom-theme/jquery-ui-1.10.0.custom.css"/>
	<link rel="stylesheet" href="../../css-3rd-party/jquery-ui-custom-theme/jquery.ui.1.10.0.ie.css"/>
	
	<link rel="stylesheet" href="../../css-3rd-party/bootstrap_integration.css"/>

	<!-- Rimpiazzato <link rel="stylesheet" href="../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css"/>		-->
	<!-- Cancellato <script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.0.custom.min.js"></script> 	 -->
<%-- // shim per abilitare jquery 2 solo su IE9+ 
		<!--[if lt IE 9]>	<script  type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>			<![endif]-->
		<!--[if gte IE 9]><!--> 	<script  type="text/javascript" src="../../js-3rd-party/jquery-2.0.2.js"></script> 	<!--<![endif]-->	--%>

<style type="text/css"> /* GIF per la progress bar */ .ui-progressbar-value { background-image: url("../../images/pbar-ani2.gif"); } </style>

</head>
<body>


<div id="please-wait-modal" title="Preparing file..." style="display: none;">
    We are preparing your report, please wait...
 
    <div id="jqueryFileDownloadProgBar" class="ui-progressbar-value ui-corner-left ui-corner-right" style="width: 100%;height:22px;margin-top: 20px;"></div>
</div>
<div id="error-modal" title="Error" style="display: none;">
	<!--     There was a problem generating your report, please try again. -->
</div>



	<div class="row span12"><p></p></div>
	
	<div class="row span12">
		<div class="row span4 well">
			<a class="jqueryFileDownload" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=successful">Prova.xls (Success)</a>
			<br />
			<a class="jqueryFileDownload" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=unsuccessful">Prova.xls (Fail)</a>
			<br />
			<a class="jqueryFileDownload" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile">Prova.xls (no param)</a>
			<br />
			<a class="jqueryFileDownload" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=fileIntrovabile">Prova.xls (file introvabile)</a>
		</div>
	</div>
	
	<div class="row span12">
	<h4>Test Css3 selectivizr.js per IE8/IE7</h4>
		<div class="span2">
			<table class="table table-striped ">
				<tbody>
					<tr>
						<td>A</td>
						<td>A</td>
						<td>A</td>
					</tr>
					<tr>
						<td>B</td>
						<td>B</td>
						<td>B</td>
					</tr>
					<tr>
						<td>C</td>
						<td>C</td>
						<td>C</td>
					</tr>
					<tr>
						<td>A</td>
						<td>A</td>
						<td>A</td>
					</tr>
					<tr>
						<td>B</td>
						<td>B</td>
						<td>B</td>
					</tr>
					<tr>
						<td>C</td>
						<td>C</td>
						<td>C</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
</body>
</html>