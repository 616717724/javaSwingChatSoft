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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.dao.GroupChat;
import com.dao.User;
import com.matcher.MyMatcher;
import com.socket.Client;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.panel.BgPanel;
import com.ui.panel.chat.Group_chat_all_panel;
import com.ui.setIcon.SetIcon;

public class Update_Group_Info_Dialog extends JDialog implements Txt_size{
	private int windx;
	private int windy;
	private int width;
	private int height;
	public JPanel reg_panel;
	JLabel lblname;
	private JLabel lblphone;
	private JTextField txtg_name;
	private JTextField txtg_id;
	private JLabel btnreg;
	private JLabel imgname;
	private JLabel imgphone;
	private JLabel btncancel;
	ImageIcon icon_false;
	ImageIcon icon_true;
	int name_sig = 0, phone_sig = 0, dep_sig=0,pwd_sig = 0, pwd_again_sig = 0;// 注册标记
	int load = 0;
	private JTextPane txtnotice;
	
	GroupChat group_chat;
	Group_chat_all_panel group_char_all_panel;
//	Update_Group_Info_Dialog user_info_dialog=null;

	public Update_Group_Info_Dialog() {
		init();
		initListener();
		this.setVisible(true);
	}
	
	public Update_Group_Info_Dialog(GroupChat group_chat,Group_chat_all_panel group_char_all_panel) {
		this.group_chat=group_chat;
		this.group_char_all_panel=group_char_all_panel;
		init();
		initListener();
//		set
		setModal(true);
//		requestFocus();
		this.setVisible(true);
	}

	public void init() {
		// this.setUndecorated(true);
//		this.setAlwaysOnTop(true);
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
		BgPanel p=new BgPanel("bg5.png");
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

				lblname = new JLabel("群名:");
				lblname.setHorizontalAlignment(SwingConstants.CENTER);
				lblname.setBounds(31, 31, 75, 35);
				p.add(lblname);

				lblphone = new JLabel("群号:");
				lblphone.setHorizontalAlignment(SwingConstants.CENTER);
				lblphone.setBounds(31, 76, 75, 35);
				p.add(lblphone);

				txtg_name = new JTextField();
				txtg_name.setEditable(false);
				txtg_name.setText(group_chat.getG_name());
				// txtname.setToolTipText("dasdasdasd");
				txtg_name.setBounds(116, 31, txtwitdh, txtheight);
				p.add(txtg_name);
				txtg_name.requestFocus();

				txtg_id = new JTextField(group_chat.getID());
				txtg_id.setEditable(false);
//				txtg_id.setText(user.getU_phone());
				txtg_id.setBounds(116, 76, txtwitdh, txtheight);
				p.add(txtg_id);
				txtg_id.setColumns(10);

				btnreg = new JLabel("修改信息");
				btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/update_button.png")));
				btnreg.setHorizontalAlignment(SwingConstants.CENTER);
				btnreg.setToolTipText("修改");
				btnreg.setBounds(116, 378, 111, 50);
				p.add(btnreg);

				imgname = new JLabel("");
				// imgname.setIcon(icon_true);
				imgname.setBounds(326, 31, 58, 35);
				p.add(imgname);

				imgphone = new JLabel("");
				// imgphone.setIcon(icon_false);
				imgphone.setBounds(326, 75, 58, 35);
				p.add(imgphone);

				btncancel = new JLabel("");
				btncancel.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/canel_button.png")));
				btncancel.setHorizontalAlignment(SwingConstants.CENTER);
				btncancel.setToolTipText("取消");
				btncancel.setBounds(225, 378, 100, 50);
				p.add(btncancel);
				
				JLabel label_2 = new JLabel("公告:");
				label_2.setHorizontalAlignment(SwingConstants.CENTER);
				label_2.setBounds(31, 133, 75, 35);
				p.add(label_2);
				
				txtnotice = new JTextPane();
				txtnotice.setText(group_chat.getG_notice());
				txtnotice.setEditable(false);
				txtnotice.setBounds(116, 133, 200, 235);
				p.add(txtnotice);
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		btnreg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(btnreg.getText().equals("")){
					if (1 == name_sig) {
						String g_name=txtg_name.getText();
						String id=txtg_id.getText();
						String u_notice=txtnotice.getText();
//						String u_password=txtpwd.getText();
//						user_info_dialog=null;
						// 当前用户号+当前用户名+目标用户号+目标用户名+文本消息
						String mesg = "type:4,group_info||" + id+ "||" + g_name + "||"+ u_notice+"||"+group_chat.getG_list();
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
						group_char_all_panel.editorPane.setText(u_notice);
						group_char_all_panel.lbl_friend_name.setText(g_name);
//						SecondFrame.lbl_user_id=u_phone;
//						SecondFrame.lbl_usericon.setText(u_name.substring(0,1));
//						SecondFrame.lbl_username.setText(u_name);
//						SecondFrame.lbl_usernumb.setText(u_phone);
						JOptionPane.showMessageDialog(new JLabel(""),
								"修改成功");
						dispose();
					} else {
						JOptionPane.showMessageDialog(new JLabel(""), "信息不正确");
					}
				}
				if(btnreg.getText().equals("修改信息")){
					btnreg.setText("");
					btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/confirm_button.png")));
//					txtphone.setEditable(true);
					txtg_name.setEditable(true);
					txtnotice.setEditable(true);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btnreg.getText().equals("修改信息")){
				btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/update_active.png")));
				}else{
				btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/confirm_active.png")));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(btnreg.getText().equals("修改信息")){
					btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/update_button.png")));
					}else{
					btnreg.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/confirm_button.png")));
					}
			}
		});

		btncancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				FristFrame.f.change_panel("登录");
//				user_info_dialog=null;
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/canel_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btncancel.setIcon(new ImageIcon(Update_Group_Info_Dialog.class.getResource("/com/resource/img/canel_button.png")));
			}
		});

		txtg_name.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(txtg_name.isEditable()){
					String s = txtg_name.getText().replace(" ", "");
					if (s.equals("")) {
						txtg_name.setText("中英文不超过20个字节");
					}	
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}
		});
		txtg_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				if (txtg_name.getText().equals("中英文不超过20个字节")) {
					txtg_name.setText("");
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO 自动生成的方法存根
				if (txtg_name.getText().equals("中英文不超过20个字节")) {
					txtg_name.setText("");
				}
			}
		});
		txtg_name.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(txtg_name.isEditable()){
					String s = txtg_name.getText().replace(" ", "");
					if (s.length()>0&&s.length() <= 20
							&& !txtg_name.getText().equals("中英文不超过20个字节")) {
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
		

		txtg_id.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(txtg_id.isEditable()){
					String s = txtg_id.getText().replace(" ", "");
					if (s.equals("")) {
						txtg_id.setText("请输入手机号");
					}
					String regex = "^[1]+[3,5]+\\d{9}$";
					if (MyMatcher.match(regex, txtg_id.getText())) {
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
				if (txtg_id.getText().equals("请输入手机号")) {
					txtg_id.setText("");
				}
			}
		});
	}
	

	public static void main(String[] args) {
		GroupChat groupchat=new GroupChat();
		groupchat.setID("120321");
		groupchat.setG_name("聊提提踢踢提提踢踢");
		groupchat.setG_notice("阿萨德就爱了快速的冷空气忘记了肯德基暗恋谁看得见啊时机到来矿务局的冷空气家玩了的空间奥斯卡了记录框自己瞎吹，米奇网");
		new Update_Group_Info_Dialog(groupchat,null);
	}

}
