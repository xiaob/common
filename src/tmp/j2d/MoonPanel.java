package tmp.j2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.yuan.common.swing.Circle;

public class MoonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public MoonPanel(){
		super();
		super.setPreferredSize(new Dimension(400, 400));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		Shape ellipseShape = new Ellipse2D.Double(100, 100, 300, 200);
		Shape circleShape = new Circle(250, 200, 100);
		Shape leftRectShape = new Rectangle2D.Double(250, 100, 150, 200);
		
		Area moonArea = new Area(ellipseShape);
		moonArea.subtract(new Area(leftRectShape));
		moonArea.subtract(new Area(circleShape));
		
//		g2.setColor(Color.blue);
		g2.setPaint(new GradientPaint(100, 100, Color.green, 100, 300, Color.blue, true));
		g2.fill(moonArea);
	}

}
