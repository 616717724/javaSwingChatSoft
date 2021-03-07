package com.ui.panel.list.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeNode;
import javax.swing.JSplitPane;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.dao.User;
import com.socket.Client;
import com.ui.Txt_size;
import com.ui.panel.BgPanel;
import com.ui.panel.JTree.FriTreeNode;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class TJ extends JDialog implements Txt_size{

	private JPanel contentPane;
	JSplitPane splitPane;
	public static Add_friend_panel friend_panel,friend_panel2;
	private JTextField textField;
	int width = 600, height = 300;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	private Dimension screensize;
	List<User> list_user;
	String u_name;
	String u_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TJ frame = new TJ();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TJ() {
		init();
		initListener();
//		setStyle();
	}
	public TJ(String u_id,String u_name,List<User> list_user) {
		this.u_id=u_id;
		this.u_name=u_name;
		this.list_user=list_user;
		init();
		initListener();
		setModal(true);
		setVisible(true);
//		setStyle();
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);
		contentPane = new BgPanel("bg5.png");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		friend_panel = new Add_friend_panel(list_user);
		friend_panel2 = new Add_friend_panel("new");
//		getContentPane().add(friend_panel);
		
		JPanel panel = new JPanel();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, friend_panel, friend_panel2);
		splitPane.setBounds(217, 10, 355, 239);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.add(splitPane);
		setVisible(true);
		splitPane.setDividerLocation(0.5);
		
		textField = new JTextField();
		textField.setBounds(48, 70, 143, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("群名:");
		lblNewLabel.setBounds(12, 74, 55, 18);
		contentPane.add(lblNewLabel);
		
		final JLabel button = new JLabel("");
		button.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/confirm_button.png")));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String g_name=textField.getText();
				if(g_name.replace(" ","").equals("")||g_name.length()<1){
					JOptionPane.showMessageDialog(new JLabel(""),"群名不能为空");
				}else{
					List<TreeNode> f=friend_panel2.fritree.get_children_list();
					String id=u_id;
					String name=u_name;
					String name_list=u_name;
					String id_list=u_id;
					if(null!=f){
						for(TreeNode t:f){
							id=((FriTreeNode)t).getID();
							if(!id.equals(u_id)){
								id_list=id_list+","+id;
								name=((FriTreeNode)t).getUsername().getText();
								name_list=name_list+","+name;
								System.out.println("id:"+id+" name:"+name);	
							}
						}	
					}
					System.out.println("id_list:"+id_list);
					System.out.println("name_list:"+name_list);
					String mesg = "type:4,group_create||"+g_name+"||"+id_list+"||"+name_list+"||"+u_id;
					System.out.println("完整消息:" + mesg);
					try {
						Client.dato.writeUTF(mesg);
						Client.dato.flush();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						JOptionPane.showMessageDialog(new JLabel(""),
								"创建失败");
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JLabel(""),
							"创建成功");
					dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/confirm_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/confirm_button.png")));
			}
		});
		
		button.setBounds(75, 123, 83, 50);
		contentPane.add(button);
		
		final JLabel button_1 = new JLabel("");
		button_1.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/canel_button.png")));
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				button_1.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/canel_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_1.setIcon(new ImageIcon(TJ.class.getResource("/com/resource/img/canel_button.png")));
			}
		});
		
		button_1.setBounds(75, 182, 83, 38);
		contentPane.add(button_1);
//		setStyle();
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		
	}
	public static void add(String p_name,FriTreeNode aChild){
		if("old".equals(p_name)){
			friend_panel2.fritree.addchild(aChild);
			friend_panel2.jtree.updateUI();
		}else{
			friend_panel.fritree.addchild(aChild);
			friend_panel.jtree.updateUI();
		}
	}
	
	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
	}
}
