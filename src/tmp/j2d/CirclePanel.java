package tmp.j2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class CirclePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int x = 100;
	private int y = 100;
	
	private int oldX = 100;
	private int oldY = 100;
	
	public CirclePanel(){
		//设置组件首选大小
		this.setPreferredSize(new Dimension(640, 480));
		this.setBackground(Color.WHITE);
		this.addMouseListener(new MyMouseListener(this));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		g.setXORMode(Color.WHITE);
		
		//先差除
		if((x != oldX) || (y != oldY)){
			g.drawOval(oldX - 50, oldY - 50, 100, 100);
		}
		
		g.drawOval(x - 50, y - 50, 100, 100);
		
		//记忆老位置
		oldX = x;
		oldY = y;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		
	}

}

class MyMouseListener extends MouseAdapter{
	
	private CirclePanel panel;
	
	public MyMouseListener(CirclePanel panel){
		this.panel = panel;
	}
	
	public void mouseClicked(MouseEvent ev){
		panel.setPosition(ev.getX(), ev.getY());
		panel.repaint();
	}
	
}
