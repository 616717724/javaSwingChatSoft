package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
	// general_information_system
	public static Connection CreateConnection() {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/general_information_system?"
				+ "user=root&password=123&useUnicode=true&characterEncoding=UTF8";
		try {
			// 动态加载mysql驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动程序");
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url);
			System.out.println("连接数据库成功");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.out.println("驱动加载失败");
		}
		catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.err.println(e.getMessage());
			System.out.println("连接失败");
		}
		return conn;
	}
	public static void main(String[] args) {
		MysqlConnection.CreateConnection();
	}
}
