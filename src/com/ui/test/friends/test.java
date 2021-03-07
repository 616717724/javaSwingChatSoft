package com.ui.test.friends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JButton;

import com.ui.test.Tree.TreeSwingNodeRenderer;

import java.awt.GridLayout;

public class test extends JFrame {

	private JPanel contentPane,ap[];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		ap=new JPanel[2];
//		for(int i=0;i<ap.length;i++){
//			ap[i]=new JPanel();
//			ap[i].setSize(200, 80);
//			ap[i].add(new JButton(""+i));
//			ap[i].setBackground(new Color(i*10, i*10, i*10));
//			contentPane.add(ap[i]);
//		}
//		JTree jTree=new JTree(new Friends_tree());
		JTree jTree=new JTree();
		jTree.setCellRenderer(new Friends_tree());
		JScrollPane scroll=new JScrollPane();
		contentPane.add(scroll);
		//contentPane.add(new Friend_panel());
		scroll.setViewportView(jTree);
	}

}
