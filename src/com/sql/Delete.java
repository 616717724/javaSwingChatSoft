package com.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.conn.MysqlConnection;

public class Delete {
	Connection conn = null;
	Statement sta = null;
	PreparedStatement pre = null;
	public Delete(Connection conn) {
		this.conn = conn;
	}
	public void Delete_Group(String id){
		try {
			String sql="delete from group_chat where id="+id;
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
	}//Delete_Group
	
	public static void main(String[] args) {
		
	}
}
