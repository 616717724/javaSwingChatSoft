package com.ui.panel.chat;

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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
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
import com.ui.ScreenCapture;
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
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Friend_chat_all_panel extends JPanel implements Txt_size {
	JPanel center_panel, top_panel, friend_info_panel, foot_panel, tool_panel;
	JPanel tool_panel2, send_panel;
	JScrollPane center_panel1, center_panel2;
	JLabel lbl_friend_icon, lbl_friend_name, lbl_friend_nub;
	public JTextPane edit_receive, edit_send;
	public StyledDocument doc;
	public SimpleAttributeSet my_attrib, fri_attrib, mesg_attrib;
	public JLabel btn_emoj, btn_font, btn_print, btn_file, btn_history,
			btn_close, btn_send;
	public JLabel sig = new JLabel("");
	EmojJDialog emoj;
	String name_icon = "???";// ???????????????
	public String lbl_name_text = "?????????";// ?????????
	public String lbl_user_id = "????????????";// ????????????
	static JFrame frame;
	private JPanel history_panel;
	private JTextPane history_JTextpanel;
	private JScrollPane scrollPane;
	private JSplitPane splitPane;
	private JComboBox choice;
	private int num = 0;
	private JPanel center_panel0;
	private JPanel center_panel3;
	public JProgressBar progressBar;
	private JLabel lbl_cancel;
	private JLabel lbl_file_name;

	public static File file;
	public static String path;
	private JTabbedPane tabbedPane;

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
		// TODO ???????????????????????????

		setStyle();

		setLayout(new BorderLayout(0, 0));
		// center_panel.setLayout(new BorderLayout(0, 0));
		

		my_attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(my_attrib, Color.green);
		fri_attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(fri_attrib, Color.blue);

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
		lbl_friend_icon.setFont(new Font("??????", Font.PLAIN, 35));
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

		btn_emoj = new JLabel("??????");
		btn_emoj.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/emoticon.png")));
		// btn_emoj.setBorderPainted(false);
		// btn_emoj.setContentAreaFilled(false);
		btn_emoj.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point loc = getLocationOnScreen();
				if ("".equals(sig.getText())) {
					emoj = new EmojJDialog(Friend_chat_all_panel.this, loc.x,
							loc.y + 50, 300, 300);
				} else {

				}
			}
		});
		tool_panel2.add(btn_emoj);

		btn_font = new JLabel("??????");
		btn_font.setVisible(false);
		// btn_font.setMargin(new Insets(0,0,0,0));
		// btn_font.setBorder(BorderFactory.createRaisedBevelBorder());
		// btn_font.setBorder(BorderFactory.createLoweredBevelBorder());
		tool_panel2.add(btn_font);

		btn_print = new JLabel("??????");
		btn_print.setIcon(new ImageIcon(Friend_chat_all_panel.class.getResource("/com/resource/img/screenCapture.png")));
		// btn_print.setVisible(false);
		tool_panel2.add(btn_print);

		btn_file = new JLabel("??????");
		btn_file.setIcon(new ImageIcon(Friend_chat_all_panel.class
				.getResource("/com/resource/img/send.png")));
		// btn_file.setVisible(false);
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
				if (!btn_file.isEnabled()) {
					int tn = JOptionPane.showConfirmDialog(null,
							"???????????????????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
					if (tn == JOptionPane.YES_OPTION) {
						close_panel();
					}
				} else {
					close_panel();
				}
				// System.out.println("lbl_user_id:"+lbl_user_id);
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
		edit_send.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // ????????????????????????????????????
				{
					send_mesg();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					close_panel();
				}
			}
		});
		btn_send.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// System.out.println(my_name+"?????????:"+lbl_user_id);
				send_mesg();
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
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.NORTH);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center_panel,
				tabbedPane);
		add(splitPane, BorderLayout.CENTER);

		center_panel0 = new JPanel();
		center_panel.add(center_panel0, BorderLayout.CENTER);
		center_panel0.setLayout(new BorderLayout(0, 0));

		center_panel1 = new JScrollPane();
		center_panel0.add(center_panel1);
		center_panel1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		edit_receive = new JTextPane();
		edit_receive.setEditable(false);
		loadJTextPanel(edit_receive);
		doc = edit_receive.getStyledDocument();
		// center_panel.add(edit_receive, BorderLayout.CENTER);
		center_panel1.setViewportView(edit_receive);

		center_panel3 = new JPanel();
		center_panel3.setVisible(false);
		center_panel3.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(100, 50));
		center_panel3.add(progressBar, BorderLayout.NORTH);

		lbl_file_name = new JLabel("?????????");
		lbl_file_name.setPreferredSize(new Dimension(100, 50));
		center_panel3.add(lbl_file_name, BorderLayout.CENTER);

		lbl_cancel = new JLabel("??????");
		lbl_cancel.setVisible(false);
		lbl_cancel.setPreferredSize(new Dimension(100, 50));
		center_panel3.add(lbl_cancel, BorderLayout.SOUTH);
		// splitPane.setMinimumSize(50);
		choice = new JComboBox();
		// choice.setLightWeightPopupEnabled(false);
		history_panel.add(choice, BorderLayout.SOUTH);
		tabbedPane.setVisible(false);
		setChoice();
		tabbedPane.addTab("????????????", null, history_panel);
		tabbedPane.addTab("????????????", null, center_panel3);
	}

	private void setStyle() {
		// TODO ???????????????????????????
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO ??????????????? catch ???
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO ??????????????? catch ???
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO ??????????????? catch ???
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO ??????????????? catch ???
			e2.printStackTrace();
		}
	}

	@Override
	public void initListener() {
		// TODO ???????????????????????????
		btn_history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (!history_panel.isVisible()) {
				// history_panel.setVisible(true);
				// splitPane.setDividerLocation(0.6);
				// } else {
				// history_panel.setVisible(false);
				// }
				if (!tabbedPane.isVisible()) {
					tabbedPane.setVisible(true);
					tabbedPane.setSelectedIndex(0);
					splitPane.setDividerLocation(0.6);
				} else {
					tabbedPane.setVisible(false);
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
				// TODO ???????????????????????????
				// System.out.println("asdasdasdasd");
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) choice.getSelectedItem();
					System.out.println(s);
					loadJTextPanel(history_JTextpanel, s);
					// loadJTextPanel(history_JTextpanel,"2017???10???13??? ?????????");
					// loadJTextPanel(edit_receive);
				}
			}
		});

		btn_file.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// if (!center_panel3.isVisible()) {
				// center_panel3.setVisible(true);
				if (!tabbedPane.isVisible()) {
					tabbedPane.setVisible(true);
					tabbedPane.setSelectedIndex(1);
					splitPane.setDividerLocation(0.6);
					// System.out.println("??????");
					JFileChooser chooser = new JFileChooser();
					path = "";
					String file_name = "";
					if (btn_file.isEnabled()) {
						int isnull = chooser.showOpenDialog(null);
						if (isnull == chooser.APPROVE_OPTION)// ????????????
						{
							file_name = chooser.getName(new File(chooser
									.getSelectedFile().getPath()));
							path = chooser.getSelectedFile().getPath();
							file = new File(path);
							// System.out.println("file:"+file.isFile());
							if (!file_name.equals("") && file.isFile()) {
								lbl_file_name.setText(file_name);
								long l = file.length();
								String size = "";
								if (l > (1024 * 1024)) {
									size = l / (1024 * 1024) + "MB";
								} else if (l > 1024) {
									size = l / (1024) + "KB";
								} else {
									size = l + "B";
								}
								try {
									String id = SecondFrame.lbl_user_id;
									String name = SecondFrame.lbl_username
											.getText();
									String to_id = lbl_user_id;
									String mesg = "type:5," + id + "||" + name
											+ "||" + to_id + "||" + file_name
											+ "-??????-" + size;
									Client.dato.writeUTF(mesg);
									// BufferedInputStream buf_in = new
									// BufferedInputStream(
									// new FileInputStream(path));
									// Socket temp_client = new
									// Socket("127.0.0.1",
									// 8877);
									// BufferedOutputStream buf_out = new
									// BufferedOutputStream(
									// temp_client.getOutputStream());
									// BufferedOutputStream buf_out = new
									// BufferedOutputStream(
									// Client.client.getOutputStream());
									// int len;
									// byte[] b = new byte[1024];
									// System.out.println("????????????");
									// while ((len = buf_in.read(b)) > 0) {
									// buf_out.write(b, 0, len);
									// // System.out.println("len" + len);
									// buf_out.flush();
									// }
									// // buf_out.close();
									// buf_in.close();
									// System.out.println("????????????");
								} catch (IOException e1) {
									// TODO ??????????????? catch ???
									e1.printStackTrace();
									JOptionPane
											.showMessageDialog(null, "???????????????");
								}
							} else {
								JOptionPane.showMessageDialog(null, "???????????????");
							}
							// System.out.println(path);
						} else {
							System.err.println("???????????????");
							// JOptionPane.showMessageDialog(null, "???????????????");
						}
					}
				} else {
					// center_panel3.setVisible(false);
					tabbedPane.setVisible(false);
				}
			}
		});
		btn_print.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ScreenCapture().startATime(Friend_chat_all_panel.this);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO ???????????????????????????
		super.paint(g);
		if (num == 0) {
			edit_send.requestFocus();
		}
		num = 1;
	}

	void close_panel() {
		int tempnub = 0;
		// System.out.println("table_id:"+Friend_panel.table_id);
		for (String tmp : Friend_panel.table_id) {
			if (tmp.equals(lbl_user_id)) {
				// System.out.println("remove:" + tempnub);
				// System.out.println("size"+Friend_panel.table_id.size());
				Friend_panel.table_id.remove(tempnub);// ??????list
				Friends_ChatFrame.tabbedPane.remove(tempnub);// ??????table
				if (0 == Friend_panel.table_id.size()) {
					frame.dispose();
				}
				saveJTextPanel();
				break;
			}
			tempnub++;
			// System.out.println("tempnub:"+tempnub);
		}
	}

	void send_mesg() {
		String text = edit_send.getText();
		if (!"".equals(text)) {
			try {
				// ???????????????+???????????????+???????????????+???????????????+????????????
				String mesg = "type:6," + SecondFrame.lbl_user_id + "||"
						+ SecondFrame.lbl_name_text + "||" + lbl_user_id + "||"
						+ lbl_name_text + "||" + text;
				System.out.println("????????????:" + mesg);
				Client.dato.writeUTF(mesg);
				Client.dato.flush();
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				String time = sdf.format(d);
				String u = SecondFrame.lbl_name_text + "  " + time + "\n";
				String[] message = mesg.split("\\|\\|");
				// System.out.println(message[0]+"???????????????:"+mesg);
				mesg = message[4];
				setText(u, mesg);
				edit_send.setText("");
			} catch (IOException e1) {
				// TODO ??????????????? catch ???
				e1.printStackTrace();
				System.err.println("??????????????????");
			}
		} else {
			JOptionPane.showMessageDialog(new JLabel(""), "???????????????????????????!");
		}
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
			// TODO ??????????????? catch ???
			e.printStackTrace();
		}
		// this.edit_receive.setCaretPosition(this.edit_receive.getText().length());
	}

	public void setText(String test, Icon img) {
		try {
			System.out.println("text-mesg:" + test);
			if (null != img) {
				edit_receive.setCaretPosition(doc.getLength());
				doc.insertString(doc.getLength(), test, fri_attrib);
				Thread.sleep(100);
				edit_receive.setCaretPosition(doc.getLength());
				edit_receive.insertIcon(img);
				System.out.println(doc.getLength());
				doc.insertString(doc.getLength(), "\n\r", null);
				Client.img = null;
			} else {
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
				// doc.insertString(doc.getLength(), test + "\n", fri_attrib);
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
				edit_receive.setCaretPosition(edit_receive.getStyledDocument()
						.getLength());
			}
			// edit_receive.setCaretPosition(edit_receive.getStyledDocument()
			// .getLength());
		} catch (BadLocationException e) {
			// TODO ??????????????? catch ???
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO ??????????????? catch ???
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
			System.out.println("????????????" + lbl_user_id + "???????????????");
			obj_out.writeObject(edit_receive.getStyledDocument());
			obj_out.flush();
			obj_out.close();
			System.out.println("????????????" + lbl_user_id + "???????????????");
		} catch (FileNotFoundException e1) {
			// TODO ??????????????? catch ???
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "????????????????????????");
		} catch (IOException e1) {
			// TODO ??????????????? catch ???
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "????????????????????????");
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
				System.out.println("????????????");
				StyledDocument styledo = (StyledDocument) obj_in.readObject();
				jt.setStyledDocument(styledo);
				// Document tmpdo=jt.getDocument();
				// tmpdo.insertString(tmpdo.getLength(),"\n",null);
				obj_in.close();
				System.out.println("????????????");
			} catch (FileNotFoundException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:???????????????");
			} catch (IOException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:??????????????????");
			} catch (ClassNotFoundException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:???????????????");
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
				System.out.println("????????????");
				StyledDocument mydo = (StyledDocument) obj_in.readObject();
				jt.setStyledDocument(mydo);
				// jt.updateUI();
				// Document tmpdo=jt.getDocument();
				// tmpdo.insertString(tmpdo.getLength(),"\n",null);
				obj_in.close();
				System.out.println("????????????");
			} catch (FileNotFoundException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:???????????????");
			} catch (IOException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:??????????????????");
			} catch (ClassNotFoundException e) {
				// TODO ??????????????? catch ???
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JLabel(""), "??????:???????????????");
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
				// System.out.println("??????:" + file2.getName());
				String[] fn = file2.getName().split("&");
				if (fn[0].equals(SecondFrame.lbl_user_id)
						&& fn[1].equals(lbl_user_id)) {
					choice.addItem(fn[2].substring(0, fn[2].length() - 5));
				}
			}
		}
		if (0 < choice.getItemCount()) {
			choice.setSelectedIndex(choice.getItemCount() - 1);
		}
		// choice.addItem("2017???10???14???15:15:53");
	}

}
