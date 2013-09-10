package tmp.j2d;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.yuan.common.swing.SwingTool;

public class FontPropertiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JList nameList;
	protected JComboBox sizeBox;
	protected JCheckBox boldBox;
	protected JCheckBox italicBox;
	protected FontListener listener;
	public static final Integer[] fontSizes = {10, 12, 14, 18, 24, 32, 48, 64};
	
	public FontPropertiesPanel(){
		super();
		buildComponents();
		buildLayout();
//		super.setBorder(SwingTool.createEtchedTitledBorder("字体"));
		super.setBorder(SwingTool.createBevelTitledBorder("字体"));
	}
	
	protected void buildComponents(){
		String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		nameList = new JList(names);
		nameList.setSelectedIndex(0);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				handleFontPropertyChange();
			}
		});
		sizeBox = new JComboBox(fontSizes);
		sizeBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				handleFontPropertyChange();
			}
		});
		boldBox = new JCheckBox("粗体");
		boldBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				handleFontPropertyChange();
			}
		});
		italicBox = new JCheckBox("斜体");
		italicBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				handleFontPropertyChange();
			}
		});
	}
	
	protected void buildLayout(){
		super.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		
		gbc.gridx = 0;
		add(new JLabel("字体名:", JLabel.LEFT), gbc);
		
		gbc.gridx = 1;
		add(new JScrollPane(nameList), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("大小:", JLabel.LEFT), gbc);
		
		gbc.gridx = 1;
		add(sizeBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(boldBox, gbc);
		
		gbc.gridx = 1;
		add(italicBox, gbc);
	}
	
	protected void handleFontPropertyChange(){
//		JOptionPane.showMessageDialog(null, (String)nameList.getSelectedValue(), "提示", JOptionPane.INFORMATION_MESSAGE);
		if(listener != null){
			listener.fontChanged(getSelectedFont());
		}
	}
	
	public Font getSelectedFont(){
		String name = (String)(nameList.getSelectedValue()); 
		int style = 0;
		style += (boldBox.isSelected() ? Font.BOLD : 0);
		style += (italicBox.isSelected() ? Font.ITALIC : 0);
		int size = (Integer)sizeBox.getSelectedItem();
		return new Font(name, style, size);
	}

}
