package tmp.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageTest {

	public static void main(String[] args) throws IOException {
//		test1();
//		test3();
		test5();

	}
	
	//指定大小进行缩放 
	public static void test1() throws IOException{
		//size(宽度, 高度)  
		  
		/*   
		 * 若图片横比200小，高比300小，不变   
		 * 若图片横比200小，高比300大，高缩小到300，图片比例不变   
		 * 若图片横比200大，高比300小，横缩小到200，图片比例不变   
		 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300   
		 */   
		Thumbnails.of("d:/1.jpg")   
		        .size(200, 200)  
		        .toFile("d:/1_200x200.jpg");  
		  
//		Thumbnails.of("images/a380_1280x1024.jpg")   
//		        .size(2560, 2048)   
//		        .toFile("c:/a380_2560x2048.jpg"); 
	}
	
	//按照比例进行缩放
	public static void test2() throws IOException{
	    //scale(比例)  
	    Thumbnails.of("images/a380_1280x1024.jpg")   
	            .scale(0.25f)  
	            .toFile("c:/a380_25%.jpg");  
	      
	    Thumbnails.of("images/a380_1280x1024.jpg")   
	            .scale(1.10f)  
	            .toFile("c:/a380_110%.jpg");  
	}
	
	//不按照比例，指定大小进行缩放
	public static void test3() throws IOException{
	    //keepAspectRatio(false) 默认是按照比例缩放的  
	    Thumbnails.of("d:/1.jpg")   
	            .size(200, 200)   
	            .keepAspectRatio(false)   
	            .toFile("d:/2_200x200.jpg");  
	}
	
	//旋转
	public static void test4() throws IOException{
		 //rotate(角度),正数：顺时针 负数：逆时针  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .rotate(90)   
		        .toFile("c:/a380_rotate+90.jpg");   
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .rotate(-90)   
		        .toFile("c:/a380_rotate-90.jpg"); 
	}
	
	//水印
	public static void test5() throws IOException{
		 //watermark(位置，水印图，透明度)  
		Thumbnails.of("d:/1.jpg")   
		        .size(592, 544)  
		        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("d:/3000020_3.jpg")), 0.5f)   
		        .outputQuality(0.8f)   
		        .toFile("d:/1_watermark_bottom_right.jpg");  
		  
		Thumbnails.of("d:/1.jpg")   
		        .size(592, 544)  
		        .watermark(Positions.CENTER, ImageIO.read(new File("d:/3000020_3.jpg")), 0.5f)   
		        .outputQuality(0.8f)   
		        .toFile("d:/1_watermark_center.jpg"); 
	}
	
	//裁剪
	public static void test6() throws IOException{
		 //sourceRegion()  
		  
		//图片中心400*400的区域  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(Positions.CENTER, 400,400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_center.jpg");  
		  
		//图片右下400*400的区域  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(Positions.BOTTOM_RIGHT, 400,400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_bootom_right.jpg");  
		  
		//指定坐标  
		Thumbnails.of("images/a380_1280x1024.jpg")  
		        .sourceRegion(600, 500, 400, 400)  
		        .size(200, 200)  
		        .keepAspectRatio(false)   
		        .toFile("c:/a380_region_coord.jpg"); 
	}
	
	//转化图像格式 
	public static void test7() throws IOException{
		 //outputFormat(图像格式)  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .outputFormat("png")   
		        .toFile("c:/a380_1280x1024.png");   
		  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .outputFormat("gif")   
		        .toFile("c:/a380_1280x1024.gif"); 
	}
	
	//输出到OutputStream
	public static void test8() throws IOException{
		 //toOutputStream(流对象)  
		OutputStream os = new FileOutputStream("c:/a380_1280x1024_OutputStream.png");  
		Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .toOutputStream(os); 
	}
	
	//输出到BufferedImage 
	public static void test9() throws IOException{
		 //asBufferedImage() 返回BufferedImage  
		BufferedImage thumbnail = Thumbnails.of("images/a380_1280x1024.jpg")   
		        .size(1280, 1024)  
		        .asBufferedImage();  
		ImageIO.write(thumbnail, "jpg", new File("c:/a380_1280x1024_BufferedImage.jpg")); 
	}

}
