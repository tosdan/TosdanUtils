$(function () {
	
//	initExample1();
    initExample2();
    
});

/**
 * Sicuramente il metodo con maggior leggibilita'
 */
function initExample2() {
    $(document).on("click", "a.jqueryFileDownload", function () {
   	 
        var $preparingFileModal = $("#please-wait-modal");
        $preparingFileModal.dialog({ modal: true });
 
        $.fileDownload( $(this).prop('href'), {
        	
        	// Con post manda in crisi IE9 (accesso negato non so che). Per abilitare post bisogna forzare IE9+ in modalita IE8 con meta http-equiv="X-UA-Compatible" content="IE=8;" 
        	httpMethod: "get" ,
        	data: { prova: 'prova' } // dati aggiuntivi
        
        })
        .done( function (url) {
        	
                $preparingFileModal.dialog('close');
                
        })
        .fail( function (responseHtml, url) {
            	
           	var risposta = $( $.trim(responseHtml) ).html();
            $preparingFileModal.dialog('close');
            
            // NB. Estrarla piu' di 1 volta con un altro $("#error-modal") manda in crisi IE8+
            $("#error-modal").html(risposta)
                			 .dialog({ modal: true, width: 720, height: 520 });
        });
        
        return false; //this is critical to stop the click event which will trigger a normal file download!
    });
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


