package com.ui.test.Tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.ui.setIcon.SetIcon;

public class JTreeList extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTreeList frame = new JTreeList();
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
	public JTreeList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init();
	}

	void init() {
		Image image = SetIcon.getPicture("bg.png", 50, 50).getImage();
		ImageIcon icon = new ImageIcon(image);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
		JLabel name = new JLabel("name");
		JLabel text = new JLabel("text");
		JLabel id = new JLabel("id");
		FriTreeSwingNode ftn = new FriTreeSwingNode(name, text, icon, id);
		for (int i = 0; i < 5; i++) {
			FriTreeSwingNode ftn2 = new FriTreeSwingNode(name, text, icon, id);
			for (int j = 0; j < 5; j++) {
				name = new JLabel("name" + j);
				text = new JLabel("text" + j);
				id = new JLabel("id" + j);
				ftn2.addchild(new FriTreeSwingNode(name, text, icon, id));
			}
			name = new JLabel("name" + i);
			text = new JLabel("text" + i);
			id = new JLabel("id" + i);
			ftn.addchild(ftn2);
		}
		// FriTreeNode ftn=new FriTreeNode("name","text", icon, "id");
		// for(int i=0;i<5;i++){
		// ftn.addchild(new FriTreeNode("name"+i,"text"+i, icon, "id"+i));
		// }
		FriTreeSwingRender ftr = new FriTreeSwingRender();
		JTree jTree = new JTree(ftn);
		jTree.setCellRenderer(ftr);
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setViewportView(jTree);
	}

}
