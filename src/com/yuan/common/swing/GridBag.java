package com.yuan.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

public class GridBag {

	protected GridBagConstraints gbc = new GridBagConstraints();
	protected Container container;
	protected int gridx = 0;
	protected int gridy = 0;
	
	public GridBag(Container container){
		this.container = container;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
	}
	
	public void addRow(List<Component> cList, List<Integer> fillList){
		gridx = 0;
		for(int i=0; i<cList.size(); i++){
			Component c = cList.get(i);
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			switch (fillList.get(i)) {
			case GridBagConstraints.HORIZONTAL:
				gbc.weightx = 1; //网格列缩放权重
				gbc.weighty = 0; //网格行缩放权重
				gbc.fill = GridBagConstraints.HORIZONTAL;
				break;
			case GridBagConstraints.VERTICAL:
				gbc.weightx = 0; //网格列缩放权重
				gbc.weighty = 1; //网格行缩放权重
				gbc.fill = GridBagConstraints.VERTICAL;
				break;
			case GridBagConstraints.BOTH:
				gbc.weightx = 1; //网格列缩放权重
				gbc.weighty = 1; //网格行缩放权重
				gbc.fill = GridBagConstraints.BOTH;
				break;
			default :
				gbc.weightx = 0; //网格列缩放权重
				gbc.weighty = 0; //网格行缩放权重
				gbc.fill = GridBagConstraints.NONE;
				break;
			};
			container.add(c, gbc);
			gridx ++;
		}
		
		gridy ++;
	}
	
	protected int getMaxGridHeight(List<Integer> gridHeightList){
		int max = 1;
		for(Integer gridHeight : gridHeightList){
			if(gridHeight.intValue() > max){
				max = gridHeight;
			}
		}
		return max;
	}
}
