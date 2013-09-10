package com.yuan.common.swing;

import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.yuan.common.file.FileUtil;


public class TextViewer extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private DropArea dropArea;
	
	public TextViewer(){
		this(640, 480);
	}
	
	public TextViewer(int width, int height){
		super();
		dropArea = new DropArea();
		dropArea.getDocument().addDocumentListener(new DocumentHandle());
		super.setViewportView(dropArea);
		//设置组件首选大小
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void setText(String text){
		dropArea.setText(text);
	}
	
	public String getText(){
		return dropArea.getText();
	}
	
	public JTextArea getTextArea(){
		return  dropArea;
	}
	
	public void scrollToBottom(){
		dropArea.setCaretPosition(dropArea.getDocument().getLength());
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

class DropArea extends JTextArea {

	private static final long serialVersionUID = 1L;
	
	public DropArea(){
		super();
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropAdapter(){
			public void drop(List<File> fileList, DropTargetDropEvent event){
				for(File f : fileList){
					resetText(FileUtil.readTextFile(f.getAbsolutePath(), "UTF-8"));
				}
			}
		});
	}
	
	private void resetText(String text){
		setText(text);
	}
	
}
