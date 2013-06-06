package com.github.tosdan.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.tosdan.beta.utils.servlets.DownloadServlet;
import com.github.tosdan.utils.io.ReadToOutputStream;
import com.github.tosdan.utils.servlets.BasicHttpServlet;

/**
 * 
 * @author Daniele
 * @version 0.1.0-r13.06.05
 */
@SuppressWarnings( "serial" )
public class JqueryFileDownloadJSTests extends BasicHttpServlet
{
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }

	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		String richiesta = req.getParameter( "richiesta" );
		ServletOutputStream out = resp.getOutputStream();
		
		if (("successful").equalsIgnoreCase(richiesta)) {
			String filename = "prova.xls";
			resp.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\"" );
			
			// Il coockie e' necessario per comunicare ad jQuery.fileDownload.js che il download e' avvenuto
			// Va inserito prima di iniziare a trasmettere il flusso di output
			Cookie cookie = new Cookie( "fileDownload", "true" );
			cookie.setPath( "/" );
			resp.addCookie( cookie );
			
			try {
				Thread.sleep(3000); // simla il tempo necessario ad una ipotetica elaborazione
				ReadToOutputStream.readFile( filename, out ); // simula la lettura e il download del file generato dell'elaborazione
			} catch ( IOException e ) {
				// TODO File non trovato (o stream non disponibile per la lettura in caso di inputstream)
				e.printStackTrace();
			} catch ( InterruptedException e ) {
				e.printStackTrace(); // TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}	
			
		} else if (("unsuccessful").equalsIgnoreCase(richiesta)) {
			String filename = this._ctx.getRealPath( "/jsp/jquery-downloadjs/error.jsp" );
			resp.setHeader("content-type","text/html; charset=UTF-8");
			resp.setContentType( "text/html" );
			ReadToOutputStream.readFile( filename, out );
			
		} else if (("fileIntrovabile").equalsIgnoreCase(richiesta)) {
			try {
				Thread.sleep(1000); // simla il tempo necessario ad una ipotetica elaborazione
			} catch ( InterruptedException e ) {
				e.printStackTrace();// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}		
			
			String filename = "introvabile.txt";
			String param = DownloadServlet.filenameParam;
			req.setAttribute( param, filename );
			RequestDispatcher disp = req.getRequestDispatcher( "/servlet/download/fwd" );
			disp.forward( req, resp );
			
		} else {
			try {
				Thread.sleep(2000); // simla il tempo necessario ad una ipotetica elaborazione
			} catch ( InterruptedException e ) {
				e.printStackTrace();// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}		
			
			String filename = "prova.xls";
			String param = DownloadServlet.filenameParam;
			req.setAttribute( param, filename );
			RequestDispatcher disp = req.getRequestDispatcher( "/servlet/download/fwd" );
			disp.forward( req, resp );
			
			
		}
		
		out.close();
			
	}
}
