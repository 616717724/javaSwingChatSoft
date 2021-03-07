package com.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.conn.MysqlConnection;

public class Update {
	Connection conn = null;
	Statement sta = null;
	PreparedStatement pre = null;
	
	
	public void Update_All_Satus0(){
		try {
			conn=MysqlConnection.CreateConnection();
			String sql = "update user set u_satus=0";
			sta = conn.createStatement();
			sta.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}//Update_All_Satus0
	
	public boolean Update_User_Info(String u_phone,String u_name,String u_dept){
		try {
			conn=MysqlConnection.CreateConnection();
			System.out.println("u_phone:"+u_phone+" u_name:"+u_name+" u_dept:"+u_dept);
			String sql = "update user set u_name='"+u_name+"',u_dept='"+u_dept+"' where u_phone like '"+u_phone+"'";
			sta = conn.createStatement();
			sta.executeUpdate(sql);
			System.out.println(u_phone+"更新信息成功");
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}//Update_User_Info
	
	public void Update_User_Pwd(String u_phone,String u_password){
		try {
			conn=MysqlConnection.CreateConnection();
//			System.out.println("u_phone:"+u_phone+" u_name:"+u_name+" u_dept:"+u_dept);
			String sql = "update user set u_password=MD5('"+u_password+"') where u_phone like '"+u_phone+"'";
			sta = conn.createStatement();
			sta.executeUpdate(sql);
			System.out.println(u_phone+"更新密码成功");
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	
	public void Update_Group_Info(String id,String g_name,String g_notice){
		try {
			conn=MysqlConnection.CreateConnection();
//			System.out.println("u_phone:"+u_phone+" u_name:"+u_name+" u_dept:"+u_dept);
			String sql = "update group_chat set g_name='"+g_name+"', g_notice='"+g_notice+"' where id like '"+id+"'";
			sta = conn.createStatement();
			sta.executeUpdate(sql);
			System.out.println("群"+id+"更新信息成功");
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}//Update_Group_Info
	
	public void Update_Group_AddUser(String g_id,String u_id,String u_name){
		try {
			conn=MysqlConnection.CreateConnection();
//			System.out.println("u_phone:"+u_phone+" u_name:"+u_name+" u_dept:"+u_dept);
			String sql = "update group_chat set g_list=concat(g_list,',"+u_id+"'), g_user_name_list=concat(g_user_name_list,',"+u_name+"') where id like '"+g_id+"'";
			sta = conn.createStatement();
			sta.executeUpdate(sql);
			System.out.println("群"+g_id+"更新信息成功");
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}//Update_Group_Info
	
	public static void main(String[] args) {
//		new Update().Update_All_Satus0();
		String[] s=("13727582359||用户一||人力部/阿达瓦/阿萨德青蛙").split("\\|\\|");
//		System.out.println(s[0]);
//		new Update().Update_User_Info(s[0],s[1],s[2].replace("/","||"));
//		new Update().Update_Group_Info("10016","测试群三2","通知");
		new Update().Update_Group_AddUser("10001","asdasd","asdasd");
	}
}
