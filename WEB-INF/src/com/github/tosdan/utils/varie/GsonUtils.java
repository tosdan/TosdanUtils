package com.github.tosdan.utils.varie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

	public GsonUtils() {
		// TODO Auto-generated constructor stub
	}

	public static Gson getInstance() {
		return new GsonBuilder()
//						.setDateFormat("dd/MM/yyyy") //TODO non mi ricordo perchè l'avevo messo
						.setPrettyPrinting().create();
	}	

	public static String parseRequestBody(HttpServletRequest req) throws IOException {
		StringBuilder json = new StringBuilder();
		BufferedReader reqReader = req.getReader();
		String line;
		while ( (line = reqReader.readLine()) != null ) {
			json.append(line);
		}
		reqReader.close();
		return json.toString();
	}
	
	@Deprecated
	public static String parseJsonRequest( HttpServletRequest req ) throws IOException {
		return parseRequestBody(req);
	}
	
	public static Map<String, Object> convertTreeMapToHashMap( Map<String, Object> paramsMap ) {
		Map<String, Object> retval = new HashMap<String, Object>();
		for(String key : paramsMap.keySet()) {
			retval.put(key, paramsMap.get(key));
		}
		return retval;
	}
}
