package org.apache.jsp.jsp.jquery_iframe_002dtransport_js;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class iframetransport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>iframe-transport.js Tests</title>\r\n");
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
      out.write("<script type=\"text/javascript\" src=\"../../js-3rd-party/bootstrap-fileupload.js\"></script>\r\n");
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
      out.write("\r\n");
      out.write("<script type=\"\">\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<style>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t\t\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t\t<form action=\"\" class=\"well span7\">\r\n");
      out.write("\t\t\t<fieldset>\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"testo\" />\r\n");
      out.write("\t\t\t\t<label class=\"checkbox\">Checkbox\r\n");
      out.write("\t\t\t\t\t<input type=\"checkbox\" class=\"no-outline\" name=\"checkscatola\" value=\"check1\" />\r\n");
      out.write("\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t<label class=\"radio\">\r\n");
      out.write("\t\t\t\t\t<input class=\"no-outline\" type=\"radio\" name=\"radiob\" value=\"1\" />Radio1\r\n");
      out.write("\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t<label class=\"radio\">\r\n");
      out.write("\t\t\t\t\t<input class=\"no-outline\" type=\"radio\" name=\"radiob\" value=\"2\" />Radio2\r\n");
      out.write("\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\r\n");
      out.write("        \r\n");
      out.write("\t\t\t\t<div class=\"fileupload fileupload-new\" data-provides=\"fileupload\">\r\n");
      out.write("\t\t\t\t\t<div class=\"input-append\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"uneditable-input span3\"><i class=\"icon-file fileupload-exists\"></i> <span class=\"fileupload-preview\"></span></div>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"btn btn-file\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"fileupload-new\">Scelta file</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"fileupload-exists\">Cambia</span>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"file\" name=\"fileCaricato\" id=\"fileCaricato\"/>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"#\" class=\"btn fileupload-exists\" data-dismiss=\"fileupload\">Reset</a>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"controls\">\r\n");
      out.write("\t\t\t\t\t<input class=\"btn\" type=\"submit\" value=\"Invia\"/>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</fieldset>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t\t<a href=\"javascript:void()\" class=\"btn\">Pulsante</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"row span12\">\r\n");
      out.write("\t\t\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
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
