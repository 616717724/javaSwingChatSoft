package com.ui;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface Txt_size {
	int txtwitdh=200,txtheight=35;//登录注册输入框大小
	
	//顶部面板
	int lblwitdh=40,lblheight=20;
	Point point = new Point();//鼠标移动
	void init();
	void initListener();
	public void paint(Graphics g);
}
