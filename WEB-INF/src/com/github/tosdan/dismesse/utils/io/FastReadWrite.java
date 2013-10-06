package com.github.tosdan.dismesse.utils.io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FastReadWrite
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main( String[] args ) throws IOException
	{
		File out = new File( "out.txt" );
		FileWriter fw = new FileWriter( out );		
		BufferedWriter bfW = new BufferedWriter( fw );
		
		File f = new File( "" );
		InputStream is;
		
		is = new FileInputStream( f );
		InputStreamReader isR = new InputStreamReader( is );
		
        char[] buffer = new char[ 4 * 1024 ];
        int c = 0;
        while (c >= 0)
        {
            c = isR.read(buffer, 0, buffer.length);
            if (c > 0)
            {
            	StringBuilder builder = new StringBuilder();
            	builder.append( buffer );
            	
            	InputStream tempIs = new ByteArrayInputStream( builder.toString().getBytes() );
            	BufferedReader reader = new BufferedReader( new InputStreamReader(tempIs) );
            	
            	reader.read( buffer, 0, buffer.length );
            	
            	bfW.write( buffer, 0, buffer.length );
            	
            	tempIs.close();
            }
        }
        
        isR.close();
		bfW.close();
		
	}

}
