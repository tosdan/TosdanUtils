package com.github.tosdan.utils.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.yaml.snakeyaml.Yaml;

/**
 * Filtro per impostare l'header http di tutte le response per le chiamate alle pagine jsp
 * @author tosdan
 *
 */
public class NoCacheFilterParametrized implements Filter {

	private Map<String, Object> conf;

	private static final String DEBUG_NO_CACHE_FILTER = "debugNoCacheFilter";
	private static final String PROPERTIES_FILE = "NoCacheFilterParametrized.yml";

	@Override
	public void init( FilterConfig arg0 ) throws ServletException {
		try {
			conf = loadConf();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) res;
	    if (req.getParameter("ReloadNoCacheFilter") != null) {
	    	loadConf();
	    }
	    setHeader(response, req.getParameter(DEBUG_NO_CACHE_FILTER) != null);
	    chain.doFilter(req, response);
	}

	private void setHeader( HttpServletResponse response, boolean debug  ) throws IOException {
		String debugString = "";
		try {
			if ( conf != null && !conf.isEmpty() ) {

				debugString += "\t[NoCacheFilter START]";
				if (conf.containsKey("Cache-Control")) {
					response.setHeader("Cache-Control", conf.get("Cache-Control").toString());
					debugString += String.format("Cache-Control=%s\n", conf.get("Cache-Control").toString());
				}
				if (conf.containsKey("Pragma")) {
					response.setHeader("Pragma", conf.get("Pragma").toString()); // HTTP 1.0.
					debugString += String.format("Pragma=%s\n", conf.get("Pragma").toString());
				}
				if (conf.containsKey("Expires")) {
					response.setDateHeader("Expires", Integer.valueOf(conf.get("Expires").toString())); // Proxies.
					debugString += String.format("Expires=%s\n", Integer.valueOf(conf.get("Expires").toString()));
				}
				debugString += "\t[NoCacheFilter END]";

				if ( debug ) {
					System.out.println(debugString);
				}

			}
		} catch ( Exception e ) {
			// TODO log
			e.printStackTrace();
		}
	}

	@SuppressWarnings( "unchecked" )
	public Map<String, Object> loadConf() throws IOException {
		Map<String, Object> retval = new HashMap<String, Object>();
		URL url = NoCacheFilterParametrized.class.getResource(PROPERTIES_FILE);
		if (url != null) {
			InputStream is = url.openStream();

			retval = (Map<String, Object>) new Yaml().load(is);
			if (retval.containsKey("info")) {
				System.out.println("Loading NoCacheFilter config file...");
				System.out.printf("NoCacheFilter: %s params loaded.\n",
						(retval == null) ? 0 : retval.size());
			}
			is.close();
		} else {
			// TODO Warning
		}
		return retval;
	}

	@Override public void destroy() { conf = null; }
}
