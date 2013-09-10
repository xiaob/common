package tmp.j2d;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public abstract class ImageCache {
	
	protected BufferedImage bufferedImage;
	protected String title;
	protected int width;
	protected int height;
	
	public ImageCache(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void renderToFrame(){
		ImageFrame imageFrame = new ImageFrame(title);
		imageFrame.showImage(bufferedImage);
	}
	
	//图形绘制
	public void graphics(){
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Shape shape1 = buildShape();
		Shape shape2 = transform(shape1);
		
		Graphics2D g2d = (Graphics2D)bufferedImage.getGraphics();
		setGraphicsProperties(g2d);
		g2d.draw(shape2);
	}
	
	//构建2D形状
	protected abstract Shape buildShape();
	
	//进行几何变换
	protected abstract Shape transform(Shape shape);
	
	//应用颜色和其它绘制属性
	protected abstract void setGraphicsProperties(Graphics2D g2d);

}
