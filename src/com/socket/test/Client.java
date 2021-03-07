package com.socket.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame {

	private JPanel contentPane;
	static int num = 0;
	Map list_client;
	Socket client;
	JTextPane textPane_1;
	JTextPane textPane;
	InputStream input;
	BufferedReader bur;
	OutputStream output;
	BufferedWriter bufw;
	String this_client_id = "";

	int MESSAGE = 0;// 发送的信息为聊天消息
	int LOGIN = 1;// 发送的信息为登录验证
	int REGISTER = 2;// 发送的信息为注册
	private JPanel panel_1;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 100, 450, 300);
		setTitle("客户端");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init_UI();
		init_Server();
	}

	void init_Server() {
		try {
			System.out.println("正在连接...");
			client = new Socket("127.0.0.1", 7788);
//			client = new Socket("39.108.122.204", 7788);
//			output = client.getOutputStream();
			this_client_id=new Random().nextInt(100)+"";
//			output.write((this_client_id+"").getBytes());
			bufw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"));
			bufw.write(this_client_id);
			bufw.newLine();
			bufw.flush();
			new Thread() {
				public void run() {
					try {
						//output.write(("type:"+MESSAGE+",多余的消息").getBytes());
						//output.write(("多余的消息").getBytes());
						input = client.getInputStream();
						bur=new BufferedReader(new InputStreamReader(input,"UTF-8"));
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						//e1.printStackTrace();
						System.out.println("客户端连接失败");
					}
					while (true) {
						try {
//							if(null!=input){
//								byte[] buf = new byte[1024];
//								int len = input.read(buf);
//								String s = textPane.getText();
//								textPane.setText(s + new String(buf, 0, len));
////								System.out.println("已连接上主机\n".equals(new String(buf, 0, len)));
////								System.out.println("asdasd"+new String(buf, 0, len));	
//							}else{
//								System.out.println("DDDDD");
//							}
							String s = textPane.getText();
							textPane.setText(s + bur.readLine()+"\n");
							textPane.setCaretPosition(textPane.getText().length());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							//e.printStackTrace();
							System.out.println("与服务器连接中断!");
							try {
								if (null != client)
									client.close();
								if (null != input) {
									input.close();
								}
								if (null != output) {
									output.close();
								}
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								//e1.printStackTrace();
								System.out.println("连接关闭失败!");
							}
							break;
						}
					}//while
				}
			}.start();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				if (null != input) {
					input.close();
				}
				if (null != output) {
					output.close();
				}
				if (null != client) {
					client.close();
				}
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				//e1.printStackTrace();
				System.err.println("客户端关闭失败!");
			}
			System.err.println("与服务器连接失败!");
		}
	}

	void init_UI() {
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setTitle("客户端");
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		textPane_1 = new JTextPane();
		panel.add(textPane_1);

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);

		JButton btnNewButton = new JButton("聊天消息");
		panel_1.add(btnNewButton);

		btnNewButton_1 = new JButton("注册消息");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					output = client.getOutputStream();
					String s = "type:" + REGISTER + ",客户端" + this_client_id
							+ ":" + textPane_1.getText() + "\n";
					output.write(s.getBytes());
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		});
		panel_1.add(btnNewButton_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					output = client.getOutputStream();
					bufw=new BufferedWriter(new OutputStreamWriter(output,"UTF-8"));
					String s = "type:" + MESSAGE + ",客户端" + this_client_id
							+ ":" + textPane_1.getText();
//					output.write(s.getBytes());
					//字符串S不能包含\n换行符
					bufw.write(s);
					bufw.newLine();
					bufw.flush();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
	}

}
