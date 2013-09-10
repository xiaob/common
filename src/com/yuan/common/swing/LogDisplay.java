package com.yuan.common.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LogDisplay extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	
	public LogDisplay(){
		super();
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(240,255,240));
		textArea.setFont(new Font("宋体", Font.BOLD, 12));
		textArea.getDocument().addDocumentListener(new DocumentHandle());
		super.setViewportView(textArea);
		//设置组件首选大小
		this.setPreferredSize(new Dimension(640, 480));
		
//		SwingAppender.setTextArea(textArea);
	}
	
	public void scrollToBottom(){
		textArea.setCaretPosition(textArea.getDocument().getLength());
//		textArea.selectAll();
	}
	
	private class DocumentHandle implements DocumentListener{
		
		@Override
		public void removeUpdate(DocumentEvent event) {
			
		}
		
		@Override
		public void insertUpdate(DocumentEvent event) {
			scrollToBottom();
		}
		
		@Override
		public void changedUpdate(DocumentEvent event) {
			
		}
	}

}
