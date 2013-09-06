// Abilita la modalita' Debug
function ctrlAltD_ebug(inputField, debugValue) {
	var divDebugModeMsg = $("<div id='divDebug'><div>"),
		$inputField = $('#'+inputField),
		P,
		debugOn = 'true',
		debugOff = 'false';
	
	if (debugValue) {
		debugOn = debugValue;
		debugOff = '';
	}
	$inputField.val(debugOff);
	
	$('body').prepend(divDebugModeMsg);
	divDebugModeMsg.css({'border':'2px solid #444', 'background-color':'#DEE3E7'}).hide();
	P = $("<p>Modalit&agrave; debug Attiva</p>")
			.css( { 'text-shadow':'1px 1px #CCC', 'color':'red', 'font-size':20, 'text-align':'center' } );
	divDebugModeMsg.append(P);
	
	$(document).keypress("d",function(e) {
		if(e.ctrlKey)
			if (e.altKey)
			{
				divDebugModeMsg.slideToggle(100);
				
				if ( $inputField.val() === debugOn )
					$inputField.val(debugOff);
				else
					$inputField.val(debugOn);
			}
	});
};