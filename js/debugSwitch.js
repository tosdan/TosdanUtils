// Abilita la modalita' Debug
var debugSwitch = {
	init: function(inputFields, combo, debugValueForTrue, debugValueForFalse) {
		
		function preventSelection() {
			$('.preventSelection').each(function(i,e) {
				var $elem = $(e);
				if (typeof e.style.MozUserSelect != "undefined") // Firefox
					e.style.MozUserSelect = 'none';
				else if (typeof this.onselectstart != 'undefined')  // IE
					$elem.bind('selectstart', function(){return false;});
				else { // Altri
					$elem.bind('selectstart', function(){return false;});
					$elem.bind('mousedown', function(){return false;});
				}
			});
		}
		
		function makeDebugDiv() {
			var styleP = { 'text-shadow': '1px 1px #888',
							'color': '#FE4532',
							'font-size': 24,
							'text-align': 'center',
							'padding-bottom': 5,
							'font-family': 'Verdana',
							'margin': 0 } ,
				P = $("<p>Modalit&agrave; debug</p>").css(styleP).addClass('preventSelection') ,			
				styleDIV = {'border': '2px solid #444',
							 'border-radius': 5,
							 'position': 'relative',
							 'background-color': '#DEE3E7',
							 'box-shadow': '1px 1px 9px #888',
							 'padding-bottom': 3,
							 'padding-left': 3,
							 'padding-right': 3,
							 'padding-top': 5,
							 'width': 370,
							 'line-height': '26px',
							 'margin': '0 auto'} ,
				$divDebug = $("<div id='divModalitaDebugAttiva'>").hide()
																.addClass('preventSelection')
																.css(styleDIV)
																.append(P)
																.append($close);
			if ($.isArray(inputFields)) {
				$(inputFields).each(function(i,e) {
					if ($.isPlainObject(e)) {
						e = e.id;
					}
					$divDebug.append(getCheckBox(e));
				});
			} else {
				$divDebug.append(getCheckBox(inputFields));
			}
				
			return $divDebug;
		}
		
		function getCheckBox(id) {
			var div = $('<div>').addClass('preventSelection').css({ 'background-color':'#C4C5DA','box-shadow': '1px 1px 5px #888', 'padding-bottom': 2,'padding-right':5,'margin': 3,'line-height': '10px','display': 'inline-block', 'border':'1px solid #777', 'border-radius':3}),
				check = $('<input>').addClass('preventSelection').attr('type','checkbox').prop({'id': id, 'checked': true}),
				label = $('<label>').addClass('preventSelection').append(id).css( { 'line-height':'12px','cursor':'pointer', 'font-family': 'Verdana', 'font-size': 12,'text-shadow': '1px 1px #d1d1d1'} );
			$(label).on('click', function() {
				$(this).siblings('input').trigger('mousedown').trigger('mouseup').trigger('click');
			});
			$(check).on('click', function() { toggleSingle(id); });
			$(eventi).on('closeDebugMode', function() { $(check).prop('checked', true); });
			div.addClass('preventSelection').append(check).append(label);
			return div;	
		}
		
		function getX() {
			var FF = (window.mozInnerScreenX != null),
				top = -4;
			if (FF) top = -6;
			return $('<a>').addClass('preventSelection').css({'position': 'absolute','text-decoration':'none', 'padding-left': 2, 'top': top, 'font-size':22, 'font-weight': 'bold'}).append('&#215;');
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
			closeDiv = $('<div>').addClass('preventSelection').css(styleClose).append(getX());
			return closeDiv;
		}
		
		function toggleDebugMode() {
			
			if ( !active ) {
				$divDebug.slideDown(200);
				$close.delay(220).fadeIn(170);
			} else {
				$(eventi).trigger('closeDebugMode');
				$close.hide();
				$divDebug.slideUp(400);
			}

			toggleAll();
		}
		
		function setCache() {
			if ($.isArray(inputFields)) {
				$(inputFields).each(function(i, elem) {
					var id = elem,
						trueValue = debugOn,
						falseValue = debugOff;
					
					if ($.isPlainObject(elem)) {
						id = elem.id;
						if (typeof elem.trueValue != 'undefined') {
							trueValue = elem.trueValue;
							falseValue = '';
						}
						if (typeof elem.falseValue != 'undefined')
							falseValue = elem.falseValue;
					}
					cache[id] = { jQelem: $('#'+id)
								, trueValue: trueValue
								, falseValue: falseValue 
					};
				});
				
			} else {
				cache[id] = { jQelem: $('#'+inputFields)
							, trueValue: debugOn
							, falseValue: debugOff 
				};
			}
		}
		
		function setup() {
			if (typeof debugValueForTrue != 'undefined') {
				debugOn = debugValueForTrue;
				debugOff = '';
			}
			if (typeof debugValueForFalse != 'undefined')
				debugOff = debugValueForFalse;
			
			// popolamento dell'oggetto cache
			setCache();
			
			// reset iniziale dei campi input al loro rispettivo valore di false. Contamporaneamente popola anche l'oggetto status
			setAllInputs(false);
			
			$('body').prepend( $divDebug );
			
			// Associazioen degli eventi
			Mousetrap.bind(combo, toggleDebugMode);
			$close.on('click', toggleDebugMode);
			preventSelection();
		}

		function setStatus(id, value) {
			status[id] = value;
		}
		function setInputElem(cacheInput, value) {
			var $input = cacheInput.jQelem;
			if (value === true) 
				$input.val(cacheInput.trueValue);
			else if (value === false) 
				$input.val(cacheInput.falseValue);
		}
		
		function setSingleInputById(id, value) {
			setInputElem(cache[id], value);
			setStatus(id, value);
		}
		function setAllInputs(value) {
			for(key in cache) {
				setSingleInputById(key, value);
			}
		}
		
		function toggleSingle(id) {
			setSingleInputById(id, !status[id]);
		}
		function toggleAll() {
			setAllInputs(!active);
			active = !active;
		}
		
		
		var debugOn = 'true',
			debugOff = 'false',
			status = {} , 
			eventi = {} ,
			cache = {} ,
			active = false,
			$close = getCloseDiv().hide();
			$divDebug = makeDebugDiv();
		
		setup();
	}
};