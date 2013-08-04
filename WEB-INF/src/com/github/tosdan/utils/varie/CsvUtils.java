package com.github.tosdan.utils.varie;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * 
 * @author Daniele
 * @version 0.0.1-b2013-08-03
 */
public class CsvUtils
{
	/**
	 * 
	 * @param csvFile Percorso file.
	 * @param key Chiave secondo la quale raggruppare i records estratti.
	 * @return Mappa con chiave key e valore lista di records appartenenti a key.
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(String csvFile, String key) throws IOException {		
		return getKeyedRecords( csvFile, key, null );
	}
	
	/**
	 * 
	 * @param csvFile Percorso file.
	 * @param key Chiave secondo la quale raggruppare i records estratti.
	 * @param processor Oggetto per effettuare elaborazioni sul contenuto di ogni cella.
	 * @return Mappa con chiave key e valore lista di records appartenenti a key.
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(String csvFile, String key, CellProcessor[] processor) throws IOException {

		ICsvMapReader mapReader = new CsvMapReader( new FileReader( csvFile ), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE );
		final String[] header = mapReader.getHeader( true );
		
		if (processor == null)
			processor = new CellProcessor[header.length];
		
		Map<String, List<Map<String, Object>>> keyedRecordsList = new LinkedHashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> recordsMapList;
		Map<String, Object> record;
		
		while ( (record = mapReader.read(header, processor)) != null ) {
			String tempKeyValue = record.get( key ).toString();
			recordsMapList = keyedRecordsList.get( tempKeyValue );
			
			if (recordsMapList == null)
				recordsMapList = new ArrayList<Map<String, Object>>();
			recordsMapList.add( record );
			
			keyedRecordsList.put( tempKeyValue, recordsMapList );
		}
		
		return keyedRecordsList;
	}

}
