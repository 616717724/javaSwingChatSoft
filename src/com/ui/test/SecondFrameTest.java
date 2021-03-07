package com.ui.test;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.ui.Txt_size;
import com.ui.panel.TopPanel;
import com.ui.setIcon.SetIcon;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class SecondFrameTest extends JDialog implements Txt_size {

	int fminwitdh = 300, fminheight = 500;// 最小高宽
	int windx = 500, windy = 200;// 默认位置
	Dimension screensize;
	private JPanel contentPane;
	JPanel center_panel,bottom_panel;
	JPanel userinfo_panel;
	private JLabel lbl_usericon;
	private JPanel panel;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecondFrameTest frame = new SecondFrameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SecondFrameTest() {
		center_panel = new JPanel();
		getContentPane().add(center_panel);
		userinfo_panel = new JPanel();
		
		bottom_panel=new JPanel();
		init();
		initTrayIcon();
	}

	public void init() {
		// TODO 自动生成的方法存根
		setUndecorated(true);
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		windx = ((int) screensize.getWidth() - fminwitdh);
		windy = 0;
		setBounds(windx, windy, fminwitdh, fminheight);
		// 设置顶部面板
				getContentPane().add(new TopPanel(this,1), BorderLayout.NORTH);
		
		// getContentPane().add(new FriJTree().jTree, BorderLayout.CENTER);
		
		center_panel.setLayout(new BorderLayout(0, 0));
		String s = SecondFrameTest.class.getResource("/com/img/help.png")
				.toString();
		System.out.println(s);
		userinfo_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		center_panel.add(userinfo_panel, BorderLayout.NORTH);		
				
//				lbl_usericon = new JLabel(
//						"<html><b style='color:white;font-size:40px; font-family:宋体;'>李<br/></b></html>");
						lbl_usericon = new JLabel("李");
						userinfo_panel.add(lbl_usericon);
						lbl_usericon.setForeground(Color.WHITE);
						lbl_usericon.setFont(new Font("宋体", Font.PLAIN, 35));
						lbl_usericon.setOpaque(true);
						lbl_usericon.setBorder(new CompoundBorder(new EtchedBorder(),
								new LineBorder(Color.white)));
						lbl_usericon.setBackground(new Color(13, 196, 254));
						
						panel = new JPanel();
						userinfo_panel.add(panel);
						panel.setLayout(new GridLayout(0, 1, 0, 0));
//						JLabel lbl_userinfo = new JLabel(
//								"<html><b style='font-size:10px; font-family:宋体;'>用户名</b><br/>账号</html>");
						JLabel lbl_userinfo = new JLabel("用户名");
						lbl_userinfo.setBackground(Color.RED);
						panel.add(lbl_userinfo);
						lbl_userinfo.setSize(40, 40);
						// lblNewLabel.setIcon(new
						// ImageIcon(SecondFrame.class.getResource("/com/img/help.png")));
						lbl_userinfo.setHorizontalAlignment(SwingConstants.LEFT);
						
						label = new JLabel("账号");
						label.setHorizontalAlignment(SwingConstants.LEFT);
						panel.add(label);
	}

	public void initListener() {
		// TODO 自动生成的方法存根
		
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	// TODO 这个地方可以考虑，tray就只要一个，在主窗体出来之后换掉其上面的监听
	private void initTrayIcon() {
		if (SystemTray.isSupported()) {
			try {
				final SystemTray tray = SystemTray.getSystemTray();
				final TrayIcon icon = new TrayIcon(SetIcon.getPicture(
						"logo.png", 40, 20).getImage(), "system");
				icon.setImageAutoSize(true); // 自动适应大小
				icon.addMouseListener(new MouseAdapter() {
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
}
