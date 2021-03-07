package com.ui.test.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
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
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.socket.Client;
import com.ui.Friends_ChatFrame;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.panel.list.Friend_panel;
import com.ui.setIcon.EmojJDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSplitPane;
import javax.swing.JComboBox;

import java.awt.Choice;
import javax.swing.ImageIcon;

public class Friend_chat_all_panel extends JFrame implements Txt_size {
	JPanel center_panel, top_panel, friend_info_panel, foot_panel, tool_panel;
	JPanel tool_panel2, send_panel;
	JScrollPane center_panel1, center_panel2;
	JLabel lbl_friend_icon, lbl_friend_name, lbl_friend_nub;
	public JTextPane edit_receive, edit_send;
	public StyledDocument doc;
	public SimpleAttributeSet my_attrib, fri_attrib;
	JLabel btn_emoj, btn_font, btn_print, btn_file, btn_history, btn_close,
			btn_send;
	public JLabel sig = new JLabel("");
	EmojJDialog emoj;
	String name_icon = "李";// 用户名头像
	public String lbl_name_text = "用户名";// 用户名
	public String lbl_user_id = "用户账号";// 用户账号
	static JFrame frame;
	private JPanel history_panel;
	private JTextPane history_JTextpanel;
	private JScrollPane scrollPane;
	private JSplitPane splitPane;
	private JComboBox choice;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		Friend_chat_all_panel.frame = frame;
	}

	public Friend_chat_all_panel(String name_icon, String lbl_name_text,
			String lbl_user_id) {
		this.name_icon = name_icon;
		this.lbl_name_text = lbl_name_text;
		this.lbl_user_id = lbl_user_id;
		init();
		initListener();
	}

	public Friend_chat_all_panel() {
		init();
		initListener();
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根

		setStyle();

		setLayout(new BorderLayout(0, 0));
		// center_panel.setLayout(new BorderLayout(0, 0));

		my_attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(my_attrib, Color.green);
		fri_attrib = new SimpleAttributeSet();

		center_panel = new JPanel();
		// add(center_panel, BorderLayout.CENTER);
		center_panel.setLayout(new BorderLayout(0, 0));
		top_panel = new JPanel();
		center_panel.add(top_panel, BorderLayout.NORTH);
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
		lbl_friend_name.setOpaque(true);
		lbl_friend_name.setBackground(Color.WHITE);
		lbl_friend_name.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_name);

		lbl_friend_nub = new JLabel(lbl_user_id);
		lbl_friend_nub.setOpaque(true);
		lbl_friend_nub.setBackground(Color.WHITE);
		lbl_friend_nub.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_nub);

		center_panel1 = new JScrollPane();
		center_panel.add(center_panel1, BorderLayout.CENTER);
		center_panel1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		edit_receive = new JTextPane();
		edit_receive.setEditable(false);
		loadJTextPanel(edit_receive);
		doc = edit_receive.getStyledDocument();
		// center_panel.add(edit_receive, BorderLayout.CENTER);
		center_panel1.setViewportView(edit_receive);

		foot_panel = new JPanel();
		center_panel.add(foot_panel, BorderLayout.SOUTH);
		foot_panel.setLayout(new BorderLayout(0, 0));

		tool_panel = new JPanel();
		tool_panel.setBackground(Color.WHITE);
		foot_panel.add(tool_panel, BorderLayout.NORTH);
		tool_panel.setLayout(new BorderLayout(0, 0));

		tool_panel2 = new JPanel();
		tool_panel2.setBackground(Color.WHITE);
		tool_panel.add(tool_panel2, BorderLayout.WEST);

		btn_emoj = new JLabel("表情");
		btn_emoj.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/emoticon.png")));
		// btn_emoj.setBorderPainted(false);
		// btn_emoj.setContentAreaFilled(false);
		tool_panel2.add(btn_emoj);

		btn_font = new JLabel("文字");
		btn_font.setVisible(false);
		// btn_font.setMargin(new Insets(0,0,0,0));
		// btn_font.setBorder(BorderFactory.createRaisedBevelBorder());
		// btn_font.setBorder(BorderFactory.createLoweredBevelBorder());
		tool_panel2.add(btn_font);

		btn_print = new JLabel("截图");
		btn_print.setVisible(false);
		tool_panel2.add(btn_print);

		btn_file = new JLabel("文件");
		btn_file.setVisible(false);
		tool_panel2.add(btn_file);

		btn_history = new JLabel("");
		btn_history.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/info_button.png")));
		tool_panel.add(btn_history, BorderLayout.EAST);

		edit_send = new JTextPane();
		edit_send.setPreferredSize(new Dimension(200, 100));
		// doc=edit_send.getStyledDocument();
		center_panel2 = new JScrollPane();
		center_panel2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		foot_panel.add(center_panel2);
		center_panel2.setViewportView(edit_send);

		send_panel = new JPanel();
		send_panel.setBackground(Color.WHITE);
		foot_panel.add(send_panel, BorderLayout.SOUTH);
		send_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btn_close = new JLabel("");
		btn_close.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/close2_button.png")));
		btn_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_close.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/close2_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_close.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/close2_button.png")));
			}
		});
		btn_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// frame.dispose();
				int tempnub = 0;
				for (String tmp : Friend_panel.table_id) {
					if (tmp.equals(lbl_user_id)) {
						// System.out.println("remove:" + tempnub);
						// System.out.println("size"+Friend_panel.table_id.size());
						Friend_panel.table_id.remove(tempnub);// 移除list
						Friends_ChatFrame.tabbedPane.remove(tempnub);// 移除table
						if (0 == Friend_panel.table_id.size()) {
							frame.dispose();
						}
						saveJTextPanel();
						break;
					}
					tempnub++;
					// System.out.println(tempnub);
				}
				// System.out.println(lbl_user_id);
			}
		});
		send_panel.add(btn_close);

		btn_send = new JLabel("");
		btn_send.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/send_button.png")));
		btn_send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_send.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/send_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_send.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/send_button.png")));
			}
		});
		btn_send.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// System.out.println(my_name+"发送给:"+lbl_user_id);
				String text = edit_send.getText();
				if (!"".equals(text)) {
					try {
						// 当前用户号+当前用户名+目标用户号+目标用户名+文本消息
						String mesg = "type:6," + SecondFrame.lbl_user_id
								+ "||" + SecondFrame.lbl_name_text + "||"
								+ lbl_user_id + "||" + lbl_name_text + "||"
								+ text;
						System.out.println("完整消息:" + mesg);
						Client.dato.writeUTF(mesg);
						Client.dato.flush();
						DateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date d = new Date();
						String time = sdf.format(d);
						String u = SecondFrame.lbl_name_text + "  " + time
								+ "\n";
						String[] message = mesg.split("\\|\\|");
						// System.out.println(message[0]+"发来的消息:"+mesg);
						mesg = message[4];
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
		});
		send_panel.add(btn_send);

		history_panel = new JPanel();
		// history_panel.setVisible(false);
		// add(history_panel, BorderLayout.EAST);
		history_panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		history_panel.add(scrollPane, BorderLayout.CENTER);

		history_JTextpanel = new JTextPane();
		history_panel.setVisible(false);
		history_JTextpanel.setEditable(false);
		scrollPane.setViewportView(history_JTextpanel);
		loadJTextPanel(history_JTextpanel);
		// history_panel.add(history_JTextpanel, BorderLayout.CENTER);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center_panel,
				history_panel);
		// splitPane.setMinimumSize(50);
		choice = new JComboBox();
		// choice.setLightWeightPopupEnabled(false);
		history_panel.add(choice, BorderLayout.SOUTH);
		setChoice();
		add(splitPane, BorderLayout.CENTER);
	}

	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
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
			public void mouseEntered(MouseEvent e) {
				btn_history.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/info_active.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_history.setIcon(new ImageIcon(Friend_chat_all_panel.class
						.getResource("/com/resource/img/info_button.png")));
			}
		});

		choice.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				// System.out.println("asdasdasdasd");
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) choice.getSelectedItem();
					System.out.println(s);
					loadJTextPanel(history_JTextpanel, s);
					// loadJTextPanel(history_JTextpanel,"2017年10月13日 星期五");
					// loadJTextPanel(edit_receive);
				}
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
		super.paint(g);
		edit_send.requestFocus();
	}

	public void setText(String u, String test) {
		// String s=this.edit_receive.getText();
		// this.edit_receive.setText(s+test+"\n");
		try {
			doc.insertString(doc.getLength(), u, my_attrib);
			doc.insertString(doc.getLength(), test + "\n", fri_attrib);
			edit_receive.setCaretPosition(edit_receive.getStyledDocument()
					.getLength());
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
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				edit_receive.insertIcon(img);
				// doc.insertString(doc.getLength(),"\n",fri_attrib);
				// doc.insertString(doc.getLength(),"\n",null);
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				doc.insertString(doc.getLength(), "\n", null);
				doc.insertString(doc.getLength(), "\n", null);
				doc.insertString(doc.getLength(), "\n", null);
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				Client.img = null;
			} else {
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				doc.insertString(doc.getLength(), test + "\n", fri_attrib);
			}
			edit_receive.setCaretPosition(edit_receive.getStyledDocument()
					.getLength());
		} catch (BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void saveJTextPanel() {
		Date d = new Date();
		String time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id + "&"
				+ time + ".data";
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File("./data" + url);
		try {
			ObjectOutputStream obj_out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			System.out.println("正在写入" + lbl_user_id + "的聊天记录");
			obj_out.writeObject(edit_receive.getStyledDocument());
			obj_out.flush();
			obj_out.close();
			System.out.println("聊天记录" + lbl_user_id + "的写入完成");
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
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id + "&"
				+ time + ".data";
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
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	public void loadJTextPanel(JTextPane jt, String time) {
		// Date d = new Date();
		// time = DateFormat.getDateInstance(DateFormat.FULL).format(d);
		String url = "/" + SecondFrame.lbl_user_id + "&" + lbl_user_id + "&"
				+ time + ".data";
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
//				if (fn[0].equals(SecondFrame.lbl_user_id)
//						&& fn[1].equals(lbl_user_id)) {
					choice.addItem(fn[2].substring(0, fn[2].length() - 5));
//				}
			}
		}
		if (0 < choice.getItemCount()) {
			choice.setSelectedIndex(choice.getItemCount() - 1);
		}
		// choice.addItem("2017年10月14日15:15:53");
	}

	public static void main(String[] args) {
		new Friend_chat_all_panel().setVisible(true);
	}
}
