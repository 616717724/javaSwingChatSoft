package com.socket;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.conn.MysqlConnection;
import com.dao.GroupChat;
import com.dao.User;
import com.sql.Delete;
import com.sql.Insert;
import com.sql.Select;
import com.sql.Update;

public class Server extends JFrame {
	private JPanel contentPane;
	Map<String, Socket> list_client = new Hashtable();// Hashtable为线程安全
	Map<String, Socket> list_client2 = new Hashtable();// Hashtable为线程安全
	Map<String, ObjectOutputStream> list_obj_output = new Hashtable();
	Map<String, GroupChat> map_group_chat = new Hashtable();;
	ServerSocket server, server2;
	JTextPane textPane;

	InputStream input;
	OutputStream output;
	DataInputStream dati;
	DataOutputStream dato;
	// BufferedReader bur;
	// BufferedWriter bufw;

	// ObjectInputStream obj_input;
	// ObjectOutputStream obj_output;

	JTextPane textPane_1;
	Connection conn = null;
	int num = 0;

	final int TYPE = 5;
	final int MESSAGE = 6;
	final int LOGIN = 1;// 发送的信息为登录验证
	final int REGISTER = 2;// 发送的信息为注册
	final int UPDATE = 3;// 信息为更新消息
	final int ALL = 4;// 消息为广播消息

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 将所有用户设置为离线
					new Update().Update_All_Satus0();
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
			// textPane.setText("服务器已启动\n");
			// textPane.setCaretPosition(textPane.getText().length());
			Set_Text("服务器已启动\n");
			new Thread() {
				public void run() {
					while (true) {
						try {
							Socket client = server.accept();
							// input = client.getInputStream();
							// byte[] buf = new byte[1024];
							// int len;
							// len = input.read(buf);
							// String id = new String(buf, 0, len);
							// System.out.println("客户端" + id + "已连接");
							// String s = textPane.getText();
							// textPane.setText(s + "客户端" + id + "已连接\n");
							// output = client.getOutputStream();
							// output.write(("已连接上主机" + "\n").getBytes());
							// list_client.put(id, client);
							ClientListener(client);
							File_server();
							// output.write("A".getBytes());
							// output.close();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							// e.printStackTrace();
							// System.out.println("这个错误还不知道怎么处理....");
							System.err.println("服务器接收客户端异常");
						}
					}
				}
			}.start();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			// e.printStackTrace();
			try {
				if (null != server) {
					server.close();
				}
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				// e1.printStackTrace();
				System.err.println("服务器关闭异常");
			}
			System.err.println("服务器启动失败!");
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

