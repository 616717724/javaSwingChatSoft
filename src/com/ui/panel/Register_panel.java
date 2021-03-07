package com.ui.panel;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.matcher.MyMatcher;
import com.socket.Client;
import com.ui.FristFrame;
import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.setIcon.SetIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register_panel extends JPanel implements Txt_size {
	public JPanel reg_panel;
	JLabel lblname;
	private JLabel lblphone;
	private JLabel label;
	private JLabel label_1;
	private JTextField txtname;
	private JTextField txtphone;
	private JPasswordField txtpwd;
	private JPasswordField txtpwdagain;
	private JLabel btnreg;
	private JLabel imgname;
	private JLabel imgphone;
	private JLabel imgpwd;
	private JLabel imgpwdagain;
	private JLabel btncancel;

	ImageIcon icon_false;
	ImageIcon icon_true;
	int name_sig = 0, phone_sig = 0, pwd_sig = 0, pwd_again_sig = 0;// 注册标记
	int load = 0;

	public Register_panel() {
		// reg_panel = init();
		init();
		initListener();
	}

	public void init() {
		// 设置字体
		// new SetFont().SetDefault(Font.PLAIN, 13);

		// 设置图片
		icon_true = new ImageIcon("./resource/img/true.png");
		Image img = icon_true.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon_true = new ImageIcon(img);
		icon_false = new ImageIcon("./resource/img/false.png");
		img = icon_false.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon_false = new ImageIcon(img);

		this.setLayout(null);

		lblname = new JLabel("姓名:");
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.setBounds(31, 31, 75, 35);
		this.add(lblname);

		lblphone = new JLabel("手机号:");
		lblphone.setHorizontalAlignment(SwingConstants.CENTER);
		lblphone.setBounds(31, 76, 75, 35);
		this.add(lblphone);

		label = new JLabel("密码:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(31, 121, 75, 35);
		this.add(label);

		label_1 = new JLabel("确认密码:");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(31, 166, 75, 35);
		this.add(label_1);

		txtname = new JTextField();
		txtname.setText("中英文不超过20个字节");
		// txtname.setToolTipText("dasdasdasd");
		txtname.setBounds(116, 31, txtwitdh, txtheight);
		this.add(txtname);
		txtname.requestFocus();

		txtphone = new JTextField();
		txtphone.setText("请输入手机号");
		txtphone.setBounds(116, 76, txtwitdh, txtheight);
		this.add(txtphone);
		txtphone.setColumns(10);

		txtpwd = new JPasswordField();
//		txtpwd.setEchoChar('|');
		txtpwd.setBounds(116, 121, txtwitdh, txtheight);
		this.add(txtpwd);
		// txtpwd.dispatchEvent(new FocusEvent(txtpwd, FocusEvent.FOCUS_GAINED,
		// true));
		// txtpwd.requestFocusInWindow();

		txtpwdagain = new JPasswordField();
//		txtpwdagain.setEchoChar('|');
		txtpwdagain.setBounds(116, 166, txtwitdh, txtheight);
		this.add(txtpwdagain);

		btnreg = new JLabel("");
		btnreg.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/reg_button.png")));
		btnreg.setHorizontalAlignment(SwingConstants.CENTER);
		btnreg.setToolTipText("注册");
		btnreg.setBounds(116, 205, 100, 50);
		this.add(btnreg);

		imgname = new JLabel("");
		// imgname.setIcon(icon_true);
		imgname.setBounds(326, 31, 58, 35);
		this.add(imgname);

		imgphone = new JLabel("");
		// imgphone.setIcon(icon_false);
		imgphone.setBounds(326, 75, 58, 35);
		this.add(imgphone);

		imgpwd = new JLabel("");
		imgpwd.setBounds(326, 121, 58, 35);
		this.add(imgpwd);

		imgpwdagain = new JLabel("");
		imgpwdagain.setBounds(326, 166, 58, 35);
		this.add(imgpwdagain);

		btncancel = new JLabel("");
		btncancel.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/canel_button.png")));
		btncancel.setHorizontalAlignment(SwingConstants.CENTER);
		btncancel.setToolTipText("取消");
		btncancel.setBounds(216, 205, 100, 50);
		this.add(btncancel);
	}

	public void initListener() {
		// TODO 自动生成的方法存根
		btnreg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (4 == name_sig + phone_sig + pwd_sig + pwd_again_sig&&txtpwd.getText().equals(txtpwdagain.getText())) {
					String u_name=txtname.getText();
					String u_phone=txtphone.getText();
					String u_password=txtpwd.getText();
					if(null==FristFrame.client){
						FristFrame.client=new Client();
						FristFrame.client.register_info(u_name, u_phone, u_password);
					}else{
						FristFrame.client.register_info(u_name, u_phone, u_password);
					}
				} else {
					JOptionPane.showMessageDialog(new JLabel(""), "信息不正确");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnreg.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/reg_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnreg.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/reg_button.png")));
			}
		});

		btncancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FristFrame.f.change_panel("登录");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/canel_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Register_panel.class.getResource("/com/resource/img/canel_button.png")));
			}
		});

		txtname.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				String s = txtname.getText().replace(" ", "");
				if (s.equals("")) {
					txtname.setText("中英文不超过20个字节");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}
		});
		txtname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				if (txtname.getText().equals("中英文不超过20个字节")) {
					txtname.setText("");
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO 自动生成的方法存根
				if (txtname.getText().equals("中英文不超过20个字节")) {
					txtname.setText("");
				}
			}
		});
		txtname.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				String s = txtname.getText().replace(" ", "");
				if (s.length()>0&&s.length() <= 20
						&& !txtname.getText().equals("中英文不超过20个字节")) {
					imgname.setIcon(icon_true);
					name_sig = 1;
				} else {
					imgname.setIcon(icon_false);
					name_sig = 0;
					// txtname.requestFocus();
				}
			}
		});

		txtphone.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				String s = txtphone.getText().replace(" ", "");
				if (s.equals("")) {
					txtphone.setText("请输入手机号");
				}
				String regex = "^[1]+[3,5]+\\d{9}$";
				if (MyMatcher.match(regex, txtphone.getText())) {
					imgphone.setIcon(icon_true);
					phone_sig = 1;
				} else {
					imgphone.setIcon(icon_false);
					phone_sig = 0;
					// txtphone.requestFocus();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				if (txtphone.getText().equals("请输入手机号")) {
					txtphone.setText("");
				}
			}
		});
		txtpwd.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				String s = txtpwd.getText().replace(" ", "");
				if (s.equals("")) {
					imgpwd.setIcon(icon_false);
					pwd_sig = 0;
				} else {
					pwd_sig = 1;
					imgpwd.setIcon(icon_true);
				}
			}
		});
		txtpwdagain.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				String s = txtpwdagain.getText().replace(" ", "");
				if (!s.equals(txtpwd.getText())
						|| txtpwdagain.getText().length() < 1) {
					imgpwdagain.setIcon(icon_false);
					pwd_again_sig = 0;
				} else {
					pwd_again_sig = 1;
					imgpwdagain.setIcon(icon_true);
				}
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (0 == load) {
			txtname.requestFocus();
			load++;
		}
	}

	public void paintComponent(Graphics g) {
//		System.out.println("paintComponent");
		super.paintComponent(g);
		Image image = new SetIcon().getPicture2("bg.png", this.getWidth(),
				this.getHeight()).getImage();
		g.drawImage(image, 0, 0, this);
	}
}
