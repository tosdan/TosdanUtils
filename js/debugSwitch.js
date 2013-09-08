// Abilita la modalita' Debug
function debugSwitch(inputFields, combo, debugValue) {
	
	function makeDebugDiv() {
		var styleP = { 'text-shadow': '1px 1px #CCC',
						'color': '#FE4532',
						'font-size': 24,
						'text-align': 'center',
						'margin': 0 } ,
			P = $("<p>Modalit&agrave; debug Attiva</p>").css(styleP) ,			
			styleDIV = {'border': '2px solid #444',
						 'border-radius': 3,
						 'position': 'relative',
						 'background-color': '#DEE3E7',
						 'box-shadow': '1px 1px 9px #888',
						 'height': 30,
						 'width': 300,
						 'margin': '0 auto'} ,
			$divDebug = $("<div id='divModalitaDebugAttiva'>").hide()
															.css(styleDIV)
															.append(P)
															.append($close);

		return $divDebug;
	}
	
	function getX() {
		var FF = !(window.mozInnerScreenX == null),
			top = -4;
		if (FF) top = -6;
		return $('<a>').css({'position': 'absolute', 'padding-left': 2, 'top': top, 'font-size':24, 'font-weight': 'bold'}).append('&#215;');
	}
	
	function getCloseDiv() {
		var styleClose = {'position': 'absolute',
							'border': '2px solid #000',
							'display': 'inline-block',
							'cursor':'pointer', 
							'border-radius': 17, 
							'right': -7,
							'top': -7,
							'margin': 0,
							'padding': 0,
							'width': 18,
							'z-index': 100 ,
							'box-shadow': '1px 1px 9px #888', 
							'background-color': '#DEE3E7',
							'height': 17 } ,
		closeDiv = $('<div>').css(styleClose).append(getX());
		return closeDiv;
	}

	function toggleInputOnOff(elem, value) {
		if ($.isArray(elem)) {
			$(elem).each(function(i,e) {
				var id = e,
					customVal = value;
				if ($.isPlainObject(e)) {
					id = e.id;
					customVal = e[value];
					if (!customVal && value === 'true')
						customVal = value;
				}
				$('#'+id).val(customVal);
			});
			
		} else 
			$('#'+elem).val(value);
	}
	
	function toggleDebugMode() {
		if ( !active ) {
			toggleInputOnOff(inputFields, debugOn);
			$divDebug.slideDown(200);
			$close.delay(220).fadeIn(170);
		} else {
			toggleInputOnOff(inputFields, debugOff);
			$close.hide();
			$divDebug.slideUp(400);
		}
		active = !active;
	}
	
	function init() {
		if (debugValue) {
			debugOn = debugValue;
			debugOff = '';
		}

		toggleInputOnOff(inputFields, debugOff);
		$('body').prepend( $divDebug );
		Mousetrap.bind(combo, toggleDebugMode);
		$close.on('click', toggleDebugMode);
	}

	var debugOn = 'true',
		debugOff = 'false',
		active = false,
		$close = getCloseDiv().hide();
		$divDebug = makeDebugDiv();
	
	init();
};