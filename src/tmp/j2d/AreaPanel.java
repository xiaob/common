package tmp.j2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.yuan.common.swing.Circle;

public class AreaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public AreaPanel(){
		super();
		super.setPreferredSize(new Dimension(600, 600));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		Graphics2D g2 = (Graphics2D)g;
		Shape s1 = new Ellipse2D.Double(100, 100, 400, 400);
		Shape s2 = new Ellipse2D.Double(200, 100, 200, 200);
		Shape s3 = new Ellipse2D.Double(200, 300, 200, 200);
		Shape leftRect = new Rectangle2D.Double(100, 100, 200, 400);
		Shape rightRect = new Rectangle2D.Double(300, 100, 200, 400);
		
		Area a2 = new Area(s2);
		Area a3 = new Area(s3);
		Area leftRectArea = new Area(leftRect);
		Area rightRectArea = new Area(rightRect);
		
		Area leftCircleArea = new Area(s1);
		leftCircleArea.subtract(rightRectArea);
		leftCircleArea.add(a2);//radius
		leftCircleArea.subtract(a3);
		
		Area rightCircleArea = new Area(s1);
		rightCircleArea.subtract(leftRectArea);
		rightCircleArea.subtract(a2);
		rightCircleArea.add(a3);
		
		g2.setColor(Color.white);
		g2.fill(leftCircleArea);
		g2.setColor(Color.black);
		g2.fill(rightCircleArea);
		
		g2.fill(new Circle(300, 200, 10));
		g2.setColor(Color.white);
		g2.fill(new Circle(300, 400, 10));
	}

}
