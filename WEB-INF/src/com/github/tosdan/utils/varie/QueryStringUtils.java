package com.github.tosdan.utils.varie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QueryStringUtils {

	@SuppressWarnings( "unchecked" )
	public static Map<String, Object> parse(String queryString, Gson gson) {
		Map<String, Object> retval = new HashMap<String, Object>();		
		List<String> temp;
		Object prevVal;
	    if (queryString != null) {
			for( String pair : queryString.split("&") ) {

				int eq = pair.indexOf("=");
				if ( eq > 0 ) {
					String key = decode(pair.substring(0, eq));
					String value = decode(pair.substring(eq + 1));

					if ( !key.isEmpty() && !value.isEmpty() ) {

						prevVal = retval.get(key);
						if ( prevVal != null ) {
							if ( prevVal instanceof List ) {
								((List<String>) prevVal).add(value);
							} else {
								temp = new ArrayList<String>();
								temp.add(prevVal.toString());
								temp.add(value);
								retval.put(key, temp);
							}
						} else {
							Object objValue = getValue(value, gson);
							retval.put(key, objValue);
						}
					}
				}
			} 
		}
		return retval;
	}

	private static Object getValue(String value, Gson gson) {
		Object retval = value;
		
		boolean jsonKeyPairLike = value.contains(":") && value.contains("\"");
		boolean arrayLike = value.contains("[") && value.contains("]"); 
		boolean objectLike = value.contains("{") && value.contains("}"); 
		
		if (jsonKeyPairLike && (arrayLike || objectLike)) {
			try {
				retval = gson.fromJson(value, Object.class);
			} catch (Exception e) {
				retval = value;
			}
		}
		
		return retval;
	}
	
	public static String decode(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    Map<String, Object> result = QueryStringUtils.parse("k1=v1"+"&k2"+"&k3=v3"+ "&k3"+ "&=" +"&k1=v4"+"&k1=v2"+"&k5=hello+%22world", gson);
	    System.out.println("Result:" + result);
	    result = QueryStringUtils.parse("nomeProcedura=casPhoenix&parametriEstrazione=%7B%22mesi%22:%5B%22gennaio%22,%22febbraio%22,%22marzo%22,%22aprile%22,%22maggio%22,%22giugno%22,%22luglio%22,%22agosto%22,%22settembre%22,%22ottobre%22,%22novembre%22,%22dicembre%22%5D,%22anno%22:%222016%22,%22mese%22:%22maggio%22,%22aziende%22:%5B%7B%22codazi%22:%22103599%22,%22desazi%22:%22CASSA+CENTRALE+BANCA%22,%22dscompany%22:%22CASSA+CENTRALE+BANCA%22,%22idfoldersh%22:%220000000693%22,%22codazi6cifre%22:%22103599%22,%22ConversionType%22:%22GP3%22,%22InputPath%22:%22%2Fmnt%2Fgp3%2Fclientdata%2F000001%2Foutput%2F%22,%22DtLastLoad%22:%22apr+12+2016++8:33AM%22,%22ActionPermitted%22:%221,1,0,0,0%22,%22timbratura%22:%22B%22,%22codgpres%22:%2204%22,%22idcompany%22:%22103599%22,%22idgroup%22:%22113+++++++%22,%22ambiente_hr%22:%22000001%22,%22ambiente%22:%22000001%22%7D%5D%7D", gson);
	    System.out.println("Result:" + result);
	    System.out.println(result.get("parametriEstrazione"));
	    System.out.println(result.get("parametriEstrazione").getClass().getName());
//	    System.out.println("Result:" + gson.fromJson((String) result.get("nomeProcedura"), Object.class));
	}
}
