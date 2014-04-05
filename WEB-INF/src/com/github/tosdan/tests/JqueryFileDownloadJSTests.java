package com.github.tosdan.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.github.tosdan.beta.utils.servlets.DownloadServlet;
import com.github.tosdan.dismesse.utils.servlets.BasicHttpServletV2;
import com.github.tosdan.utils.varie.JsonUtils;

/**
 * 
 * @author Daniele
 * @version 0.1.1-b2013-08-08
 */
@SuppressWarnings( "serial" )
public class JqueryFileDownloadJSTests extends BasicHttpServletV2
{
	@Override protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }
	@Override protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException { this.doService( req, resp ); }

	protected void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		String richiesta = req.getParameter( "richiesta" );
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		System.out.println( "req.getParameter('prova') = " + req.getParameter("prova") );
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
				IOUtils.copy( new FileInputStream(filename), servletOutputStream ); // simula la lettura e il download del file generato dell'elaborazione
			} catch ( IOException e ) {
				// TODO File non trovato (o stream non disponibile per la lettura in caso di inputstream)
				e.printStackTrace();
			} catch ( InterruptedException e ) {
				e.printStackTrace(); // TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}	
			
			
			
		} else if (("unsuccessful").equalsIgnoreCase(richiesta)) {

			String filename = ctx.getRealPath( "/jsp/jquery-downloadjs/error.jsp" );
			resp.setHeader("Content-Type","text/html; charset=UTF-8");
			resp.setHeader( "Cache-Control", "private" );
			resp.setContentType( "text/html" );
			
			IOUtils.copy( new FileInputStream(filename), servletOutputStream );
			
			
		} else if (("successMaMessaggioErrore").equalsIgnoreCase(richiesta)) { // Messaggio Errore con json
			resp.setContentType( "text/html; charset=UTF-8" );
			System.out.println("req.getHeader('X-Requested-With') = " + req.getHeader("X-Requested-With"));
			Map<String, Object> errMap = new HashMap<String, Object>();
			errMap.put("error", true);
			errMap.put("errMsg", "errore ebbasta");
			
			servletOutputStream.print(JsonUtils.toJSON(errMap));
			
		} else if (("fileIntrovabile").equalsIgnoreCase(richiesta)) {
			
			try {
				Thread.sleep(1000); // simla il tempo necessario ad una ipotetica elaborazione
			} catch ( InterruptedException e ) {
				e.printStackTrace();// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}		
			
			String filename = "introvabile.txt";
			String param = DownloadServlet.FILENAME_PARAM;
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
			String param = DownloadServlet.FILENAME_PARAM;
			req.setAttribute( param, filename );
			RequestDispatcher disp = req.getRequestDispatcher( "/servlet/download/fwd" );
			disp.forward( req, resp );
			
			
		}
			
	}
	
}
