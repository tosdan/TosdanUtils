<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>iframe-transport.js Tests</title>


<!--[if lt IE 9]>	<script src="../../js-3rd-party/html5shiv.js"></script>		<![endif]-->
<script type="text/javascript" src="[JS library]"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
   <script type="text/javascript" src="../../js-3rd-party/selectivizr.js"></script>
   <noscript><link rel="stylesheet" href="[fallback css]" /></noscript>
<![endif]--> 
<script type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js-3rd-party/bootstrap.js"></script>
<%-- // shim per abilitare jquery 2 solo su IE9+ 
	<!--[if lt IE 9]>	<script  type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>			<![endif]-->
	<!--[if gte IE 9]><!--> 	<script  type="text/javascript" src="../../js-3rd-party/jquery-2.0.2.js"></script> 	<!--<![endif]-->	--%>
<script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.0.custom.min.js"></script> 	-->
<script type="text/javascript" src="../../js-3rd-party/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../../js-3rd-party/bootstrap-fileupload.js"></script>


<link rel="stylesheet" href="../../css-3rd-party/bootstrap.css"/>
<!--[if lt IE 8]>	<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie7buttonfix.css"> 	<![endif]-->
<!--[if IE 8]>		<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie8buttonfix.css"> 	<![endif]-->
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery-ui-1.10.0.custom.css"/>
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery.ui.1.10.0.ie.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap_integration.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap-fileupload.css"/>
<!-- <link rel="stylesheet" href="../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css"/>		-->

<script type="text/javascript">

$("#myform").submit(function() {
    $.ajax(this.action, {
        files: $(":file", this),
        iframe: true
        
    }).complete(function(data) {
        console.log(data);
    });
    
});
</script>
<style></style>
</head>
<body>
	<div class="row span12">
		
	</div>
	
	<div class="row span12">
		<form action="" class="well span7" id="myform">
			<fieldset>
				<input type="text" name="testo" />
				<label class="checkbox">Checkbox
					<input type="checkbox" class="no-outline" name="checkscatola" value="check1" />
				</label>
				<label class="radio">
					<input class="no-outline" type="radio" name="radiob" value="1" />Radio1
				</label>
				<label class="radio">
					<input class="no-outline" type="radio" name="radiob" value="2" />Radio2
				</label>
        
				<div class="fileupload fileupload-new" data-provides="fileupload">
					<div class="input-append">
						<div class="uneditable-input span3"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div>
						<span class="btn btn-file">
							<span class="fileupload-new">Scelta file</span>
							<span class="fileupload-exists">Cambia</span>
							<input type="file" name="fileCaricato" id="fileCaricato"/>
						</span>
						<a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Reset</a>
					</div>
				</div>

				<div class="controls">
					<input class="btn" type="submit" value="Invia"/>
				</div>
			</fieldset>
		</form>
	</div>
	
	<div class="row span12">
		<a href="javascript:void()" class="btn">Pulsante</a>
	</div>
	
	<div class="row span12">
		
	</div>
	
	
</body>
</html>