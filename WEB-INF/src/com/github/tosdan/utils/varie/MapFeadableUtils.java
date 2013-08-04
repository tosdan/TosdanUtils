package com.github.tosdan.utils.varie;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * @author Daniele
 * @version 0.0.1-b2013-08-03
 */
public class MapFeadableUtils
{
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

}
