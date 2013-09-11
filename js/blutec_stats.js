function blutecStats(ctxPath, options) {
	var cookieOfTheDay = 'cookieOfTheDay_' + moment().format('L'),
		optionsMap;
	
	if ($.isArray(options)) {
		optionsMap = {};
		for (var i = 0 ; i < options.length ; i++)
			optionsMap[options[i].name] = options[i].value;
	}
	options = optionsMap ? optionsMap : options;
	
	if( !Cookies(cookieOfTheDay) || (options && options.justTest) ) {
		var getTodayCookie = $.getJSON(ctxPath+'/servlet/uam/do?richiesta=getTodayCookie');
		
		getTodayCookie.done(function(responseData) {
			
			var data = { cookieEnabled: navigator.cookieEnabled
						, sqlName: 'userStatistics'
						, oldTime: responseData.oldTime
						, colorDepth: screen.colorDepth
						, scrW: screen.width
						, scrH: screen.height
						, scrAW: screen.availWidth
						, scrAH: screen.availHeight};			
			
			$.extend(data, options);
			$.post(ctxPath+'/servlet/uam/do?richiesta=sendScreenStats', data );
		});
		
	} else {
//		console.log("Oggi il cookie e' gia' stato inserito.");
	}
}