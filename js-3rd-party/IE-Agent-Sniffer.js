// Browser agent sniffer.
// works with IE10, IE9, IE8, IE7
var IESniffer = (function() {
  "use strict";
  var tmp = (document["documentMode"] || document.attachEvent) && "ev"
       , msie = tmp 
                  && (tmp = window[tmp + "al"])
                  && tmp("/*@cc_on 1;@*/")
                  && +((/msie (\d+)/i.exec(navigator.userAgent) || [])[1] || 0)
  ;
  return msie || void 0;
})();