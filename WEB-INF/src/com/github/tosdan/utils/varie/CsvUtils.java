package com.github.tosdan.utils.varie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * 
 * @author Daniele
 * @version 0.0.4-b2014-10-31
 */
public class CsvUtils {
	
	// *********************************************************************
	// **********************      SCRITTURA CSV      **********************
	// *********************************************************************


	/**
	 * 
	 * @param outputCsvFile
	 * @param mapList
	 * @throws IOException
	 */
	public static void writeMap(String outputCsvFile, List<Map<String, Object>> mapList) throws IOException {
		writeMap(outputCsvFile, null, mapList);
	}
	
	/**
	 * 
	 * @param outputCsvFile
	 * @param mapList
	 * @param processor
	 * @throws IOException
	 */
	public static void writeMap(String outputCsvFile, String[] nameMapping, List<Map<String, Object>> mapList) throws IOException {
		writeMap(outputCsvFile, nameMapping, mapList, null);
	}
	
	/**
	 * 
	 * @param outputCsvFile
	 * @param nameMapping
	 * @param mapList
	 * @param processor
	 * @throws IOException
	 */
	public static void writeMap(String outputCsvFile, String[] nameMapping, List<Map<String, Object>> mapList, CellProcessor[] processor) throws IOException {
		writeMap(new FileWriter(outputCsvFile), nameMapping, mapList, processor);
	}
	

	/**
	 * 
	 * @param writer
	 * @param mapList
	 * @throws IOException
	 */
	public static void writeMap(Writer writer, List<Map<String, Object>> mapList) throws IOException {
		writeMap(writer, null, mapList);
	}
	
	/**
	 * 
	 * @param writer
	 * @param nameMapping
	 * @param mapList
	 * @throws IOException
	 */
	public static void writeMap(Writer writer, String[] nameMapping, List<Map<String, Object>> mapList) throws IOException {
		writeMap(writer, nameMapping, mapList, null);
	}
	
	
	/**
	 * 
	 * @param writer
	 * @param nameMapping
	 * @param mapList
	 * @param processor
	 * @throws IOException
	 */
	public static void writeMap(Writer writer, String[] nameMapping, List<Map<String, Object>> mapList, CellProcessor[] processor) throws IOException {
		ICsvMapWriter mapWriter = null;
		
		try {
			mapWriter = new CsvMapWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			
			if (nameMapping == null) {
				Set<String> headerSet = mapList.get(0).keySet();
				nameMapping = new String[headerSet.size()];
				int i = 0;
				for( String colName : headerSet ) {
					nameMapping[i] = colName;
					i += 1;
				}
			}
			
			if (processor == null) {
				processor = new CellProcessor[nameMapping.length];
			}
			
			mapWriter.writeHeader(nameMapping);
			
			for(Map<String, Object> currMap : mapList) {
				mapWriter.write(currMap, nameMapping, processor);
			}
			
		} finally {
			IOUtils.closeQuietly(mapWriter);
		}
	}
	
	/**
	 * 
	 * @param writer
	 * @param nameMapping
	 * @param list
	 * @throws IOException
	 */
	public static <T> void writeBean(Writer writer, String[] nameMapping, List<T> list) throws IOException {
		writeBean(writer, nameMapping, list, null);
	}
	
	/**
	 * 
	 * @param writer
	 * @param nameMapping
	 * @param list
	 * @param processor
	 * @throws IOException
	 */
	public static <T> void writeBean(Writer writer, String[] nameMapping, List<T> list, CellProcessor[] processor) throws IOException {
		CsvBeanWriter mapWriter = null;
		
		try {
			mapWriter = new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			
			if (processor == null) {
				processor = new CellProcessor[nameMapping.length];
			}
			
			mapWriter.writeHeader(nameMapping);
			
			for(T currObj : list) {
				mapWriter.write(currObj, nameMapping, processor);
			}
			
		} finally {
			IOUtils.closeQuietly(mapWriter);
		}
	}
	
	// *********************************************************************
	// ***********************      LETTURA CSV      ***********************
	// *********************************************************************

