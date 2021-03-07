package com.socket;

import java.awt.SystemTray;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.dao.GroupChat;
import com.dao.User;
import com.ui.Friends_ChatFrame;
import com.ui.FristFrame;
import com.ui.Group_ChatFrame;
import com.ui.SecondFrame;
import com.ui.dialog.TipDialog;
import com.ui.panel.Login_panel;
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.panel.chat.Group_chat_all_panel;
import com.ui.panel.list.Friend_panel;
import com.ui.panel.list.Group_panel;

public class Client {
	/*
	 * @buf大小
	 */
	final int BUF_SIZE=1024;

	public static Socket client;
	public static List<User> list_user;
	List<GroupChat> list_group;
	Map<String,String> message_map=new HashMap<String,String>();
	public static Map<String,Friend_chat_all_panel> panel_map=new HashMap<String,Friend_chat_all_panel>();
	
	InputStream input;
	public static OutputStream output;
//	public static BufferedReader bufr;
//	public static BufferedWriter bufw;
	public static DataInputStream dati;
	public static DataOutputStream dato;
	
	public static ObjectInputStream obj_input=null;
	public static ObjectOutputStream obj_output=null;
	
	SecondFrame secondframe;
	TipDialog tip_dialog;
	public static Icon img=null;
	
	String user_id="";
	public static String user_pwd="";

	final int TYPE = 5;
	final int MESSAGE = 6;// 发送的信息为聊天消息
	final int LOGIN = 1;// 发送的信息为登录验证
	final int REGISTER = 2;// 发送的信息为注册
	final int UPDATE=3;//信息为更新消息
	final int ALL=4;//消息为广播消息

	/**
	 * Create the frame.
	 */
	public Client() {
		init_Server();
	}

