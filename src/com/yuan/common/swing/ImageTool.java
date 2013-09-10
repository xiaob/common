package com.yuan.common.swing;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTool {
	
	public static BufferedImage captureScreen(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			return new Robot().createScreenCapture(new Rectangle(dimension));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage scaleImage(BufferedImage srcImage, double sx, double sy){
		AffineTransform xform = new AffineTransform();
		xform.scale(sx, sy);
		BufferedImageOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(srcImage, null);
	}
	
	public static BufferedImage scaleImage(String imageFile, double sx, double sy){
		try {
			BufferedImage bi = ImageIO.read(new File(imageFile));
			return scaleImage(bi, sx, sy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
