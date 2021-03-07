package com.ui.test.friends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ui.Txt_size;

public class Friend_panel extends JPanel implements Txt_size{
	public Friend_panel(){
		init();
	}
	@Override
	public void init() {
		// TODO 自动生成的方法存根
		this.setLayout(new BorderLayout(0, 0));
		this.setSize(200, 50);
		this.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		this.setPreferredSize(new Dimension(300, 50));
		
		JLabel label = new JLabel("头像");
		this.add(label, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("用户名");
		this.add(lblNewLabel, BorderLayout.CENTER);
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		
	}
	public Component getView() {
		return this;
	}

}
