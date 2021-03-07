package com.ui.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.dao.User;
import com.matcher.MyMatcher;
import com.socket.Client;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.panel.BgPanel;
import com.ui.setIcon.SetIcon;

public class Update_User_Info_Dialog extends JDialog implements Txt_size {
	private int windx;
	private int windy;
	private int width;
	private int height;
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
	JLabel imgdep;
	private JLabel btncancel;
	JLabel label_4;
	ImageIcon icon_false;
	ImageIcon icon_true;
	int name_sig = 0, phone_sig = 0, dep_sig = 0, pwd_sig = 0,
			pwd_again_sig = 0;// 注册标记
	int load = 0;
	private JTextField txtdep;

	User user;
	Update_User_Info_Dialog user_info_dialog = null;

	public Update_User_Info_Dialog() {
		init();
		initListener();
		this.setVisible(true);
	}

	public Update_User_Info_Dialog(User user) {
		this.user = user;
		init();
		initListener();
		// set
		setModal(true);
		requestFocus();
		this.setVisible(true);
	}

	public void init() {
		// this.setUndecorated(true);
		// this.setAlwaysOnTop(true);
		new SetFont().SetDefault(Font.PLAIN, 13);
		Image image = SetIcon.getPicture("logo.png", 200, 200).getImage();
		this.setIconImage(image);
		this.setTitle("修改信息");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		width = 450;
		height = 500;
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		this.setSize(width, height);
		this.setLocation(windx, windy);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		BgPanel p = new BgPanel("bg5.png");
		getContentPane().add(p);

		// 设置图片
		icon_true = new ImageIcon("./resource/img/true.png");
		Image img = icon_true.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon_true = new ImageIcon(img);
		icon_false = new ImageIcon("./resource/img/false.png");
		img = icon_false.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon_false = new ImageIcon(img);

		p.setLayout(null);

		lblname = new JLabel("姓名:");
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.setBounds(31, 31, 75, 35);
		p.add(lblname);

		lblphone = new JLabel("手机号:");
		lblphone.setHorizontalAlignment(SwingConstants.CENTER);
		lblphone.setBounds(31, 76, 75, 35);
		p.add(lblphone);

		label = new JLabel("原密码:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(31, 253, 75, 35);
		p.add(label);

		label_1 = new JLabel("新密码:");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(31, 298, 75, 35);
		p.add(label_1);

		txtname = new JTextField();
		txtname.setEditable(false);
		txtname.setText(user.getU_name());
		// txtname.setToolTipText("dasdasdasd");
		txtname.setBounds(116, 31, txtwitdh, txtheight);
		p.add(txtname);
		txtname.requestFocus();

		txtphone = new JTextField(user.getU_phone());
		txtphone.setEditable(false);
		txtphone.setText(user.getU_phone());
		txtphone.setBounds(116, 76, txtwitdh, txtheight);
		p.add(txtphone);
		txtphone.setColumns(10);

		txtpwd = new JPasswordField();
		txtpwd.setEditable(false);
//		txtpwd.setEchoChar('|');
		txtpwd.setBounds(116, 253, txtwitdh, txtheight);
		p.add(txtpwd);
		// txtpwd.dispatchEvent(new FocusEvent(txtpwd, FocusEvent.FOCUS_GAINED,
		// true));
		// txtpwd.requestFocusInWindow();

		txtpwdagain = new JPasswordField();
		txtpwdagain.setEditable(false);
//		txtpwdagain.setEchoChar('|');
		txtpwdagain.setBounds(116, 298, txtwitdh, txtheight);
		p.add(txtpwdagain);

		btnreg = new JLabel("修改信息");
		btnreg.setIcon(new ImageIcon(Update_User_Info_Dialog.class
				.getResource("/com/resource/img/update_button.png")));
		btnreg.setHorizontalAlignment(SwingConstants.CENTER);
		btnreg.setToolTipText("修改个人信息");
		btnreg.setBounds(126, 178, 165, 50);
		p.add(btnreg);

		imgname = new JLabel("");
		// imgname.setIcon(icon_true);
		imgname.setBounds(326, 31, 58, 35);
		p.add(imgname);

		imgphone = new JLabel("");
		// imgphone.setIcon(icon_false);
		imgphone.setBounds(326, 75, 58, 35);
		p.add(imgphone);

		imgpwd = new JLabel("");
		imgpwd.setBounds(326, 253, 58, 35);
		p.add(imgpwd);

		imgpwdagain = new JLabel("");
		imgpwdagain.setBounds(326, 298, 58, 35);
		p.add(imgpwdagain);

		btncancel = new JLabel("");
		btncancel.setIcon(new ImageIcon(Update_User_Info_Dialog.class
				.getResource("/com/resource/img/canel_button.png")));
		btncancel.setHorizontalAlignment(SwingConstants.CENTER);
		btncancel.setToolTipText("取消");
		btncancel.setBounds(161, 401, 100, 50);
		p.add(btncancel);

		JLabel label_2 = new JLabel("部门:");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(31, 133, 75, 35);
		p.add(label_2);

		txtdep = new JTextField();
		txtdep.setToolTipText("多个部门用/分开(例如:开发部/开发部一)");
		String dep = user.getU_dept().replace("||", "/");
		txtdep.setText(dep);
		txtdep.setEditable(false);
		txtdep.setColumns(10);
		txtdep.setBounds(116, 133, 200, 35);
		p.add(txtdep);

		imgdep = new JLabel("");
		imgdep.setBounds(326, 132, 58, 35);
		p.add(imgdep);

		label_4 = new JLabel("修改密码");
		label_4.setIcon(new ImageIcon(Update_User_Info_Dialog.class
				.getResource("/com/resource/img/pwd_button.png")));
		label_4.setToolTipText("修改密码");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(135, 356, 165, 43);
		p.add(label_4);
		// this.setFocusable(true);
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		btnreg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (btnreg.getText().equals("")) {
					if (2 == name_sig + dep_sig) {
						String u_name = txtname.getText();
						String u_phone = txtphone.getText();
						String u_dep = txtdep.getText();
						// String u_password=txtpwd.getText();
						user_info_dialog = null;
						// 当前用户号+当前用户名+目标用户号+目标用户名+文本消息
						String mesg = "type:3,user_info||" + u_phone + "||"
								+ u_name + "||" + u_dep;
						System.out.println("完整消息:" + mesg);
						try {
							Client.dato.writeUTF(mesg);
							Client.dato.flush();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							JOptionPane.showMessageDialog(new JLabel(""),
									"修改失败");
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(new JLabel(""), "修改成功");
						user.setU_phone(u_phone);
						user.setU_name(u_name);
						user.setU_dept(u_dep);
						SecondFrame.lbl_user_id = u_phone;
						SecondFrame.lbl_usericon.setText(u_name.substring(0, 1));
						SecondFrame.lbl_username.setText(u_name);
						SecondFrame.lbl_usernumb.setText(u_phone);
						dispose();
					} else {
						JOptionPane.showMessageDialog(new JLabel(""), "信息不正确");
					}
				}
				if (btnreg.getText().equals("修改信息")) {
					btnreg.setText("");
					// txtphone.setEditable(true);
					txtname.setEditable(true);
					txtdep.setEditable(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnreg.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/update_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnreg.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/update_button.png")));
			}
		});

		label_4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (label_4.getText().equals("确定")) {
					if (!Client.user_pwd.equals(txtpwd.getText())) {
						JOptionPane.showMessageDialog(new JLabel(""), "密码不对");
					} else {
						String p = txtpwdagain.getText().replace(" ", "");
						if ("".equals(p) || p.length() < 1) {
							JOptionPane.showMessageDialog(new JLabel(""),
									"不能为空");
						} else {
							String u_phone = txtphone.getText();
							String u_password = txtpwdagain.getText();
							String mesg = "type:3,user_pwd||" + u_phone + "||"
									+ u_password;
							System.out.println("完整消息:" + mesg);
							try {
								Client.dato.writeUTF(mesg);
								Client.dato.flush();
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								JOptionPane.showMessageDialog(new JLabel(""),
										"修改失败");
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(new JLabel(""),
									"修改成功");
							// user.setU_password(u_password);
							Client.user_pwd = u_password;
							dispose();
						}
					}
					// if (4 == name_sig + phone_sig + pwd_sig + pwd_again_sig)
					// {
					// String u_name=txtname.getText();
					// String u_phone=txtphone.getText();
					// String u_password=txtpwd.getText();
					//
					// } else {
					// JOptionPane.showMessageDialog(new JLabel(""), "信息不正确");
					// }
				}
				if (label_4.getText().equals("修改密码")) {
					label_4.setText("确定");
					;
					txtpwd.setEditable(true);
					txtpwdagain.setEditable(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				label_4.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/pwd_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label_4.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/pwd_button.png")));
			}
		});

		btncancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// FristFrame.f.change_panel("登录");
				user_info_dialog = null;
				dispose();
			}

			public void mouseEntered(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/canel_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Update_User_Info_Dialog.class
						.getResource("/com/resource/img/canel_button.png")));
			}
		});

		txtname.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if (txtname.isEditable()) {
					String s = txtname.getText().replace(" ", "");
					if (s.equals("")) {
						txtname.setText("中英文不超过20个字节");
					}
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
				if (txtname.isEditable()) {
					String s = txtname.getText().replace(" ", "");
					if (s.length() > 0 && s.length() <= 20
							&& !txtname.getText().equals("中英文不超过20个字节")) {
						imgname.setIcon(icon_true);
						name_sig = 1;
					} else {
						imgname.setIcon(icon_false);
						name_sig = 0;
						// txtname.requestFocus();
					}
				}
			}
		});

		txtdep.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				if (txtdep.isEditable()) {
					String s = txtdep.getText().replace(" ", "");
					if (s.equals("部门号不能为空")) {
						txtdep.setText("");
						imgdep.setIcon(icon_false);
					}
					// dep_sig = 0;
					// }else{
					// imgdep.setIcon(icon_true);
					// dep_sig = 1;
					// }
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if (txtdep.isEditable()) {
					String s = txtdep.getText().replace(" ", "");
					if (s.equals("")) {
						txtdep.setText("部门号不能为空");
						imgdep.setIcon(icon_false);
						dep_sig = 0;
					} else {
						imgdep.setIcon(icon_true);
						dep_sig = 1;
					}
				}
			}

		});

		txtphone.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if (txtphone.isEditable()) {
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
				if (txtpwd.isEditable()) {
					String s = txtpwd.getText().replace(" ", "");
					if (s.equals("")
							|| !Client.user_pwd.equals(txtpwd.getText())) {
						imgpwd.setIcon(icon_false);
						pwd_sig = 0;
					} else {
						pwd_sig = 1;
						imgpwd.setIcon(icon_true);
					}
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
				if (txtpwdagain.isEditable()) {
					String s = txtpwdagain.getText().replace(" ", "");
					if (txtpwdagain.getText().length() < 1) {
						imgpwdagain.setIcon(icon_false);
						pwd_again_sig = 0;
					} else {
						pwd_again_sig = 1;
						imgpwdagain.setIcon(icon_true);
					}
				}
			}
		});
	}

	public Update_User_Info_Dialog getUser_info_dialog() {
		return user_info_dialog;
	}

	public void setUser_info_dialog(Update_User_Info_Dialog user_info_dialog) {
		this.user_info_dialog = user_info_dialog;
	}

	public static void main(String[] args) {
		User u = new User();
		u.setU_dept("人力部||人力部一台柱||人力部||人力部一台柱");
		u.setU_name("五千元");
		u.setU_phone("10001");
		u.setU_password("123");
		new Update_User_Info_Dialog(u);
	}

}
