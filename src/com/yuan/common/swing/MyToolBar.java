package com.yuan.common.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class MyToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	private ActionListener listener;
	
	public MyToolBar(ActionListener listener){
		super();
		this.listener = listener;
	}
	
	public void addButton(String text, String imgFile){
		try {
			BufferedImage bi = ImageIO.read(new File(imgFile));
			double sx = 32.0/new Integer(bi.getWidth()).doubleValue();
			double sy = 32.0/new Integer(bi.getHeight()).doubleValue();
			addButton(text, new ImageIcon(ImageTool.scaleImage(bi, sx, sy)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addButton(String text, Icon icon){
		JButton button = new JButton(text, icon);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setToolTipText(text);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(listener != null){
					listener.actionPerformed(event);
				}
			}
		});
		super.add(button);
	}

}
