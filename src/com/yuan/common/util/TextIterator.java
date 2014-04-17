package com.yuan.common.util;

import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.Iterator;

import com.yuan.common.file.FileUtil;


public class TextIterator implements Iterator<String> {

	private BreakIterator bi;
	private String text;
	private int lastIndex;
	private int currentIndex;
	
	private TextIterator(BreakIterator bi, String text){
		this.bi = bi;
		this.text = text;
		bi.setText(text);
	    lastIndex = bi.first(); 
	}
	
	public static TextIterator getLineInstance(String text){
		return new TextIterator(BreakIterator.getLineInstance(), text);
	}
	
	public static TextIterator getWordInstance(String text){
		return  new TextIterator(BreakIterator.getWordInstance(), text);
	}
	public static TextIterator getSentenceInstance(String text){
		return  new TextIterator(BreakIterator.getSentenceInstance(), text);
	}
	
	@Override
	public boolean hasNext() {
		currentIndex = bi.next();
		return currentIndex != BreakIterator.DONE;
	}

	@Override
	public String next() {
		String s = text.substring(lastIndex, currentIndex);
		lastIndex = currentIndex;
		return s;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("本迭代器是只读的."); 
	}
	
	public static void main(String args[])throws Exception{
		TextIterator iterator = TextIterator.getSentenceInstance(FileUtil.readText(Paths.get("d:/address.txt", "GBK")));
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

}
