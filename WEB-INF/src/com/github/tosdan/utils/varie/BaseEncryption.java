package com.github.tosdan.utils.varie;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;

public class BaseEncryption
{
	public static String encryptStatic(String s, String password) {
		BaseEncryption be = new BaseEncryption();
		return be.encrypt( s, password );
	}
	
	public static String decryptStatic(String s, String password) {
		BaseEncryption be = new BaseEncryption();
		return be.decrypt( s, password );
	}

	private StandardPBEStringEncryptor stdEncryptor;
	
	public BaseEncryption() {
		this.stdEncryptor = new StandardPBEStringEncryptor();
		stdEncryptor.setKeyObtentionIterations(1);
//		stdEncryptor.setProvider(new BouncyCastleProvider());   // necessaria libreria bouncy castle (mette a disposizione algoritmi piu' robusti)
		
//		FixedStringSaltGenerator fixedGenerator = new FixedStringSaltGenerator();                                                                                         
//		fixedGenerator.setSalt("ungeneratore");
		ZeroSaltGenerator zeroGenerator = new ZeroSaltGenerator(); 
		// con un generatore zero o fisso si ottiene sempre la stessa stringa cifrata a partire da stessa password e stessa stringa da cifrare
		stdEncryptor.setSaltGenerator(zeroGenerator);
		
		stdEncryptor.setAlgorithm("PBEWITHMD5ANDDES");		
	}
	
	public String encrypt(String s, String password) {
		stdEncryptor.setPassword(password);
		return stdEncryptor.encrypt(s);
	}
	
	public String decrypt(String s, String password) {
		stdEncryptor.setPassword(password);
		return stdEncryptor.decrypt(s);
	}
	
	public BaseEncryption setBase64Encoding() {
		stdEncryptor.setStringOutputType( "base64" );
		return this;
	}
	public BaseEncryption setHexEncoding() {
		stdEncryptor.setStringOutputType( "hexadecimal" ); // di default e' Base64 che produce stirnghe piu' corte
		return this;
	}
}
