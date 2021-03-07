package com.ui.test.list;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JListDemo4 {
	public JListDemo4() {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(3);
		jf.setSize(200, 200);
		// String[] data = {"one", "two", "three", "four","one", "two", "three",
		// "four","one", "two", "three", "four"};
		// JList list = new JList(data);
		JList list = new JList();
		JPanel[] panel = new JPanel[10];
		for (int i = 0; i < 10; i++) {
			panel[i] = new JPanel();
			panel[i].setSize(50, 50);
			panel[i].setLocation(0, (i + 1) * 50);
			JLabel label = new JLabel("郭靖");
			JButton btn = new JButton("按钮");
			panel[i].add(label);
			panel[i].add(btn);
			list.add(panel[i]);
		}
		list.remove(3);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 100));
		// scrollPane.add(list);
		scrollPane.setViewportView(list);
		jf.add(scrollPane);
		jf.setVisible(true);
	}

	public static void main(String[] args) {
		new JListDemo4();
	}
}
