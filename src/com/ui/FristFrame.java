package com.ui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iflytek.Iflytek;
import com.socket.Client;
import com.ui.panel.Login_panel;
import com.ui.panel.MustKnow_panel;
import com.ui.panel.Register_panel;
import com.ui.panel.TopPanel;
import com.ui.setIcon.SetIcon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FristFrame extends JDialog implements Txt_size {
	private JTextField txtnub;
	private JPasswordField txtpwd;
	JLabel lblnub, lblpwd, lblreg;
	Dimension screensize;
	int width = 400, height = 300;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	String t_log = "登录", t_reg = "注册";
	public static FristFrame f;
	public static Client client=null;
	public TrayIcon icon;

	// JPanel top_panel;

	public static void main(String[] args) {
		// f = new FristFrame();
		// f.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					f = new FristFrame();
//					f.setVisible(true);
//					new Iflytek().start("欢迎使用即时通信系统");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FristFrame() {
		init();
		initTrayIcon();
		initListener();
		setVisible(true);
	}

	public void init() {
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);
//		setAlwaysOnTop(true);
//		setAlwaysOnTop(false);
		setTitle(t_log);
		// setDefaultCloseOperation(3);
		setResizable(false);

		setUndecorated(true);
		// setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
//		System.out.println(this.getWidth());
		// 设置顶部面板
		getContentPane().add(new TopPanel(this,0), BorderLayout.NORTH);
		
		//login
		getContentPane().add(new Login_panel());
	}

	// 改变页面
	public void change_panel(String t_name) {
		if (t_name.equals("登录")) {
			setTitle(t_log);
			getContentPane().removeAll();
			getContentPane().add(new TopPanel(this,0), BorderLayout.NORTH);
			getContentPane().add(new Login_panel());
			getContentPane().validate();
		} else if (t_name.equals("同意")) {
			setTitle("注册须知");
			getContentPane().removeAll();
			getContentPane().add(new TopPanel(this,0), BorderLayout.NORTH);
			getContentPane().add(new MustKnow_panel());
			getContentPane().validate();
		} else if (t_name.equals("注册")) {
			setTitle(t_reg);
			getContentPane().removeAll();
			getContentPane().add(new TopPanel(this,0), BorderLayout.NORTH);
			getContentPane().add(new Register_panel());
			getContentPane().validate();
		}
	}
	
	// TODO 这个地方可以考虑，tray就只要一个，在主窗体出来之后换掉其上面的监听
		private void initTrayIcon() {
			if (SystemTray.isSupported()) {
				try {
					final SystemTray tray = SystemTray.getSystemTray();
					icon = new TrayIcon(SetIcon.getPicture("logo.png", 40, 20).getImage(), "system");
					icon.setImageAutoSize(true); //自动适应大小
					icon.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if (e.getButton() == MouseEvent.BUTTON1) {
								setVisible(true);
								// 获取焦点
								requestFocus();
							}
						}
					});
					
					PopupMenu pm = new PopupMenu();
					MenuItem mit = new MenuItem("Exit");
					mit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							tray.remove(icon);
							System.exit(0);
						}
					});
					pm.add(mit);
					icon.setPopupMenu(pm);
					tray.add(icon);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	public void initListener() {
		// 主窗体事件
	}

	public void paint(Graphics g) {
		super.paint(g);
		// top_panel.requestFocus();
	}

}
