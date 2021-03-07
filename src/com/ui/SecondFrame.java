package com.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeModel;

import com.dao.GroupChat;
import com.dao.User;
import com.ui.dialog.Update_User_Info_Dialog;
import com.ui.panel.TopPanel;
import com.ui.panel.JTree.FriTreeNode;
import com.ui.panel.list.Friend_panel;
import com.ui.panel.list.Group_panel;
import com.ui.panel.list.test.TJ;
import com.ui.setIcon.SetIcon;

import javax.swing.ImageIcon;

public class SecondFrame extends JDialog implements Txt_size {

	int fminwitdh = 300, fminheight = 500;// 最小高宽
	int windx = 500, windy = 200;// 默认位置
	Dimension screensize;
	public TrayIcon icon;
	private JPanel contentPane;
	JPanel center_panel, friend_panel;
	JPanel userinfo_panel;
	static Friend_panel frilist_panel;
	Group_panel seclist_panel;
	JPanel toppanel;
	JPanel footpanel;
	FriTreeNode fritree;
	JLabel add_button;
	DefaultTreeModel model;
	JTree jtree;
	JScrollPane friscroll, groupscroll, recenscroll;
	public static JLabel lbl_username;
	public static JLabel lbl_usericon;
	private JPanel username_panel;
	public static JLabel lbl_usernumb;
	public static JTabbedPane tabbedPane;
	String name_icon = "李";// 用户名头像
	public static String lbl_name_text = "用户名";// 用户名
	public static String lbl_user_id = "用户账号";// 用户账号

	static List<User> list_user;
	List<GroupChat> list_group_chat;
	User user;
	Update_User_Info_Dialog user_info_dialog=null;
	
	BufferedReader bufr;
	BufferedWriter bufw;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecondFrame frame = new SecondFrame();
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
	public SecondFrame() {
		init();
		initListener();
		initTrayIcon();
	}
	
	public SecondFrame(List<User> list_user,String lbl_user_id,String lbl_name_text) {
		this.list_user=list_user;
		this.lbl_name_text = lbl_name_text;
		this.lbl_user_id = lbl_user_id;
		init();
		initListener();
		initTrayIcon();
		setStyle();
	}
	
	public SecondFrame(List<User> list_user,List<GroupChat> list_group_chat,String lbl_user_id,String lbl_name_text) {
		this.list_group_chat=list_group_chat;
		this.list_user=list_user;
		this.lbl_name_text = lbl_name_text;
		this.lbl_user_id = lbl_user_id;
		user=new User();
		user.setU_phone(lbl_user_id);
		user.setU_name(lbl_name_text);
		for(User temp:list_user){
			if(temp.getU_phone().equals(lbl_user_id)){
				user.setU_dept(temp.getU_dept());
				user.setU_password(temp.getU_password());
			}
		}
		init();
		initListener();
		initTrayIcon();
		setStyle();
	}
	
	public SecondFrame(List<User> list_user,User user) {
		this.user=user;
		this.list_user=list_user;
		this.lbl_name_text = user.getU_name();
		this.lbl_user_id = user.getU_phone();
		init();
		initListener();
		initTrayIcon();
		setStyle();
	}

