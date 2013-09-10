package tmp.j2d;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

public class DividerLayout implements LayoutManager2 {

	public static final String WEST = "WEST";
	public static final String EAST = "EAST";
	public static final String CENTER = "CENTER";
	
	protected Component westComponent;
	protected Component eastComponent;
	protected Component centerComponent;
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(WEST.equalsIgnoreCase((String)constraints)){
			westComponent = comp;
		}else if(CENTER.equalsIgnoreCase((String)constraints)){
			centerComponent = comp;
		}else if(EAST.equalsIgnoreCase((String)constraints)){
			eastComponent = comp;
		}

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension size;
		int width = 0;
		int height = 0;
		if((westComponent != null) && (westComponent.isVisible())){
			size = westComponent.getMaximumSize();
			width = Math.max(width, size.width);
			height = Math.max(height, size.height);
		}
		if((eastComponent != null) && (eastComponent.isVisible())){
			size = eastComponent.getMaximumSize();
			width = Math.max(width, size.width);
			height = Math.max(height, size.height);
		}
		width *= 2;
		if((centerComponent != null) && (centerComponent.isVisible())){
			size = centerComponent.getPreferredSize();
			width += size.width;
			height = Math.max(height, size.height);
		}
		return new Dimension(width, height);
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {

	}

	@Override
	public void layoutContainer(Container arg0) {

	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension size;
		int width = 0;
		int height = 0;
		
		return null;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if(comp == westComponent){
			westComponent = null;
		}else if(comp == centerComponent){
			centerComponent = null;
		}else if(comp == eastComponent){
			centerComponent = null;
		}
		
	}

}
