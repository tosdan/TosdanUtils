<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% String ctxPath = application.getContextPath(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<%=ctxPath%>/js-3rd-party/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js-3rd-party/mousetrap.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/debugSwitch.js"></script>
<script type="text/javascript">
$(function() {
// 	debugSwitch.init(['debug1', 'debug2'], ['ctrl+alt+d','d e b u g space m o d e']);
	debugSwitch.init([{'id':'debug1', trueValue: 'ciao'}, 'debug2'], ['ctrl+alt+d','d e b u g space m o d e']);
});
</script>

<title>Debug Switch</title>
</head>
<body>
<form id="demo" action="">
<br>
	<input id="debug1" value="" type="text" />
<!-- 	<input id="debug1" value="" type="hidden" /> -->
<br>	
	<input id="debug2" value="" type="text" />
<!-- 	<input id="debug2" value="" type="hidden" /> -->
</form>
</body>
</html>