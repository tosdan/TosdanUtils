/**
 * @author Daniele
 * @version 0.1.1b2013-08-09
 */
window.Q$tring = {
    /**
     * 
     */
	parseParams: function(query) {
	    var match,
		    pl     = /\+/g,  // Regex for replacing addition symbol with a space
		    search = /([^&=]+)=?([^&]*)/g,
		    decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); } ,
		    params = {};
		
	    while (match = search.exec(query)) {
	    	
	    	var matchedParam = decode(match[1]) ,
	    		matchedValue = decode(match[2]) ,
	    		valueStored = params[matchedParam];

//			console.log(matchedParam + ' - ' + matchedValue);
	    	if ( valueStored ) {
	    		if (typeof valueStored === 'string') {
	    			var tempArray = new Array();
	    			tempArray.push( valueStored, matchedValue );
	    			params[matchedParam] = tempArray;
	    			
	    		} else {		    			
	    			params[matchedParam] = storedValue.push( matchedValue );
	    		}
	    	} else {
	    		params[matchedParam] = matchedValue;
	    	}
	    }
	    
	    return params;
	} ,
	
	/**
	 * Restituisce un array di oggetti chiave: nomeParametro -> valore: valoreParametro.
	 * Utile da dare direttamente in pasto ad una chiamata ajax come campo data.
	 */
	paramsToMapsArray: function(paramsObject) {
		var parametri = new Array(); 
//		console.log(paramsObject);
		for(key in paramsObject) {
			var valore = paramsObject[key];
			
//			console.log(key + ' - ' + valore);
			if (typeof valore === 'string') {
				parametri.push( { name: key, value: valore } );
			} else {
				// Se l'oggetto e' un array, viene aggiunta una coppia key->valore per ogni elemento contento nell'array
				for(var i = 0 ; i < valore.length ; i++) {
//					console.log(key + ' - ' + valore[i]);
					parametri.push( { name: key, value: valore[i] } );
				}
			}
		}
		
// 		Soluzione alternativa: riproduce una querystring con tutti i parametri estratti. A differenza dell'attuale soluzione, questa richiederebbe jQuery.
//		var recursiveDecoded = decodeURIComponent( $.param( window.params, true) );
//		parametri = recursiveDecoded; 		
//		console.log('recursiveDecoded');		console.log(recursiveDecoded);		console.log('-----------');
	
		return parametri;
	} ,
	
	/**
	 * 
	 */
	getQueryStringObject: function() {
		return Q$tring.params;
	}
};


/**
 * Recuper e parsing dei parametri non appena la pagina ha terminato il caricamento.
 */
(window.onpopstate = function () {
	
    var query = window.location.search.substring(1);
    Q$tring.params = Q$tring.parseParams(query);
    
})();







