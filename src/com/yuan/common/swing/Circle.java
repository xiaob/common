package com.yuan.common.swing;

import java.awt.geom.*; 
public class Circle extends Ellipse2D.Double {

	private static final long serialVersionUID = 1L;
	protected double cx;
	protected double cy;
	protected double radius;
	
	public Circle(double cx, double cy, double radius){
		super(cx - radius, cy - radius, 2*radius, 2*radius);
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}

	public double getCx() {
		return cx;
	}

	public double getCy() {
		return cy;
	}

	public double getRadius() {
		return radius;
	}

}
