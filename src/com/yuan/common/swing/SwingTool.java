package com.yuan.common.swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class SwingTool {

	public static TitledBorder createBevelTitledBorder(String title){
		return new TitledBorder(new BevelBorder(BevelBorder.RAISED), title);
	}
	
	public static TitledBorder createEtchedTitledBorder(String title){
		return new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), title);
	}
}