	public void init() {
		// TODO 自动生成的方法存根
		setUndecorated(true);
//		setAlwaysOnTop(true);
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		windx = ((int) screensize.getWidth() - fminwitdh);
		windy = 0;
		setBounds(windx, windy, fminwitdh, fminheight);
		// 设置顶部面板
		toppanel = new TopPanel(this, 0);
		getContentPane().add(toppanel, BorderLayout.NORTH);

		// 设置底部面板
		footpanel = new JPanel();
		add_button = new JLabel("");
		add_button.setIcon(new ImageIcon(SecondFrame.class.getResource("/com/resource/img/create_group_button.png")));

		// 设置中间面板
		center_panel = new JPanel();
		center_panel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(center_panel);
		userinfo_panel = new JPanel();
//		userinfo_panel.setBackground(Color.WHITE);
		userinfo_panel.setBackground(new Color(29,178,255));

		userinfo_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		center_panel.add(userinfo_panel, BorderLayout.NORTH);

		lbl_usericon = new JLabel(lbl_name_text.substring(0,1));
		lbl_usericon.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_usericon.setPreferredSize(new Dimension(50,50));
		lbl_usericon.setForeground(Color.WHITE);
		lbl_usericon.setFont(new Font("宋体", Font.PLAIN, 35));
		lbl_usericon.setOpaque(true);
		lbl_usericon.setBorder(new CompoundBorder(new EtchedBorder(),
				new LineBorder(Color.white)));
		lbl_usericon.setBackground(new Color(13, 196, 254));
		userinfo_panel.add(lbl_usericon);

		username_panel =  new JPanel();
		username_panel.setPreferredSize(new Dimension(100,50));
//		username_panel.setOpaque(false);
//		username_panel.setBackground(null);// 把背景设置为会  
//		buttonPanel.setOpaque(false);
//		username_panel.setBackground(Color.WHITE);
		username_panel.setBackground(new Color(29,178,255));
		username_panel.setLayout(new GridLayout(0, 1, 0, 0));
		userinfo_panel.add(username_panel);

		lbl_username = new JLabel(lbl_name_text);
		lbl_username.setHorizontalAlignment(SwingConstants.LEFT);
//		lbl_username.setOpaque(true);
		username_panel.add(lbl_username);

		lbl_usernumb = new JLabel(lbl_user_id);
		lbl_usernumb.setHorizontalAlignment(SwingConstants.LEFT);
//		lbl_usernumb.setOpaque(true);
		username_panel.add(lbl_usernumb);

		// 设置好友面板
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(false);
		tabbedPane.setBackground(new Color(255, 255, 255, 200));
		center_panel.add(tabbedPane, BorderLayout.CENTER);

		frilist_panel = new Friend_panel(list_user);
//		tabbedPane.addTab("好友", new ImageIcon(SecondFrame.class.getResource("/com/resource/img/tab_boddy.png")), frilist_panel, "好友面板");
		ImageIcon icon=new ImageIcon(SecondFrame.class.getResource("/com/resource/img/tab_boddy.png"));
		Image img=icon.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon= new ImageIcon(img);
		tabbedPane.addTab("",icon, frilist_panel, "好友面板");
		seclist_panel = new Group_panel(list_group_chat);
//		tabbedPane.addTab("群聊", new ImageIcon(SecondFrame.class.getResource("/com/resource/img/tab_group.png")), seclist_panel, "群聊面板");
		icon=new ImageIcon(SecondFrame.class.getResource("/com/resource/img/tab_group.png"));
		img=icon.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon= new ImageIcon(img);
		tabbedPane.addTab("",icon, seclist_panel, "群聊面板");
		footpanel.add(add_button);
		getContentPane().add(footpanel, BorderLayout.SOUTH);

		// 设置用户信息面板
		init_userpanel();
		setStyle();

	}
	public void Reload(List<User> list_user){
//		frilist_panel.Reload(list_user);
	}
	public void Update_User_Satus(String u_phone,String u_satus){
		frilist_panel.Update_User_Satus(u_phone,u_satus);
	}
	public void Add_User(String u_phone,String u_name){
		frilist_panel.Add_User(u_phone,u_name);
	}
	public void Update_User_Info(String u_phone,String u_name,String dep){
		frilist_panel.Update_User_Info(u_phone, u_name, dep);
	}
	public void Update_Group_Info(String id,String g_name,String g_notice){
		seclist_panel.Update_Group_Info(id,g_name,g_notice);
	}
	public void Add_Group(GroupChat group_chat){
		seclist_panel.Add_Group(group_chat);
	}
	public void Remove_Group(String g_id){
		seclist_panel.Remove_Group(g_id);
	}
	public void Remove_User(String id,String u_phone){
		seclist_panel.Remove_User(id, u_phone);
	}//Remove_User
	public void Add_Group_User(String id,String u_phone,String u_name){
		seclist_panel.Add_User(id, u_phone, u_name);
	}

	public void init_userpanel() {
		friend_panel = new JPanel();
	}
	public void Group_AddUser(String g_id,String u_phone,String u_name){
		
	}

	public void initListener() {
		// TODO 自动生成的方法存根
		lbl_usericon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_usericon.setBorder(new CompoundBorder(new EtchedBorder(),
						new LineBorder(Color.gray)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_usericon.setBorder(new CompoundBorder(new EtchedBorder(),
						new LineBorder(Color.white)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
//				if(null==user_info_dialog){
					user_info_dialog=new Update_User_Info_Dialog(user);
					user_info_dialog.setUser_info_dialog(user_info_dialog);
//				}
			}
		});
		
		add_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println("修改信息按钮");
				new TJ(lbl_user_id,lbl_name_text,list_user);
				System.out.println(Friend_panel.user_fritree);
//				frilist_panel.search_expand_node();
//				frilist_panel.Add_User("123","2123");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				add_button.setIcon(new ImageIcon(SecondFrame.class.getResource("/com/resource/img/create_group_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				add_button.setIcon(new ImageIcon(SecondFrame.class.getResource("/com/resource/img/create_group_button.png")));
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	// TODO 这个地方可以考虑，tray就只要一个，在主窗体出来之后换掉其上面的监听
	private void initTrayIcon() {
		if (SystemTray.isSupported()) {
			try {
				final SystemTray tray = SystemTray.getSystemTray();
				icon = new TrayIcon(SetIcon.getPicture(
						"logo.png", 40, 20).getImage(), "system");
				icon.setImageAutoSize(true); // 自动适应大小
				icon.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							setVisible(true);
							// 获取焦点
							requestFocus();
						}
					}
				});

				PopupMenu pm = new PopupMenu();
				MenuItem mit = new MenuItem("Exit");
				mit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tray.remove(icon);
						System.exit(0);
					}
				});
				pm.add(mit);
				icon.setPopupMenu(pm);
				tray.add(icon);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public static void setTabbedPane(JTabbedPane tabbedPane) {
		SecondFrame.tabbedPane = tabbedPane;
	}

	public String getLbl_name_text() {
		return lbl_name_text;
	}

	public void setLbl_name_text(String lbl_name_text) {
		this.lbl_name_text = lbl_name_text;
	}
}
