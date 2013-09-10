package com.yuan.common.swing;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * 按键控制台
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class KeyConsole {

	private JFrame frame;
	private GlobalKeyListener globalKeyListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeyConsole window = new KeyConsole();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KeyConsole() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("按键控制台");
		frame.setBounds(100, 100, 450, 300);
		center(frame);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(globalKeyListener != null){
					System.out.println("globalKeyListener.destory ...");
					globalKeyListener.destory();
				}
				System.exit(0);  //关闭
			} 
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		registKeyListener();  
		globalKeyListener = new GlobalKeyListener();
		globalKeyListener.init();
	}
	
	private void center(JFrame frame){
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setLocation((int) (screenWidth - frame.getWidth()) / 2, (int) (screenHeight - frame.getHeight()) / 2); 
	}
	
	private void registAWTKeyListener(){
		Toolkit tk = Toolkit.getDefaultToolkit();  
        tk.addAWTEventListener(new ImplAWTEventListener(), AWTEvent.KEY_EVENT_MASK);
	}

}
/**全局键盘事件, 失去焦点则会失效*/
class ImplAWTEventListener implements AWTEventListener {  
	
	private MouseRobot robot;
	
	public ImplAWTEventListener(){
		robot = new MouseRobot();
	}
	
    @Override  
    public void eventDispatched(AWTEvent event) {  
        if (event.getClass() == KeyEvent.class) {  
            // 被处理的事件是键盘事件.  
            KeyEvent keyEvent = (KeyEvent) event;  
            if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {  
                //按下时你要做的事情  
                keyPressed(keyEvent);  
            } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {  
                //放开时你要做的事情  
                keyReleased(keyEvent);  
            }  
        }
    }  
    
    private void keyPressed(KeyEvent event) {
    	switch (event.getKeyCode()) {
		case KeyEvent.VK_UP :
			robot.moveUp();
			break;
		case KeyEvent.VK_DOWN :
			robot.moveDown();
			break;
		case KeyEvent.VK_LEFT :
			robot.moveLeft();
			break;
		case KeyEvent.VK_RIGHT :
			robot.moveRight();
			break;
		}
    } 
    
    private void keyReleased(KeyEvent event) {
    	
    }  
}  


/**
 * 全局键盘事件监听器, 失去焦点也能响应键盘事件
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
class GlobalKeyListener implements HotkeyListener{

	private static final Logger LOG = LoggerFactory.getLogger(GlobalKeyListener.class);
	private MouseRobot robot;
			
	/**
	 * 初始化
	 */
	public void init(){
		robot = new MouseRobot();
		
		try {
			File dll = new File(new File("").getCanonicalFile(), "lib/JIntellitype64.dll");
			System.out.println(dll.getAbsolutePath());
			JIntellitype.setLibraryLocation(dll.getAbsolutePath());
			
			JIntellitype.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (JIntellitype.checkInstanceAlreadyRunning("按键控制台")) {
		   LOG.error("An instance of this application is already running");
		   System.exit(1);
		}
		
		JIntellitype.getInstance().registerSwingHotKey(1, KeyEvent.VK_UNDEFINED, KeyEvent.VK_UP);
		JIntellitype.getInstance().registerSwingHotKey(2, KeyEvent.VK_UNDEFINED, KeyEvent.VK_DOWN);
		JIntellitype.getInstance().registerSwingHotKey(3, KeyEvent.VK_UNDEFINED, KeyEvent.VK_LEFT);
		JIntellitype.getInstance().registerSwingHotKey(4, KeyEvent.VK_UNDEFINED, KeyEvent.VK_RIGHT);
		
		JIntellitype.getInstance().addHotKeyListener(this);
	}
	
	/**
	 * 注销键盘事件监听
	 */
	public void destory(){
		JIntellitype.getInstance().unregisterHotKey(1);
		JIntellitype.getInstance().unregisterHotKey(2);
		JIntellitype.getInstance().unregisterHotKey(3);
		JIntellitype.getInstance().unregisterHotKey(4);
		
		JIntellitype.getInstance().cleanUp();
	}
	
	@Override
	public void onHotKey(int aIdentifier) {
		switch (aIdentifier) {
		case 1:
			robot.moveUp();
			break;
		case 2:
			robot.moveDown();
			break;
		case 3:
			robot.moveLeft();
			break;
		case 4:
			robot.moveRight();
			break;
		}
	}
	
}

class MouseRobot{
	
	private Robot robot;
	
	private Point centerPoint;
	private Point leftPoint;
	private Point rightPoint;
	private Point upPoint;
	private Point downPoint;
	
	public MouseRobot(){
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension dimension = tk.getScreenSize();
		double screenWidth = dimension.getWidth();
		double screenHeight = dimension.getHeight();
		
		int cpX = (int) (screenWidth) / 2;
		int cpY = (int) (screenHeight) / 2;
		int xRange = 100;
		int yRange = 100;
		centerPoint = new Point(cpX, cpY);
		leftPoint = new Point(cpX - xRange, cpY);
		rightPoint = new Point(cpX + xRange, cpY);
		upPoint = new Point(cpX, cpY - yRange);
		downPoint = new Point(cpX, cpY + yRange);
		
		printPoint(centerPoint);
		printPoint(leftPoint);
		printPoint(rightPoint);
		printPoint(upPoint);
		printPoint(downPoint);
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void printPoint(Point point){
		System.out.println(point.getX() + ", " + point.getY());
	}
	
	private void moveCenter(){
    	robot.mouseRelease(InputEvent.BUTTON1_MASK);
    	robot.mouseMove((int)centerPoint.getX(), (int)centerPoint.getY());
    }
    private void moveTo(Point point){
    	moveCenter();
    	robot.mousePress(InputEvent.BUTTON1_MASK);
    	if((int)point.getX() == (int)centerPoint.getX()){
    		dragX(point);
    	}else{
    		dragY(point);
    	}
    	moveCenter();
    }
    private void dragX(Point point){
    	int step = ((int)point.getX() - (int)centerPoint.getX()) / 5;
    	for(int i=1; i<=5; i++){
    		robot.mouseMove((int)centerPoint.getX() + step*1, (int)point.getY());
    		sleep(20);
    	}
    }
    private void dragY(Point point){
    	int step = ((int)point.getY() - (int)centerPoint.getY()) / 5;
    	for(int i=1; i<=5; i++){
    		robot.mouseMove((int)point.getX(), (int)centerPoint.getY()+ step*1);
    		sleep(20);
    	}
    }
    private void sleep(long millis){
    	try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void moveUp(){
    	moveTo(downPoint);
//    	moveTo(upPoint);
    }
    public void moveDown(){
    	moveTo(upPoint);
//    	moveTo(downPoint);
    }
    public void moveLeft(){
    	moveTo(rightPoint);
//    	moveTo(leftPoint);
    }
    public void moveRight(){
    	moveTo(leftPoint);
//    	moveTo(rightPoint);
    }
    
}
