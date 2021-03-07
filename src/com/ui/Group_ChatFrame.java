package com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dao.GroupChat;
import com.dao.User;
import com.ui.panel.TopPanel;
import com.ui.panel.chat.Group_chat_all_panel;
import com.ui.panel.list.Friend_panel;
import com.ui.panel.list.Group_panel;
import com.ui.setIcon.SetIcon;

public class Group_ChatFrame extends JFrame implements Txt_size {

	// private JPanel contentPane;
	Dimension screensize;
	int width = 1024, height = 550;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	public ImageIcon icon = null;
	public Image img = SetIcon.getPicture(
			"logo.png", 40, 40).getImage();
	public static JTabbedPane tabbedPane;

	private Friend_panel friend_panel_list;// 成员列表
	Group_chat_all_panel all_panel;
	String name_icon = "群";// 用户名头像
	String lbl_name_text = "群名";// 用户名
	String lbl_user_id = "群账号";// 用户账号
	String notie = "暂无";// 群公告
	int pepole_now = 0, pepole_all = 0;// 群人数
	
	List<User> list_user;
	GroupChat group_chat;
	private String text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Group_ChatFrame frame = new Group_ChatFrame();
					frame.setVisible(true);
					// frame.txtpwd.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Group_ChatFrame(String lbl_user_id,String lbl_name_text) {
//		private Friend_panel friend_panel_list;// 成员列表
//		String name_icon = "群";// 用户名头像
//		String lbl_name_text = "群名";// 用户名
//		String lbl_user_id = "群账号";// 用户账号
//		String notie = "暂无";// 群公告
//		int pepole_now = 0, pepole_all = 0;// 群人数
		this.lbl_user_id=lbl_user_id;
		this.lbl_name_text=lbl_name_text;
		init();
		setVisible(true);
		// init_panel();
	}
	
	public Group_ChatFrame(List<User> list_user,String lbl_user_id,String lbl_name_text) {
		this.list_user=list_user;
		this.lbl_user_id=lbl_user_id;
		this.lbl_name_text=lbl_name_text;
		init();
		setVisible(true);
		// init_panel();
	}
	
	public Group_ChatFrame(List<User> list_user,GroupChat group_chat) {
		this.list_user=list_user;
		this.group_chat=group_chat;
		this.lbl_user_id=group_chat.getID()+"";
		this.lbl_name_text=group_chat.getG_name();
		init();
		setVisible(true);
		// init_panel();
	}
	
	public Group_ChatFrame() {
		init();
		setVisible(true);
		// init_panel();
	}

	private void init_panel() {
		// 设置字体
	}

	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);

		setTitle("群聊窗口");
		setIconImage(img);
		setDefaultCloseOperation(3);
		setStyle();
		setUndecorated(true);

		JDialog tmp_d = new JDialog(this, "");
		tmp_d.setSize(this.getWidth(), this.getHeight());
		// 通过d去设置JFrame
		JPanel top = new TopPanel(tmp_d, 2);
		getContentPane().add(top, BorderLayout.NORTH);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(new Color(13,196,254));
		tabbedPane.setOpaque(true);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
//		Group_chat_all_panel all_panel = new Group_chat_all_panel(list_user,lbl_user_id,lbl_name_text);
		all_panel = new Group_chat_all_panel(list_user,group_chat);
		all_panel.setFrame(this);
		tabbedPane.addTab(lbl_name_text, null, all_panel);

	}

	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根

	}

	public Friend_panel getFriend_panel_list() {
		return friend_panel_list;
	}

	public void setFriend_panel_list(Friend_panel friend_panel_list) {
		this.friend_panel_list = friend_panel_list;
	}

	public String getName_icon() {
		return name_icon;
	}

	public void setName_icon(String name_icon) {
		this.name_icon = name_icon;
	}

	public String getLbl_name_text() {
		return lbl_name_text;
	}

	public void setLbl_name_text(String lbl_name_text) {
		this.lbl_name_text = lbl_name_text;
	}

	public String getLbl_user_id() {
		return lbl_user_id;
	}

	public void setLbl_user_id(String lbl_user_id) {
		this.lbl_user_id = lbl_user_id;
	}

	public String getNotie() {
		return notie;
	}

	public void setNotie(String notie) {
		this.notie = notie;
	}

	public int getPepole_now() {
		return pepole_now;
	}

	public void setPepole_now(int pepole_now) {
		this.pepole_now = pepole_now;
	}

	public int getPepole_all() {
		return pepole_all;
	}

	public void setPepole_all(int pepole_all) {
		this.pepole_all = pepole_all;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
	public void setText(String text,Icon img) {
		this.text = text;
		all_panel.setText(text,img);
	}

	public void remove_group(String g_id) {
		// TODO 自动生成的方法存根
		int tempnub = 0;
		for (String tmp : Group_panel.table_id) {
			if (tmp.equals(g_id)) {
//				System.out.println("remove:" + tempnub);
				JOptionPane.showMessageDialog(new JLabel(""),"该群已被解散");
				// System.out.println("size"+Friend_panel.table_id.size());
				Group_panel.table_id.remove(tempnub);// 移除list
				Group_ChatFrame.tabbedPane.remove(tempnub);// 移除table
				if (0 == Group_panel.table_id.size()) {
					this.dispose();
				}
				break;
			}
			tempnub++;
//			System.out.println(tempnub);
		}
	}//remove_group
//	public void Remove_User(String u_phone){
//		all_panel.Remove_User(u_phone);
//	}//Remove_User
}
