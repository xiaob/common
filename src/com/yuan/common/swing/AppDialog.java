package com.yuan.common.swing;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class AppDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public AppDialog(){
		this(400, 400);
	}
	
	public AppDialog(int width, int height){
		super();
		//设置组件首选大小
		super.setPreferredSize(new Dimension(width, height));
		super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public AppDialog(Frame owner, int width, int height){
		super(owner, true);
		//设置组件首选大小
		super.setPreferredSize(new Dimension(width, height));
		super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void center(){
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (screenWidth - this.getWidth()) / 2, (int) (screenHeight - this.getHeight()) / 2); 
	}
	
	public void renderToScreen(){
		this.pack();
		center();
		this.setVisible(true);
	}
	
	protected void waitSelectting(){
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	protected void finishSelectting(){
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	protected void closeWindow(){
		finishSelectting();
		this.dispose();
	}
}
