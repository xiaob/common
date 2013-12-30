package tmp.j2d;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.yuan.common.swing.ImageTool;

public class GraphDemo {
	
	public static BufferedImage drawDemo1(){
		BufferedImage bufferedImage = new BufferedImage(500, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		//绘制一个椭圆
		Shape ellipse = new Ellipse2D.Double(150, 100, 200, 200);
		GradientPaint paint = new GradientPaint(100, 100, Color.WHITE, 400, 400, Color.GRAY);
		g2.setPaint(paint);
		g2.fill(ellipse);
		
		//设置透明度
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2.setComposite(ac);
		g2.setColor(Color.BLUE);
		
		//绘制透明文本
		Font font = new Font("Serif", Font.BOLD, 120);
		g2.setFont(font);
		g2.drawString("Java", 120, 200);
		
		//获取文本字体的轮墩线
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, "2D");
		Shape glyph = gv.getOutline(150, 300);
		
		//绘制可旋转字体
		g2.rotate(Math.PI/6, 200, 300);
		g2.fill(glyph);
		
		return bufferedImage;
	}
	
	public static BufferedImage drawPicture(){
		try {
			return ImageIO.read(new File("E:\\桌面壁纸\\1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage drawHello2D(){
		BufferedImage bufferedImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		g2.setColor(Color.BLUE);
		Ellipse2D e = new Ellipse2D.Double(-100, -50, 200, 100);
		AffineTransform tr = new AffineTransform();
		tr.rotate(Math.PI/6.0); //设置旋转变换
		Shape shape = tr.createTransformedShape(e);
		g2.translate(300, 200); // 进行平移变换
		g2.scale(2, 2); //进行缩放变换
		g2.draw(shape);
		g2.drawString("Hello 2D", 0, 0);
		
		return bufferedImage;
	}
	
	public static BufferedImage drawSpirograph(){
		BufferedImage bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		int nPonits = 1000;
		double r1 = 60;
		double r2 = 50;
		double p = 70;
		
		g2.translate(200, 200); // 进行平移变换
		g2.setColor(Color.BLUE);
		
		int x1 = (int)(r1 + r2 - p);
		int y1 = 0;
		int x2;
		int y2;
		for(int i=0; i<nPonits; i++){
			double t = i*Math.PI/90;
			x2 = (int)((r1 + r2)*Math.cos(t) - p*Math.cos((r1+r2)*t/r2));
			y2 = (int)((r1 + r2)*Math.sin(t) - p*Math.sin((r1+r2)*t/r2));
			g2.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
		
		return bufferedImage;
	}
	
	public static BufferedImage drawQuad(){
		BufferedImage bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		QuadCurve2D quad = new QuadCurve2D.Double(0, 0, 0.5, 0.7, 1, 1);
		AffineTransform tr = new AffineTransform();
		tr.translate(400, 300); // 进行平移变换, 
		tr.scale(20, 40); //缩放
		Shape shape = tr.createTransformedShape(quad);
		
		g2.setColor(Color.BLUE);
		g2.draw(shape);
		
		return bufferedImage;
	}
	
	public static BufferedImage drawShape(){
		BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		GeneralPath path = new GeneralPath();
		path.moveTo(-2f, 0f);
		path.quadTo(0f, 2f, 2f, 0f);
		path.quadTo(0f, -2f, -2f, 0f);
		path.moveTo(-1f, 0.5f);
		path.lineTo(1f, -0.5f);
		path.lineTo(1f, 0.5f);
		path.lineTo(-1f, -0.5f);
		path.closePath();
		
		AffineTransform tr = new AffineTransform();
		tr.translate(400, 300); // 进行平移变换, 
		tr.scale(20, 40); //缩放
		Shape shape = tr.createTransformedShape(myShape3());
		
		g2.setColor(Color.BLUE);
		g2.drawLine(400, 0, 400, 600);
		g2.drawLine(0, 300, 800, 300);
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(3));
		g2.draw(shape);
		
		return bufferedImage;
	}
	
	public static Shape myShape(){
		GeneralPath path = new GeneralPath();
		
		double y;
		double nPoints = 10*Math.PI;
		for(double x=-10*Math.PI; x<nPoints; x += 0.1){
			y = -Math.sin(x);
			if(x == -10*Math.PI){
				path.moveTo(x, y);
			}else{
				path.lineTo(x, y);
			}
			
		}
		
//		path.closePath();
		return path;
	}
	
	/**
	 * 参数方程作图
	 * x = t的平方
	 * y = t的三次方
	 * @return Shape
	 */
	public static Shape myShape1(){
		GeneralPath path = new GeneralPath();
		double x = 0;
		double y = 0;
		path.moveTo(0, 0);
		for(double t=-30; t<30; t+=0.1){
			x = Math.pow(t, 2);
			y = Math.pow(t, 3);
			if(t == -30){
				path.lineTo(x, y);
			}else{
				path.lineTo(x, y);
			}
		}
		return path;
	}
	/**
	 * 参数方程作图
	 * x = 20tcost
	 * y = 20tsint
	 * 0<=t<=8*PI
	 * @return Shape
	 */
	public static Shape myShape2(){
		GeneralPath path = new GeneralPath();
		double x = 0;
		double y = 0;
		
		for(double t=0; t<=8*Math.PI; t+=0.1){
			x = 20*t*Math.cos(t);
			y = 20*t*Math.sin(t);
			if( t== 0){
				path.moveTo(x, y);
			}else{
				path.lineTo(x, y);
			}
		}
		return path;
	}
	
	public static Shape myShape3(){
		GeneralPath path = new GeneralPath();
		
		double y;
		double nPoints = 1;
		for(double x=0; x<=nPoints; x += 0.1){
			y = Math.sqrt(1-x*x);
			System.out.println(x + ", " + y);
			if(x == 0){
				path.moveTo(x, y);
			}else{
				path.lineTo(x, y);
			}
			
		}
		
//		path.closePath();
		return path;
	}
	
	public static BufferedImage drawRect(){
		BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		AffineTransform tr = new AffineTransform();
//		tr.translate(400, 300); // 进行平移变换, 
//		tr.scale(20, 40); //缩放
		tr.rotate(Math.PI/4, 400, 300);
		Shape shape = tr.createTransformedShape(new Rectangle2D.Double(200, 100, 400, 400));
		
		g2.setColor(Color.BLUE);
		g2.drawLine(400, 0, 400, 600);
		g2.drawLine(0, 300, 800, 300);
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(3));
		g2.draw(shape);
		
		return bufferedImage;
	}
	public static BufferedImage drawRect2(){
		BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(3));
		for(int i=0; i<8; i++){
			g2.drawLine(i*100, 0, i*100, 600);
			g2.drawLine(0, i*75, 800, i*75);
		}
		
		return bufferedImage;
	}
	
	public static BufferedImage drawFont(){
		BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		
		
		g2.setColor(Color.BLUE);
		Font font = new Font("宋体", Font.BOLD, 120);
		GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), "字体轮墩");
		Shape glyph = gv.getOutline(150, 300);
		g2.fill(glyph);
		return bufferedImage;
	}
	
	public static double f(double x){
		return 0;
	}
	
	public static void writeToFile(BufferedImage bi){
		try {
			ImageIO.write(bi, "JPEG", new File("d:/tmp.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		BufferedImage bi = null;
		
//		bi = drawDemo1();
//		bi = drawPicture();
//		bi = drawHello2D();
//		bi = drawSpirograph();
//		bi = drawLine();
		bi = drawShape();
//		bi = drawRect();
//		bi = drawRect2();
//		bi = drawQuad();
//		bi = drawFont();
//		bi = ImageTool.captureScreen();
//		writeToFile(bi);
		
		ImageFrame imageFrame = new ImageFrame("Java2D 演示");
//		imageFrame.fullScreen();
		imageFrame.showImage(bi);
	}

}
