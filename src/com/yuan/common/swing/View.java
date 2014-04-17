package com.yuan.common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.yuan.common.file.FileUtil;

public class View extends AppFrame {

	private static final long serialVersionUID = 1L;

	protected JDesktopPane desktopPane;
	protected MyToolBar toolBar;
	protected JMenuBar menuBar;
	protected JMenu fileMenu;
	protected JMenuItem openMenuItem;
	protected JMenuItem saveMenuItem;
	protected JMenuItem saveAsMenuItem;
	protected JMenuItem exitMenuItem;
	protected JMenu helpMenu;
	protected JMenuItem helpMenuItem;
	protected JMenuItem aboutMenuItem;
	
	protected int cwX = 10;
	protected int cwY = 10;
	protected int cwW = 500;
	protected int cwH = 300;
	protected List<Integer> zIndexList = new ArrayList<Integer>();
	protected ResourceManager resourceManager;
	static{
//		SkinTool.substance();
	}
	public View(){
		this("视图");
	}
	
	public View(String title){
		this(title, 800, 600);
	}
	
	public View(String title, int width, int height){
		super(title);
		super.setPreferredSize(new Dimension(width, height));
		
		buildResource();
		buildMenu();
		buildToolBar();
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(128, 128, 128));
		desktopPane.setBorder(BorderFactory.createLoweredBevelBorder());
		
		super.setJMenuBar(menuBar);
		Container contentPane = super.getContentPane();
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropAdapter(){
			public void drop(List<File> fileList, DropTargetDropEvent event){
				for(File f : fileList){
					addFile(f);
				}
			}
		});
	}
	
	protected void buildResource(){
		resourceManager = new ResourceManager(View.class);
		resourceManager.put("openIcon", "resource/broom16.png");
		resourceManager.put("saveIcon", "resource/goblete16.png");
		resourceManager.put("saveAsIcon", "resource/gobletf16.png");
		resourceManager.put("exitIcon", "resource/hat16.png");
		resourceManager.put("helpIcon", "resource/map16.png");
		resourceManager.put("aboutIcon", "resource/owl16.png");
		resourceManager.put("oneIcon", "resource/chinchilla.png");
		resourceManager.put("twoIcon", "resource/donkey.png");
	}
	protected JMenuItem newMenuItem(String text, String imgFile){
		try {
			BufferedImage bi = ImageIO.read(new File(imgFile));
			double sx = 16.0/new Integer(bi.getWidth()).doubleValue();
			double sy = 16.0/new Integer(bi.getHeight()).doubleValue();
			return new JMenuItem(text, new ImageIcon(ImageTool.scaleImage(bi, sx, sy)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JMenuItem(text);
	}
	protected void buildMenu(){
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("文件");
		openMenuItem = newMenuItem("打开", resourceManager.get("openIcon"));
		saveMenuItem = newMenuItem("保存", resourceManager.get("saveIcon"));
		saveAsMenuItem = newMenuItem("另存为", resourceManager.get("saveAsIcon"));
		exitMenuItem = newMenuItem("退出", resourceManager.get("exitIcon"));
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		helpMenu = new JMenu("帮助");
		helpMenuItem = newMenuItem("帮助", resourceManager.get("helpIcon"));
		aboutMenuItem = newMenuItem("关于", resourceManager.get("aboutIcon"));
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}
	protected void buildToolBar(){
		toolBar = new MyToolBar(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "我的工具栏");
			}
		});
		toolBar.addButton("图标一", resourceManager.get("oneIcon"));
		toolBar.addButton("图标二", resourceManager.get("twoIcon"));
	}
	
	public void addFile(File f){
		addFile(f, "UTF-8");
	}
	public void addFile(File f, String encoding){
		if(zIndexList.isEmpty()){
			cwX = 10;
			cwY = 10;
		}else{
			cwX += 20;
			cwY += 20;
		}
		if((cwX >= desktopPane.getWidth()) || (cwY >= desktopPane.getHeight())){
			cwX = 10;
			cwY = 10;
		}
		
		final ContentWindow cw = new ContentWindow(f.getAbsolutePath());
		try {
			cw.setText(FileUtil.readText(f.toPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		cw.addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameClosed(InternalFrameEvent e){
//				cw.dispose();
				zIndexList.remove(cw.getClientProperty("zIndex"));
				desktopPane.remove(cw);
			}
		});
		int cwZIndex = makeZIndex();
		cw.putClientProperty("zIndex", cwZIndex);
		desktopPane.add(cw, cwZIndex);
		cw.setBounds(cwX, cwY, cwW, cwH);
		cw.setVisible(true);
	}
	
	protected int makeZIndex(){
		int zIndex = 199;
		for(Integer index : zIndexList){
			if(index.intValue() > zIndex){
				zIndex = index;
			}
		}
		if(zIndex == Integer.MAX_VALUE){
			zIndex = 199;
		}
		zIndex ++ ;
		while(zIndexList.contains(zIndex)){
			zIndex ++ ;
		}
		zIndexList.add(zIndex);
		return zIndex;
	}
	
	public static void main(String args[]){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				View view = new View();
				view.renderToScreen();
				view.addFile(new File("D:\\test\\test\\Book.java"));
				view.addFile(new File("D:\\test\\test\\Book.java"));
			}
		});
		
	}
	
}

class ContentWindow extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	
	private TextViewer textViewer;
	
	public ContentWindow(String title){
		super(title, true, true, true);
		textViewer = new TextViewer();
		super.getContentPane().add(textViewer, BorderLayout.CENTER);
	}
	
	public void setText(String text){
		textViewer.setText(text);
	}
	
	public String getText(){
		return textViewer.getText();
	}
	
}
