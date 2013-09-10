package tmp.j2d;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphPanelDemo {
	
	public static void grphDemo(){
		JPanel panel = null;
		
//		panel = new CirclePanel();
//		panel = new GameLifePanel();
//		panel = new ImageViewer();
//		panel = new FontPropertiesPanel();
//		panel = new DownLoadPanel();
		panel = new AreaPanel();
//		panel = new MoonPanel();
		
		ImageFrame imageFrame = new ImageFrame("Java2D 演示");
		imageFrame.setIcon("E:\\其它\\图标\\java\\fix.gif");
		imageFrame.showPanel(panel);
	}
	
	public static void compDemo(){
		Component component = null;
		
//		component = new TextViewer();
//		component = new DefaultTable();
		component = new TreePanel();
		
		ImageFrame imageFrame = new ImageFrame("Java2D 演示");
		imageFrame.showComponent(component);
	}
	
	public static void threadGraphDemo(){
		ThreadPanel panel = null;
//		panel = new RainPanel();
		panel = new TypeWordPanel("您好, 欢迎光临!");
//		panel = new ScrollWordPanel("您好, 欢迎光临!");
//		panel = new PicturePanel("F:\\游戏\\街机游戏模拟器\\winkawaks\\sshots");
		
		ImageFrame imageFrame = new ImageFrame("Java2D 演示");
		imageFrame.showPanel(panel);
		
		panel.start();
		try {
//			Thread.sleep(5*1000);
			panel.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panel.shutdown();
	}

	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				grphDemo();
//				threadGraphDemo();
//				compDemo();
//				log();
			}
		});
	}
	
	private static void log(){
		final Logger log = LoggerFactory.getLogger(GraphPanelDemo.class);
		new Thread(){
			public void run(){
				for(int i=0; i<40; i++){
					log.info("swing日志测试" + i);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
