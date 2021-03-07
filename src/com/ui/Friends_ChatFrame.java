package com.ui;

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
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.setIcon.SetIcon;
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

import javax.swing.Icon;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import com.socket.Client;

public class Friends_ChatFrame extends JFrame implements Txt_size {

	private JPanel contentPane;
	Dimension screensize;
	int width = 700, height = 550;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	public ImageIcon icon = null;
	public Image img = SetIcon.getPicture(
			"logo.png", 40, 40).getImage();
	public static JTabbedPane tabbedPane;
	Friend_chat_all_panel all_panel;
	String name_icon = "李";// 用户名头像
	String lbl_name_text = "用户名";// 用户名
	String lbl_user_id = "用户账号";// 用户账号
	String text;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Friends_ChatFrame frame = new Friends_ChatFrame();
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
	public Friends_ChatFrame(String name_icon, String lbl_name_text,
			String lbl_user_id) {
		this.name_icon = name_icon;
		this.lbl_name_text = lbl_name_text;
		this.lbl_user_id = lbl_user_id;
		setIconImage(img);
		init();
		// init_panel();
		setVisible(true);
	}
	
//	public Friends_ChatFrame(String name_icon, String lbl_name_text,
//			String lbl_user_id,String my_phone,String my_name,BufferedReader bufr,BufferedWriter bufw) {
//		this.name_icon = name_icon;
//		this.lbl_name_text = lbl_name_text;
//		this.lbl_user_id = lbl_user_id;
//		setIconImage(img);
//		init();
//		// init_panel();
//		setVisible(true);
//	}

	public Friends_ChatFrame() {
//		getContentPane().setBackground(new Color(13,196,254));
		setIconImage(img);
		init();
		// init_panel();
		setVisible(true);
	}

	private void init_panel() {
		// 设置字体
	}

	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
//		System.out.println("paintComponents");
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	public void paint(Graphics g){
		super.paint(g);
//		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
//		System.out.println("paint");
		
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		setStyle();
		
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);

		setTitle("聊天窗口");
		setDefaultCloseOperation(3);
		setUndecorated(true);

		JDialog tmp_d = new JDialog(this, "");
		tmp_d.setSize(this.getWidth(), this.getHeight());
		// 通过d去设置JFrame
		JPanel top = new TopPanel(tmp_d, 1);
		getContentPane().add(top, BorderLayout.NORTH);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(new Color(13,196,254));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		all_panel = new Friend_chat_all_panel(name_icon, lbl_name_text,
				lbl_user_id);
		all_panel.setFrame(this);
		tabbedPane.addTab(lbl_name_text, null, all_panel);
		Client.panel_map.put(lbl_user_id,all_panel);
	}

	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根

	}

	public String getText() {
		return text;
	}

	public void setText(String text,Icon img) {
		this.text = text;
		all_panel.setText(text,img);
	}
}
