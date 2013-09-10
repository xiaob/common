package tmp.j2d;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.yuan.common.swing.ResourceManager;

public class DefaultTable extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	public DefaultTable(){
		super(new MyTable());
	}
}
class MyTable extends JTable {
	
	private static final long serialVersionUID = 1L;

	public MyTable(){
		super(new TableValues());
		String[] c1 = {"a", "b", "c"};
		this.setComboBoxCellEditor(0, c1);
		this.setProgressCellRender(5);
		this.setButtonCellRender(6);
		this.setColumnWidth(2, 150);
		this.setColumnWidth(5, 200);
		this.setColumnWidth(6, 150);
	}
	
	public void setComboBoxCellEditor(int columnIndex, String[] data){
		super.getColumnModel().getColumn(columnIndex).setCellEditor(new DefaultCellEditor(new JComboBox(data)));
	}
	public void setCellEditor(int columnIndex, JCheckBox checkBox){
		super.getColumnModel().getColumn(columnIndex).setCellEditor(new DefaultCellEditor(checkBox));
	}
	public void setColumnWidth(int columnIndex, int width){
		super.getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
	}
	public void setProgressCellRender(int columnIndex){
		super.getColumnModel().getColumn(columnIndex).setCellRenderer(new ProgressRender());
	}
	public void setButtonCellRender(int columnIndex){
		super.getColumnModel().getColumn(columnIndex).setCellRenderer(new ButtonRender(null, null));
	}
	
	protected class DateCellEditor extends AbstractCellEditor{
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
			
			return null;
		}
		
		@Override
		public Object getCellEditorValue() {
			
			return null;
		}
	}
	
	protected class ProgressRender implements TableCellRenderer{
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setPreferredSize(new Dimension(100, 20));
			progressBar.setValue((Integer)value);
			progressBar.setStringPainted(true);
			return progressBar;
		}
	}
	
	protected class ButtonRender implements TableCellRenderer{
		private String text;
		private ActionListener listener;
		
		public ButtonRender(String text, ActionListener listener){
			this.text = text;
			this.listener = listener;
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			JButton button = new JButton((String)value);
			if(listener != null){
				button.addActionListener(listener);
			}
			return button;
		}
	}

}

class TableValues extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	protected String[] columnNames = {
		"第一列", "第二列", "第三列", "第四列", "第五列", "第六列", "第七列"
	};
	
	protected Object[][] values = {
		{
			"c", "f",
			new Date().getTime(), new Double(12.34), true, 10, "按钮1"
		},	
		{
			"c", "f",
			new Date().getTime(), new Double(12.34), false, 20, "按钮1"
		},	
		{
			"c", "f",
			new Date().getTime(), new Double(12.34), true, 30, "按钮1"
		},	
		{
			"c", "f",
			new Date().getTime(), new Double(12.34), true, 40, "按钮1"
		},	
		{
			"c", "f",
			new Date().getTime(), new Double(12.34), false, 50, "按钮1"
		}
	};
	
	protected ResourceManager resourceManager;
	
	public TableValues(){
		super();
		resourceManager = new ResourceManager(TableValues.class);
		resourceManager.put("icon1", "resource/map16.png");
		for(int i=0; i<values.length; i++){
			values[i][1] = new ImageIcon(resourceManager.get("icon1"));
		}
	}
	
	public int getRowCount(){
		return values.length;
	}
	
	public int getColumnCount(){
		return values[0].length;
	}
	
	public Object getValueAt(int row, int column){
		return values[row][column];
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}
	
	public Class<?> getColumnClass(int column){
		Class type = super.getColumnClass(column);
		switch(column){
		case 1 : 
			type = Icon.class;
			break;
		case 2 : 
			type = Date.class;
			break;
		case 3 :
			type = Double.class;
			break;
		case 4 :
			type = Boolean.class;
			break;
		}
		return type;
	}
	
	public boolean isCellEditable(int row, int column){
		return true;
	}
	
	public void setValueAt(Object aValue, int row, int column){
		values[row][column] = aValue;
		super.fireTableCellUpdated(row, column);
	}
	
}
