package com.socket.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.conn.MysqlConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Server extends JFrame {
	private JPanel contentPane;
	Map<String, Socket> list_client = new Hashtable();// Hashtable为线程安全
	ServerSocket server;
	JTextPane textPane;
	InputStream input;
	BufferedReader bur;
	OutputStream output;
	BufferedWriter bufw;
	JTextPane textPane_1;

	final int TYPE = 5;
	final int MESSAGE = 6;
	final int LOGIN = 1;// 发送的信息为登录验证
	final int REGISTER = 2;// 发送的信息为注册
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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
	public Server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("服务器");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init_UI();
		init_Server();
	}

	void init_Server() {
		try {
			server = new ServerSocket(7788);
			System.out.println("服务器已启动");
			textPane.setText("服务器已启动\n");
			new Thread() {
				public void run() {
					while (true) {
						try {
							Socket client = server.accept();
							// input=client.getInputStream();
							bur = new BufferedReader(new InputStreamReader(
									client.getInputStream(), "UTF-8"));
							// byte[] buf = new byte[1024];
							// int len;
							// len = input.read(buf);
							// String id=new String(buf, 0, len);
							String id = bur.readLine();
							System.out.println(id);
							// list_client.put(num+"", client);
							list_client.put(id, client);
							System.out.println("客户端" + id + "已连接");
							String s = textPane.getText();
							textPane.setText(s + "客户端" + id + "已连接\n");
							ClientListener(id, client);
							output = client.getOutputStream();
							bufw = new BufferedWriter(new OutputStreamWriter(
									output, "UTF-8"));
							bufw.write("已连接上主机");
							bufw.newLine();
							bufw.flush();
							// output.write(("已连接上主机" + "\n").getBytes());
							// output.write("A".getBytes());
							// output.close();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							// e.printStackTrace();
							System.err.println("这个错误还不知道怎么处理....");
							break;
						}
					}// while
				}
			}.start();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.err.println("服务器终止!");
		}
	}

	void init_UI() {
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setTitle("服务端");
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		textPane_1 = new JTextPane();
		textPane_1.setToolTipText("客户端号:+发送消息");
		textPane_1.setText("0:");
		panel.add(textPane_1);

		JButton btnNewButton = new JButton("发送消息");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = textPane_1.getText();
				String[] as = s.split(":");
				// System.out.println(as[0]);
				String index = as[0];
				if (!"0".equals(index)) {
					Socket tmpclient = list_client.get(index);
					try {
						// output = tmpclient.getOutputStream();
						// output.write((as[1] + "\n").getBytes());
						bufw=new BufferedWriter(new OutputStreamWriter(tmpclient.getOutputStream(),"UTF-8"));
						bufw.write(as[1]);
						bufw.newLine();
						bufw.flush();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}else{
					//否则进行广播
					for (Map.Entry<String, Socket> entry : list_client.entrySet()) {
						try {
							Socket tmp_s=entry.getValue();
							bufw=new BufferedWriter(new OutputStreamWriter(tmp_s.getOutputStream(),"UTF-8"));
							bufw.write(as[1]);
							bufw.newLine();
							bufw.flush();
						} catch (UnsupportedEncodingException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					}
				}
			}
		});
		panel.add(btnNewButton, BorderLayout.EAST);

		btnNewButton_1 = new JButton("遍历");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = 0;
				for (Map.Entry<String, Socket> entry : list_client.entrySet()) {
					String s = textPane.getText();
					textPane.setText(s + "客户端" + entry.getKey() + " 保持连接\n");
					num++;
				}
				String s = textPane.getText();
				textPane.setText(s + "共" + num + "个客户端 保持连接\n");
			}
		});
		panel.add(btnNewButton_1, BorderLayout.NORTH);
	}

	void ClientListener(final String id, final Socket client) {
		new Thread() {
			InputStream client_intput;
			BufferedReader client_bufr;

			public void run() {
				try {
					client_intput = client.getInputStream();
					client_bufr = new BufferedReader(new InputStreamReader(
							client_intput, "UTF-8"));
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					// e.printStackTrace();
					System.err.println("客户端连接失败");
				}
				while (true) {
					// if (!client_intput.equals("")) {
					try {
						// byte[] buf = new byte[1024];
						// int len;
						// len = client_intput.read(buf);
//						String s = textPane.getText();
						// String mesg = new String(buf, 0, len);
						String mesg = client_bufr.readLine();
						String s = textPane.getText();//这一句放上面会有不同的效果
						System.out.println(mesg.length() + "  " + mesg);
						char kind = mesg.substring(TYPE, MESSAGE).toCharArray()[0];
						mesg = mesg.substring(MESSAGE + 1);
						switch (kind) {
						case '0':
							textPane.setText(s + mesg + "\n");
							textPane.setCaretPosition(textPane.getText().length());
							mesg = mesg.substring(mesg.indexOf(":")+1);
							SendTo(mesg);
							break;
						case '2':
							MysqlConnection.CreateConnection();
							break;
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						// e.printStackTrace();
						System.out.println("客户端断开连接");
						list_client.remove(id);
						break;
					}
					// }if
				}
			}
		}.start();
	}

	void SendTo(String mesg) {
		String[] as=mesg.split(":");
		System.out.println(mesg+":"+as.length);
		if(1<as.length){
			String index=as[0];
			Socket tmpclient = list_client.get(index);
			try {
				// output = tmpclient.getOutputStream();
				// output.write((as[1] + "\n").getBytes());
				bufw=new BufferedWriter(new OutputStreamWriter(tmpclient.getOutputStream(),"UTF-8"));
				bufw.write(as[1]);
				bufw.newLine();
				bufw.flush();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}
}