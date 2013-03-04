package org.apache.jsp.UploadFlieServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.GregorianCalendar;
import java.util.Enumeration;

public final class FormPerServlet_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" >\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\r\n");
      out.write("<title>Demo UploadFlieServlet</title>\r\n");
      out.write(" <style type=\"text/css\">\r\n");
      out.write("  #campo1 {\r\n");
      out.write("  \twidth: 300px;\r\n");
      out.write("  }\r\n");
      out.write(" </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<form action=\"");
      out.print( application.getContextPath() );
      out.write("/UploadServlet?returnQueryString=prova%3Dciao&upload-path=d:/temp/upsrvlt/\" enctype=\"multipart/form-data\" method=\"post\">\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t<h2>UploadFlieServlet Demo</h2>\r\n");
      out.write("\t\t\t<p>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ sessionScope[\"UploadServlet-result\"] }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" &nbsp;</p>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<label for=\"check1\">check1</label>\r\n");
      out.write("\t\t<input id=\"check1\" type=\"checkbox\" name=\"check\" value=\"check1\"/>\r\n");
      out.write("\t\t<label for=\"check2\">check2</label>\r\n");
      out.write("\t\t<input id=\"check2\" type=\"checkbox\" name=\"check\" value=\"check2\"/>\r\n");
      out.write("\t\t<label for=\"check3\">check3</label>\r\n");
      out.write("\t\t<input id=\"check3\" type=\"checkbox\" name=\"check\" value=\"check3\"/>\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\t\t<label for=\"campo1\">Campo di testo:</label>\r\n");
      out.write("\t\t<input type=\"text\" name=\"campo1\" id=\"campo1\" value=\"Anche questo testo viene intercettato dalla servlet\"/>\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\t\t<input type=\"file\" name=\"file1\" id=\"file1\" />\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\r\n");
      out.write("<!-- \t<input type=\"hidden\" value=\"prova=ciao\" name=\"returnQueryString\"/> -->\r\n");
      out.write("\r\n");
      out.write("\t\t<input type=\"submit\" value=\"carica file\" />\r\n");
      out.write("\r\n");
      out.write("\t\t");
 	session.removeAttribute( "upload-result" ); 
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t</form>\r\n");
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
