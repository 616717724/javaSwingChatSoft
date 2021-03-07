package com.ui.dialog;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import com.socket.Client;
import com.ui.Friends_ChatFrame;
import com.ui.SecondFrame;
import com.ui.panel.TopPanel;
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.panel.list.Friend_panel;
import com.ui.setIcon.SetIcon;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class TipDialog extends JDialog {
	// JDialog this = new JDialog();
	final int BUF_SIZE = 1024;
	JLabel jl;
	Map<String, String> message_map = new HashMap<String, String>();
	String u_name, u_phone;
	String text;
	private String file_info = "";
	private JLabel lbl_save;
	private JLabel lbl_cancel;
	private JFileChooser filechooser;
	public static JProgressBar progressBar;

	public TipDialog(String u_phone, String u_name, String file_info) {
		// this.setSize(300, 200);
		this.u_phone = u_phone;
		this.u_name = u_name;
		this.file_info = file_info;
		this.setSize(200, 200);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		Image image = SetIcon.getPicture("bg.png", 200, 200).getImage();
		this.setIconImage(image);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int windx = ((int) screensize.getWidth() - 200);
		int windy = ((int) screensize.getHeight() - 250);
		this.setLocation(windx, windy);

		Container c2 = this.getContentPane();
		TopPanel top = new TopPanel(this, 3);
		JButton jbb = new JButton("收到");
		jbb.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		jl = new JLabel(u_name);
		jl.setForeground(new Color(13, 196, 254));
		jl.setBounds(0, 10, 197, 28);
		panel.add(jl);
		jl.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jl.setBackground(Color.WHITE);
		jl.setOpaque(true);
		jl.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel = new JLabel("发来消息");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 37, 197, 34);
		panel.add(lblNewLabel);
		lbl_save = new JLabel("接收");
		lbl_save.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_save.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lbl_save.setBackground(Color.WHITE);
		lbl_save.setBounds(0, 105, 86, 34);
		panel.add(lbl_save);

		lbl_cancel = new JLabel("拒绝");
		lbl_cancel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_cancel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lbl_cancel.setBackground(Color.WHITE);
		lbl_cancel.setBounds(111, 105, 86, 34);
		panel.add(lbl_cancel);

		progressBar = new JProgressBar();
		progressBar.setBounds(24, 81, 146, 14);
		panel.add(progressBar);

		if (file_info.equals("")) {
			lbl_save.setVisible(false);
			lbl_cancel.setVisible(false);
			progressBar.setVisible(false);
		} else {
			lblNewLabel.setText("发来文件" + file_info);
		}

		c2.add(top, BorderLayout.NORTH);

		jbb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				// System.exit(0);
			}

		});

		// System.out.println("OK");

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// this.setVisible(true);
		initListener();
	}

	public void initListener() {
		jl.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
				jl.setText("<html><u style='color:red;'>" + u_name
						+ "</u><html>");
			}

			public void mouseExited(MouseEvent e) {
				jl.setForeground(new Color(13, 196, 254));
				jl.setText(u_name);
			}

			public void mouseClicked(MouseEvent e) {
				// FristFrame.f.change_panel("同意");
				// Create_Friend_Frame();
				if (!file_info.equals("")) {
//					save_file();
				}
				file_info="";
				clean();
			}
		});
		lbl_save.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				lbl_save.setCursor(new Cursor(Cursor.HAND_CURSOR));
				lbl_save.setText("<html><u style='color:red;'>" + "接收"
						+ "</u><html>");
			}

			public void mouseExited(MouseEvent e) {
				lbl_save.setForeground(new Color(13, 196, 254));
				lbl_save.setText("接收");
			}

			public void mouseClicked(MouseEvent e) {
				if (lbl_save.isEnabled()) {
					save_file();
					// clean();
				}
			}
		});
		lbl_cancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				lbl_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				lbl_cancel.setText("<html><u style='color:red;'>" + "拒绝"
						+ "</u><html>");
			}

			public void mouseExited(MouseEvent e) {
				lbl_cancel.setForeground(new Color(13, 196, 254));
				lbl_cancel.setText("拒绝");
			}

			public void mouseClicked(MouseEvent e) {
				if (lbl_cancel.isEnabled()) {
					String id = SecondFrame.lbl_user_id;
					String name = SecondFrame.lbl_username.getText();
					String mesg = "";
					mesg = "type:5," + id + "||" + name + "||" + u_phone + "||"
							+ "拒绝";
					try {
						Client.dato.writeUTF(mesg);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					clean();
				}
			}
		});
	}

	void save_file() {
		filechooser = new JFileChooser();
		filechooser.setDialogTitle("另存为...");
		filechooser.setSelectedFile(new File(file_info));
		int returnVal = filechooser.showSaveDialog(TipDialog.this);
		String id = SecondFrame.lbl_user_id;
		String name = SecondFrame.lbl_name_text;
		String mesg = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			// System.out.println("u_phone:"+u_phone);
			mesg = "type:5," + id + "||" + name + "||" + u_phone + "||" + "接收"
					+ file_info;
			try {
				Client.dato.writeUTF(mesg);
				start_receive_file();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}// if
		else {
			mesg = "type:5," + id + "||" + name + "||" + u_phone + "||" + "拒绝";
		}
	}

	void start_receive_file() {
		try {
			new Thread() {
				String path = filechooser.getSelectedFile().getPath();
				Socket temp_client = new Socket("127.0.0.1", 8877);
//				Socket temp_client=new Socket("172.18.41.251",8877);
				DataOutputStream temp_out = new DataOutputStream(
						temp_client.getOutputStream());
				BufferedInputStream buf_in = new BufferedInputStream(
						temp_client.getInputStream());
				BufferedOutputStream buf_out = new BufferedOutputStream(
						new FileOutputStream(path));
				int len;
				byte[] b = new byte[BUF_SIZE];

				public void run() {
					try {
						lbl_save.setEnabled(false);
						lbl_cancel.setEnabled(false);
						int length = get_File_size(file_info);
						long size = length / BUF_SIZE;// 总共要传输多少次
						temp_out.writeUTF(SecondFrame.lbl_user_id);
						Thread.sleep(3000);
						temp_client.setSoTimeout(2000);
						System.out.println("开始读");
						int count = 0;
						while ((len = buf_in.read(b)) > 0) {
							buf_out.write(b, 0, len);
							// System.out.println("len:"+len)
							if(size>0){
								progressBar.setValue((int)(count*100/size));
							}else{
								progressBar.setValue(100);
							}
							buf_out.flush();
							count++;
						}
						buf_out.close();
						buf_in.close();
						temp_client.close();
						System.out.println("读完");
						openFilePath(path.replace(file_info,""));
//						lbl_save.setEnabled(true);
//						lbl_cancel.setEnabled(true);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "错误:对方中断发送");
						try {
							buf_out.close();
							buf_in.close();
							temp_client.close();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "错误:对方中断发送");
						try {
							buf_out.close();
							buf_in.close();
							temp_client.close();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					}
				}
			}.start();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	int get_File_size(String file_info) {
		int ind = file_info.indexOf("大小-");
		System.out.println(ind);
		file_info = file_info.substring(ind + 3);
		int i = 0;
		String size_mod = file_info.substring(file_info.length() - 2);
		if (size_mod.equals("MB")) {
			String size = file_info.substring(0, file_info.length() - 2);
			i = Integer.parseInt(size) * (1024 * 1024);
			System.out.println(i);
		} else if (size_mod.equals("KB")) {
			String size = file_info.substring(0, file_info.length() - 2);
			i = Integer.parseInt(size) * (1024);
			System.out.println(i);
		} else {
			String size = file_info.substring(0, file_info.length() - 1);
			i = Integer.parseInt(size);
			System.out.println(i);
		}
		return i;
	}

	void Create_Friend_Frame() {
		String icon = u_name.substring(0, 1);
		if (0 == Friend_panel.table_id.size()) {
			Friend_panel.fri_chat_frame = new Friends_ChatFrame(icon, u_name,
					u_phone);
			Friend_panel.fri_chat_frame.setText(text, null);
			Friend_panel.table_id.add(u_phone);
		} else {
			int tmpnub = 0;
			for (String tmp : Friend_panel.table_id) {
				if (tmp.equals(u_phone)) {
					System.out.println("好友已在聊天框");
					Friend_panel.fri_chat_frame.getTabbedPane()
							.setSelectedIndex(tmpnub);
					Friend_chat_all_panel tmp_panel = (Friend_chat_all_panel) Friend_panel.fri_chat_frame
							.getTabbedPane().getSelectedComponent();
					// tmp_panel.setText(text);
					// Friend_panel.fri_chat_frame.setText(text);
					break;
				} else {
					if (tmpnub >= (Friend_panel.fri_chat_frame.getTabbedPane()
							.getTabCount() - 1)) {
						Friend_chat_all_panel all_panel = new Friend_chat_all_panel(
								icon, u_name, u_phone);
						Friend_panel.fri_chat_frame.getTabbedPane().addTab(
								u_name, null, all_panel);
						Friend_panel.fri_chat_frame
								.getTabbedPane()
								.setSelectedIndex(
										Friend_panel.fri_chat_frame
												.getTabbedPane().getTabCount() - 1);
						Friend_panel.table_id.add(u_phone);
						// all_panel.setText(text);
						break;
					}
					// System.out.println(tmpnub);
				}
				tmpnub++;
				// System.out.println(tmpnub);
			}
		}
	}

	public void setMap(Map<String, String> message_map) {
		// System.out.println("setMap");
		this.message_map = message_map;
	}

	public void clean() {
		// System.out.println("dispose");
		// this.message_map.remove(u_phone);
		this.setVisible(false);
		// this.dispose();
	}

	public static void main(String[] args) {
		new TipDialog("服务器", "服务器", "");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static void openFilePath(String FILE_PATH) {
		try {
	            String[] cmd = new String[5];  
	            cmd[0] = "cmd";  
	            cmd[1] = "/c";  
	            cmd[2] = "start";  
	            cmd[3] = " ";  
	            cmd[4] = FILE_PATH;  
	            Runtime.getRuntime().exec(cmd);  
		} catch (final Exception e) {
			System.out.println("Error exec!");
		}
	}
}
