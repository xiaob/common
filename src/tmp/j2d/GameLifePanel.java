package tmp.j2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameLifePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private int n = 30;
	boolean[][] cells1;
	boolean [][] cells2;
	
	public GameLifePanel(){
		//设置组件首选大小
		this.setPreferredSize(new Dimension(400, 400));
		this.setBackground(Color.WHITE);
		cells1 = new boolean[n][n];
		cells2 = new boolean[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				cells1[i][j] = Math.random() < 0.1;
				cells2[i][j] = false;
			}
		}
		Timer timer = new Timer(1000, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.LIGHT_GRAY);
		int p = 0;
		int c = 16;
		int len = c*n;
		for(int i=0; i<=n; i++){
			g2.drawLine(0, p, len, p);
			g2.drawLine(p, 0, p, len);
			p += c;
		}
		
		g2.setColor(Color.BLACK);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(cells1[i][j]){
					int x = i*c;
					int y = j*c;
					g2.fillOval(x, y, c, c);
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		
	}

}
