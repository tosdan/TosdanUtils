package com.github.tosdan.utils.sql;

public interface RowProcessorFormatter {
	
	String format(String nomeColonna, Object valoreColonna);
	boolean isToFormat(String nomeColonna);
	
}
