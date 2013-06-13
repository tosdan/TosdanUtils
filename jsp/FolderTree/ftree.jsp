<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="fuelux">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>

<!--[if lt IE 9]>	<script src="../../js-3rd-party/html5shiv.js"></script>		<![endif]-->
<!--[if (gte IE 6)&(lte IE 8)]>
	<script type="text/javascript" src="[JS library]"></script>
	<script type="text/javascript" src="../../js-3rd-party/selectivizr.js"></script>
	<noscript><link rel="stylesheet" href="[fallback css]" /></noscript>
<![endif]--> 
<script type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js-3rd-party/fuelux/loader.js"></script>
<script type="text/javascript" src="../../js-3rd-party/fuelux/require.js"></script>
<!-- <script type="text/javascript" src="../../js-3rd-party/bootstrap.js"></script> -->
<%-- // shim per abilitare jquery 2 solo su IE9+ 
	<!--[if lt IE 9]>	<script  type="text/javascript" src="../../js-3rd-party/jquery-1.9.1.js"></script>			<![endif]-->
	<!--[if gte IE 9]><!--> 	<script  type="text/javascript" src="../../js-3rd-party/jquery-2.0.2.js"></script> 	<!--<![endif]-->	--%>
<script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="../../js-3rd-party/jquery-ui-1.10.0.custom.min.js"></script> 	-->
<script type="text/javascript" src="../../js-3rd-party/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../../js-3rd-party/bootstrap-fileupload.js"></script>

<script type="text/javascript" src="../../js-3rd-party/fuelux/tree.js"></script>
<script type="text/javascript" src="../../js-3rd-party/fuelux/sample/data.js"></script>
<script type="text/javascript" src="../../js-3rd-party/fuelux/sample/datasource.js"></script>
<script type="text/javascript" src="../../js-3rd-party/fuelux/sample/datasourceTree.js"></script>


<link rel="stylesheet" href="../../css-3rd-party/bootstrap.css"/>
<!--[if lt IE 8]>	<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie7buttonfix.css"> 	<![endif]-->
<!--[if IE 8]>		<link rel="stylesheet" href="../../css-3rd-party/bootstrap-ie8buttonfix.css"> 	<![endif]-->
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery-ui-1.10.0.custom.css"/>
<link rel="stylesheet" href="../../css-3rd-party/custom-theme/jquery.ui.1.10.0.ie.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap_integration.css"/>
<link rel="stylesheet" href="../../css-3rd-party/bootstrap-fileupload.css"/>
<!-- <link rel="stylesheet" href="../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css"/>		-->
<link rel="stylesheet" href="../../css-3rd-party/fuelux.css"/>
<link rel="stylesheet" href="../../css-3rd-party/fuelux-integrations.css"/>
	
<script type="text/javascript">


//TREE
$(function () {
	var DataSourceTree = function (options) {
		this._data = options.data;
		this._url = options.url;
	};

	DataSourceTree.prototype = {
		data: function (options, callback) {
			console.log('options: ');
			console.log(options);
			var dataSrcOpts = this;
			var additionalParams =  options.additionalParameters ? options.additionalParameters : {};
// 			console.log( additionalParams );
			
			$.ajax({
				method: 'post' ,
				url: dataSrcOpts._url ,
				data: additionalParams ,
				success: function ( jsonData, textStatus, jqXHR ) {
					var json = $.parseJSON(jsonData);
// 					console.log(json.result);
					callback({ data: json.result });
				} 
			});				
		}
	
	};
	
	/* * * * */
	
	var dataSourceTree = new DataSourceTree({
// 		data:  [ { name: "root" , type: "folder" , additionalParameters: { idfolder: '0' } } ],
		url: '/TosdanUtils/servlet/ftree/' 
	});
	
	$('#ex-tree').on('loaded', function (e) {
		console.log('Loaded');
	});
	
	$('#ex-tree').tree({
		dataSource: dataSourceTree,
		loadingHTML: '<div class="static-loader">Loading...</div>',
		multiSelect: false,
		cacheItems: false
	});
	
	$('#ex-tree').on('selected', function (e, info) {
		console.log('Select Event: ', info);
	});
	
	$('#ex-tree').on('opened', function (e, info) {
		console.log('Open Event: ', info);
	});
	
	$('#ex-tree').on('closed', function (e, info) {
		console.log('Close Event: ', info);
	});
});


</script>

<style type="text/css">

	
</style>

</head>
<body>


	<!-- TREE -->
	<div id="ex-tree" class="tree">
		<div class="tree-folder" style="display:none;">
			<div class="tree-folder-header">
				<i class="icon-folder-close"></i>

				<div class="tree-folder-name"></div>
			</div>
			<div class="tree-folder-content"></div>
			<div class="tree-loader" style="display:none">
			</div>
		</div>
		<div class="tree-item" style="display:none;">
			<i class="tree-dot"></i>

			<div class="tree-item-name"></div>
		</div>
	</div>

</body>
</html>