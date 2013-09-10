package com.yuan.common.log;

import java.io.IOException;
import java.io.Writer;

import javax.swing.JTextArea;

import org.apache.log4j.WriterAppender;

public class SwingAppender extends WriterAppender {

	private static JTextArea textArea;
	
	public static void setTextArea(JTextArea area){
		textArea = area;
	}
	
	@Override
	public void activateOptions() {
		setWriter(new LogWriter());
	}
	
	private static class LogWriter extends Writer{

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			if(textArea != null){
				textArea.append(new String(cbuf, off, len));
			}
		}

		@Override
		public void flush() throws IOException {
			
		}

		@Override
		public void close() throws IOException {
			
		}
		
	}
}


