package com.github.tosdan.utils.varie;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * 
 * @author Daniele
 *
 */
public class RandUtils {

	public static void main( String[] args ) {
		String r;
		Set<String> set = new HashSet<String>();
		int j = 10;
		long ms = System.currentTimeMillis();
		long ms2;
		int collisions = 0;
		for (long i = 0; i < 5000000 ; i++) {
			if (i%100000==0) {
				ms2 = System.currentTimeMillis();
				System.out.print(( (50*40) - (ms2-ms)/1000)/60D + " sec. ");
				System.out.println(i);
			}
			r = getRandomString(j);
			if (set.contains(r)) {
				collisions += 1;
			} else {
				set.add(r);
			}
		}
		System.out.println(collisions);
//		System.out.println(getRandomString(2));
	}
	
	/**
	 * Produce una stringa alfanumerica casuale lunga n caratteri.
	 * 
	 * 6 1 collisione ogni 8.000 
	 * 7 1 collisione ogni 25.000
	 * 8 1 collisione ogni 70.000
	 * 9 1 collisione ogni 1.200.000
	 * @param length Lunghezza, in caratteri, della stringa in uscita.
	 * @return Stringa casuale.
	 */
	public static String getRandomString(int length) {
		String randomString = null;
		try {
			
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			
			BigInteger randomInteger = BigInteger.probablePrime(5 * length, secureRandom);
			randomString = randomInteger.toString(32); // 2^5 1 carattere è rappresentato da 5 bits
			
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalAnnotationException(e);
		}
		return randomString;
	}
}
