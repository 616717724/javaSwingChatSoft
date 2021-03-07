package com.ui.panel.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.dao.GroupChat;
import com.dao.User;
import com.socket.Client;
import com.ui.Group_ChatFrame;
import com.ui.ScreenCapture;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.dialog.Update_Group_Info_Dialog;
import com.ui.panel.list.Friend_panel;
import com.ui.panel.list.Group_panel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ui.setIcon.EmojJDialog;

import javax.swing.JSplitPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Group_chat_all_panel extends JPanel implements Txt_size {

	// private JTabbedPane tabbedPane;
	private JPanel all_panel;
	private JPanel chat_panel;
	private JPanel top_panel;
	private JLabel lbl_friend_icon;
	private JPanel friend_info_panel;
	public JLabel lbl_friend_name;
	private JLabel lbl_friend_nub;
	private JScrollPane center_panel;
	public JTextPane edit_receive, edit_send;
	public JLabel sig = new JLabel("");
	private JPanel foot_panel;
	private JPanel tool_panel;
	private JPanel tool_panel2;
	private JLabel btn_emoj;
	private JLabel btn_font;
	private JButton btn_print;
	private JButton btn_file;
	private JLabel btn_history;
	private JPanel send_panel;
	private JLabel btn_close;
	private JLabel btn_send;
	private JPanel group_panel;
	private JLabel lbl_all_nub;
	private JPanel panel_2;
	public JTextPane editorPane;

	private Friend_panel friend_panel_list;// 成员列表
	String name_icon = "群";// 用户名头像
	public String lbl_name_text = "群名";// 用户名
	public String lbl_user_id = "群账号";// 用户账号
	String notie = "暂无";// 群公告
	public String u_list = "";
	String u_main = "";
	int pepole_now = 0, pepole_all = 0;// 群人数

	public GroupChat group_chat;
	List<User> list_user;
	public StyledDocument doc;
	public SimpleAttributeSet my_attrib, fri_attrib, mesg_attrib;

	static JFrame frame;
	private JSplitPane splitPane;
	private JPanel history_panel;
	private JScrollPane scrollPane_1;
	private JTextPane history_JTextpanel;
	private JComboBox choice;
	private JButton button;
	private JButton btnNewButton;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		Group_chat_all_panel.frame = frame;
	}

	public Group_chat_all_panel(String lbl_user_id, String lbl_name_text) {
		// private Friend_panel friend_panel_list;//成员列表
		// String name_icon = "群";// 用户名头像
		// String lbl_name_text = "群名";// 用户名
		// String lbl_user_id = "群账号";// 用户账号
		// String notie="暂无";//群公告
		// int pepole_now=0,pepole_all=0;//群人数
		this.lbl_user_id = lbl_user_id;
		this.lbl_name_text = lbl_name_text;
		init();
		initListener();
	}

	public Group_chat_all_panel(List<User> list_user, String lbl_user_id,
			String lbl_name_text) {
		this.list_user = list_user;
		this.lbl_user_id = lbl_user_id;
		this.lbl_name_text = lbl_name_text;
		init();
		initListener();
		setStyle();
	}

	public Group_chat_all_panel(List<User> list_user, GroupChat group_chat) {
		this.list_user = list_user;
		this.lbl_user_id = group_chat.getID() + "";
		this.lbl_name_text = group_chat.getG_name();
		this.notie = group_chat.getG_notice();
		this.u_list = group_chat.getG_list();
		this.pepole_all = group_chat.getG_list().split(",").length + 1;
		u_main = group_chat.getG_main_id();
		this.group_chat = group_chat;
		init();
		initListener();
		setStyle();
	}

	public Group_chat_all_panel() {
		init();
		initListener();
	}

	@Override
	public void init() {
		setStyle();

		setLayout(new BorderLayout(0, 0));

		history_panel = new JPanel();
		// history_panel.setPreferredSize(new Dimension(50,100));
		// add(history_panel, BorderLayout.EAST);
		all_panel = new JPanel();
		// all_panel.setPreferredSize(new Dimension(700, 530));
		// add(all_panel,BorderLayout.WEST);
		all_panel.setLayout(new BorderLayout(0, 0));
		chat_panel = new JPanel();
		chat_panel.setLayout(new BorderLayout(0, 0));
		all_panel.add(chat_panel, BorderLayout.CENTER);

		top_panel = new JPanel();
		chat_panel.add(top_panel, BorderLayout.NORTH);
		top_panel.setBackground(Color.WHITE);
		top_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lbl_friend_icon = new JLabel(name_icon);
		lbl_friend_icon.setOpaque(true);
		lbl_friend_icon.setForeground(Color.WHITE);
		lbl_friend_icon.setFont(new Font("宋体", Font.PLAIN, 35));
		lbl_friend_icon.setBorder(new CompoundBorder(new EtchedBorder(),
				new LineBorder(Color.white)));
		lbl_friend_icon.setBackground(new Color(13, 196, 254));
		top_panel.add(lbl_friend_icon);

		friend_info_panel = new JPanel();
		friend_info_panel.setBackground(Color.WHITE);
		top_panel.add(friend_info_panel);
		friend_info_panel.setLayout(new GridLayout(0, 1, 0, 0));

		lbl_friend_name = new JLabel(lbl_name_text);
		lbl_friend_name.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_name);

		lbl_friend_nub = new JLabel(lbl_user_id);
		lbl_friend_nub.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_nub);

		button = new JButton("修改群信息");
		button.setVisible(false);
		btnNewButton = new JButton("退出该群");
		// btnNewButton.setVisible(false);
		if (u_main.equals(SecondFrame.lbl_user_id)) {
			button.setVisible(true);
			btnNewButton.setText("解散该群");
		}
		top_panel.add(button);

		top_panel.add(btnNewButton);

		center_panel = new JScrollPane();
		center_panel
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chat_panel.add(center_panel, BorderLayout.CENTER);
		// center_panel.setLayout(new BorderLayout(0, 0));

		edit_receive = new JTextPane();
		edit_receive.setEditable(false);
		loadJTextPanel(edit_receive);
		doc = edit_receive.getStyledDocument();
		center_panel.setViewportView(edit_receive);
		// center_panel.add(edit_receive, BorderLayout.CENTER);

		foot_panel = new JPanel();
		chat_panel.add(foot_panel, BorderLayout.SOUTH);
		foot_panel.setLayout(new BorderLayout(0, 0));

		tool_panel = new JPanel();
		foot_panel.add(tool_panel, BorderLayout.NORTH);
		tool_panel.setLayout(new BorderLayout(0, 0));

		tool_panel2 = new JPanel();
		tool_panel.add(tool_panel2, BorderLayout.WEST);

		btn_emoj = new JLabel("表情");
		btn_emoj.setIcon(new ImageIcon(Group_chat_all_panel.class
				.getResource("/com/resource/img/emoticon.png")));
		btn_emoj.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point loc = getLocationOnScreen();
				if ("".equals(sig.getText())) {
					new EmojJDialog(Group_chat_all_panel.this, loc.x,
							loc.y + 50, 300, 300);
				}
			}
		});
		tool_panel2.add(btn_emoj);

		btn_font = new JLabel("截图");
		btn_font.setIcon(new ImageIcon(Group_chat_all_panel.class.getResource("/com/resource/img/screenCapture.png")));
