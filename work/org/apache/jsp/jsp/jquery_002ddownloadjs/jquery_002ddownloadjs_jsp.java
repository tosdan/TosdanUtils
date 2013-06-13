package org.apache.jsp.jsp.jquery_002ddownloadjs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class jquery_002ddownloadjs_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=8; IE=7; Chrome=1\" /> <!-- Problemi con IE9+ quindi forzatura ad IE 8. Si potrebbe risovere anche impostando a get il methed di fileDownload.js -->\r\n");
      out.write("<title>jQuery Download.js Tests</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--[if lt IE 9]>\t<script src=\"../../js-3rd-party/html5shiv.js\"></script>\t\t<![endif]-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"[JS library]\"></script>\r\n");
      out.write("<!--[if (gte IE 6)&(lte IE 8)]>\r\n");
      out.write("   <script type=\"text/javascript\" src=\"../../js-3rd-party/selectivizr.js\"></script>\r\n");
      out.write("   <noscript><link rel=\"stylesheet\" href=\"[fallback css]\" /></noscript>\r\n");
      out.write("<![endif]--> \r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-1.9.1.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/bootstrap.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-ui-1.10.3/ui/jquery-ui.js\"></script>\r\n");
      out.write("<!-- <script type=\"text/javascript\" src=\"../../js-3rd-party/jquery-ui-1.10.0.custom.min.js\"></script> \t-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/jquery.fileDownload.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap.css\"/>\r\n");
      out.write("<!--[if lt IE 8]>\t<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap-ie7buttonfix.css\"> \t<![endif]-->\r\n");
      out.write("<!--[if IE 8]>\t\t<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap-ie8buttonfix.css\"> \t<![endif]-->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/custom-theme/jquery-ui-1.10.0.custom.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/custom-theme/jquery.ui.1.10.0.ie.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css-3rd-party/bootstrap_integration.css\"/>\r\n");
      out.write("<!-- <link rel=\"stylesheet\" href=\"../../js-3rd-party/jquery-ui-1.10.3/themes/redmond/jquery-ui.css\"/>\t\t-->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("$(function () {\r\n");
      out.write("    $(document).on(\"click\", \"a.fileDownloadCustomRichExperience\", function () {\r\n");
      out.write(" \r\n");
      out.write("        var $preparingFileModal = $(\"#preparing-file-modal\");\r\n");
      out.write("        $preparingFileModal.dialog({ modal: true });\r\n");
      out.write(" \r\n");
      out.write("        $.fileDownload( $(this).prop('href'), {\r\n");
      out.write("        \thttpMethod: \"post\" ,\r\n");
      out.write("        \tdata: { prova: 'prova' } , \r\n");
      out.write("            successCallback: function (url) {\r\n");
      out.write("                $preparingFileModal.dialog('close');\r\n");
      out.write("                \r\n");
      out.write("            } ,\r\n");
      out.write("            \r\n");
      out.write("            failCallback: function (responseHtml, url) {\r\n");
      out.write("            \tconsole.log('responseHtml: '+responseHtml);\r\n");
      out.write("            \tvar risposta = $( $.trim(responseHtml) ).html();\r\n");
      out.write("            \tconsole.log('risposta: '+risposta);\r\n");
      out.write(" \t\t\t\t$('#error-modal').html(risposta);\r\n");
      out.write("                $preparingFileModal.dialog('close');\r\n");
      out.write("                $(\"#error-modal\").dialog({ modal: true, width: 720, height: 520 });\r\n");
      out.write("            }\r\n");
      out.write("            \r\n");
      out.write("        });\r\n");
      out.write("        return false; //this is critical to stop the click event which will trigger a normal file download!\r\n");
      out.write("    });\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write(" \t.ui-progressbar-value { /* gif per la progress bar */\r\n");
      out.write("\t    background-image: url(\"../../images/pbar-ani2.gif\");\r\n");
      out.write("\t}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div id=\"preparing-file-modal\" title=\"Preparing report...\" style=\"display: none;\">\r\n");
      out.write("    We are preparing your report, please wait...\r\n");
      out.write(" \r\n");
      out.write("    <div class=\"ui-progressbar-value ui-corner-left ui-corner-right\" style=\"width: 100%; height:22px; margin-top: 20px;\"></div>\r\n");
      out.write("</div>\r\n");
      out.write(" \r\n");
      out.write("<div id=\"error-modal\" title=\"Error\" style=\"display: none;\">\r\n");
      out.write("<!--     There was a problem generating your report, please try again. -->\r\n");
      out.write("</div>\r\n");
      out.write("\t<div class=\"row span12\"><p></p></div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t\t<div class=\"row span4 well\">\r\n");
      out.write("\t\t\t<a class=\"fileDownloadCustomRichExperience\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/servlet/jqdownjs/getFile?richiesta=successful\">Prova.xls (Success)</a>\r\n");
      out.write("\t\t\t<br />\r\n");
      out.write("\t\t\t<a class=\"fileDownloadCustomRichExperience\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/servlet/jqdownjs/getFile?richiesta=unsuccessful\">Prova.xls (Fail)</a>\r\n");
      out.write("\t\t\t<br />\r\n");
      out.write("\t\t\t<a class=\"fileDownloadCustomRichExperience\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/servlet/jqdownjs/getFile\">Prova.xls (no param)</a>\r\n");
      out.write("\t\t\t<br />\r\n");
      out.write("\t\t\t<a class=\"fileDownloadCustomRichExperience\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/servlet/jqdownjs/getFile?richiesta=fileIntrovabile\">Prova.xls (file introvabile)</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t<h4>Test Css3 selectivizr.js per IE8/IE7</h4>\r\n");
      out.write("\t\t<div class=\"span2\">\r\n");
      out.write("\t\t\t<table class=\"table table-striped \">\r\n");
      out.write("\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t\t<td>A</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t\t<td>B</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t\t<td>C</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
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