	void init_Server() {
		try {
			client = new Socket("127.0.0.1", 7788);
//			client = new Socket("172.18.41.251", 7788);
//			client = new Socket("39.108.122.204", 7788);
//			String IP=JOptionPane.showInputDialog(null,"请输入IP地址","IP地址",JOptionPane.QUESTION_MESSAGE);
//			client = new Socket(IP, 7788);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				if (null != client) {
					client.close();
				}
				if (null != input) {
					input.close();
				}
				if (null != output) {
					output.close();
				}
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(new JLabel(""),
					"连接服务器失败");
			System.exit(0);
			System.out.println("客户端终止!");
		}
	}

	
	//注册
	public void register_info(final String u_name, final String u_phone,
			final String u_password) {
		final OutputStream client_output;
		final DataOutputStream client_dato;
		try {
			client_output= client.getOutputStream();;
			client_dato=new DataOutputStream(client_output);
			new Thread() {
				InputStream client_intput;
				DataInputStream client_dati;
				public void run() {
					try {
						String s = "type:" + REGISTER + "," + u_name + "||"
								+ u_phone + "||" + u_password;
						client_dato.writeUTF(s);
						client_dato.flush();
						client_intput= client.getInputStream();
						client_dati=new DataInputStream(client_intput);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					while (true) {
						try {
							String mesg=client_dati.readUTF();
							System.out.println(mesg);
							if ("注册成功".equals(mesg)) {
								JOptionPane.showMessageDialog(new JLabel(""),
										"注册成功");
								FristFrame.f.change_panel("登录");
								client_dati.close();
								client_dato.close();
								client_intput.close();
								client_output.close();
								client.close();
								FristFrame.client = null;
								break;
							} else if (!mesg.equals("已连接上主机\n")) {
								JOptionPane.showMessageDialog(new JLabel(""),
										mesg);
								break;
							}
						} catch (IOException e) {
							System.err.println("与服务器连接中断:108");
							JOptionPane.showMessageDialog(new JLabel(""),
									"与服务器连接中断...");
							break;
						}
					}// while
				}// run
			}.start();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}//register_info(final String u_name, final String u_phone,final String u_password)
	
	//登录----登录目前只能用InputStream接收否则会object会阻塞
	public void login_info(final String u_phone,final String u_password){
		try {
			output = client.getOutputStream();
			dato=new DataOutputStream(output);
			new Thread() {
				public void run() {
					try {
						String s = "type:" + LOGIN + ","+ u_phone + "||" + u_password;
						user_pwd=u_password;
						dato.writeUTF(s);
						dato.flush();
						input = client.getInputStream();
						dati=new DataInputStream(input);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					while (true) {
						try {
							System.out.println("等待登录信息");
							String mesg=dati.readUTF();
							System.out.println("接收登录信息");
							System.out.println(mesg);
							if ("登录成功".equals(mesg.substring(0,4))) {
								System.out.println("接收好友列表");
								if(null==obj_input){
									obj_input=new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
								}
								if(null==obj_output){
									obj_output=new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));	
								}
								System.out.println("开始接收好友列表");
								list_user=(List<User>)obj_input.readObject();
								System.out.println("接收好友列表完毕");
								System.out.println("开始接收群列表");
								list_group=(List<GroupChat>)obj_input.readObject();
								System.out.println("接收群列表完毕");
								final String u_name=mesg.substring(4);
								new Thread(){
									public void run(){
										secondframe=new SecondFrame(list_user,list_group,u_phone,u_name);
										secondframe.setVisible(true);
										user_id=u_phone;
										FristFrame.f.dispose();
										receive_message(client);
									}
								}.start();
								SystemTray.getSystemTray().remove(FristFrame.f.icon);
								break;
							} else if (!mesg.equals("已连接上主机\n")) {
								JOptionPane.showMessageDialog(new JLabel(""),
										mesg);
								Login_panel.btnlog.setIcon(new ImageIcon(Login_panel.class.getResource("/com/resource/img/login_button.png")));
								break;//break很重要,用于中断线程,结束接收消息
							}
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
							System.err.println("与服务器连接中断:207");
							break;
						} catch (ClassNotFoundException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}// while
				}// run
			}.start();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	
	public void receive_message(final Socket client){
		new Thread() {
			//InputStream client_input;
//			BufferedReader client_bufr;
			public void run() {
				while(true){
					try {
//						client_input = client.getInputStream();
//						client_bufr=new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
//						String mesg=client_bufr.readLine();
//						String mesg=bufr.readLine();
						System.out.println("等待接收消息:");
						String mesg=dati.readUTF();
						System.out.println("接收消息:"+mesg);
						String[] info = mesg.substring(MESSAGE + 1).split("\\|\\|");
						char kind = mesg.substring(TYPE, MESSAGE).toCharArray()[0];
						if((3==info.length&&kind=='6')||(5==info.length&&kind=='7')){
							System.out.println("接收图片");
							img=(Icon)obj_input.readObject();
							System.out.println("接收图片完毕");
							message_filter(mesg);
						}else{
							message_filter(mesg);	
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						JOptionPane.showMessageDialog(new JLabel(""),
								"接发消息出错!与服务器连接中断...");
						SystemTray.getSystemTray().remove(secondframe.icon);
//						secondframe.dispose();
						System.exit(0);
						break;
					} catch (ClassNotFoundException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}	
				}
			}
		}.start();
	}
	
	//过滤消息
	public void message_filter(String mesg){
		System.out.println("未过滤消息:"+mesg);
		char kind = mesg.substring(TYPE, MESSAGE).toCharArray()[0];
		mesg=mesg.substring(MESSAGE+1);
		switch(kind){
		case '3':
			console_update_message(mesg);
			break;
		case '4':
			console_update_group_message(mesg);
			break;
		case '5':
			file_info(mesg);
			break;
		case '6':
			chat_message(mesg);
			break;
		case '7':
			group_chat_message(mesg);
			break;
		}
	}//message_filter
	
	private void file_info(String message) {
		// TODO 自动生成的方法存根
		String[] mesg=message.split("\\|\\|");
		String my_id = SecondFrame.lbl_user_id;
		String my_name=SecondFrame.lbl_name_text;
		String send_id=mesg[0];
//		String send_name = mesg[1];
		String file_info = mesg[2];
		String u=mesg[1];
		if(file_info.subSequence(0,2).equals("接收")){
			System.out.println("对方接收");
			u=u+"接收文件:\n";
			String start_mesg="type:5,"+my_id+"||"+my_name+"||"+send_id+"||"+"开始发送文件";
			try {
				dato.writeUTF(start_mesg);
				send_file(send_id);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(file_info.subSequence(0,2).equals("拒绝")){
			System.out.println("对方拒绝");
			u=u+"拒绝接收文件:\n";
		}else if(!"开始发送文件".equals(file_info)){
			u=u+"发来文件:\n";
			tip_dialog = new TipDialog(send_id,mesg[1],file_info);
			tip_dialog.setVisible(true);
		}
		Juge_Create_Friend_Frame(mesg[0],mesg[1],u+file_info,img);
	}//file_info

	//发送文件
	void send_file(final String id) {
		// TODO 自动生成的方法存根
		try {
			new Thread(){
				String path=Friend_chat_all_panel.path;
				Socket temp_client=new Socket("127.0.0.1",8877);
//				Socket temp_client=new Socket("172.18.41.251",8877);
				DataOutputStream temp_out=new DataOutputStream(temp_client.getOutputStream());
				BufferedInputStream buf_in=new BufferedInputStream(new FileInputStream(path));
				BufferedOutputStream buf_out=new BufferedOutputStream(temp_client.getOutputStream());
				Friend_chat_all_panel temp_panel=panel_map.get(id);
				int len;
				byte[] b=new byte[BUF_SIZE];
				public void run(){
					try {
						long length = new File(path).length();//文件总大小
						long size = length / BUF_SIZE;//总共要传输多少次
						temp_out.writeUTF(SecondFrame.lbl_user_id);
						temp_panel.btn_file.setEnabled(false);
						Thread.sleep(3000);
						temp_client.setSoTimeout(2000);
						System.out.println("开始读");
						int count=0;
						while((len=buf_in.read(b))>0){
							buf_out.write(b,0,len);
							++count;
//							System.out.println("len:"+len);
							if(size>0){
								temp_panel.progressBar.setValue((int) (count*100 / size));
							}else{
								temp_panel.progressBar.setValue(100);
							}
							buf_out.flush();
						}
						buf_out.close();
						buf_in.close();
						temp_client.close();
						System.out.println("读完");
						temp_panel.setText("文件发送完成\n",path+"发送完成");
						temp_panel.btn_file.setEnabled(true);
					} catch (IOException e) {
//						// TODO 自动生成的 catch 块
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "错误:对方中断连接");
						temp_panel.btn_file.setEnabled(true);
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
						JOptionPane.showMessageDialog(null, "错误:对方中断连接");
						temp_panel.btn_file.setEnabled(true);
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
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//			buf_out.close();
	}//send_file

	//处理更新消息
	void console_update_message(String mesg){
		String[] update_message = mesg.split("\\|\\|");
//		System.out.println(mesg);
//		System.out.println(update_message[0]);
//		System.out.println(update_message[1]);
		String u_phone=update_message[0];
		String u_satus=update_message[1];
		System.out.println("接收到好友状态更新消息");
		System.out.println(mesg);
		if("login".equals(update_message[1])){
			u_satus="1";
			secondframe.Update_User_Satus(u_phone,u_satus);
		}else if("logout".equals(update_message[1])){
			u_satus="0";
			if(!update_message[0].equals(""))
			secondframe.Update_User_Satus(u_phone,u_satus);
		}else if("register".equals(update_message[1])){
//			System.out.println(update_message[2]);
			String u_name=update_message[2];
			secondframe.Add_User(u_phone, u_name);
		}else if("user_info".equals(update_message[0])){
			secondframe.Update_User_Info(update_message[1],update_message[2],update_message[3].replace("/","||"));
		}
	}//console_update_message
	
	//处理更新消息
	void console_update_group_message(String mesg){
		String[] update_message = mesg.split("\\|\\|");
		System.out.println("接收到群更新消息");
		System.out.println(mesg);
		if("group_info".equals(update_message[0])){
			if(4==update_message.length){
				secondframe.Update_Group_Info(update_message[1],update_message[2],update_message[3]);	
			}else{
				secondframe.Update_Group_Info(update_message[1],update_message[2],"");
			}
		}else if("group_create".equals(update_message[0])){
			GroupChat tg=new GroupChat();
			tg.setID(update_message[1]);
			tg.setG_name(update_message[2]);
			tg.setG_list(update_message[3]);
			tg.setG_user_name_list(update_message[4]);
			tg.setG_main_id(update_message[5]);
			tg.setG_notice("无");
			secondframe.Add_Group(tg);
		}else if("group_out".equals(update_message[0])){
			secondframe.Remove_User(update_message[1],update_message[2]);
		}else if("group_clean".equals(update_message[0])){
			secondframe.Remove_Group(update_message[1]);
		}else if("group_into".equals(update_message[0])){
			secondframe.Add_Group_User(update_message[1],update_message[2],update_message[3]);
		}
	}//console_update_group_message
	
	public void chat_message(String mesg){
		String[] message = mesg.split("\\|\\|");
		System.out.println(message[0]+"发来的消息:"+mesg);
		
//		System.out.println(message_map.get(message[0]));
//		System.out.println(null==message_map.get(message[0]));
//		String s="";
		String u=message[1]+"  "+message[2]+"\n";
		if(!user_id.equals(message[0])){
			if(4==message.length){
				if(Juge_Create_Friend_Frame(message[0],message[1],u+message[3],img)){
					//如果没有打开窗口，就有提示
					tips(message[0],message[1],u+message[3]);
					img=null;
				}
			}else{
				if(Juge_Create_Friend_Frame(message[0],message[1],u,img)){
					//如果没有打开窗口，就有提示
					tips(message[0],message[1],u);
				}
			}
		}
	}//chat_message
	
	void group_chat_message(String mesg){
		System.out.println("群聊消息:"+mesg);
		String[] message = mesg.split("\\|\\|");
		System.out.println(message[3]+" 群发来消息:"+mesg);
		
//		String s="";
		String u=message[1]+"  "+message[4]+"\n";
		if(!user_id.equals(message[0])){
			if(6==message.length){
				if(Juge_Create_Group_Frame(message[2],message[3],u+message[5],img)){
					//如果没有打开窗口，就有提示
					tips(message[2],message[3],u+message[5]);
				}
			}else{
				if(Juge_Create_Group_Frame(message[2],message[3],u,img)){
					//如果没有打开窗口，就有提示
					tips(message[2],message[3],u);
					img=null;
				}
			}
		}else{
			img=null;
		}
	}//group_chat_message
	
	//true为没打开
		boolean Juge_Create_Friend_Frame(String u_phone,String u_name,String text,Icon img){
			String icon=u_name.substring(0,1);
			if (0 == Friend_panel.table_id.size()) {
				Friend_panel.fri_chat_frame = new Friends_ChatFrame(icon, u_name, u_phone);
				Friend_panel.fri_chat_frame.setText(text,img);
				Friend_panel.table_id.add(u_phone);
				return true;
			} else {
				int tmpnub = 0;
				for (String tmp : Friend_panel.table_id) {
					if (tmp.equals(u_phone)) {
						System.out.println("好友已在聊天框");
						Friend_panel.fri_chat_frame.getTabbedPane().setSelectedIndex(tmpnub);
						Friend_chat_all_panel tmp_panel=(Friend_chat_all_panel)Friend_panel.fri_chat_frame.getTabbedPane().getSelectedComponent();
						tmp_panel.setText(text,img);
//						Friend_panel.fri_chat_frame.setText(text);
						return false;
//						break;
					} else {
						if (tmpnub >= (Friend_panel.fri_chat_frame.getTabbedPane()
								.getTabCount() - 1)) {
							Friend_chat_all_panel all_panel = new Friend_chat_all_panel(
									icon, u_name, u_phone);
							Friend_panel.fri_chat_frame.getTabbedPane().addTab(u_name, null,
									all_panel);
							Friend_panel.fri_chat_frame.getTabbedPane().setSelectedIndex(
									Friend_panel.fri_chat_frame.getTabbedPane()
											.getTabCount() - 1);
							Friend_panel.table_id.add(u_phone);
							all_panel.setText(text,img);
							return true;
//							break;
						}
//						System.out.println(tmpnub);
					}
					tmpnub++;
//					System.out.println(tmpnub);
				}
			}
			return true;
		}//
		
		//true为没打开
		boolean Juge_Create_Group_Frame(String id,String name,String text,Icon img){
//			String icon=u_name.substring(0,1);
//			List<User> list_user=Group_panel.map__group_chat_list.get(id);
			List<User> list_user = Group_panel.getUserList(id);
			GroupChat group_chat=Group_panel.map__group_chat.get(id);
			if(0==Group_panel.table_id.size()){
//				System.out.println("g_id:"+id);
//				List<User> temp_user=map__group_chat_list.get(id);
//				group_chat_frame=new Group_ChatFrame(temp_user,id,name);
				Group_panel.group_chat_frame=new Group_ChatFrame(list_user,group_chat);
				Group_panel.group_chat_frame.setText(text,img);
				Group_panel.table_id.add(id);
				return true;
			}else{
				int tmpnub=0;
				for(String tmp:Group_panel.table_id){
					if(tmp.equals(id)){
						System.out.println("群已在聊天框");
						Group_panel.group_chat_frame.getTabbedPane().setSelectedIndex(tmpnub);
						Group_chat_all_panel tmp_panel=(Group_chat_all_panel)Group_panel.group_chat_frame.getTabbedPane().getSelectedComponent();
						tmp_panel.setText(text,img);
						return false;
					}else{
						if(tmpnub>=(Group_panel.group_chat_frame.getTabbedPane().getTabCount()-1)){
//							List<User> temp_user=map__group_chat_list.get(id);
//							Group_chat_all_panel all_panel = new Group_chat_all_panel(temp_user,id,name);
							Group_chat_all_panel all_panel = new Group_chat_all_panel(list_user,group_chat);
							Group_panel.group_chat_frame.getTabbedPane().addTab(name, null, all_panel);
							Group_panel.group_chat_frame.getTabbedPane().setSelectedIndex(Group_panel.group_chat_frame.getTabbedPane().getTabCount()-1);
							Group_panel.table_id.add(id);
							all_panel.setText(text,img);
							return true;
						}
//						System.out.println(tmpnub);
					}
					tmpnub++;
//					System.out.println(tmpnub);
				}
			}
			return true;
		}//Juge_Create_Group_Frame
		
		
	void tips(String id,String name,String info){
		if(null==message_map.get(id)){
			tip_dialog = new TipDialog(id,name,"");
			tip_dialog.setMap(message_map);
//			tip_dialog.setText(u+s);
			message_map.put(id,info);
			tip_dialog.setVisible(true);
		}else{
			info=message_map.get(id)+"\n"+info;
			message_map.put(id,info);
//			tip_dialog.setText(s);
			if(!tip_dialog.isVisible()){
				tip_dialog.setVisible(true);
			}
//			System.out.println("尚未打开的消息:"+s);
		}
	}//tips

}