//		btn_font.setVisible(false);
		tool_panel2.add(btn_font);

		// btn_print = new JButton("截图");
		btn_print = new JButton("邀请成员");
		btn_print.setIcon(new ImageIcon(Group_chat_all_panel.class
				.getResource("/com/resource/img/send.png")));
		// btn_print.setVisible(false);
		tool_panel2.add(btn_print);

		btn_file = new JButton("文件");
		btn_file.setVisible(false);
		tool_panel2.add(btn_file);

		btn_history = new JLabel("");
		btn_history.setIcon(new ImageIcon(Group_chat_all_panel.class
				.getResource("/com/resource/img/info_button.png")));

		tool_panel.add(btn_history, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		foot_panel.add(scrollPane, BorderLayout.CENTER);

		edit_send = new JTextPane();
		edit_send.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // 判断按下的键是否是回车键
				{
					send_mesg();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					close_panel();
				}
			}
		});
		edit_send.setPreferredSize(new Dimension(200, 100));
		scrollPane.setViewportView(edit_send);

		send_panel = new JPanel();
		foot_panel.add(send_panel, BorderLayout.SOUTH);
		send_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btn_close = new JLabel("");
		btn_close.setIcon(new ImageIcon(Group_chat_all_panel.class
				.getResource("/com/resource/img/close2_button.png")));

		send_panel.add(btn_close);

		btn_send = new JLabel("");
		btn_send.setIcon(new ImageIcon(Group_chat_all_panel.class
				.getResource("/com/resource/img/send_button.png")));

		send_panel.add(btn_send);

		group_panel = new JPanel();
		all_panel.add(group_panel, BorderLayout.EAST);
		group_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane panel_1 = new JScrollPane();
		panel_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		group_panel.add(panel_1, BorderLayout.NORTH);
		// panel_1.setLayout(new BorderLayout(0, 0));

		editorPane = new JTextPane();
		editorPane.setText("公告：\r\n" + notie);
		editorPane.setEditable(false);
		editorPane.setPreferredSize(new Dimension(200, 100));
		// panel_1.add(editorPane, BorderLayout.NORTH);
		panel_1.setViewportView(editorPane);

		panel_2 = new JPanel();
		group_panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		// lbl_all_nub = new JLabel("成员列表("+pepole_now+"/"+pepole_all+")");
		lbl_all_nub = new JLabel("成员列表:" + pepole_all);
		panel_2.add(lbl_all_nub, BorderLayout.NORTH);

		friend_panel_list = new Friend_panel(list_user);
		friend_panel_list.setGroup_Main_id(u_main);
		friend_panel_list.setGroup_id(lbl_user_id);
		friend_panel_list.setPreferredSize(new Dimension(25, 100));
		panel_2.add(friend_panel_list);
		friend_panel_list.jtree.expandRow(0);
		my_attrib = new SimpleAttributeSet();
		fri_attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(my_attrib, Color.green);
		StyleConstants.setForeground(fri_attrib, Color.blue);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, all_panel,
				history_panel);
		splitPane.setBackground(Color.WHITE);
		splitPane.setDividerLocation(0.2);
		history_panel.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		history_JTextpanel = new JTextPane();
		history_JTextpanel.setPreferredSize(new Dimension(100, 100));
		history_panel.setVisible(false);
		scrollPane_1.setViewportView(history_JTextpanel);
		choice = new JComboBox();
		history_panel.add(choice, BorderLayout.SOUTH);
		history_panel.add(scrollPane_1, BorderLayout.CENTER);
		loadJTextPanel(history_JTextpanel);
		setChoice();
		add(splitPane, BorderLayout.CENTER);
	}

	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		btn_send.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				send_mesg();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn_send.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/send_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_send.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/send_button.png")));
			}
		});

		btn_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				close_panel();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn_close.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/close2_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_close.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/close2_button.png")));
			}
		});

		btn_history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!history_panel.isVisible()) {
					history_panel.setVisible(true);
					splitPane.setDividerLocation(0.6);
				} else {
					history_panel.setVisible(false);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_history.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/info_button.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn_history.setIcon(new ImageIcon(Group_chat_all_panel.class
						.getResource("/com/resource/img/info_active.png")));
			}
		});
		choice.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) choice.getSelectedItem();
					System.out.println(s);
					loadJTextPanel(history_JTextpanel, s);
				}
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Update_Group_Info_Dialog(group_chat,
						Group_chat_all_panel.this);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("退出按钮");
				if (btnNewButton.getText().equals("解散该群")) {
					String mesg = "type:4,group_clean||" + lbl_user_id + "||"
							+ group_chat.getG_list();
					try {
						int tn = JOptionPane
								.showConfirmDialog(null, "确定要解散该群吗？", "解散群提示",
										JOptionPane.YES_NO_OPTION);
						if (tn == JOptionPane.YES_OPTION) {
							Client.dato.writeUTF(mesg);
							Client.dato.flush();
							JOptionPane.showMessageDialog(new JLabel(""),
									"解散成功");
						}
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						JOptionPane.showMessageDialog(new JLabel(""), "解散失败");
						e1.printStackTrace();
					}
				} else {
					String mesg = "type:4,group_out||" + lbl_user_id + "||"
							+ SecondFrame.lbl_user_id + "||"
							+ SecondFrame.lbl_name_text + "||"
							+ group_chat.getG_list();
					System.out.println("完整消息:" + mesg);
					try {
						int tn = JOptionPane
								.showConfirmDialog(null, "确定要退出该群吗？", "退出群提示",
										JOptionPane.YES_NO_OPTION);
						if (tn == JOptionPane.YES_OPTION) {
							Client.dato.writeUTF(mesg);
							Client.dato.flush();
							JOptionPane.showMessageDialog(new JLabel(""),
									"退出成功");
							List<User> temp_user=Group_panel.map__group_chat_list.get(lbl_user_id);
							for(User tu:temp_user){
								if(tu.getU_phone().equals(SecondFrame.lbl_user_id)){
									temp_user.remove(tu);
									break;
								}
							}
						}
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						JOptionPane.showMessageDialog(new JLabel(""), "退出失败");
						e1.printStackTrace();
					}
				}
			}
		});

		btn_print.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String u_id = JOptionPane.showInputDialog("请输入要邀请的成员手机号");
				String u_name = "";
				boolean ishave = false;
				for (User tu : list_user) {
					if (tu.getU_phone().equals(u_id)) {
						ishave = true;
					}
				}
				if (!ishave) {
					for (User tu : Client.list_user) {
						if (tu.getU_phone().equals(u_id)) {
							u_name = tu.getU_name();
						}
					}
					if (!u_name.equals("")) {
						// friend_panel_list.Add_User(u_id, u_name);
						String mesg = "type:4,group_into||" + lbl_user_id
								+ "||" + u_id + "||" + u_name + "||"
								+ lbl_name_text +"||"+ group_chat.getG_list() + "||"+group_chat.getG_user_name_list()+"||"+group_chat.getG_main_id();
						try {
							Client.dato.writeUTF(mesg);
							Client.dato.flush();
//							Add_User(u_id,u_name);
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "该用户不存在", "该用户不存在",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "该用户已存在", "该用户已存在",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btn_font.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ScreenCapture().startATime(Group_chat_all_panel.this);
			}
		});
	}

	public void Remove_User(String u_phone) {
		friend_panel_list.Remove_User(u_phone);
		String g_list=group_chat.getG_list();
		g_list=g_list.replace(","+u_phone, "");
		group_chat.setG_list(g_list);
	}

	public void Add_User(String u_id, String u_name) {
		friend_panel_list.Add_User(u_id, u_name, 1);
		String temp = group_chat.getG_list();
		group_chat.setG_list(temp + "," + u_id);
		temp = group_chat.getG_user_name_list();
		group_chat.setG_user_name_list(temp + "," + u_name);
	}

	public void setText(String u, String test) {
		// String s=this.edit_receive.getText();
		// this.edit_receive.setText(s+test+"\n");
		try {
			edit_receive.setCaretPosition(edit_receive.getStyledDocument()
					.getLength());
			doc.insertString(doc.getLength(), u, my_attrib);
			doc.insertString(doc.getLength(), test + "\n", mesg_attrib);
		} catch (BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// this.edit_receive.setCaretPosition(this.edit_receive.getText().length());
	}

	public void setText(String test, Icon img) {
		try {
			System.out.println("test:" + test);
			if (null != img) {
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				doc.insertString(doc.getLength(), test, fri_attrib);
				Thread.sleep(100);
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
//				doc.insertString(doc.getLength(), "\n", null);
				edit_receive.insertIcon(img);
				doc.insertString(doc.getLength(), "\n", null);
				Client.img = null;
			} else {
				String[] mesg = test.split("\n");
				if (mesg.length < 2) {
					doc.insertString(doc.getLength(), mesg[0] + "\n",
							mesg_attrib);
				} else {
					doc.insertString(doc.getLength(), mesg[0] + "\n",
							fri_attrib);
					doc.insertString(doc.getLength(), mesg[1] + "\n",
							mesg_attrib);
				}
				// doc.insertString(doc.getLength(),"\n", mesg_attrib);
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
			}
		} catch (BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void saveJTextPanel() {
		Date d = new Date();
		String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id
				+ "group" + "&" + time + ".data";
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File("./data" + url);
		try {
			ObjectOutputStream obj_out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			System.out.println("正在写入" + lbl_user_id + "群的聊天记录");
			obj_out.writeObject(edit_receive.getStyledDocument());
			obj_out.flush();
			obj_out.close();
			System.out.println("聊天记录" + lbl_user_id + "群的写入完成");
		} catch (FileNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public void loadJTextPanel(JTextPane jt) {
		Date d = new Date();
		String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id
				+ "group" + "&" + time + ".data";
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File("./data" + url);
		if (file.exists()) {
			ObjectInputStream obj_in;
			try {
				obj_in = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(file)));
				System.out.println("开始读取");
				StyledDocument styledo = (StyledDocument) obj_in.readObject();
				jt.setStyledDocument(styledo);
				// Document tmpdo=jt.getDocument();
				// tmpdo.insertString(tmpdo.getLength(),"\n",null);
				obj_in.close();
				System.out.println("读取完毕");
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件找不到");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件读取失败");
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件已损坏");
			}
		}
	}

	public void loadJTextPanel(JTextPane jt, String time) {
		// Date d = new Date();
		// time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id
				+ "group" + "&" + time + ".data";
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		System.out.println("url:" + url);
		File file = new File("./data" + url);
		if (dir.exists()) {
			ObjectInputStream obj_in;
			try {
				obj_in = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(file)));
				System.out.println("开始读取");
				StyledDocument mydo = (StyledDocument) obj_in.readObject();
				jt.setStyledDocument(mydo);
				// jt.updateUI();
				// Document tmpdo=jt.getDocument();
				// tmpdo.insertString(tmpdo.getLength(),"\n",null);
				obj_in.close();
				System.out.println("读取完毕");
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件找不到");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件读取失败");
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "错误:文件已损坏");
			}
		}
	}

	public void setChoice() {
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File[] files = dir.listFiles();
		for (File file2 : files) {
			if (file2.isFile()) {
				// System.out.println("文件:" + file2.getName());
				String[] fn = file2.getName().split("&");
				if (fn[0].equals(SecondFrame.lbl_user_id)
						&& fn[1].equals(lbl_user_id + "group")) {
					choice.addItem(fn[2].substring(0, fn[2].length() - 5));
				}
			}
		}
		if (0 < choice.getItemCount()) {
			choice.setSelectedIndex(choice.getItemCount() - 1);
		}
		// choice.addItem("2017年10月14日15:15:53");
	}

	public String getName_icon() {
		return name_icon;
	}

	public void setName_icon(String name_icon) {
		this.name_icon = name_icon;
	}

	public String getLbl_name_text() {
		return lbl_name_text;
	}

	public void setLbl_name_text(String lbl_name_text) {
		this.lbl_name_text = lbl_name_text;
	}

	public String getLbl_user_id() {
		return lbl_user_id;
	}

	public void setLbl_user_id(String lbl_user_id) {
		this.lbl_user_id = lbl_user_id;
	}

	public int getPepole_now() {
		return pepole_now;
	}

	public void setPepole_now(int pepole_now) {
		this.pepole_now = pepole_now;
	}

	public int getPepole_all() {
		return pepole_all;
	}

	public void setPepole_all(int pepole_all) {
		this.pepole_all = pepole_all;
	}

	public String getNotie() {
		return notie;
	}

	public void setNotie(String notie) {
		this.notie = notie;
	}

	public Friend_panel getFriend_panel() {
		return friend_panel_list;
	}

	public void setFriend_panel(Friend_panel friend_panel_list) {
		this.friend_panel_list = friend_panel_list;
	}

	void close_panel() {
		// TODO 自动生成的方法存根
		int tempnub = 0;
		for (String tmp : Group_panel.table_id) {
			if (tmp.equals(lbl_user_id)) {
				System.out.println("remove:" + tempnub);
				// System.out.println("size"+Friend_panel.table_id.size());
				Group_panel.table_id.remove(tempnub);// 移除list
				Group_ChatFrame.tabbedPane.remove(tempnub);// 移除table
				if (0 == Group_panel.table_id.size()) {
					frame.dispose();
				}
				saveJTextPanel();
				break;
			}
			tempnub++;
			// System.out.println(tempnub);
		}
		// System.exit(0);
	}

	void send_mesg() {
		// TODO 自动生成的方法存根
		String text = edit_send.getText();
		if (!"".equals(text)) {
			try {
				// 当前用户号+当前用户名+群号ID+群名+群用户列表+文本消息
				String mesg = "type:7," + SecondFrame.lbl_user_id + "||"
						+ SecondFrame.lbl_name_text + "||" + lbl_user_id + "||"
						+ lbl_name_text + "||" + group_chat.getG_list() + "||" + text;
				// Client.bufw.write(mesg);
				// Client.bufw.newLine();
				// Client.bufw.flush();
				System.out.println("群聊完整消息:" + mesg);
				Client.dato.writeUTF(mesg);
				Client.dato.flush();
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				String time = sdf.format(d);
				String u = SecondFrame.lbl_name_text + "  " + time + "\n";
				String[] message = mesg.split("\\|\\|");
				// System.out.println(message[0]+"发来的消息:"+mesg);
				mesg = message[5];
				setText(u, mesg);
				edit_send.setText("");
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
				System.err.println("消息发送失败");
			}
		} else {
			JOptionPane.showMessageDialog(new JLabel(""), "不能发送空的消息哦!");
		}
	}
}
