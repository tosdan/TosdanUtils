package org.apache.jsp.SqlManagerFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sql_002dmanager_002dconf_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<title>Configurazione Sql Manager</title>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../css-3rd-party/bootstrap.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../css-3rd-party/bootstrap_integration.css\"/>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"../js-3rd-party/jquery-1.9.1.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../js-3rd-party/bootstrap.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<SCRIPT type=\"text/javascript\">\r\n");
      out.write("\tvar context = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ pageContext.request.contextPath }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("' ,\r\n");
      out.write("\t\taddress = context+'/servlet/sqlmanagerconf/test?dato=ciao&dato=addio' ;\r\n");
      out.write("\t\r\n");
      out.write("\tfunction test() {\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\turl: address ,\r\n");
      out.write("\t\t\ttype: 'post' ,\r\n");
      out.write("// \t\t\tdata: { dato: 'ciao', dato: 'addio' } ,\r\n");
      out.write("\t\t\tsuccess: function ( data, textStatus, jqXHR ) {\r\n");
      out.write("\t\t\t\t console.log('success');\r\n");
      out.write("\t\t\t\t\tconsole.log(data);\r\n");
      out.write("// \t\t\t\t\tconsole.log(jqXHR);\r\n");
      out.write("// \t\t\t\t\tconsole.log(textStatus);\r\n");
      out.write("\t\t\t} ,\r\n");
      out.write("\t\t\tcomplete: function (jqXHR, textStatus) {\r\n");
      out.write("\t\t\t\tconsole.log('complete');\r\n");
      out.write("// \t\t\t\tconsole.log(jqXHR);\r\n");
      out.write("// \t\t\t\tconsole.log(jqXHR.responseText);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<a class=\"btn\"><i class=\"icon-plus\"></i> Add Query</a>\r\n");
      out.write("\r\n");
      out.write("    <!-- Button to trigger modal -->\r\n");
      out.write("    <a href=\"#myModal\" role=\"button\" class=\"btn\" data-toggle=\"modal\">Launch demo modal</a>\r\n");
      out.write("     \r\n");
      out.write("    <!-- Modal -->\r\n");
      out.write("    <div id=\"myModal\" class=\"modal hide fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("\t    <div class=\"modal-header\">\r\n");
      out.write("\t\t    <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">x</button>\r\n");
      out.write("\t\t    <h3 id=\"myModalLabel\">Modal header</h3>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t    <p>One fine body...</p>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t    <button class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">Chiudi</button>\r\n");
      out.write("\t\t    <button class=\"btn btn-primary\" onclick=\"test();\">Salva</button>\r\n");
      out.write("\t    </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    \r\n");
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
