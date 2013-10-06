package com.github.tosdan.dismesse.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class LargeFileSeverV2
{
	public static void buffrw() throws IOException
	{
		File f = new File( "F:/transfers/_DVD Monsile dati aggiornati/BORDOUT.txt" );
		Reader reader = new FileReader( f );
		
		BufferedReader br = new BufferedReader( reader );

		String riga = "";
		BufferedWriter bfW = null;
		int counter = 100;
		String prevCode = "";

		StringBuilder builder = null;
		
		while (  (riga = br.readLine()) !=null)
		{
			String percorso = "F:/transfers/_DVD Monsile dati aggiornati/tabelle/";
//			File dir = new File( percorso );
			// cancellare il contenuto della cartella di destinazione ad ogni unovo avvio
			
			String code = riga.substring( 45,50 );
			
			if ( ! code.equals(prevCode) && ! prevCode.equals("") ) 
			{
				bfW.write ( builder.toString() );
				bfW.close();
				builder = new StringBuilder();
				
				counter++;
			}
			
			String tabella = percorso + "Tabella_" + counter + "_" + code + ".txt";

			boolean append = true;
			FileWriter fTab = new FileWriter( tabella, append );
		
			int max = 1*1024*1024;
			
			if ( prevCode.equals("") )
			{
				builder = new StringBuilder();
				builder.append( riga.trim() );
				builder.append( "\n" );
				
				prevCode = code;
				bfW = new BufferedWriter( fTab );
			}
			else if ( ! code.equals(prevCode) )
			{
				builder = new StringBuilder();
				builder.append( riga.trim() );
				builder.append( "\n" );

				prevCode = code;
				bfW = new BufferedWriter( fTab );
			}
			else
			{
				builder.append( riga.trim() );
				builder.append( "\n" );
			}
			
			if (builder.length() >= max)
			{
				bfW.write ( builder.toString() );
//				bfW.newLine();		
				builder = new StringBuilder();		
			}
		}
		br.close();
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main( String[] args ) throws IOException
	{
		buffrw();
	}

}
