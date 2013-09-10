package tmp.j2d;

import javax.swing.JSplitPane;

import com.yuan.common.swing.TextViewer;

public class TreePanel extends JSplitPane {

	private static final long serialVersionUID = 1L;
	
	public TreePanel(){
		super();
		TextViewer textViewer = new TextViewer();
		super.setLeftComponent(new DefaultTree(textViewer.getTextArea()));
		super.setRightComponent(textViewer);
	}

}
