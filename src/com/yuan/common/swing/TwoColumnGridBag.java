package com.yuan.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

public class TwoColumnGridBag extends GridBag {

	public TwoColumnGridBag(Container container){
		super(container);
	}
	
	public void addLeftAutoRow(Component c1, Component c2){
		addAutoRow(c1, c2, GridBagConstraints.HORIZONTAL, GridBagConstraints.NONE);
	}
	
	public void addRightAutoRow(Component c1, Component c2){
		addAutoRow(c1, c2, GridBagConstraints.NONE, GridBagConstraints.HORIZONTAL);
	}
	
	public void addAutoRow(Component c1, Component c2, int fill1, int fill2){
		List<Component> cList = new ArrayList<Component>();
		cList.add(c1);
		cList.add(c2);
		
		List<Integer> fillList = new ArrayList<Integer>();
		fillList.add(fill1);
		fillList.add(fill2);
		
		super.addRow(cList, fillList);
	}
}
