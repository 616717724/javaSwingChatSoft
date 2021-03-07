package com.ui.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowFactory;

import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.panel.TopPanel;
import com.ui.test.loading.HomePanel;

import javax.swing.JTextArea;
import javax.swing.DropMode;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JInternalFrame;

import java.awt.BorderLayout;

import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.Box;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;

public class SwingTemp extends JFrame implements Txt_size {

	private JPanel contentPane;
	Dimension screensize;
	int width = 700, height = 550;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	public ImageIcon icon=null;  
    public Image img=null;
    JTabbedPane tabbedPane;
    JPanel all_panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingTemp frame = new SwingTemp();
					frame.setVisible(true);
					// frame.txtpwd.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public SwingTemp() {
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);

		setTitle("temp");
		setDefaultCloseOperation(3);
		setUndecorated(true);
		init();
//		init_panel();
	}

	private void init_panel() {
		// 设置字体
	}

	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		JDialog tmp_d=new JDialog(this,"");
		tmp_d.setSize(this.getWidth(),this.getHeight());
		//通过d去设置JFrame
		JPanel top=new TopPanel(tmp_d,1);
		getContentPane().add(top,BorderLayout.NORTH);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		all_panel=new JPanel();
		tabbedPane.addTab("好友一",null,all_panel);
		all_panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_friend_name = new JLabel("New label");
		all_panel.add(lbl_friend_name, BorderLayout.NORTH);
		
		JPanel center_panel = new JPanel();
		all_panel.add(center_panel, BorderLayout.CENTER);
		center_panel.setLayout(new BorderLayout(0, 0));
		
		JEditorPane editorPane = new JEditorPane();
		center_panel.add(editorPane, BorderLayout.CENTER);
		
		JPanel foot_panel = new JPanel();
		all_panel.add(foot_panel, BorderLayout.SOUTH);
		foot_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel tool_panel = new JPanel();
		foot_panel.add(tool_panel, BorderLayout.NORTH);
		tool_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel tool_panel2 = new JPanel();
		tool_panel.add(tool_panel2, BorderLayout.WEST);
		
//		JButton btnNewButton_2 = new JButton("表情");
		JButton btnNewButton_2 = new JButton();
//		JLabel btnNewButton_2 = new JLabel();
//		ImageIcon icon_load = new ImageIcon("./resource/img/loading.gif");
		ImageIcon icon_load = new ImageIcon("./resource/img/help.png");
//		img = icon_load.getImage();
//		img = img.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING);
//		icon_load = new ImageIcon(img);
		ImageIcon icon_true = new ImageIcon("./resource/img/true.png");
//		Image img = icon_true.getImage();
//		img = img.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING);
//		icon_true = new ImageIcon(img);
		btnNewButton_2.setIcon(icon_load);
		tool_panel2.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("文字");
		tool_panel2.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("截图");
		tool_panel2.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("文件");
		tool_panel2.add(btnNewButton_5);
		
		JComboBox comboBox = new JComboBox();
		for(int i=0;i<20;i++){
			comboBox.addItem("asd"+i);
		}
		tool_panel.add(comboBox, BorderLayout.EAST);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setPreferredSize(new Dimension(200, 100));
		foot_panel.add(editorPane_1);
		
		JPanel send_panel = new JPanel();
		foot_panel.add(send_panel, BorderLayout.SOUTH);
		send_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel send_panel2 = new JPanel();
		send_panel.add(send_panel2, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("关闭");
		send_panel2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("发送");
		send_panel2.add(btnNewButton_1);
	}
	public void paint(Graphics g) {
		super.paint(g);
	}
	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		
	}
}
