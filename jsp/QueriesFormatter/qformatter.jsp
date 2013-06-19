<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="css/qformatter.css" rel="stylesheet" />

<script src="../../js-3rd-party/jquery-1.9.1.js"></script>
<script src="../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
<script src="../../js-3rd-party/bootstrap.js"></script>
<script src="../../js-3rd-party/bootstrap-fileupload.js"></script>

<script src="js/sh_main.js"></script>
<script src="js/sh_sql.js"></script>

<script type="text/javascript">
	
	var ctxPath = '${ pageContext.request.contextPath }';
	// inizializzazione della funzionalita' dei tooltip di bootstrap
	$(function attivaTooltip() {
	    $('[data-toggle="tooltip"]').tooltip();
	    $('#query-parametrica').html( $('#query').html() );
	});

	// oggetto che rappresenta la sessione
	var dataStore = window.sessionStorage;
	
	// inserisce un parametro in sessione
	function sessionSet( key, value ) {
		try {
			dataStore.setItem(key, escape(value));
		} catch(e) {
				if(e.code == 22) {
					dataStore.clear();
			}
		}
	}
	
	// preleva un parametro dalla sessione
	function sessionGet(key) {
		return unescape(dataStore.getItem(key));
	}
	
	// recupero dei nomi dei parametri che compaiono nella textarea passata
	function retrieveParams(textarea) {
		var params = $('#'+textarea).val() ,
			// se EL e' abilitato (come dovrebbe) per il simbolo del dollaro e' necessario doppio escape non singolo .   
			paramPattern = /\\$\{[ ]*([a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)*)[ ]*(,[ ]*([a-zA-Z])+)?\}/g ,
			result ,
			paramsObj = new Object();
		
		while ( result = paramPattern.exec(params) ) {
			paramsObj[ result[1] ] = result[1]; // group(1) della regexp
		}
		return paramsObj;
	}
	
	// popola la textarea target con i parametri individuati nella textarea
	// sorgente disponendoli gia' pronti per la compilazione
	function popolaAssoc(textarea, target) {
		var result = "" ,
			paramsObj = retrieveParams(textarea);
// 		console.log(paramsObj);
		for (propertyName in paramsObj) {
			if (result.length > 0)
				result += "\n;\n";
			result += propertyName + "=";
		}
		$('#'+target).effect('highlight', {'color': '#99B3FF'}, 1250).val(result);
	}
	
	// crea un oggetto javascript contenente una mappa [parametro -> valore] 
	// (parametro da sostituire -> valore sostitutivo) 
	// recuperata dal contenuto dell'area 'associazini parametri' 
	function prelevaAssoc() {
		var assoc = $('#associazioni').val() ,
			arrayAssoc = assoc.split(";") ,
			result = new Object() ,
			i;
		
		for( i = 0 ; i < arrayAssoc.length ; i++ ) {
			var paramArray = arrayAssoc[i].split("=");

			if (paramArray.length < 2)
				continue;
			
			var paramName = (paramArray[0]).trim();
			var paramVal = (paramArray[1]).trim();
			
			if (paramVal.length < 1)
				continue;
			
			if ( paramVal.indexOf(',') < 0 ) {
				result[paramName] = paramVal;
			} else {
				arrayParamVal = paramVal.split(',');
				result[paramName] = arrayParamVal;
			}				
		}
		return result;
	}
	
	function compilaQueryAjax( paramsMap, onSuccess, onComplete ) {
		$.ajax({
			url: ctxPath+"/servlet/qformat/",
	        type: "post",
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8" ,
// 	        dataType: "html",
			//contentType: false,
	        data	 : { jsonParams:	JSON.stringify(paramsMap) } ,  
	        success  : function ( returnValue, textStatus, jqXHR ) {
	        			if ( onSuccess !== undefined )
							onSuccess( returnValue, textStatus, jqXHR );	        			
			        },
			complete : function ( jqXHR, textStatus ) {
						if( onComplete !== undefined )
							onComplete( jqXHR, textStatus );						
					}
		});
	}
	
