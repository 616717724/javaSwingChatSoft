package com.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.conn.MysqlConnection;
import com.dao.GroupChat;
import com.dao.User;

public class Select {
	Connection conn = null;
	Statement sta = null;
	PreparedStatement pre = null;

	public Select() {

	}

	public Select(Connection conn) {
		this.conn = conn;
	}

	public List<User> Select_All_User() {
		List<User> list_user=new ArrayList<User>();
		try {
			String sql = "select * from user";
			sta = conn.createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				User user=new User();
//				System.out.print(res.getInt("ID") + "\t");
//				System.out.print(res.getString("u_id") + "\t");
//				System.out.print(res.getString("u_name") + "\t");
//				System.out.print(res.getString("u_password") + "\t");
//				System.out.print(res.getString("u_phone") + "\t");
//				System.out.println("");
				user.setId(res.getInt("id"));
				user.setU_name(res.getString("u_name"));
				user.setU_password(res.getString("u_password"));
				user.setU_phone(res.getString("u_phone"));
				user.setU_dept(res.getString("u_dept"));
				user.setU_satus(res.getInt("u_satus"));
				list_user.add(user);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println();
		return list_user;
	}//Select_All_User

	public boolean Check_Insert_User(String u_phone) {
		try {
			String sql = "select * from user where u_phone like " + u_phone;
			sta = conn.createStatement();
			ResultSet res = sta.executeQuery(sql);
			if (!res.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return false;
	}// Check_Inset_User

	public boolean Check_Login(String u_phone, String u_password) {
		try {
			String sql = "select * from user where u_phone like ? and u_password like MD5(?)";
			pre = conn.prepareStatement(sql);
			pre.setString(1, u_phone);
			pre.setString(2, u_password);
			ResultSet res = pre.executeQuery();
			if (res.next()) {
				sql = "update user set u_satus=1 where u_phone like ?";
				pre = conn.prepareStatement(sql);
				pre.setString(1, u_phone);
				pre.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return false;
	}// Check_Login
	
	public boolean Logout(String u_phone) {
		try {
			String sql = "update user set u_satus=0 where u_phone like ?";
			pre = conn.prepareStatement(sql);
			pre.setString(1, u_phone);
			pre.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return false;
	}// Logout

	public List<User> Select_friend_List(String u_phone) {
		List<User> list_user=new ArrayList<User>();
		try {
			//功能一:查询好友ID
			String sql = "select f_list from friend "
					+ "where f_uid =(select id from user where u_phone like '"+u_phone+"')";
			sta = conn.createStatement();
			ResultSet res = sta.executeQuery(sql);
			res.next();
			String res_string=res.getString("f_list");
			System.out.println(res_string);
			String[] res_list=res_string.split(",");
			for(String t:res_list){
				System.out.print("好友ID:"+t+"  ");
			}
			System.out.println("");
			
			//功能二:找到好友信息
			for(String t:res_list){
				sql="select * from user where id="+Integer.parseInt(t);
				res = sta.executeQuery(sql);
				
				User user=new User();
				while(res.next()){
					System.out.println("名字:"+res.getString("u_name")+" 电话:"+res.getString("u_phone"));
					user.setId(res.getInt("id"));
					user.setU_name(res.getString("u_name"));
					user.setU_password(res.getString("u_password"));
					user.setU_phone(res.getString("u_phone"));
					user.setU_dept(res.getString("u_dept"));
					user.setU_satus(res.getInt("u_satus"));
					list_user.add(user);
				}
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return list_user;
	}//Select_friend_List
	
	
	public String Select_U_name(String u_phone) {
		String u_name="";
		try {
			String sql = "select u_name from user where u_phone like ?";
			pre = conn.prepareStatement(sql);
			pre.setString(1, u_phone);
			ResultSet res = pre.executeQuery();
			if (res.next()) {
				u_name=res.getString("u_name");
				return u_name;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return u_name;
	}// Select_U_name
	
	public List<GroupChat> Select_Group(String u_phone) {
		List<GroupChat> group_list=new ArrayList<GroupChat>();
		try {
			String sql = "select g_id from group_list where u_id like ?";
			pre = conn.prepareStatement(sql);
			pre.setString(1, u_phone);
			ResultSet res = pre.executeQuery();
			String g_id="";
			while (res.next()) {
				g_id=g_id+res.getString("g_id")+",";
			}
			System.out.println("g_id:"+g_id);
			if(!"".equals(g_id)){
				String[] g_id_list=g_id.split(",");
				for(String tmp_g_id:g_id_list){
					sql = "select * from group_chat where id = ?";
					pre = conn.prepareStatement(sql);
					pre.setInt(1, Integer.parseInt(tmp_g_id));
//					System.out.println("tmp_g_id:"+tmp_g_id);
					res = pre.executeQuery();
					while (res.next()) {
						GroupChat group=new GroupChat();
						group.setID(res.getString("id"));
						group.setG_name(res.getString("g_name"));
						group.setG_list(res.getString("g_list"));
						group.setG_user_name_list(res.getString("g_user_name_list"));
						group.setG_main_id(res.getString("g_main_id"));
						group.setG_mag_list(res.getString("g_mag_list"));
						group.setG_notice(res.getString("g_notice"));
						group_list.add(group);
					}
				}	
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (null != sta) {
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		for(GroupChat tmp_g:group_list){
			System.out.println(tmp_g.getID());
		}
		return group_list;
	}// Select_U_name

	public static void main(String[] args) {
		Select s = new Select(MysqlConnection.CreateConnection());
		// s.SelectAllUser();
		// System.out.println(s.Check_Insert_User("15812962259"));
		//System.out.println(s.Check_Login("15812962259", "15812962259"));
//		s.Select_friend_List("15812962258");
		s.Select_Group("10001");
	}
}
