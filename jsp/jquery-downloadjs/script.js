$(function () {
	
//	initExample1();
    initExample2();
    
});

/**
 * Sicuramente il metodo con maggior leggibilita'
 */
function initExample2() {
    $(document).on("click", "a.jqueryFileDownload", function (evt) {
   	 
        var $preparingFileModal = $("#please-wait-modal");
        $preparingFileModal.dialog({ modal: true });
 
        $.fileDownload( $(this).prop('href'), {
        	
        	// Con post manda in crisi IE9 (accesso negato non so che). Per abilitare post bisogna forzare IE9+ in modalita IE8 con meta http-equiv="X-UA-Compatible" content="IE=8;" 
        	httpMethod: "get" ,
        	data: { prova: 'prova' } // dati aggiuntivi
        
        })
        .always( function (url) { // sia che fallisca sia che riesca.
                $preparingFileModal.dialog('close');
                // NB. un qualsiasi console.log blocca always/done/fail. Non ha senso, ma e' cosi'
            	var json = $.parseJSON(url);
            	if (json.error)
            		showErrorModal(json.errMsg);
        })
        .fail( function (responseHtml, url) {
           	var risposta = $( $.trim(responseHtml) ).html();
//            $preparingFileModal.dialog('close'); <- inserito nell'always
           	showErrorModal(risposta);
            // NB. Estrarla piu' di 1 volta con un altro $("#error-modal") manda in crisi IE8+
//            $("#error-modal").html(risposta).dialog({ modal: true, width: 720, height: 520 });
        });
        
        evt.preventDefault();
        return false; //this is critical to stop the click event which will trigger a normal file download!
    });
}



function showErrorModal(msg) {
	$("#error-modal").html(msg).dialog({ modal: true, width: 720, height: 520 });
}



function initExample1() {
    $(document).on("click", "a.jqueryFileDownload", function () {
    	 
        var $preparingFileModal = $("#please-wait-modal");
        $preparingFileModal.dialog({ modal: true });
 
        $.fileDownload( $(this).prop('href'), {
// Con post manda in crisi IE9 (accesso negato non so che). Per abilitare post bisogna forzare IE9+ in modalita IE8 con meta http-equiv="X-UA-Compatible" content="IE=8;" 
        	httpMethod: "get" ,
        	data: { prova: 'prova' } , 
            successCallback: function (url) {
                $preparingFileModal.dialog('close');
                
            } ,
            
            failCallback: function (responseHtml, url) {
            	
            	var risposta = $( $.trim(responseHtml) ).html();
                $preparingFileModal.dialog('close');
                
                $("#error-modal") // NB. Estrarla piu' di 1 volta con un altro $("#error-modal") manda in crisi IE8+
                				.html(risposta)
                				.dialog({ modal: true, width: 720, height: 520 });
            }
            
        });
        return false; //this is critical to stop the click event which will trigger a normal file download!
    });
}


