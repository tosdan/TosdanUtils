<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=7;" /> <!-- Problemi con IE9+ quindi forzatura ad IE 8. Si potrebbe risovere anche impostando a get il methed di fileDownload.js -->
<title>jQuery Download.js Tests</title>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap.css"/>
<!--[if lt IE 8]><link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie7buttonfix.css"><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie8buttonfix.css"><![endif]-->
<!-- <link rel="stylesheet" href="../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css"/> -->
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery-ui-1.10.0.custom.css"/>
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery.ui.1.10.0.ie.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap_integration.css"/>
<script type="text/javascript" src="../../js-3rd-party/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<%--! 
// shim per abilitare jquery 2 solo su IE9+ 
<!--[if lt IE 9]>
    <script  type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
<![endif]-->
<!--[if gte IE 9]><!-->
    <script  type="text/javascript" src="../../js-3rd-party/jquery-2.0.2.js"></script>
<!--<![endif]-->
--%>
<script type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js-3rd-party/bootstrap.js"></script>
<!-- <script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.0.custom.min.js"></script> -->
<script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
<script type="text/javascript" src="../../js-3rd-party/jquery.fileDownload.js"></script>
<script type="text/javascript">
$(function () {
    $(document).on("click", "a.fileDownloadCustomRichExperience", function () {
 
        var $preparingFileModal = $("#preparing-file-modal");
        $preparingFileModal.dialog({ modal: true });
 
        $.fileDownload( $(this).prop('href'), {
        	httpMethod: "post" ,
        	data: { prova: 'prova' } , 
            successCallback: function (url) {
                $preparingFileModal.dialog('close');
                
            } ,
            
            failCallback: function (responseHtml, url) {
            	console.log('responseHtml: '+responseHtml);
            	var risposta = $( $.trim(responseHtml) ).html();
            	console.log('risposta: '+risposta);
 				$('#error-modal').html(risposta);
                $preparingFileModal.dialog('close');
                $("#error-modal").dialog({ modal: true, width: 720, height: 520 });
            }
            
        });
        return false; //this is critical to stop the click event which will trigger a normal file download!
    });
});
</script>

<style>
	.ui-progressbar-value {
	    background-image: url("../../images/pbar-ani2.gif");
	}
</style>

</head>

<body>

<div id="preparing-file-modal" title="Preparing report..." style="display: none;">
    We are preparing your report, please wait...
 
    <div class="ui-progressbar-value ui-corner-left ui-corner-right" style="width: 100%; height:22px; margin-top: 20px;"></div>
</div>
 
<div id="error-modal" title="Error" style="display: none;">
<!--     There was a problem generating your report, please try again. -->
</div>
	<div class="row"><p></p></div>
	<div class="row span4 well">
		<a class="fileDownloadCustomRichExperience" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=successful">Prova.xls (Success)</a>
		<br />
		<a class="fileDownloadCustomRichExperience" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=unsuccessful">Prova.xls (Fail)</a>
		<br />
		<a class="fileDownloadCustomRichExperience" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile">Prova.xls (no param)</a>
		<br />
		<a class="fileDownloadCustomRichExperience" href="${pageContext.request.contextPath}/servlet/jqdownjs/getFile?richiesta=fileIntrovabile">Prova.xls (file introvabile)</a>
	</div>
</body>

</html>