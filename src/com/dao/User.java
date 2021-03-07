package com.dao;

import java.io.Serializable;

//序列化用于ObjectScoket
public class User implements Serializable{
	int id;
	String u_name;
	String u_password;
	String u_phone;
	String u_dept;
	int u_satus;
	int u_account_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public int getU_satus() {
		return u_satus;
	}

	public void setU_satus(int u_satus) {
		this.u_satus = u_satus;
	}

	public int getU_account_status() {
		return u_account_status;
	}

	public void setU_account_status(int u_account_status) {
		this.u_account_status = u_account_status;
	}

	public String getU_dept() {
		return u_dept;
	}

	public void setU_dept(String u_dept) {
		this.u_dept = u_dept;
	}
}
