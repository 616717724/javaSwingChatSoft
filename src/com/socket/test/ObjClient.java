package com.socket.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.dao.User;

public class ObjClient {
	public ObjClient() {
		init_Server();
	}

	void init_Server() {
		try {
			final Socket client = new Socket("127.0.0.1", 7788);
			// output = client.getOutputStream();
			// output.write((this_client_id+"").getBytes());
			new Thread() {
				public void run() {
					ObjectInputStream input = null;
					try {
						// output.write(("type:"+MESSAGE+",多余的消息").getBytes());
						// output.write(("多余的消息").getBytes());
						input = new ObjectInputStream(new BufferedInputStream(
								client.getInputStream()));
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						 e1.printStackTrace();
//						System.out.println("客户端连接失败");
					}
					while (true) {
						try {
							if (null != input) {
//								byte[] buf = new byte[1024];
//								int len = input.read(buf);
								List<User> u=(List<User>) input.readObject();
								for(User t:u){
									System.out.println(t.getU_name());	
								}
								System.out.println("开始再次接收");
								input.readObject();
								System.out.println("再次接收完成");
								// System.out.println("已连接上主机\n".equals(new
								// String(buf, 0, len)));
								// System.out.println("asdasd"+new String(buf,
								// 0, len));
							} else {
//								System.out.println("DDDDD");
							}
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							 e.printStackTrace();
							//System.out.println("与服务器连接中断!");
							try {
								if (null != client)
									client.close();
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								// e1.printStackTrace();
								System.out.println("连接关闭失败!");
							}
							break;
						}
					}// while
				}
			}.start();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			// e.printStackTrace();
			System.err.println("与服务器连接失败!");
		}
	}
	public static void main(String[] args) {
		new ObjClient();
	}
}
