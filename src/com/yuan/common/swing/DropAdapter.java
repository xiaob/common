package com.yuan.common.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import com.yuan.common.util.ReflectUtil;

public class DropAdapter implements DropTargetListener {

	@Override
	public void dragEnter(DropTargetDragEvent event) {
		if(event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			return ;
		}
		event.rejectDrag();
	}

	@Override
	public void dragExit(DropTargetEvent event) {

	}

	@Override
	public void dragOver(DropTargetDragEvent event) {

	}

	@Override
	public void drop(DropTargetDropEvent event) {
		if(event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			event.acceptDrop(DnDConstants.ACTION_COPY);
			Transferable t = event.getTransferable();
			try {
				Object data = t.getTransferData(DataFlavor.javaFileListFlavor);
				List<File> list = ReflectUtil.castList(List.class.cast(data), File.class);
				drop(list, event);
				event.dropComplete(true);
			} catch (Exception e) {
				e.printStackTrace();
				event.dropComplete(false);
			}
		}//if
	}
	
	public void drop(List<File> fileList, DropTargetDropEvent event){
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {

	}

}
