package tmp.j2d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yuan.common.swing.LogDisplay;

public class DownLoadPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel northPanel;
	private JTextField url;
	private JButton downButton;
	
	private JTextArea contentArea;
	private JProgressBar progressBar;
	
	public DownLoadPanel(){
		super();
		//设置组件首选大小
		this.setPreferredSize(new Dimension(800, 600));
		buildComponents();
		buildLayout();
//		SwingAppender.setTextArea(contentArea);
	}
	
	protected void buildComponents(){
		northPanel = new JPanel();
		url = new JTextField(50);
		downButton = new JButton("执行");
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				doAction();
			}
		});
		
		contentArea = new JTextArea();
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setPreferredSize(new Dimension(400, 50));
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
//		progressBar.setBorderPainted(false);
//		progressBar.setIndeterminate(false);
	}
	
	protected void buildLayout(){
		super.setLayout(new BorderLayout());
		
		northPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.gridx = 0;
		gbc.weightx = 1; //网格缩放权重
		gbc.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(url, gbc);
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 0;
		northPanel.add(downButton, gbc);
		
		add(northPanel, BorderLayout.NORTH);
//		add(new JScrollPane(contentArea), BorderLayout.CENTER);
		add(new LogDisplay(), BorderLayout.CENTER);
		add(progressBar, BorderLayout.SOUTH);
	}
	
	protected void doAction(){
		contentArea.append(url.getText());
		new Thread(){
			public void run(){
				for(int i=0; i<=100; i++){
					progressBar.setValue(i);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
