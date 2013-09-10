package tmp.j2d;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.yuan.common.swing.ResourceManager;
import com.yuan.common.util.CharacterCoder;

public class TrayDemo {

	public static void main(String[] args) {
		ResourceManager resourceManager = new ResourceManager(TrayDemo.class);
		resourceManager.put("icon1", "resource/map16.png");
		
		if(SystemTray.isSupported()){
			SystemTray tray = SystemTray.getSystemTray();
			try {
//				tray.add(new TrayIcon(getImage(resourceManager.get("icon1")), "系统托盘信息", getMenu()));
				TrayIcon trayIcon = new TrayIcon(getImage(resourceManager.get("icon1")));
				trayIcon.setToolTip("系统托盘信息");
				trayIcon.setPopupMenu(getMenu());
				
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static Image getImage(String imgFile){
		try {
			return ImageIO.read(new File(imgFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static PopupMenu getMenu(){
		ActionHandle handle = new ActionHandle();
		PopupMenu menu = new PopupMenu();
		menu.add(new MenuItem("弹出菜单")).addActionListener(handle);
		menu.add(new MenuItem("about")).addActionListener(handle);
		menu.add(new MenuItem("exit")).addActionListener(handle);
		return menu;
	}
	
	private static class ActionHandle implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			MenuItem item = (MenuItem)event.getSource();
			String label = item.getLabel();
			if(label.equals("exit")){
				System.exit(0);
			}else{
				JOptionPane.showConfirmDialog(null, label);
			}
		}
	}

}
