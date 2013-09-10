package tmp.j2d;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PicturePanel extends ThreadPanel {

	private static final long serialVersionUID = 1L;
	private File[] fs = null;
	private int index = 0;
	
	public PicturePanel(String pictureDir){
		super(800, 600);
		fs = new File(pictureDir).listFiles();
	}
	
	public void run(){
		try {
			call();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void call()throws InterruptedException{
		for(int i=1; i<fs.length; i++){
			index = i;
			this.repaint();
			Thread.sleep(100);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		
		try {
			BufferedImage bi = ImageIO.read(fs[index]);
			AffineTransform xform = new AffineTransform();
			xform.scale(4, 4);
			BufferedImageOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);
			g2.drawImage(op.filter(bi, null), 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
