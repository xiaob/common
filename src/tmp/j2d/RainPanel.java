package tmp.j2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class RainPanel extends ThreadPanel{

	private static final long serialVersionUID = 1L;
	Point2D.Double[] pts = new Point2D.Double[1200];
	
	public RainPanel(){
		super(640, 480);
		for(int i=0; i<pts.length; i++){
			pts[i] = new Point2D.Double(Math.random(), Math.random());
		}
	}
	
	protected void call()throws InterruptedException{
		for(int i=0; i<pts.length; i++){
			double x = pts[i].getX();
			double y = pts[i].getY();
			y += 0.1*Math.random();
			if(y > 1){
				y = 0.3*Math.random();
				x = Math.random();
			}
			pts[i].setLocation(x, y);
			
		}
		this.repaint();
		Thread.sleep(100);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		g.setXORMode(Color.BLACK);
		
		g.setColor(Color.RED);
		for(int i=0; i<pts.length; i++){
			int x = (int)(640*pts[i].x);
			int y = (int)(480*pts[i].y);
			int h = (int)(25*Math.random());
			g.drawLine(x, y, x, y+h);
		}
		
		
	}

}
