/**
 * 
 */
package com.github.tosdan.utils.varie;

import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author Daniele
 *
 */
public class FilenameTools {

	/**
	 * Restituisce il nome del file con una stringa casuale alfanumerica di 9 caratteri inserita dopo il nome 
	 * file passato come parametro.
	 * Es: <b>helloworld.txt</b> => <b>helloworld_abcd1234z.txt</b>
	 * @param filename
	 * @return
	 */
	public static String getRandomizedFilename(String filename) {
		String filenameUnivico = null;
		String estensione = FilenameUtils.getExtension(filename);
		String randomString = RandUtils.getRandomString(9); // 1 collisione ogni 10.000.000
		filenameUnivico = FilenameUtils.getBaseName(filename) + "_" + randomString +"."+ estensione;
		return filenameUnivico;
		
	}
}
