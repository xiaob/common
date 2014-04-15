package com.yuan.common.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextDataParser implements Iterator<List<String>> {

	private BufferedReader br;
	private String line = null;
	private String seperator;
	
	public TextDataParser(File dataFile){
		this(dataFile, "\t");
	}
	
	public TextDataParser(File dataFile, String seperator){
		try {
			br = Files.newBufferedReader(dataFile.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.seperator = seperator;
	}
	
	@Override
	public boolean hasNext() {
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line != null;
	}

	@Override
	public List<String> next() {
		if(line  != null){
			return Arrays.asList(line.split(seperator));
		}
		return null;
	}

	@Override
	public void remove() {

	}
	
	public void close(){
		if(br != null){
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
