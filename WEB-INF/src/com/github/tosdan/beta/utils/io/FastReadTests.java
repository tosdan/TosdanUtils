package com.github.tosdan.beta.utils.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FastReadTests
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main( String[] args ) throws IOException
	{
//		leggi1(); // 300% piu' lento del 2
		leggi2(); // piu' rapido
//		leggi3(); // poco piu lento del 2 del 90%-75%
	}

	public static void leggi1() throws IOException
	{
		File f = new File( "d:/tmp/a.txt" );
		InputStream is = new FileInputStream( f );
		InputStreamReader isR = new InputStreamReader( is, "ISO-8859-1" );
		BufferedReader bf = new BufferedReader( isR );
     	StringBuilder builder = new StringBuilder();
     	String temp;
     	long st = System.nanoTime();
     	while ( (temp = bf.readLine()) !=null ) {
     		builder.append( temp + "\n");
     	}
        long et = System.nanoTime();
     	bf.close();
//     	System.out.println( builder );
        System.out.printf("completato in %d ms.%n", (et - st) / 1000000);
	}
	
	// 3 volte più veloce
	public static void leggi2() throws IOException
	{
		File f = new File( "d:/tmp/a.txt" );
		InputStream is = new FileInputStream( f );
		InputStreamReader isR = new InputStreamReader( is, "ISO-8859-1" );
		BufferedReader bf = new BufferedReader( isR );
		int available = is.available();
        char[] buffer = new char[ available ];
		StringBuilder builder = new StringBuilder();
     	long st = System.nanoTime();
     	while ( bf.read(buffer, 0 , buffer.length) != -1 ) {
     		builder.append( buffer );
     		available = is.available();
     	}
        long et = System.nanoTime();
     	bf.close();
     	
//     	System.out.println( builder );
		System.out.printf( "dimensione buffer: %d Byte ", available );
        System.out.printf("\ncompletato in %d ms.%n", (et - st) / 1000000);
	}
	
	public static void leggi3() throws IOException
	{
		File f = new File( "d:/tmp/a.txt" );
//		File f = new File( "d:/tmp/b.txt" );
		FileInputStream is = new FileInputStream( f );
		int available = is.available();
		FileChannel fileChannel = is.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(available);

     	long st = System.nanoTime();
        int bytes = fileChannel.read(byteBuffer);
		StringBuilder builder = new StringBuilder();
        while(bytes!=-1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
            	builder.append( (char)byteBuffer.get() );
            }
            byteBuffer.clear();
            bytes = fileChannel.read(byteBuffer);
        }
        long et = System.nanoTime();
        
        is.close();
//     	System.out.println( builder );
		System.out.printf( "dimensione buffer: %d Byte ", available );
        System.out.printf("\ncompletato in %d ms.%n", (et - st) / 1000000);
	}
	
}
