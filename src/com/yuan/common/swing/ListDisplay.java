package com.yuan.common.swing;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ListDisplay extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private MyList myList;
	
	public ListDisplay(){
		super();
		myList = new MyList();
		super.setViewportView(myList);
	}
	
	public void addElement(Object obj){
		myList.addElement(obj);
	}
	
	public void removeSelected(){
		if(!myList.isSelectionEmpty()){
			myList.removeSelected();
		}
	}
	
	public void clear(){
		myList.clear();
	}
	public List<String> getAllElements(){
		return myList.getAllElements();
	}
}

class MyList extends JList{

	private static final long serialVersionUID = 1L;
	
	protected DefaultListModel listModel;
	public MyList(){
		super(new DefaultListModel());
		listModel = (DefaultListModel)super.getModel();
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		super.setVisibleRowCount(5);
		setDrop();
	}
	
	public MyList(Object[] data){
		super(new DefaultListModel());
		listModel = (DefaultListModel)super.getModel();
		if(data != null){
			for(Object obj : data){
				listModel.addElement(obj);
			}
		}
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDrop();
	}
	
	protected void setDrop(){
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropAdapter(){
			public void drop(List<File> fileList, DropTargetDropEvent event){
				for(File f : fileList){
					addElement(f.getAbsolutePath());
				}
			}
		});
	}
	public void addElement(Object obj){
		listModel.addElement(obj);
	}
	
	public void removeElement(Object obj){
		listModel.removeElement(obj);
	}
	public void clear(){
		listModel.clear();
	}
	public void removeSelected(){
		listModel.remove(super.getSelectedIndex());
	}
	public List<String> getAllElements(){
		List<String> list = new ArrayList<String>();
		for(int i=0; i<listModel.getSize(); i++){
			list.add((String)listModel.elementAt(i));
		}
		return list;
	}
}
