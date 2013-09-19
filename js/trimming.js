//trimming space from both side of the string
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
 
//trimming space from left side of the string
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}
 
//trimming space from right side of the string
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}