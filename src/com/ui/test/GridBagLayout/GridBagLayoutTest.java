package com.ui.test.GridBagLayout;

import java.awt.*;
import javax.swing.*;

public class GridBagLayoutTest extends JFrame {
	public GridBagLayoutTest(String title) {
		super(title);
		initUI();
	}

	private void initUI() {
		this.setBounds(100, 100, 350, 200);
		this.setVisible(true);
		this.getContentPane().add(getPanelThree());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel getPanelOne() {
		JPanel jpl1 = new JPanel();
		jpl1.setLayout(new GridBagLayout());
		GridBagConstraints gb1 = new GridBagConstraints();
		JButton jb1 = new JButton("按钮1");
		gb1.insets = new Insets(15, 15, 15, 15);
		JButton jb2 = new JButton("按钮2");
		gb1.gridx = 0;
		gb1.gridy = 0;
		gb1.gridwidth = 2;
		gb1.gridheight = 1;
		gb1.fill = GridBagConstraints.BOTH;
		jpl1.add(jb1, gb1);
		gb1.gridx = 0;
		gb1.gridy = 1;
		jpl1.add(jb2, gb1);
		jpl1.setBackground(Color.CYAN);
		return jpl1;
	}

	private JPanel getPanelTwo() {
		JPanel jpl2 = new JPanel();
		jpl2.setLayout(new GridBagLayout());
		GridBagConstraints gb2 = new GridBagConstraints();
		JButton jb3 = new JButton("按钮3");
		gb2.gridx = 0;
		gb2.gridy = 0;
		gb2.gridwidth = 2;
		gb2.gridheight = 2;
		gb2.fill = GridBagConstraints.BOTH;
		jpl2.add(jb3, gb2);
		jpl2.setBackground(Color.WHITE);
		return jpl2;
	}

	private JPanel getPanelThree() {
		JPanel jpl3 = new JPanel();
		jpl3.setLayout(new GridBagLayout());
		GridBagConstraints gb3 = new GridBagConstraints();
		gb3.gridx = 0;
		gb3.gridy = 0;
		gb3.weightx = 2.0;
		gb3.weighty = 1.0;
		gb3.fill = GridBagConstraints.BOTH;
		jpl3.add(getPanelOne(), gb3);
		gb3.gridx = 1;
		gb3.gridy = 0;
		gb3.weightx = 3.0;
		jpl3.add(getPanelTwo(), gb3);
		return jpl3;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GridBagLayoutTest("网格包布局管理器");
	}
}
