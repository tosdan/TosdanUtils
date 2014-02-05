function blutecStats(ctxPath, options) {
	var cookieOfTheDay = 'cookieOfTheDay_' + moment().format('L'),
		urlServlet = ctxPath+'/servlet/fwk/uam/',
		optionsMap;
		
	if ($.isArray(options)) {
		optionsMap = {};
		for (var i = 0 ; i < options.length ; i++)
			optionsMap[options[i].name] = options[i].value;
	}
	options = optionsMap ? optionsMap : options;
	
	if( !Cookies(cookieOfTheDay) || (options && options.justTest) ) {
		var getTodayCookie = $.getJSON(urlServlet+'do?richiesta=getTodayCookie');
		
		getTodayCookie.done(function(responseData) {
			
			var data = { cookieEnabled: navigator.cookieEnabled
						, sqlName: 'userStatistics'
						, oldTime: responseData.oldTime
						, colorDepth: screen.colorDepth
						, scrW: screen.width
						, scrH: screen.height
						, scrAW: screen.availWidth
						, scrAH: screen.availHeight
						, richiesta: 'sendScreenStats'
			};			
			$.extend(data, options);
			$.post(urlServlet, data );
		});
		
	} else {
//		console.log("Oggi il cookie e' gia' stato inserito.");
	}
}