package com.yuan.common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.yuan.common.util.StringUtil;

public class DialogTool {
	
	public static Color showColorDialog(){
		return JColorChooser.showDialog(null, "颜色选择", new Color(0, 0, 0));
	}
	
	public static File showOpenDialog(String currentDir, String... exts){
		JFileChooser fileChooser = new JFileChooser();
		if(StringUtil.hasText(currentDir) && new File(currentDir).exists()){
			fileChooser.setCurrentDirectory(new File(currentDir));
		}
		if((exts != null) && (exts.length > 0)){
			for(String ext : exts){
				fileChooser.addChoosableFileFilter(new DefaultFileFilter(ext));
			}
		}
		
		int result = fileChooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){//确定
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	public static File showSaveDialog(String currentDir){
		JFileChooser fileChooser = new JFileChooser();
		if(StringUtil.hasText(currentDir) && new File(currentDir).exists()){
			fileChooser.setCurrentDirectory(new File(currentDir));
		}
		
		int result = fileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){//确定
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	public static void showLoginDialog(final LoginListener listener){
		new LoginDialog(listener);
	}
	
	public static Font showFontDialog(){
		return JFontDialog.showDialog("设置字体", null);
	}
	
	public static File showDirDialog(String title, String initDir, String msg){
		File idir = null;
		if(StringUtil.hasText(initDir) && new File(initDir).exists()){
			idir = new File(initDir);
		}
		return JDirChooser.showDialog(title, idir, msg);
	}
	
	public static void main(String args[]){
//		System.out.println(showSaveDialog("e:/").getAbsolutePath());
//		System.out.println(showFontDialog());
		System.out.println(showDirDialog("请选择文件夹", null, "请选择"));
//		showLoginDialog(new LoginListener() {
//			
//			@Override
//			public void login(LoginDialog owner, String username, String password) {
//				JOptionPane.showMessageDialog(null, "qqqq");
//				owner.dispose();
//				JOptionPane.showMessageDialog(null, "wwwwwww");
//			}
//			
//			@Override
//			public void giveup(LoginDialog owner) {
//				
//			}
//		});
		
	}

}

class DefaultFileFilter extends FileFilter{
	
	private String ext;
	private String description;
	
	public DefaultFileFilter(String ext){
		this.ext = ext;
		this.description = makeDescription(ext);
	}
	
	public DefaultFileFilter(String ext, String description){
		this.ext = ext;
		this.description = description;
	}
	
	private String makeDescription(String ext){
		if(ext.indexOf(',') == -1){
			return "*." + ext;
		}
		String[] es = ext.split(",");
		String d = "";
		for(String s : es){
			d += "*." + s + ",";
		}
		if(d.endsWith(",")){
			d = d.substring(0, d.length() - 1);
		}
		return d;
	}
	private boolean isValidFile(File f){
		String[] es = ext.split(",");
		for(String s : es){
			if(f.getName().endsWith("." + s)){
				return true;
			}
		}
		return false;
	}
	public boolean accept(File file){
		if(file.isDirectory() || isValidFile(file)){
			return true;
		}
		
		return false;
	}
	
	public String getDescription(){
		return description;
	}
}

class LoginDialog extends AppDialog{

	private static final long serialVersionUID = 1L;
	
	private JPanel inputPanel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	
	private JPanel southPanel;
	private JPanel actionPanel;
	private JButton loginButton;
	private JButton giveupButton;
	
	private LoginListener listener;
	
	public LoginDialog(LoginListener listener){
		super();
		//设置组件首选大小
		super.setPreferredSize(new Dimension(400, 150));
		super.setTitle("登录");
		this.listener = listener;
		buildComponents();
		buildLayout();
		super.renderToScreen();
	}
	
	protected void buildComponents(){
		inputPanel = new JPanel();
		usernameLabel = new JLabel("用户名: ");
		passwordLabel = new JLabel("密码: ");
		userNameTextField = new JTextField();
		passwordField = new JPasswordField();
		
		southPanel = new JPanel();
		actionPanel = new JPanel();
		loginButton = new JButton("登录");
		final LoginDialog owner = this;
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listener != null ){
					listener.login(owner, userNameTextField.getText(), new String(passwordField.getPassword()));
				}
			}
		});
		giveupButton = new JButton("放弃");
		giveupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listener != null ){
					listener.giveup(owner);
				}
			}
		});
	}
	
	protected void buildLayout(){
		inputPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		
		gbc.gridx = 0;
		inputPanel.add(usernameLabel , gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1; //网格缩放权重
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(userNameTextField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0; //网格缩放权重
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(passwordLabel, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1; //网格缩放权重
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(passwordField, gbc);
		
		actionPanel.setLayout(new GridLayout(1, 2, 10, 5));
		actionPanel.add(loginButton);
		actionPanel.add(giveupButton);
		
		southPanel.add(actionPanel);
		
		Container contentPane = super.getContentPane();
		contentPane.add(inputPanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
	}
	
}

class JFontDialog extends AppDialog implements ActionListener,
		ListSelectionListener, Serializable {
	
	private static final long serialVersionUID = 1L;
	private JPanel pCenter = new JPanel();
	private JTextField txtName = new JTextField();
	private JTextField txtStyle = new JTextField();
	private JTextField txtSize = new JTextField();
	private JScrollPane spName = new JScrollPane();
	private JScrollPane spStyle = new JScrollPane();
	private JScrollPane spSize = new JScrollPane();
	private JPanel pPreview = new JPanel();
	private TitledBorder titledBorder1;
	private JLabel lbName = new JLabel();
	private JLabel lbStyle = new JLabel();
	private JLabel lbSize = new JLabel();

	/**
	 * 取得所有字体名
	 */
	String fontNames[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames();
	private JList listName = new JList(fontNames);
	String fontStyles[] = { "标准",
			"粗体",
			"斜体",
			"粗斜体" };
	private JList listStyle = new JList(fontStyles);
	String fontSizes[] = { "8", "9", "10", "11", "12", "14", "16", "18", "20",
			"22", "24", "26", "28", "36", "48", "72" };
	private JList listSize = new JList(fontSizes);
	private JLabel lbPreview = new JLabel();
	private JButton bttOK = new JButton();
	private JButton bttCancel = new JButton() {
		private static final long serialVersionUID = 1L;

		public Insets getInsets() {
			return new Insets(0, 0, 0, 0);
		}
	};
	boolean hasCancel = true;

	public static Font showDialog(String title, Font initFont) {
		JFontDialog dialog = new JFontDialog();
		dialog.setTitle(title);
		if (initFont != null) {
			dialog.setFont(initFont);
		}
		dialog.renderToScreen();
		return dialog.getSelectedFont();
	}

	public JFontDialog() {
		super(480, 310);
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("预览");
		pCenter.setLayout(null);
		txtName.setBackground(Color.white);
		txtName.setEditable(false);
		txtName.setBounds(new Rectangle(15, 22, 152, 21));
		txtStyle.setBackground(Color.white);
		txtStyle.setEditable(false);
		txtStyle.setBounds(new Rectangle(174, 22, 123, 21));
		txtSize.setBackground(Color.white);
		txtSize.setEditable(false);
		txtSize.setBounds(new Rectangle(304, 22, 63, 21));
		spName.setBounds(new Rectangle(15, 47, 152, 220));
		spStyle.setBounds(new Rectangle(174, 47, 123, 113));
		spSize.setBounds(new Rectangle(304, 47, 63, 113));
		pPreview.setBorder(titledBorder1);
		pPreview.setBounds(new Rectangle(174, 170, 193, 98));
		pPreview.setLayout(null);
		lbName.setText("名称");
		lbName.setBounds(new Rectangle(15, 4, 151, 17));
		lbStyle.setText("风格");
		lbStyle.setBounds(new Rectangle(174, 4, 123, 17));
		lbSize.setText("大小");
		lbSize.setBounds(new Rectangle(304, 4, 62, 17));
		lbPreview.setBorder(BorderFactory.createLoweredBevelBorder());
		lbPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lbPreview.setText("字AaBbCc");
		lbPreview.setBounds(new Rectangle(10, 23, 171, 61));
		bttOK.setBounds(new Rectangle(379, 21, 79, 22));
		bttOK.setMnemonic('O');
		bttOK.setText("确定");
		bttCancel.setBounds(new Rectangle(379, 48, 79, 22));
		bttCancel.setMnemonic('C');
		bttCancel.setText("取消");
		listName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(pCenter);
		pCenter.add(spName, null);
		pCenter.add(lbName, null);
		pCenter.add(spStyle, null);
		pCenter.add(txtName, null);
		pCenter.add(txtStyle, null);
		pCenter.add(lbStyle, null);
		pCenter.add(txtSize, null);
		pCenter.add(lbSize, null);
		pCenter.add(spSize, null);
		pCenter.add(pPreview, null);
		pPreview.add(lbPreview, null);
		pCenter.add(bttOK, null);
		pCenter.add(bttCancel, null);
		spSize.getViewport().add(listSize, null);
		spStyle.getViewport().add(listStyle, null);
		spName.getViewport().add(listName, null);
		bttOK.addActionListener(this);
		bttCancel.addActionListener(this);
		listName.addListSelectionListener(this);
		listSize.addListSelectionListener(this);
		listStyle.addListSelectionListener(this);
		setFont(lbPreview.getFont());

		getRootPane().registerKeyboardAction(this,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		super.addWindowListener(new WindowHandle());
	}

	public void setFont(Font f) {
		if (f == null)
			throw new NullPointerException("不能设置空字体");
		listName.setSelectedValue(f.getFamily(), true);
		listStyle.setSelectedIndex(f.getStyle());
		listSize.setSelectedValue("" + f.getSize(), true);
		super.setFont(f);
	}

	public void valueChanged(ListSelectionEvent e) {
		JList obj = (JList) e.getSource();
		if (obj == listName) {
			txtName.setText(listName.getSelectedValue().toString());
		} else if (obj == listStyle) {
			txtStyle.setText(listStyle.getSelectedValue().toString());
		} else if (obj == listSize) {
			txtSize.setText(listSize.getSelectedValue().toString());
		}
		obj.scrollRectToVisible(obj.getCellBounds(obj.getSelectedIndex(), obj
				.getSelectedIndex()));
		try {
			Font f = new Font(txtName.getText(), listStyle.getSelectedIndex(),
					Integer.parseInt(txtSize.getText()));
			lbPreview.setFont(f);
		} catch (NumberFormatException ex) {
		}
	}

	public Font getSelectedFont(){
		waitSelectting();
		if (hasCancel) {
			return null;
		}
		return lbPreview.getFont();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bttOK) {
			hasCancel = false;
		} else if (obj == bttCancel) {
			hasCancel = true;
		}
		
		super.closeWindow();
	}
	
	private class WindowHandle extends WindowAdapter{

		public void windowClosing(WindowEvent e){
			hasCancel = true;
			closeWindow();
		}
	}

}

class JDirChooser extends AppDialog implements TreeSelectionListener,
		ActionListener, Serializable {
	
	private static final long serialVersionUID = 1L;

	boolean hasCancel = true;

	JPanel pCenter = new JPanel(new BorderLayout());
	JFileTree fileTree = new JFileTree();
	JScrollPane spTree = new JScrollPane(fileTree);
	JPanel pSouth = new JPanel(new BorderLayout());
	JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
	JPanel pResult = new JPanel(new BorderLayout());
	JLabel lbFolder = new JLabel("目录");
	JTextField txtResult = new JTextField();
	JButton bttCreateNew = new JButton("创建目录");
	JButton bttCancel = new JButton("取消");
	JButton bttOK = new JButton("确定");
	JLabel lbView = new JLabel();
	JPanel pAdjust = new JPanel() {
		private static final long serialVersionUID = 1L;

		public void paintChildren(Graphics g) {
			super.paintChildren(g);
			int w = getWidth();
			int h = getHeight();
			Color oldColor = g.getColor();
			// draw ///
			g.setColor(Color.white);
			g.drawLine(w, h - 12, w - 12, h);
			g.drawLine(w, h - 8, w - 8, h);
			g.drawLine(w, h - 4, w - 4, h);
			g.setColor(new Color(128, 128, 128));
			g.drawLine(w, h - 11, w - 11, h);
			g.drawLine(w, h - 10, w - 10, h);
			g.drawLine(w, h - 7, w - 7, h);
			g.drawLine(w, h - 6, w - 6, h);
			g.drawLine(w, h - 3, w - 3, h);
			g.drawLine(w, h - 2, w - 2, h);
			g.setColor(oldColor);
		}
	};

	MouseInputAdapter adjustWindowListener = new MouseInputAdapter() {
		Point oldP = null;

		public void mouseDragged(MouseEvent e) {
			if (oldP != null) {
				Point newP = e.getPoint();
				JDirChooser c = JDirChooser.this;
				c.setBounds(c.getX(), c.getY(), c.getWidth()
						+ (newP.x - oldP.x), c.getHeight() + (newP.y - oldP.y));
				c.validate();
				oldP = newP;
			}
		}

		public void mouseMoved(MouseEvent e) {
			Component c = e.getComponent();
			Rectangle r = new Rectangle(c.getWidth() - 12, 0, 12, c.getHeight());
			if (r.contains(e.getPoint())) {
				JDirChooser.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			} else {
				JDirChooser.this.setCursor(Cursor.getDefaultCursor());
			}
		}

		public void mousePressed(MouseEvent e) {
			Component c = e.getComponent();
			Rectangle r = new Rectangle(c.getWidth() - 12, 0, 12, c.getHeight());
			if (r.contains(e.getPoint())) {
				oldP = e.getPoint();
			} else {
				oldP = null;
			}
		}

		public void mouseExited(MouseEvent e) {
			JDirChooser.this.setCursor(Cursor.getDefaultCursor());
		}

		public void mouseReleased(MouseEvent e) {
			oldP = null;
		}
	};

	public static File showDialog(String title, File initDir, String msg) {
		JDirChooser dialog;
		dialog = new JDirChooser();
		dialog.setTitle(title);
		if (initDir != null) {
			try {
				dialog.setSelectFile(initDir);
			} catch (Exception ex) {
			}
		}
		if (msg != null) {
			dialog.setMsg(msg);
		}
		dialog.renderToScreen();
		return dialog.getSelectFile();
	}

	/**
	 * 取得根窗口
	 * 
	 * @param c
	 *            Component
	 * @return Window
	 */
	static Window getRootWindow(Component c) {
		if (c == null)
			return null;
		Container parent = c.getParent();
		if (c instanceof Window)
			return (Window) c;
		while (!(parent instanceof Window))
			parent = parent.getParent();
		return (Window) parent;
	}

	public JDirChooser() {
		super();
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		bttCreateNew.setPreferredSize(new Dimension(88, 21));
		bttCreateNew.setMargin(new Insets(0, 0, 0, 0));
		bttCancel.setPreferredSize(new Dimension(88, 21));
		bttCancel.setMargin(new Insets(0, 0, 0, 0));
		bttOK.setPreferredSize(new Dimension(88, 21));
		pCenter.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
		lbView.setPreferredSize(new Dimension(190, 50));
		pButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -7));
		pResult.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
		pAdjust.setPreferredSize(new Dimension(10, 15));
		this.getContentPane().add(pCenter, BorderLayout.CENTER);
		pCenter.add(spTree, BorderLayout.CENTER);
		pCenter.add(pSouth, BorderLayout.SOUTH);
		pResult.add(txtResult, BorderLayout.CENTER);
		pResult.add(lbFolder, BorderLayout.WEST);
		pSouth.add(pButtons, BorderLayout.CENTER);
		pSouth.add(pResult, BorderLayout.NORTH);
		pButtons.add(bttOK, null);
		pButtons.add(bttCancel, null);
		pButtons.add(bttCreateNew, null);
		pCenter.add(lbView, BorderLayout.NORTH);
		this.getContentPane().add(pAdjust, BorderLayout.SOUTH);
		this.setSize(400, 305);
		this.setResizable(false);
		this.txtResult.setEditable(false);
		this.txtResult.setBackground(Color.white);

		fileTree.addTreeSelectionListener(this);
		getRootPane().registerKeyboardAction(this,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		bttOK.setEnabled(false);
		bttCreateNew.setEnabled(false);
		bttOK.addActionListener(this);
		bttCancel.addActionListener(this);
		bttCreateNew.addActionListener(this);

		pAdjust.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		pAdjust.addMouseListener(adjustWindowListener);
		pAdjust.addMouseMotionListener(adjustWindowListener);
		
		super.addWindowListener(new WindowHandle());
	}

	public void setMsg(String msg) {
		this.lbView.setText(msg);
	}

	public String getMsg() {
		return this.lbView.getText();
	}

	public void valueChanged(TreeSelectionEvent event) {
		File f = fileTree.getSelectFile();
		boolean enabled = (f != null);
		bttOK.setEnabled(enabled);
		enabled = enabled && JFileTree.fileSystemView.isFileSystem(f);
		if (f != null && JFileTree.fileSystemView.isDrive(f)) {
			enabled = enabled && f.canWrite();
		}
		bttCreateNew.setEnabled(enabled);
		if (f != null) {
			txtResult.setText(JFileTree.fileSystemView.getSystemDisplayName(f));
			// fileTree.setEditable(f.renameTo(f));
		}
	}

	public JFileTree getFileTree() {
		return this.fileTree;
	}

	public void setFileTree(JFileTree tree) {
		if (tree == null || tree == this.fileTree) {
			return;
		}
		this.spTree.getViewport().setView(tree);
		this.spTree.doLayout();
	}

	public File getSelectFile() {
		super.waitSelectting();
		if (hasCancel)
			return null;
		return fileTree.getSelectFile();
	}

	public void setSelectFile(File f) throws Exception {
		fileTree.setSelectFile(f);
	}

	public void actionPerformed(ActionEvent actionEvent) {
		Object obj = actionEvent.getSource();
		if (obj == bttCreateNew) {
			String dirName = JOptionPane.showInputDialog(this, "", "new");
			if (dirName == null || dirName.trim().length() == 0) {
				return;
			}
			File f = fileTree.getSelectFile();
			f = new File(f.getAbsolutePath() + File.separator + dirName);
			if (f.mkdir()) {
				fileTree.getSelectFileNode().removeAllChildren();
				fileTree.getSelectFileNode().setExplored(false);
				fileTree.getSelectFileNode().explore();
			} else {
				JOptionPane.showMessageDialog(this, "Error", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			if (obj == bttOK) {
				hasCancel = false;
			}
			super.closeWindow();
		}
	}
	
	private class WindowHandle extends WindowAdapter{

		public void windowClosing(WindowEvent e){
			hasCancel = true;
			closeWindow();
		}
	}

}
