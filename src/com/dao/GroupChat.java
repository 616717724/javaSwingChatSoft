package com.dao;

import java.io.Serializable;

public class GroupChat implements Serializable{
	String ID,g_main_id;
	String g_name,g_list,g_mag_list,g_user_name_list,g_notice;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getG_main_id() {
		return g_main_id;
	}
	public void setG_main_id(String g_main_id) {
		this.g_main_id = g_main_id;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public String getG_list() {
		return g_list;
	}
	public void setG_list(String g_list) {
		this.g_list = g_list;
	}
	public String getG_mag_list() {
		return g_mag_list;
	}
	public void setG_mag_list(String g_mag_list) {
		this.g_mag_list = g_mag_list;
	}
	public String getG_notice() {
		return g_notice;
	}
	public void setG_notice(String g_notice) {
		this.g_notice = g_notice;
	}
	public String getG_user_name_list() {
		return g_user_name_list;
	}
	public void setG_user_name_list(String g_user_name_list) {
		this.g_user_name_list = g_user_name_list;
	}
}