		JButton btnNewButton = new JButton("发送");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				String time = sdf.format(d);
				String s = textPane_1.getText();
				String[] as = s.split(":");
				// System.out.println(as[0]);
				String index = as[0];
				if (!"0".equals(index)) {
					Socket tmpclient = list_client.get(index);
					try {
						// output = tmpclient.getOutputStream();
						// output.write((as[1] + "\n").getBytes());
						// bufw=new BufferedWriter(new
						// OutputStreamWriter(tmpclient.getOutputStream(),"UTF-8"));
						// bufw.write(as[1]);
						// bufw.newLine();
						// bufw.flush();
						as[1] = "type:6,服务器||服务器||" + time + "||" + as[1];
						dato = new DataOutputStream(tmpclient.getOutputStream());
						dato.writeUTF(as[1]);
						dato.flush();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				} else {
					// 否则进行广播
					// as[1]="type:6,服务器||服务器||服务器||服务器||"+time+"||"+as[1];
					as[1] = "type:6,服务器||服务器||" + time + "||" + as[1];
					All_Client_Message(as[1]);
				}
			}
		});
		panel.add(btnNewButton, BorderLayout.EAST);

		JButton btnNewButton_1 = new JButton("遍历");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = 0;
				for (Map.Entry<String, Socket> entry : list_client.entrySet()) {
					String s = textPane.getText();
					num++;
					textPane.setText(s + "客户端" + entry.getKey() + " 保持连接\n");
				}
				String s = textPane.getText();
				textPane.setText(s + "共" + num + "个客户端 保持连接\n");
				// textPane.setCaretPosition(textPane.getText().length());
			}
		});
		panel.add(btnNewButton_1, BorderLayout.NORTH);
	}

	void ClientListener(final Socket client) {
		new Thread() {
			InputStream client_intput;
			// BufferedReader client_bufr;
			DataInputStream client_dati;
			ObjectOutputStream obj_output;
			ObjectInputStream obj_input = null;
			String mesg = "";
			String s = "";
			String id = "";// id已废

			// byte[] buf = new byte[1024];
			// int len;

			public void run() {
				try {
					client_intput = client.getInputStream();
					client_dati = new DataInputStream(client_intput);
					obj_output = new ObjectOutputStream(
							new BufferedOutputStream(client.getOutputStream()));
				} catch (IOException e2) {
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
				while (true) {
					try {
						// len = client_intput.read(buf);
						s = textPane.getText();
						// mesg = new String(buf, 0, len);
						// mesg = client_bufr.readLine();
						System.out.println("等待消息");
						mesg = client_dati.readUTF();
						System.out.println("消息:" + mesg);
						// 获取消息种类
						char kind = mesg.substring(TYPE, MESSAGE).toCharArray()[0];
						// 分离消息内容。
						String[] user_info = mesg.substring(MESSAGE + 1).split(
								"\\|\\|");
						// for(int i=0;i<user_info.length;i++){
						// System.out.println("user_info["+i+"]:"+user_info[i]);
						// }
						Icon img = null;
						// 根据种类执行功能
						switch (kind) {
						case '0':
							// textPane.setText(s + mesg.substring(MESSAGE +
							// 1));
							// textPane.setCaretPosition(textPane.getText().length());
							s = s + mesg.substring(MESSAGE + 1);
							Set_Text(s);
							break;
						case '1':
							// textPane.setText(s + "登录消息:"
							// + mesg.substring(MESSAGE + 1) + "\n");
							// textPane.setCaretPosition(textPane.getText().length());
							s = s + "登录消息:" + mesg.substring(MESSAGE + 1)
									+ "\n";
							Set_Text(s);
							// for(String t:user_info){
							// System.out.println(t);
							// }
							id = user_info[0];
							if (null == conn) {
								conn = MysqlConnection.CreateConnection();
								Login_Message(client, obj_output, user_info[0],
										user_info[1]);
							} else {
								Login_Message(client, obj_output, user_info[0],
										user_info[1]);
							}
							break;
						case '2':
							// textPane.setText(s + "注册消息:"+
							// mesg.substring(MESSAGE + 1) + "\n");
							// textPane.setCaretPosition(textPane.getText().length());
							s = s + "注册消息:" + mesg.substring(MESSAGE + 1)
									+ "\n";
							Set_Text(s);
							// for(String t:user_info){
							// System.out.println(t);
							// }
							if (null == conn) {
								conn = MysqlConnection.CreateConnection();
								Register_Message(client, user_info[0],
										user_info[1], user_info[2]);
							} else {
								Register_Message(client, user_info[0],
										user_info[1], user_info[2]);
							}
							break;
						case '3':
							System.out.println(mesg);
							// All_Client_Message(mesg);//广播下线
							Updata_User_Info(mesg.substring(MESSAGE + 1));
							break;
						case '4':
							System.out.println(mesg);
							Updata_Group_Info(mesg.substring(MESSAGE + 1));
							break;
						case '5':
							System.out.println(mesg);
							File_Info(mesg.substring(MESSAGE + 1));
							break;
						case '6':
							System.out.println("user_info.length:"
									+ user_info.length);
							if (4 == user_info.length) {
								System.out.println("服务器接收图片对象");
								if (null == obj_input) {
									obj_input = new ObjectInputStream(
											new BufferedInputStream(
													client.getInputStream()));
								}
								img = (Icon) obj_input.readObject();
								// obj_output.writeObject("asdasd");
								// obj_output.flush();
								System.out.println("服务器接收到图片");
								Chat_Message(mesg, img);
							} else {
								Chat_Message(mesg, img);
							}
							break;
						case '7':
							System.out.println("user_info.length:"
									+ user_info.length);
							if (5 == user_info.length) {
								System.out.println("服务器接收图片对象");
								if (null == obj_input) {
									obj_input = new ObjectInputStream(
											new BufferedInputStream(
													client.getInputStream()));
								}
								img = (Icon) obj_input.readObject();
								System.out.println("服务器接收到图片");
								Group_Chat_Message(mesg, img);
							} else {
								Group_Chat_Message(mesg, img);
							}
							break;
						default:
							// textPane.setText(s + "无法识别的消息类别");
							// textPane.setCaretPosition(textPane.getText().length());
							s = s + "无法识别的消息类别";
							Set_Text(s);
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						// e.printStackTrace();
						s = textPane.getText();
						// id已废
						s = s + "客户端" + client.getInetAddress()+" id="+ id + "断开连接\n";
						list_client.remove(id);
						// textPane.setText(s);
						// textPane.setCaretPosition(textPane.getText().length());
						Set_Text(s);
						System.out.println(s);
						Select select = new Select(conn);
						select.Logout(id);
						String mesg = "type:" + UPDATE + "," + id + "||"
								+ "logout";
						All_Client_Message(mesg);// 广播下线
						try {
							list_client.remove(id);
							// if (null != client_bufr) {
							// client_bufr.close();
							// }
							if (null != client_dati) {
								client_dati.close();
							}
							if (null != client_intput) {
								client_intput.close();
							}
							if (null != client) {
								client.close();
							}
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							// e1.printStackTrace();
							System.out.println("IO关闭异常:243");
						}
						break;
					} catch (ClassNotFoundException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// while
			}

		}.start();
	}

	void Set_Text(String s) {
		textPane.setText(s);
		// textPane.setCaretPosition(textPane.getText().length());
		textPane.setCaretPosition(textPane.getStyledDocument().getLength());
	}

	// 注册消息处理
	void Register_Message(Socket client, String u_name, String u_phone,
			String u_password) {
		OutputStream client_output;
		// BufferedWriter client_bufw;
		DataOutputStream client_dato;
		String s = textPane.getText();
		Select select = new Select(conn);
		// 如果手机号没有被注册
		if (select.Check_Insert_User(u_phone)) {
			Insert insert = new Insert(conn);
			if (insert.insert_user(u_name, u_phone, u_password)) {
				s = textPane.getText();
				// textPane.setText(s + "注册成功！" + "\n");
				// textPane.setCaretPosition(textPane.getText().length());
				s = s + "注册成功！" + "\n";
				Set_Text(s);
				try {
					client_output = client.getOutputStream();
					// client_bufw = new BufferedWriter(new
					// OutputStreamWriter(client_output, "UTF-8"));
					client_dato = new DataOutputStream(client_output);
					// client_output.write(("注册成功").getBytes());
					// client_bufw.write("注册成功");
					// client_bufw.newLine();
					// client_bufw.flush();
					client_dato.writeUTF("注册成功");
					client_dato.flush();
					// client_bufw.close();
					client_dato.close();
					client_output.close();
					client.close();
					String mesg = "type:" + UPDATE + "," + u_phone + "||"
							+ "register||" + u_name;
					All_Client_Message(mesg);// 广播注册
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					// e1.printStackTrace();
					System.err.println("客户端注册消息发送失败");
					// 可能要删除注册信息
				}
			} else {
				s = textPane.getText();
				// textPane.setText(s + "失败！" + "\n");
				// textPane.setCaretPosition(textPane.getText().length());
				s = s + "失败！" + "\n";
				Set_Text(s);
				try {
					output = client.getOutputStream();
					// client_bufw = new BufferedWriter(new
					// OutputStreamWriter(output, "UTF-8"));
					client_dato = new DataOutputStream(output);
					// output.write(("注册失败").getBytes());
					// client_bufw.write("注册失败");
					// client_bufw.newLine();
					// client_bufw.flush();
					client_dato.writeUTF("注册失败");
					client_dato.flush();
					// client_bufw.close();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		}// if
		else {
			s = textPane.getText();
			// textPane.setText(s + "手机号已被注册！" + "\n");
			// textPane.setCaretPosition(textPane.getText().length());
			s = s + "手机号已被注册！" + "\n";
			Set_Text(s);
			try {
				output = client.getOutputStream();
				// client_bufw = new BufferedWriter(new
				// OutputStreamWriter(output,"UTF-8"));
				client_dato = new DataOutputStream(output);
				// output.write(("手机号已被注册").getBytes());
				// client_bufw.write("手机号已被注册");
				// client_bufw.newLine();
				// client_bufw.flush();
				client_dato.writeUTF("手机号已被注册");
				// client_bufw.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}// Register_Message

	// 登录消息处理----登录目前只能用OutputStream发送否则会object会阻塞
	void Login_Message(Socket client, ObjectOutputStream obj_output,
			String u_phone, String u_password) {
		OutputStream client_output = null;
		// BufferedWriter client_bufw = null;
		DataOutputStream client_dato = null;
		String s = textPane.getText();
		Select select = new Select(conn);
		// 如果手机号没有被注册
		if (select.Check_Login(u_phone, u_password)) {
			// textPane.setText(s + u_phone + "登录成功！" + "\n");
			// textPane.setCaretPosition(textPane.getText().length());
			if (null != list_client.get(u_phone)) {
				try {
					client_output = client.getOutputStream();
					client_dato = new DataOutputStream(client_output);
					client_dato.writeUTF("账号已在线,请注意账号安全");
					client_dato.flush();
					list_client.get(u_phone).close();
					System.out.println("用户已登入");
					list_client.remove(u_phone);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} else {
				s = s + u_phone + "登录成功！" + "\n";
				Set_Text(s);
				String mesg = "type:" + UPDATE + "," + u_phone + "||" + "login";
				All_Client_Message(mesg);// 广播上线
				try {
					String u_name = select.Select_U_name(u_phone);
					client_output = client.getOutputStream();
					// client_bufw = new BufferedWriter(new
					// OutputStreamWriter(client_output, "UTF-8"));
					client_dato = new DataOutputStream(client_output);
					// client_output.write(("登录成功"+u_name).getBytes());
					// client_bufw.write("登录成功" + u_name);
					// client_bufw.newLine();
					// client_bufw.flush();
					client_dato.writeUTF("登录成功" + u_name);
					client_dato.flush();
					list_client.put(u_phone, client);
					// ObjectOutputStream obj_output = new
					// ObjectOutputStream(client_output);
					// ObjectOutputStream obj_output = new
					// ObjectOutputStream(new
					// BufferedOutputStream(client.getOutputStream()));
					// List<User> list_user=select.Select_friend_List(u_phone);
					List<User> list_user = select.Select_All_User();
					System.out.println("开始发送好友列表");
					obj_output.writeObject(list_user);
					obj_output.flush();
					list_obj_output.put(u_phone, obj_output);
					System.out.println("发送好友列表完毕");
					List<GroupChat> list_group = select.Select_Group(u_phone);
					System.out.println("开始发送群列表");
					obj_output.writeObject(list_group);
					obj_output.flush();
					// list_obj_output.put(u_phone, obj_output);
					System.out.println("发送群列表完毕");
					// obj_output.close();
					// client_output.close();
					// client.close();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					// e1.printStackTrace();
					System.err.println("客户端登录消息发送失败");
					// try {
					// client_output.close();
					// client_bufw.close();
					// } catch (IOException e) {
					// // TODO 自动生成的 catch 块
					// e.printStackTrace();
					// }
					// 可能要删除注册信息
				}
			}
		}// if
		else {
			s = textPane.getText();
			// textPane.setText(s + u_phone + "登录失败！" + "\n");
			// textPane.setCaretPosition(textPane.getText().length());
			s = s + u_phone + "登录失败！" + "\n";
			Set_Text(s);
			try {
				client_output = client.getOutputStream();
				// client_bufw = new BufferedWriter(new
				// OutputStreamWriter(client_output, "UTF-8"));
				client_dato = new DataOutputStream(client_output);
				// output.write((u_phone + "登录失败,密码或手机号不正确").getBytes());
				// client_bufw.write(u_phone + "登录失败,密码或手机号不正确");
				// client_bufw.newLine();
				// client_bufw.flush();
				client_dato.writeUTF(u_phone + "登录失败,密码或手机号不正确");
				client_dato.flush();
				// client_bufw.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}// Login_Message

	// 广播消息;
	void All_Client_Message(String mesg) {
		for (Map.Entry<String, Socket> entry : list_client.entrySet()) {
			try {
				Socket tmp_s = entry.getValue();
				// bufw=new BufferedWriter(new
				// OutputStreamWriter(tmp_s.getOutputStream(),"UTF-8"));
				// bufw.write(mesg);
				// bufw.newLine();
				// bufw.flush();
				dato = new DataOutputStream(tmp_s.getOutputStream());
				dato.writeUTF(mesg);
				dato.flush();
			} catch (UnsupportedEncodingException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}// All_Client_Message

	// 私聊消息
	void Chat_Message(String mesg, Icon img) {
		System.out.println("聊天消息:" + mesg);
		Set_Text(textPane.getText() + "聊天消息:" + mesg + "\n");
		String[] chat_mesg = mesg.split("\\|\\|");
		System.out.println("聊天目标:" + chat_mesg[2]);
		Socket temp_client = list_client.get(chat_mesg[2]);
		// BufferedWriter temp_bufw;
		DataOutputStream temp_dato;
		if (null != temp_client) {
			try {
				// temp_bufw=new BufferedWriter(new
				// OutputStreamWriter(temp_client.getOutputStream(),"UTF-8"));
				temp_dato = new DataOutputStream(temp_client.getOutputStream());
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = new Date();
				String time = sdf.format(d);
				// temp_bufw.write(chat_mesg[0]+"||"+chat_mesg[1]+"||"+time+"||"+chat_mesg[4]);
				// temp_bufw.write(chat_mesg[0]+"||"+chat_mesg[1]+"||"+chat_mesg[4]);
				// temp_bufw.newLine();
				// temp_bufw.flush();
				if (null != img) {
					temp_dato.writeUTF(chat_mesg[0] + "||" + chat_mesg[1]
							+ "||" + time + "||");
					temp_dato.flush();
					list_obj_output.get(chat_mesg[2]).writeObject(img);
					list_obj_output.get(chat_mesg[2]).flush();
				} else {
					if (4 == chat_mesg.length) {
						// temp_dato.writeUTF(chat_mesg[0]+"||"+chat_mesg[1]+"||"+time+"||");
					} else {
						temp_dato.writeUTF(chat_mesg[0] + "||" + chat_mesg[1]
								+ "||" + time + "||" + chat_mesg[4]);
					}
				}
				temp_dato.flush();
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}// if
		else {
			System.out.println("用户不在线");
		}
	}// Chat_Message

	// 群聊
	void Group_Chat_Message(String mesg, Icon img) {
		System.out.println("群聊消息:" + mesg);
		Set_Text(textPane.getText() + "群聊消息:" + mesg + "\n");
		String[] chat_mesg = mesg.split("\\|\\|");
		System.out.println("群ID:" + chat_mesg[2]);
		String[] u_list = chat_mesg[4].split(",");
		for (String temp_u : u_list) {
			Socket temp_client = list_client.get(temp_u);
			DataOutputStream temp_dato;
			if (null != temp_client) {
				try {
					temp_dato = new DataOutputStream(
							temp_client.getOutputStream());
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d = new Date();
					String time = sdf.format(d);
					if (null != img) {
						temp_dato.writeUTF(chat_mesg[0] + "||" + chat_mesg[1]
								+ "||" + chat_mesg[2] + "||" + chat_mesg[3]
								+ "||" + time + "||");
						temp_dato.flush();
						list_obj_output.get(temp_u).writeObject(img);
						list_obj_output.get(temp_u).flush();
					} else {
						temp_dato.writeUTF(chat_mesg[0] + "||" + chat_mesg[1]
								+ "||" + chat_mesg[2] + "||" + chat_mesg[3]
								+ "||" + time + "||" + chat_mesg[5]);
						temp_dato.flush();
					}
				} catch (UnsupportedEncodingException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}// if
		}
		// Socket temp_client=list_client.get(chat_mesg[2]);
	}// Group_Chat_Message

	private void Updata_User_Info(String message) throws IOException {
		// TODO 自动生成的方法存根
		String[] mesg = message.split("\\|\\|");
		if (mesg[0].equals("user_info")) {
			if (new Update().Update_User_Info(mesg[1], mesg[2],
					mesg[3].replace("/", "||"))) {
				// new
				// DataOutputStream(tmpclient.getOutputStream()).writeUTF("type:"
				// + UPDATE + ","+ mesg[1]+"||"+"user_info_update");;
				All_Client_Message("type:" + UPDATE + "," + message);
			}
		} else if (mesg[0].equals("user_pwd")) {
			new Update().Update_User_Pwd(mesg[1], mesg[2]);
		}
	}

	private void Updata_Group_Info(String message) {
		// TODO 自动生成的方法存根
		String[] mesg = message.split("\\|\\|");
		// String[] u_list=mesg[4].split(",");
		if (mesg[0].equals("group_info")) {
			String[] u_list = mesg[4].split(",");
			new Update().Update_Group_Info(mesg[1], mesg[2], mesg[3]);
			for (String temp_u : u_list) {
				Socket temp_client = list_client.get(temp_u);
				DataOutputStream temp_dato;
				if (null != temp_client) {
					try {
						temp_dato = new DataOutputStream(
								temp_client.getOutputStream());
						temp_dato.writeUTF("type:4," + mesg[0] + "||" + mesg[1]
								+ "||" + mesg[2] + "||" + mesg[3]);
						temp_dato.flush();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// if
			}// for
		}// if
		else if (mesg[0].equals("group_create")) {
			String g_id = new Insert().create_group(mesg[1], mesg[2], mesg[3],
					mesg[4]);
			String[] u_list = mesg[2].split(",");
			for (String temp_u : u_list) {
				Socket temp_client = list_client.get(temp_u);
				DataOutputStream temp_dato;
				if (null != temp_client) {
					try {
						temp_dato = new DataOutputStream(
								temp_client.getOutputStream());
						temp_dato.writeUTF("type:4," + mesg[0] + "||" + g_id
								+ "||" + mesg[1] + "||" + mesg[2] + "||"
								+ mesg[3] + "||" + mesg[4]);
						temp_dato.flush();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// if
			}// for
		}// if
		else if (mesg[0].equals("group_out")) {
			String[] u_list = mesg[4].split(",");
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			String time = sdf.format(d);
			for (String temp_u : u_list) {
				Socket temp_client = list_client.get(temp_u);
				DataOutputStream temp_dato;
				if (null != temp_client) {
					try {
						temp_dato = new DataOutputStream(
								temp_client.getOutputStream());
						if (temp_u.equals(mesg[2])) {
							temp_dato.writeUTF("type:4," + "group_clean" + "||"
									+ mesg[1]);
						} else {
							temp_dato.writeUTF("type:7," + mesg[2] + "||"
									+ mesg[3] + "||" + mesg[1] + "||" + mesg[3]
									+ "退出群" + mesg[2] + "||" + time + "||"
									+ mesg[3] + "退出该群");
							temp_dato.flush();
							temp_dato
									.writeUTF("type:4," + mesg[0] + "||"
											+ mesg[1] + "||" + mesg[2] + "||"
											+ mesg[3]);
						}
						temp_dato.flush();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// if
			}// for

		} else if (mesg[0].equals("group_clean")) {
			new Delete(conn).Delete_Group(mesg[1]);
			String[] u_list = mesg[2].split(",");
			for (String temp_u : u_list) {
				Socket temp_client = list_client.get(temp_u);
				DataOutputStream temp_dato;
				if (null != temp_client) {
					try {
						temp_dato = new DataOutputStream(
								temp_client.getOutputStream());
						temp_dato
								.writeUTF("type:4," + mesg[0] + "||" + mesg[1]);
						temp_dato.flush();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// if
			}// for
		}// if
		else if (mesg[0].equals("group_into")) {
			String[] u_list = mesg[5].split(",");
			// new Update().Update_Group_AddUser(mesg[1],mesg[2],mesg[3]);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			String time = sdf.format(d);
			Socket temp_client0 = list_client.get(mesg[2]);
			DataOutputStream temp_dato0;
			if (null != temp_client0) {
				try {
					temp_dato0 = new DataOutputStream(
							temp_client0.getOutputStream());
					temp_dato0.writeUTF("type:4," + "group_create" + "||" + mesg[1]
							+ "||" + mesg[4] + "||" + mesg[5] + "," + mesg[2]
							+ "||" + mesg[6] + "," + mesg[3] + "||" + mesg[7]);
					temp_dato0.flush();
				} catch (UnsupportedEncodingException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}// if
			for (String temp_u : u_list) {
				Socket temp_client = list_client.get(temp_u);
				DataOutputStream temp_dato;
				if (null != temp_client) {
					try {
						temp_dato = new DataOutputStream(
								temp_client.getOutputStream());
						temp_dato.writeUTF("type:7," + mesg[2] + "||" + mesg[3]
								+ "||" + mesg[1] + "||" + mesg[3] + "进入群"
								+ mesg[2] + "||" + time + "||" + mesg[3]
								+ "进入群");
						temp_dato.flush();
						temp_dato.writeUTF("type:4," + "group_into" + "||"
								+ mesg[1] + "||" + mesg[2] + "||" + mesg[3]);
						temp_dato.flush();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}// if
			}// for
		}// if

	}// Updata_Group_Info

	// void File_Info(String message) {
	// String[] mesg = message.split("\\|\\|");
	// String send_id = mesg[0];
	// String file_info = mesg[3];
	// // Socket temp_client = list_client.get(send_id);
	// System.out.println("send_id:"+send_id+" file_info"+file_info);
	// try {
	// ServerSocket ser = new ServerSocket(8877);
	// Socket sock=ser.accept();
	// // FileOutputStream outputStream = new FileOutputStream(
	// // "./"+send_id+".txt");
	// FileOutputStream outputStream = new FileOutputStream(
	// "./"+file_info+".txt");
	// BufferedOutputStream buf_out = new BufferedOutputStream(
	// outputStream);
	// BufferedInputStream buf_in = new BufferedInputStream(
	// sock.getInputStream());
	// int len = 0;
	// byte[] b = new byte[1024];
	// System.out.println("开始接收");
	// while ((len = buf_in.read(b)) > 0) {
	// buf_out.write(b, 0, len);
	// buf_out.flush();
	// // System.out.println(len);
	// }// while
	// buf_out.close();
	// ser.close();
	// System.out.println("结束");
	// } catch (FileNotFoundException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// }
	// }// File_Info

	void File_Info(String message) {
		// TODO 自动生成的方法存根
		String[] mesg = message.split("\\|\\|");
		String send_id = mesg[0];
		String send_name = mesg[1];
		String rec_id = mesg[2];
		String file_info = mesg[3];
		Socket temp_client = list_client.get(rec_id);
		DataOutputStream temp_dato;
		if (file_info.equals("开始发送文件")) {
			// Socket temp_client2 = list_client.get(send_id);
			Save_file(send_id, rec_id);
		}
		// else if (file_info.subSequence(0, 2).equals("接收")) {
		// if (null != temp_client) {
		// try {
		// temp_dato = new DataOutputStream(temp_client.getOutputStream());
		// temp_dato.writeUTF("type:5," + send_id + "||" + send_name
		// + "||接收" + file_info);
		// temp_dato.flush();
		// } catch (IOException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }
		// }
		// }
		else if (null != temp_client) {// 提示信息
			try {
				temp_dato = new DataOutputStream(temp_client.getOutputStream());
				temp_dato.writeUTF("type:5," + send_id + "||" + send_name
						+ "||" + file_info);
				temp_dato.flush();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}// File_Info

	void Save_file(final String send_id, final String rec_id) {
		new Thread() {
			BufferedOutputStream buf_out;
			BufferedInputStream buf_in;
			public void run() {
				try {
					Thread.sleep(3000);
					Socket temp_send_client = list_client2.get(send_id);
					Socket temp_rec_client = list_client2.get(rec_id);
					if (null != temp_send_client && null != temp_rec_client) {
						// FileOutputStream outputStream = new FileOutputStream(
						// "./test.txt");
						// BufferedOutputStream buf_out = new
						// BufferedOutputStream(
						// outputStream);
						buf_out = new BufferedOutputStream(
								temp_rec_client.getOutputStream());
						buf_in = new BufferedInputStream(
								temp_send_client.getInputStream());
						// DataInputStream temp_data=new
						// DataInputStream(temp_client.getInputStream());
						int len = 0;
						byte[] b = new byte[1024];
//						Thread.sleep(3000);
						System.out.println("开始接收文件");
						while ((len = buf_in.read(b)) > 0) {
							buf_out.write(b, 0, len);
							// System.out.println(len);
							buf_out.flush();
						}// while
						System.out.println("接收完毕");
						// String to_id=temp_data.readUTF();
						// System.out.println("to_id:"+to_id);
						buf_out.close();
						buf_in.close();
						list_client2.remove(send_id);
						list_client2.remove(rec_id);
					}
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					try {
						buf_out.close();
						buf_in.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					System.out.println("文件发送断开:"+send_id);
					list_client2.remove(send_id);
					System.out.println("文件接收断开:"+rec_id);
					list_client2.remove(rec_id);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					try {
						buf_out.close();
						buf_in.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					System.out.println("文件发送断开:"+send_id);
					list_client2.remove(send_id);
					System.out.println("文件接收断开:"+rec_id);
					list_client2.remove(rec_id);
				}
			}
		}.start();
	}// Save_file

	public void File_server() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						if (null == server2) {
							server2 = new ServerSocket(8877);
						}
						Socket temp_client = server2.accept();
						DataInputStream temp_date = new DataInputStream(
								temp_client.getInputStream());
						String mesg = "";
						mesg = temp_date.readUTF();
						System.out.println("加入文件服务器：" + mesg);
						list_client2.put(mesg, temp_client);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}.start();
	}// File_server
}