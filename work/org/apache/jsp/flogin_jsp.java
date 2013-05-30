package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class flogin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=ISO-8859-15");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-15\" ?>\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-15\" />\r\n");
      out.write("<title>Fake Login</title>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css-3rd-party/bootstrap.css\" />\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
 // *** Questa pagina non deve finire in produzione *** 
String id = request.getParameter( "fakeID" );
String pagina = request.getParameter("pagina");

String pwd = request.getParameter( "pwd" );
if ( pwd != null && pwd.equals( "tosdan" ) ) {	
	if ( id == null || pagina == null ) {
		pagina = application.getContextPath() + "/jsp/AuthFilter/authfilter.jsp";
		id = "1";
		
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"row span12\">\r\n");
      out.write("\t\t\t<form action=\"\" method=\"post\">\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<label class=\"lead muted\">ID utente per il fake login.</label>\r\n");
      out.write("\t\t\t\t\t<input class=\"input-mini\" type=\"text\" name=\"fakeID\" value=\"");
      out.print( id );
      out.write("\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<label class=\"lead muted\">Pagina per il redirect.</label>\r\n");
      out.write("\t\t\t\t\t<input class=\"span7\" type=\"text\" name=\"pagina\" value=\"");
      out.print( pagina );
      out.write("\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"pwd\" value=\"");
      out.print( pwd );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<div><input class=\"btn\" type=\"submit\" /></div>\t\t\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("\t\t");

	} else {		
		session.setAttribute("UserID", id);
		
		if ( session.getAttribute( "UserID" ) != null ) {
			System.out.println( "user id "+id+" inserito correttamente in sessione" );
			response.sendRedirect( pagina );
		} else {
			out.println( "inserimento in sessione forzato fallito" );
		}
	}
}



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
