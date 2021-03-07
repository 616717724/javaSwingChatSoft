package com.ui.panel;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.socket.Client;
import com.ui.FristFrame;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.setIcon.SetIcon;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login_panel extends JPanel implements Txt_size {
	private JTextField txtphone;
	private JPasswordField txtpwd;
	JLabel lblnub, lblpwd, lblreg;
	public JPanel log_panel;
	public static JLabel btnlog;
	public static boolean log_sig = false;

	int load = 0;

	public Login_panel() {
		// log_panel=init_panel();
		init();
		initListener();
	}

	public void init() {
		this.setLayout(null);

		new SetFont().SetDefault(Font.PLAIN, 13);

		lblnub = new JLabel("手机号：");
		// lblnub.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblnub.setBounds(33, 75, 54, 15);
		this.add(lblnub);

		lblpwd = new JLabel("\u5BC6\u7801\uFF1A");
		// lblpwd.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblpwd.setBounds(33, 129, 54, 15);
		this.add(lblpwd);

		txtphone = new JTextField();
		txtphone.setBounds(91, 64, txtwitdh, txtheight);
		this.add(txtphone);

		txtpwd = new JPasswordField();
		txtpwd.setBounds(91, 118, txtwitdh, txtheight);
//		txtpwd.setEchoChar('|');
		this.add(txtpwd);

		String html_btnlog = "<html><font size='5'>登录</font></html>";
		btnlog = new JLabel("");
		btnlog.setIcon(new ImageIcon(Login_panel.class
				.getResource("/com/resource/img/login_button.png")));
		btnlog.setHorizontalAlignment(SwingConstants.CENTER);
		btnlog.setBounds(99, 167, 192, 61);
		this.add(btnlog);

		lblreg = new JLabel("注册账号");
		lblreg.setBounds(322, 228, 83, 36);
		this.add(lblreg);
		setVisible(true);
	}

	public void initListener() {
		txtpwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // 判断按下的键是否是回车键
				{
					login();
				}
			}
		});
		btnlog.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login();
				// System.out.println("登录中...");
				// new Thread(){
				// public void run(){
				// SecondFrame secondframe=new SecondFrame();
				// secondframe.setVisible(true);
				// FristFrame.f.dispose();
				// }
				// }.start();
				// SystemTray.getSystemTray().remove(FristFrame.f.icon);
			}

			public void mouseEntered(MouseEvent e) {
				btnlog.setIcon(new ImageIcon(Login_panel.class
						.getResource("/com/resource/img/login_active.png")));
			}

			public void mouseExited(MouseEvent e) {
				btnlog.setIcon(new ImageIcon(Login_panel.class
						.getResource("/com/resource/img/login_button.png")));
			}
		});

		lblreg.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				lblreg.setCursor(new Cursor(Cursor.HAND_CURSOR));
				lblreg.setText("<html><u style='color:red;'>注册账号</u><html>");
			}

			public void mouseExited(MouseEvent e) {
				lblreg.setText("注册账号");
			}

			public void mouseClicked(MouseEvent e) {
				FristFrame.f.change_panel("同意");
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (0 == load) {
			txtphone.requestFocus();
		}
		load = 1;
	}

	void login() {
		String u_phone = txtphone.getText();
		String u_password = txtpwd.getText();
		if (!("".equals(u_phone) || "".equals(u_password))) {
			// btnlog.setText("登录中...");
			btnlog.setIcon(new ImageIcon(Login_panel.class
					.getResource("/com/resource/img/logining.gif")));
			if (null == FristFrame.client) {
				FristFrame.client = new Client();
				FristFrame.client.login_info(u_phone, u_password);
			} else {
				FristFrame.client.login_info(u_phone, u_password);
			}
		} else {
			JOptionPane.showMessageDialog(new JLabel(""), "密码或手机号不能为空");
			// btnlog.setText("登录");
		}
	}

	// 设置背景
	public void paintComponent(Graphics g) {
		// System.out.println("paintComponent");
		super.paintComponent(g);
		ImageIcon image = new ImageIcon(getClass().getResource(
				"/com/resource/img/bg8.gif"));
		g.drawImage(image.getImage(), 0, 0, this.getWidth(), this.getHeight(),
				this);
	}

}
