package org.apache.jsp.jsp.FolderTree;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ftree_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html class=\"fuelux\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("\r\n");
      out.write("<!--[if lt IE 9]>\t<script src=\"../../js-3rd-party/html5shiv.js\"></script>\t\t<![endif]-->\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"[JS library]\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"../../js-3rd-party/selectivizr.js\"></script>\r\n");
      out.write("\t<noscript><link rel=\"stylesheet\" href=\"[fallback css]\" /></noscript>\r\n");
      out.write("<![endif]--> \r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-1.9.1.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/loader.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/require.js\"></script>\r\n");
      out.write("<!-- <script type=\"text/javascript\" src=\"../../js-3rd-party/bootstrap.js\"></script> -->\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js\"></script>\r\n");
      out.write("<!-- <script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-ui-1.10.0.custom.min.js\"></script> \t-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery.fileDownload.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/bootstrap-fileupload.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/tree.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/sample/data.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/sample/datasource.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/fuelux/sample/datasourceTree.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap.css\"/>\r\n");
      out.write("<!--[if lt IE 8]>\t<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap-ie7buttonfix.css\"> \t<![endif]-->\r\n");
      out.write("<!--[if IE 8]>\t\t<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap-ie8buttonfix.css\"> \t<![endif]-->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/custom-theme/jquery-ui-1.10.0.custom.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/custom-theme/jquery.ui.1.10.0.ie.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap_integration.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap-fileupload.css\"/>\r\n");
      out.write("<!-- <link rel=\"stylesheet\" href=\"../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css\"/>\t\t-->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/fuelux.css\"/>\r\n");
      out.write("<!-- <link rel=\"stylesheet\" href=\"../../css-3rd-party/fuelux-integrations.css\"/> -->\r\n");
      out.write("\t\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//TREE\r\n");
      out.write("$(function () {\r\n");
      out.write("\tvar DataSourceTree = function (options) {\r\n");
      out.write("\t\tthis._data = options.data;\r\n");
      out.write("\t\tthis._url = options.url;\r\n");
      out.write("\t};\r\n");
      out.write("\r\n");
      out.write("\tDataSourceTree.prototype = {\r\n");
      out.write("\t\tdata: function (options, callback) {\r\n");
      out.write("\t\t\tconsole.log('options: ');\r\n");
      out.write("\t\t\tconsole.log(options);\r\n");
      out.write("\t\t\tvar dataSourceOptions = this;\r\n");
      out.write("\t\t\tvar additionalParams =  options.additionalParameters ? options.additionalParameters : {};\r\n");
      out.write("\t\t\tconsole.log( additionalParams );\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\tmethod: 'post' ,\r\n");
      out.write("// \t\t\t\tasync: false , \r\n");
      out.write("\t\t\t\turl: dataSourceOptions._url ,\r\n");
      out.write("\t\t\t\tdata: additionalParams ,\r\n");
      out.write("\t\t\t\tsuccess: function ( jsonData, textStatus, jqXHR ) {\r\n");
      out.write("\t\t\t\t\tvar json = $.parseJSON(jsonData);\r\n");
      out.write("// \t\t\t\t\tconsole.log(json.result);\r\n");
      out.write("\t\t\t\t\tcallback({ data: json.result });\r\n");
      out.write("\t\t\t\t} \r\n");
      out.write("\t\t\t});\t\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t};\r\n");
      out.write("\t\r\n");
      out.write("\t/* * * * */\r\n");
      out.write("\t\r\n");
      out.write("\tvar dataSourceTree = new DataSourceTree({\r\n");
      out.write("\t\tdata:  [ { name: \"root\" , type: \"folder\" , additionalParameters: { idfolder: '0' } } ],\r\n");
      out.write("\t\turl: '/TosdanUtils/servlet/ftree/' \r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#ex-tree').on('loaded', function (e) {\r\n");
      out.write("\t\tconsole.log('Loaded');\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#ex-tree').tree({\r\n");
      out.write("\t\tdataSource: dataSourceTree,\r\n");
      out.write("\t\tloadingHTML: '<div class=\"static-loader\">Loading...</div>',\r\n");
      out.write("\t\tmultiSelect: false,\r\n");
      out.write("\t\tcacheItems: false\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#ex-tree').on('selected', function (e, info) {\r\n");
      out.write("\t\tconsole.log('Select Event: ', info);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#ex-tree').on('opened', function (e, info) {\r\n");
      out.write("\t\tconsole.log('Open Event: ', info);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#ex-tree').on('closed', function (e, info) {\r\n");
      out.write("\t\tconsole.log('Close Event: ', info);\r\n");
      out.write("\t});\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".fuelux .tree .tree-item .tree-dot {\r\n");
      out.write("\tposition: absolute;\r\n");
      out.write("\ttop: 1px;\r\n");
      out.write("\tleft: 5px;\r\n");
      out.write("\tfloat: left;\r\n");
      out.write("\tdisplay: inline-block;\r\n");
      out.write("\twidth: 14px;\r\n");
      out.write("\theight: 14px;\r\n");
      out.write("\tmargin-top: 1px;\r\n");
      out.write("\tline-height: 14px;\r\n");
      out.write("\tvertical-align: text-top;\r\n");
      out.write("\tbackground-image: url(\"../img/glyphicons-halflings.png\");\r\n");
      out.write("\tbackground-repeat: no-repeat;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".fuelux .tree-dot {\r\n");
      out.write("    width: 16px;\r\n");
      out.write("    background-position: -384px -120px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".fuelux .icon-ok {\r\n");
      out.write("    width: 16px;\r\n");
      out.write("    background-position: -408px -120px;\r\n");
      out.write("}\r\n");
      out.write(".fuelux .icon-folder-close {\r\n");
      out.write("    width: 16px;\r\n");
      out.write("    background-position: -384px -120px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<!-- TREE -->\r\n");
      out.write("\t<div id=\"ex-tree\" class=\"tree\">\r\n");
      out.write("\t\t<div class=\"tree-folder\" style=\"display:none;\">\r\n");
      out.write("\t\t\t<div class=\"tree-folder-header\">\r\n");
      out.write("\t\t\t\t<i class=\"icon-folder-close\"></i>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"tree-folder-name\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"tree-folder-content\"></div>\r\n");
      out.write("\t\t\t<div class=\"tree-loader\" style=\"display:none\">\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"tree-item\" style=\"display:none;\">\r\n");
      out.write("\t\t\t<i class=\"tree-dot\"></i>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"tree-item-name\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