fnElaboraEsito = function prova( returnValue ) {
		$('#query-compilata').html(returnValue);
		$('#div-query-compilata').hide();
		$('#div-query-compilata').fadeIn('slow');
		sh_highlightDocument();
	}
	
	function compila( onSuccess ) {
		var paramsMap = prelevaAssoc();
		paramsMap.flagPerUtilizzoMapFormatSQLValidator = $('#sqlValidator').prop('checked');
		paramsMap.testoOriginaleReservedKeyWord = $('#query-parametrica').val();
		
		compilaQueryAjax( paramsMap, onSuccess );
	}

</script>

<title>Query Formatter</title>
</head>
<body>
<div id="container">

	<div id="content" class="offset1">
			<div class="tabbable">

				<ul class="nav nav-tabs">
					<li class="active"><a href="#dafile" data-toggle="tab">Da File</a></li>
					<li><a href="#datesto" data-toggle="tab">Da Testo</a></li>
				</ul>
				
				<div class="tab-content">
				
			<!-- Tab Da File -->		
					<div class="tab-pane active" id="dafile">
						<form id="frm-file" action="">
							<fieldset>
								<legend>Compila query da File</legend>	
								<div class="fileupload fileupload-new" data-provides="fileupload">
								  <div class="input-append">
								    <div class="uneditable-input span4"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">Scegli file</span><span class="fileupload-exists">Modifica</span><input type="file" name="testoOriginaleReservedKeyWord" /></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Annulla</a>
								  </div>
								</div>
								<span class="help-block">Carica il file contenente la query.</span>
								<button type="submit" class="btn">Carica</button>
							</fieldset>
						</form>
					</div>
					
			<!-- Tab Da Testo -->					
					<div class="tab-pane" id="datesto">
						<form id="frm-testo" action="">
							<div class="span9">
								<div id="div-query-parametrica" class="row">
									<h2><small>Testo query.</small></h2>
									<textarea id="query-parametrica" class="query-testuale" rows="12"></textarea>
								</div>
								<div id="div-query-compilata" class="row">
									<h2><small>Query compilata.</small></h2>
<!-- 									<textarea id="query-compilata" class="query-testuale" rows="12" readonly="readonly"></textarea> -->
									<div id="div-interno-query-compilata">
										<pre id="query-compilata" class="sh_sql"><!-- query compilata --></pre>
									</div>
								</div>
							</div>
							
							<div class="span3">
								<h2 data-toggle="tooltip" data-placement="top" title="Parametri nella forma 'parametro=valore' separati da ' ; '">
									<small>Associazioni parametri.</small>
								</h2>
								<textarea id="associazioni" rows="20"></textarea>
								<button type="button" onclick="compila( fnElaboraEsito );" class="btn" data-toggle="tooltip" data-placement="top" title="Sostituisce tutte le occorrenze dei parametri nel testo con i valori specificati nell'elenco associazioni">
									Elabora
								</button>
								<button type="button" onclick="popolaAssoc( 'query-parametrica', 'associazioni' );" class="btn" data-toggle="tooltip" data-placement="top" title="Estrapola i parametri dal testo e li elenca nell'area di testo sopra per poterli editare.">
									Trova
								</button>
								<label class="checkbox pull-left" data-toggle="tooltip" data-placement="top" title="Abilita/Disabilita l'utilizzo del validatore di tipi SQL: solo per parametri nel formato completo \${ PARAMETRO, TIPO }">
									<input id="sqlValidator" checked="checked" type="checkbox" value="true" name="flagPerUtilizzoMapFormatSQLValidator" />
									SQL Validator
								</label>							
							</div>
						</form>
					</div>
					
				</div>
				
			</div>
			
		</div> <!-- close content -->


<div id="query" style="display: none">SELECT 
	  RTRIM(idcompany) as value
	, RTRIM(idcompany) + ' - ' + RTRIM(desazi) as text 	
FROM dataCompany
WHERE idcompany in (
	select data.idcompany		
	from 
		cpusers usr		
	inner join 
		cpusrgrp grp
	on 
		usr.code = grp.usercode
	inner join 
		blutec_friuli.dbo.datacompany data
	on 
		data.idgroup = grp.groupcode		
	inner join  
		ba_users_info info
	on 
		info.UFUSERID = usr.code
	inner join 
		ba_contact ba
	on 
		ba.COCOMPANYID = info.UFPERSON
	WHERE usr.code = \${UserID, integer}
 ) OR EXISTS (SELECT * FROM cpusrgrp WHERE groupcode = 1 AND usercode = \${UserID, integer})</div>

</div> <!-- close container -->
</body>
</html>