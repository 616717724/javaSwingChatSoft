package com.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.conn.MysqlConnection;

public class Insert {
	Connection conn = null;
	Statement sta = null;
	PreparedStatement pre = null;

	public Insert() {

	}

	public Insert(Connection conn) {
		this.conn = conn;
	}

	public boolean insert_user(String u_name, String u_phone, String u_password) {
		String sql="insert into user(u_name,u_phone,u_password) value(?,?,MD5(?))";
		try {
			pre=conn.prepareStatement(sql);
			pre.setString(1,u_name);
			pre.setString(2,u_phone);
			pre.setString(3,u_password);
			System.out.println(u_name);
			pre.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				if(pre!=null){
					pre.close();	
				}
//				if(conn!=null){
//					conn.close();
//				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return false;
	}
	public String create_group(String g_name, String g_list, String g_user_name_list,String g_main_id) {
		conn=MysqlConnection.CreateConnection();
		String sql="insert into group_chat(g_name,g_list,g_user_name_list,g_main_id,g_notice) value(?,?,?,?,'无')";
		try {
			pre=conn.prepareStatement(sql);
			pre.setString(1,g_name);
			pre.setString(2,g_list);
			pre.setString(3,g_user_name_list);
			pre.setString(4,g_main_id);
//			System.out.println(u_name);
			System.out.println("创建群"+g_name+"成功");
			pre.executeUpdate();
			sql="select ID from group_chat order by id desc limit 1";
			pre=conn.prepareStatement(sql);
			ResultSet re=pre.executeQuery();
			re.next();
			return re.getString("id");
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				if(pre!=null){
					pre.close();	
				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return "";
	}//create_group
	
	public static void main(String[] args) {
//		Insert insert=new Insert((MysqlConnection.CreateConnection()));
//		System.out.println(insert.insert_user("asdasd","1231231","123"));
		String s=new Insert().create_group("321", "10001,15812962257,10005,15812951124", "用户一,15812962257,我是四号,你好啊", "10001");
		System.out.println(s);
	}
}