	/**
	 * 
	 * @param csvFile
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(File csvFile, String key) throws IOException {		
		return getKeyedRecords( csvFile, key, null );
	}
	
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(File csvFile, String key, CellProcessor[] processor) throws IOException {
		return getKeyedRecords(new FileInputStream(csvFile), key, processor);
	}
	
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
		return getKeyedRecords(new FileInputStream(csvFile), key, processor);
	}
	
	/**
	 * 
	 * @param in
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(InputStream in, String key) throws IOException {
		return getKeyedRecords(in, key, null);
	}
	
	/**
	 * 
	 * @param in
	 * @param key
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(InputStream in, String key, CellProcessor[] processor) throws IOException {
		return getKeyedRecords(new InputStreamReader(in), key, processor);
	}
	
	/**
	 * 
	 * @param reader
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(Reader reader, String key) throws IOException {
		return getKeyedRecords(reader, key, null);
	}
	
	/**
	 * 
	 * @param reader
	 * @param key
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, Object>>> getKeyedRecords(Reader reader, String key, CellProcessor[] processor) throws IOException {
		Map<String, List<Map<String, Object>>> retval = new LinkedHashMap<String, List<Map<String, Object>>>();
		
		// CsvMapReader già incapsula il reader che riceve in un bufferedreader
		ICsvMapReader mapReader = new CsvMapReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE );
		final String[] header = mapReader.getHeader( true );
		
		if (processor == null) {
			processor = new CellProcessor[header.length];
		}
		
		List<Map<String, Object>> recordsMapList;
		Map<String, Object> currRecord;
		String tempKeyValue;
		try {
			while ( (currRecord = mapReader.read(header, processor)) != null ) {
				tempKeyValue = currRecord.get( key ).toString();
				
				recordsMapList = retval.get( tempKeyValue );
				if (recordsMapList == null) {
					recordsMapList = new ArrayList<Map<String, Object>>();
					retval.put( tempKeyValue, recordsMapList );
				}
				
				recordsMapList.add( currRecord );
			}
			
		} finally {
			IOUtils.closeQuietly(mapReader);
		}
		
		return retval;
	}

	/**
	 * 
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(File csvFile) throws IOException {
		return read(csvFile, null);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(File csvFile, CellProcessor[] processor) throws IOException {
		return read(new FileInputStream(csvFile), processor);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(String csvFile) throws IOException {
		return read(csvFile, null);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(String csvFile, CellProcessor[] processor) throws IOException {
		return read(new FileInputStream(csvFile), processor);
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(InputStream in) throws IOException {
		return read(in, null);
	}
	
	/**
	 * 
	 * @param in
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(InputStream in, CellProcessor[] processor) throws IOException {
		return read(new InputStreamReader(in), null);
	}
	
	
	/**
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(Reader reader) throws IOException {
		return read(reader, null);
	}
	
	/**
	 * 
	 * @param reader
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> read(Reader reader, CellProcessor[] processor) throws IOException {
		List<Map<String, Object>> retVal = new ArrayList<Map<String,Object>>();
		
		ICsvMapReader mapReader = null;
        try {
    		// CsvMapReader già incapsula il reader che riceve in un bufferedreader
        	mapReader = new CsvMapReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
            
        	final String[] header = mapReader.getHeader(true);
        	
    		if (processor == null) {
    			processor = new CellProcessor[header.length];
    		}
    		
        	Map<String, Object> temp;
        	while( (temp = mapReader.read(header, processor)) != null ) {
        		retVal.add(temp);	
        	}
        	
        } finally {
        	IOUtils.closeQuietly(mapReader);
        }
        
        return retVal;
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(File csvFile, Class<T> clazz) throws IOException {
		return readToBean(csvFile, clazz, null);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param clazz
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(File csvFile, Class<T> clazz, CellProcessor[] processor) throws IOException {
		return readToBean(new FileInputStream(csvFile), clazz, processor);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(String csvFile, Class<T> clazz) throws IOException {
		return readToBean(csvFile, clazz, null);
	}
	
	/**
	 * 
	 * @param csvFile
	 * @param clazz
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(String csvFile, Class<T> clazz, CellProcessor[] processor) throws IOException {
		return readToBean(new FileInputStream(csvFile), clazz, processor);
	}
	
	/**
	 * 
	 * @param in Viene chiuso dal metodo chiamato
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(InputStream in, Class<T> clazz) throws IOException {
		return readToBean(in, clazz, null);
	}
	
	/**
	 * 
	 * @param in
	 * @param clazz
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(InputStream in, Class<T> clazz, CellProcessor[] processor) throws IOException {
		return readToBean(new InputStreamReader(in), clazz, processor);
	}
	
	/**
	 * 
	 * @param reader
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(Reader reader, Class<T> clazz) throws IOException {
		return readToBean(reader, clazz, null);
	}
	
	/**
	 * 
	 * @param reader
	 * @param clazz
	 * @param processor
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> readToBean(Reader reader, Class<T> clazz, CellProcessor[] processor) throws IOException {
		List<T> retVal = new ArrayList<T>();
		
		ICsvBeanReader beanReader = null;
		try {
			// CsvMapReader già incapsula il reader che riceve in un bufferedreader
			beanReader = new CsvBeanReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			
			final String[] header = beanReader.getHeader(true);
			
    		if (processor == null) {
    			processor = new CellProcessor[header.length];
    		}
    		
			T temp;
			while( (temp = beanReader.read(clazz, header, processor)) != null ) {
				retVal.add(temp);        		
			}
			
		} finally {
			IOUtils.closeQuietly(beanReader);
		}
		
		return retVal;
	}
	
	
	/**
	 * @param in Chiuso al termine della lettura del file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readHeader(InputStream in) throws IOException {
		ICsvListReader listReader = null;
		List<String> riga;
		try {
			listReader = new CsvListReader(new InputStreamReader(in), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			
			listReader.read();
			
			riga = new ArrayList<String>();
			
			for(int i = 1 ; i <= listReader.length() ; i++) {
				riga.add(listReader.get(i));
			}
			
		} finally {
			IOUtils.closeQuietly(listReader);
		}
		return riga;
	}

	/**
	 * @Sperimentale
	 * Utile solo per la lettura dei csv con numero colonne variabili
	 * @param in Chiuso al termine della lettura del file
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readToListOfRow(InputStream in, boolean ignoreHeader) throws IOException {
		List<List<String>> listaRighe = new ArrayList<List<String>>();
		ICsvListReader listReader = null;
		try {
			listReader = new CsvListReader(new InputStreamReader(in), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			listReader.getHeader(ignoreHeader); // se true salta l'header: non e' consentito con CsvListReader
			List<String> riga;
			
			while ( (listReader.read()) != null ) {
				
				riga = new ArrayList<String>();
				listaRighe.add(riga);
								
				for(int i = 1 ; i <= listReader.length() ; i++) {
					riga.add(listReader.get(i));
				}
			}

		} finally {
			if ( listReader != null ) {
				listReader.close();
			}
		}
		return listaRighe;
	}
}
