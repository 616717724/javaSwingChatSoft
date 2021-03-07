package com.ui.test;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.ui.setIcon.SetIcon;


public class Scroll extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scroll frame = new Scroll();
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
	public Scroll() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel main_panel = new JPanel();
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 1, 0, 0));
//		contentPane.add(panel, BorderLayout.CENTER);
//		panel1.add(panel);
		scrollPane.setViewportView(main_panel);
//		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		JTextArea textArea = new JTextArea();
		main_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel friend_panel = new JPanel();
//		Border b=new Border();
//		friend_panel.setBorder();
		main_panel.add(friend_panel);
		
		JLabel lblNewLabel = new JLabel("头像");
		Image image=SetIcon.getPicture("bg.png",50,50).getImage();
		ImageIcon icon=new ImageIcon(image);
		friend_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		lblNewLabel.setIcon(icon);
		friend_panel.add(lblNewLabel);
		friend_panel.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel panel = new JPanel();
		friend_panel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("用户名");
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("个性签名");
		panel.add(lblNewLabel_1);
		
		//JPanel panel_1] = new JPanel();
		JPanel[] panela= new JPanel[10];
		for(int i=0;i<panela.length;i++){
			panela[i]=new JPanel();
			panela[i].setBorder(BorderFactory.createTitledBorder(""));
		}
		main_panel.add(panela[1]);
		panela[1].setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("头像");
		panela[1].add(label, BorderLayout.WEST);
		
		panela[4] = new JPanel();
		panela[1].add(panela[4], BorderLayout.CENTER);
		panela[4].setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_1 = new JLabel("用户名");
		panela[4].add(label_1);
		
		JLabel label_2 = new JLabel("个性签名");
		panela[4].add(label_2);
		
		panela[2] = new JPanel();
		main_panel.add(panela[2]);
		panela[2].setLayout(new BorderLayout(0, 0));
		
		JLabel label_3 = new JLabel("头像");
		panela[2].add(label_3, BorderLayout.WEST);
		
		panela[5] = new JPanel();
		panela[2].add(panela[5], BorderLayout.CENTER);
		panela[5].setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_4 = new JLabel("用户名");
		panela[5].add(label_4);
		
		JLabel label_5 = new JLabel("个性签名");
		panela[5].add(label_5);
		
	
		

	}

}
