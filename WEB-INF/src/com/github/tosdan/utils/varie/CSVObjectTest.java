package com.github.tosdan.utils.varie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.github.tosdan.utils.varie.CsvUtils.*;

public class CSVObjectTest {
	private String campoA;
	private String campoB;
	public String getCampoA() {
		return campoA;
	}
	public void setCampoA( String campoA ) {
		this.campoA = campoA;
	}
	public String getCampoB() {
		return campoB;
	}
	public void setCampoB( String campoB ) {
		this.campoB = campoB;
	}

	public static void main( String[] args ) throws IOException {
		InputStream in = CSVObjectTest.class.getResourceAsStream("file.csv");
		List<Map<String, Object>> result = read(in);

		for(Map<String, Object> map : result) { 
			System.out.println(String.format("campoA=%s, campoB=%s", map.get("campoA"), map.get("campoB")));
		}
		
		System.out.println("\n\n\n**** BeanReader");
		in = CSVObjectTest.class.getResourceAsStream("file.csv");
		List<CSVObjectTest> list = readToBean(in, CSVObjectTest.class);
		for( CSVObjectTest obj : list ) {
			System.out.println(String.format("campoA=%s, campoB=%s", obj.getCampoA(), obj.getCampoB()));
		}
		
		System.out.println("\n\n\n**** readToList");
		in = CSVObjectTest.class.getResourceAsStream("file-vr.csv");
		List<List<String>> llista = readToListOfRow(in, true);
		for( List<String> l : llista ) {
			System.out.println(l);
		}
	}

}
