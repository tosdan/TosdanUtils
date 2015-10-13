package com.github.tosdan.utils.varie;

import java.text.Normalizer;

public class TdFilenameUtils {

	public static String sanitize(String filename) {
		filename = filename.replaceAll("#", "-"); 						// il carattere # disturba il js che legge la querystring del browser
		
		filename = Normalizer.normalize(filename, Normalizer.Form.NFD);	// Converte i caratteri accentati nella controparte non accentata
				
		filename = filename.replaceAll("[^\\p{ASCII}-_]", ""); 			// Accetta solo numeri lettere e caratteri "-" e "_"

		filename = filename.replaceAll("[_]{2,}", "_");					// Non sono ammessi due o più underscore di fila, vengono trasformati in uno singolo
		
		filename = filename.replaceAll("[-]{2,}", "-");					// Non sono ammessi due o più trattini di fila, vengono trasformati in uno singolo
		
		return filename;
	
	}
}
