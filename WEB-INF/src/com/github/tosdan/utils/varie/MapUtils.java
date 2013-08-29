package com.github.tosdan.utils.varie;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * @author Daniele
 * @version 0.0.3-b2013-08-29
 */
public class MapUtils
{
	/**
	 * Trasforma una mappa di liste di mappe in una mappa di oggetti. Ad ogni chiave della mappa deve esser associata una lista di mappe che costituiranno i dati da inserire nell'oggetto creato che dovra' sostituire la lista
	 * @param sourceMap
	 * @param type
	 * @return
	 */
	public static <T extends MapFeadable> Map<String, T> turnKeyedMapListIntoMapOfT(Map<String, List<Map<String, Object>>> sourceMap, Class<T> type) {
		
		Map<String, T> destMap = new LinkedHashMap<String, T>();
		
		String key;
		T tempInstanceOfT = null;
		List<Map<String, Object>> tempMapList;
		
		Set<Entry<String, List<Map<String, Object>>>> sourceMapEntrySet = sourceMap.entrySet();
		for( Entry<String, List<Map<String, Object>>> entry : sourceMapEntrySet ) {
			
			key = entry.getKey();
			
			tempMapList = entry.getValue();			
			try {
				
				tempInstanceOfT = type.newInstance();
				
			} catch ( InstantiationException e ) {
//				e.printStackTrace();
				throw new UnsupportedOperationException( e.getMessage(), e );
			} catch ( IllegalAccessException e ) {
//				e.printStackTrace();
				throw new TypeNotPresentException( e.getMessage(), e );
			}
			
			
			for( Map<String, Object> map : tempMapList ) {
				tempInstanceOfT.addRecordAsMap( map );
			}
			
			destMap.put( key, tempInstanceOfT );
		}
		
		return destMap;
	}
	

	/**
	 * Fonde due o piu' mappe.
	 * @param maps
	 * @return
	 */
	public static Map<String, Object> mergeMaps(Map<String, ? extends Object>... maps) {
		Map<String, Object> retVal = new HashMap<String, Object>();
		for( int i = 0 ; i < maps.length ; i++ ) {
			if (maps[i] != null)
				retVal.putAll( maps[i] );
		}
		return retVal;
	}
}
