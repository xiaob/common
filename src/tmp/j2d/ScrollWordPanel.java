package tmp.j2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ScrollWordPanel extends ThreadPanel {

	private static final long serialVersionUID = 1L;
	private String word;
	private int x = 0;
	private int oldX = 0;
	
	public ScrollWordPanel(String word){
		super(800, 600);
		this.word = word;
	}
	
	@Override
	protected void call() throws InterruptedException {
		this.repaint();
		while(true){
			this.repaint();
			Thread.sleep(100);
			oldX = x;
			if(x >= super.getWidth()){
				x = 0;
			}else{
				x += 10;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.setXORMode(Color.WHITE);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		if(oldX != x){
			g2.drawString(word, oldX, 100);
		}
		g2.drawString(word, x, 100);
//		g.drawImage(createImage(), 0, 0, this);
	}
	
	protected BufferedImage createImage(){
		BufferedImage bufferedImage = new BufferedImage(super.getWidth(), super.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		g2.setColor(Color.RED);
		g2.drawString(word, x, 100);
		return bufferedImage;
	}

}
