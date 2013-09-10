package com.yuan.common.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

public class Screen extends JWindow {

	private static final long serialVersionUID = 1L;
	private JPopupMenu menu;
//	private int x = 100;
//	private int y = 100;
	protected ResourceManager resourceManager;
	protected double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	protected double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected Image backgroundImage;
	
	public Screen(){
		super();
		
		menu = new JPopupMenu();
		menu.add(new JMenuItem("退出")).addActionListener(new MenuHandle());
		menu.add(new JMenuItem("关于"));
		menu.add(new JMenuItem("帮助"));
		
		super.addMouseListener(new ScreenHandle());
		resourceManager = new ResourceManager(Screen.class);
		resourceManager.put("img1", "resource/map16.png");
		resourceManager.put("img2", "resource/4.jpg");
		try {
			backgroundImage = ImageIO.read(new File(resourceManager.get("img2")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Screen screen = new Screen();
		screen.fullScreen();
	}
	
	public void fullScreen(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice(); 
		if ( gd.isFullScreenSupported() ){
			gd.setFullScreenWindow(this); 
		}
		if (gd.isDisplayChangeSupported()) {
//		     gd.setDisplayMode(new DisplayMode(800, 600, DisplayMode.BIT_DEPTH_MULTI, 60));
		 }
	}
	
	protected void showMenu(int x, int y){
		menu.show(this, x, y);
	}
	
	private void drawCircle(int x, int y){
//		this.x = x;
//		this.y = y;
		super.repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
//		g2.setPaint(new GradientPaint(0, 0, Color.red, 800, 800, Color.green, true));
//		g2.fillRect(100, 40, 300, 20);
//		try {
//			g2.setPaint(new TexturePaint(ImageIO.read(new File(resourceManager.get("img1"))), new Rectangle2D.Double(100, 100, 200,200)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		g2.fill(new Ellipse2D.Double(100, 100, 300, 200));
//		g2.drawOval(x, y, 200, 200);
		g2.drawImage(backgroundImage, 0, 0, null);
	}
	
	private class MenuHandle implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	
	private class ScreenHandle extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e){
			if((e.getButton() == MouseEvent.BUTTON1)){
				drawCircle(e.getX(), e.getY());
//				menu.setVisible(false);
			}
			
//			if(e.getButton() == MouseEvent.BUTTON3){
//				showMenu(e.getX(), e.getY());
//			}
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger()){
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
