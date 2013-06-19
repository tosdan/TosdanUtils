<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Configurazione Sql Manager</title>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap_integration.css"/>

<script type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js-3rd-party/bootstrap.js"></script>

<SCRIPT type="text/javascript">
	var context = '${ pageContext.request.contextPath }' ,
		address = context+'/servlet/sqlmanagerconf/test?dato=ciao&dato=addio' ;
	
	function test() {
		$.ajax({
			url: address ,
			type: 'post' ,
// 			data: { dato: 'ciao', dato: 'addio' } ,
			success: function ( data, textStatus, jqXHR ) {
				 console.log('success');
					console.log(data);
// 					console.log(jqXHR);
// 					console.log(textStatus);
			} ,
			complete: function (jqXHR, textStatus) {
				console.log('complete');
// 				console.log(jqXHR);
// 				console.log(jqXHR.responseText);
			}
		});
	}
	
</SCRIPT>

</head>
<body>

	<a class="btn"><i class="icon-plus"></i> Add Query</a>

    <!-- Button to trigger modal -->
    <a href="#myModal" role="button" class="btn" data-toggle="modal">Launch demo modal</a>
     
    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		    <h3 id="myModalLabel">Modal header</h3>
		</div>
		<div class="modal-body">
		    <p>One fine body...</p>
		</div>
		<div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Chiudi</button>
		    <button class="btn btn-primary" onclick="test();">Salva</button>
	    </div>
    </div>
    
</body>
</html>