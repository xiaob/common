package tmp.j2d;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yuan.common.swing.DropAdapter;

public class ImageViewer extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ImageViewer(){
		super();
		//设置组件首选大小
		this.setPreferredSize(new Dimension(640, 480));
		setLayout(null);
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropAdapter(){
			public void drop(List<File> fileList, DropTargetDropEvent event){
				for(File f : fileList){
					addNewComponent(getLabelFromFile(f), event.getLocation());
				}
			}
		});
	}
	
	private Image getImage(File f){
//		try {
//			if(f.getName().endsWith(".ico")){
//				return (Image)Sanselan.getAllBufferedImages(f).get(0);
////				return (Image)new ICOFile(f.getAbsolutePath()).getImages().get(0);
//			}
//			return (Image)Sanselan.getBufferedImage(f);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}
	
	private JLabel getLabelFromFile(File f){
//		ImageIcon icon = new ImageIcon(f.getAbsolutePath());
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(getImage(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel label = new JLabel(icon);
		label.setText(f.getName());
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		return label;
	}
	
	private void addNewComponent(Component comp, Point location){
		comp.setLocation(location);
		comp.setSize(comp.getPreferredSize());
		add(comp);
		repaint();
	}
	
}
