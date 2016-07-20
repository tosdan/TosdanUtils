package com.github.tosdan.utils.stringhe;

public class MapFormatMatcherAlfaNumUnderscDash extends MapFormatMatcherImpl {

	public MapFormatMatcherAlfaNumUnderscDash() {
		super("");
	}
	
	@Override
	public String getRegExSchema() {
		return "[ ]*([a-zA-Z0-9_-]+)[ ]*";
	}

}
