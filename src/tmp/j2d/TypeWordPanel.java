package tmp.j2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TypeWordPanel extends ThreadPanel {

	private static final long serialVersionUID = 1L;
	private String word;
	private int index = 0;
	private Font font = new Font("宋体", Font.BOLD, 50);
	
	public TypeWordPanel(String word){
		super(800, 600);
		this.word = word;
	}
	
	@Override
	protected void call() throws InterruptedException {
		Thread.sleep(500);
		for(int i=0; i<word.length(); i++){
			index = i;
			this.repaint();
			Thread.sleep(300);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(font);
		g2.setColor(Color.RED);
		g2.drawString(word.substring(0, index), 50, 50);
	}

}
