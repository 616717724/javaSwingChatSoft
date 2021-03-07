package com.socket.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.conn.MysqlConnection;
import com.dao.User;
import com.sql.Select;

public class ObjServer {
	ServerSocket server;
	public ObjServer(){
		init_Server();
	}
	void init_Server() {
		try {
			server = new ServerSocket(7788);
			System.out.println("服务器已启动");
			new Thread() {
				public void run() {
					while (true) {
						try {
							Socket client = server.accept();
//							InputStream input = client.getInputStream();
//							byte[] buf = new byte[1024];
//							int len;
//							len = input.read(buf);
//							String id = new String(buf, 0, len);
//							System.out.println(id);
//							System.out.println("客户端" + id + "已连接");
							Select select = new Select(MysqlConnection.CreateConnection());
							List<User> list_user=select.Select_friend_List("10003");
//							client.getOutputStream().write(1);
							ObjectOutputStream output =new ObjectOutputStream(client.getOutputStream());
							System.out.println("第一次发送");
							output.writeObject(list_user);
							System.out.println("第一次发送完毕");
							System.out.println("第二次发送完毕");
							output.writeObject(list_user);
							System.out.println("第二次发送完毕");
							//output.write(("已连接上主机" + "\n").getBytes());
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							 e.printStackTrace();
							//System.err.println("这个错误还不知道怎么处理....");
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
	public static void main(String[] args) {
		new ObjServer();
	}
}
