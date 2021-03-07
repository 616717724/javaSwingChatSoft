package com.ui.panel.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.dao.GroupChat;
import com.dao.User;
import com.ui.Friends_ChatFrame;
import com.ui.Group_ChatFrame;
import com.ui.Txt_size;
import com.ui.panel.JTree.FriTreeNode;
import com.ui.panel.JTree.FriTreeNodeRenderer;
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.panel.chat.Group_chat_all_panel;

public class Group_panel extends JPanel implements Txt_size {

	FriTreeNode fritree;
	DefaultTreeModel model;
	JTree jtree;
	FriTreeNode move_temp_fritree = null, click_temp_fritree = null;
	List<GroupChat> list_group_chat;
	public static Map<String, List<User>> map__group_chat_list = new HashMap<String, List<User>>();
	public static Map<String, GroupChat> map__group_chat = new HashMap<String, GroupChat>();

	JPanel userinfo_panel;
	JPanel friend_panel;
	private JLabel lbl_usericon;
	private JPanel username_panel;
	private JLabel lbl_usernumb;
	public JScrollPane groscroll;
	Color clickcolor = new Color(248, 226, 138);
	Color movecolor = new Color(247, 226, 191);
	public static Group_ChatFrame group_chat_frame;
	public static List<String> table_id;
	private Friend_panel friend_panel_list;// 成员列表
	// String name_icon = "群";// 用户名头像
	// String lbl_name_text = "群名";// 用户名
	// String lbl_user_id = "群账号";// 用户账号
	// String notie="暂无";//群公告
	int pepole_now = 0, pepole_all = 0;// 群人数

	public Group_panel() {
		table_id = new ArrayList<String>();
		init();
		initListener();
	}

	public Group_panel(List<GroupChat> list_group_chat) {
		this.list_group_chat = list_group_chat;
		table_id = new ArrayList<String>();
		 for (GroupChat tmp : this.list_group_chat) {
		 map__group_chat.put(tmp.getID() + "", tmp);
		 String[] g_list = tmp.getG_list().split(",");
		 // String[] g_user_name_list = tmp.getG_user_name_list().split(",");
		 List<User> temp_list_user = new ArrayList<User>();
		 for (int i = 0; i < g_list.length; i++) {
		 User temp_user = new User();
		 temp_user.setU_phone(g_list[i]);
		 // temp_user.setU_name(g_user_name_list[i]);
		 String
		 name=Friend_panel.user_fritree.get(temp_user.getU_phone()).getUsername().getText();
		 temp_user.setU_name(name);
		 temp_user.setU_dept("群");
		 temp_user.setU_satus(1);
		 if (temp_user.getU_phone().equals(tmp.getG_main_id() + "")) {
		 temp_user.setU_satus(3);
		 }
		 // temp_user.setU_dept(g_user_name_list[i]);
		 temp_list_user.add(temp_user);
		 }
		 map__group_chat_list.put(tmp.getID() + "", temp_list_user);
		 }
//		for (GroupChat tmp : this.list_group_chat) {
//			map__group_chat.put(tmp.getID() + "", tmp);
//		}
		init();
		initListener();
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		setLayout(new BorderLayout());
		// fritree = addtree_test();
		fritree = addtree_list();
		model = new DefaultTreeModel(fritree);
		model.addTreeModelListener(null);// 添加动态事件
		jtree = new JTree(model);
		jtree.setToggleClickCount(1);// 点击次数
		// jTree.setCellEditor(new DefaultCellEditor(textField));//设置可编辑时的文本框
		// jTree.setInvokesStopCellEditing(true);// 修改节点文字之后生效
		jtree.setRootVisible(false);
		jtree.putClientProperty("JTree.lineStyle", "None");
		jtree.setCellRenderer(new FriTreeNodeRenderer());

		groscroll = new JScrollPane();
		groscroll.setViewportView(jtree);
		// 屏蔽横向滚动条
		groscroll
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(groscroll, BorderLayout.CENTER);
	}

	public FriTreeNode addtree_list() {
		String id = "id";
		String name = "name";
		FriTreeNode ftn = new FriTreeNode(id, name, id);
		for (GroupChat t : list_group_chat) {
			id = t.getID() + "";
			name = t.getG_name();
			FriTreeNode ftn2 = new FriTreeNode(id, name, id);
			ftn.addchild(ftn2);
		}
		return ftn;
	}

	public FriTreeNode addtree_test() {
		String[] nameString = { "李狗蛋", "王大厨", "张沙发", "大煞笔", "己二三", "巳自己", "甲发",
				"乙真人", "嘿嘿嘿" };
		String id = "id";
		String name = "name";
		String text = "text";
		FriTreeNode ftn = new FriTreeNode(id, name, text);
		for (int i = 0; i < 15; i++) {
			id = id + i;
			Random r = new Random();
			FriTreeNode ftn2 = new FriTreeNode(id, nameString[r.nextInt(9)],
					text);
			// for (int j = 0; j < 5; j++) {
			// id = id + j;
			// text = text + j;
			// ftn2.addchild(new FriTreeNode(id, nameString[r.nextInt(9)],
			// text));
			// }
			ftn.addchild(ftn2);
		}
		return ftn;
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		jtree.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO 自动生成的方法存根
				TreePath path = jtree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					FriTreeNode object = (FriTreeNode) path
							.getLastPathComponent();
					// 如果不是在同一个UserPanel移动时才重新设置颜色
					if (null != move_temp_fritree
							&& !move_temp_fritree.equals(object)) {
						// 把前一个颜色还原
						if (null != move_temp_fritree
								&& !move_temp_fritree.getUserPanel()
										.getBackground().equals(clickcolor)) {
							move_temp_fritree.getUserPanel().setBackground(
									Color.WHITE);
							move_temp_fritree.getInfoPanel().setBackground(
									Color.WHITE);
							model.reload(move_temp_fritree);
						}
						// 如果UserPanel不是被选中时设置移动的颜色
						if (!object.getUserPanel().getBackground()
								.equals(clickcolor)) {
							object.getUserPanel().setBackground(movecolor);
							object.getInfoPanel().setBackground(movecolor);
							System.out.println(object.getUsername().getText());
							// new
							// Iflytek().start(object.getUsername().getText());
						}
					}
					move_temp_fritree = object;
					model.reload(object);
				}
			}// mouseMoved

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO 自动生成的方法存根

			}
		});

		jtree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					TreePath path = jtree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						FriTreeNode object = (FriTreeNode) path
								.getLastPathComponent();
						if (null != click_temp_fritree) {
							click_temp_fritree.getUserPanel().setBackground(
									Color.WHITE);
							click_temp_fritree.getInfoPanel().setBackground(
									Color.WHITE);
							model.reload(click_temp_fritree);
						}
						object.getUserPanel().setBackground(clickcolor);
						object.getInfoPanel().setBackground(clickcolor);
						click_temp_fritree = object;
						model.reload(object);

					}
				}
				if (2 == e.getClickCount()) {
					TreePath path = jtree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						FriTreeNode object = (FriTreeNode) path
								.getLastPathComponent();
						Group_click(object);
					}
				}
			}// mouseClicked
		});

	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public List<String> getTable_id() {
		return table_id;
	}

	public void setTable_id(List<String> table_id) {
		this.table_id = table_id;
	}

	void Group_click(FriTreeNode object) {
		if (object.isLeaf()) {
			String icon = object.getUsername().getText().substring(0, 1);
			String name = object.getUsername().getText();
			String id = object.getID();
			// System.out.println(Group_panel.table_id.size());
			if (0 == Group_panel.table_id.size()) {
				System.out.println("g_id:" + id);
//				List<User> temp_user = map__group_chat_list.get(id);
				List<User> temp_user = getUserList(id);
				// group_chat_frame=new Group_ChatFrame(temp_user,id,name);
				group_chat_frame = new Group_ChatFrame(temp_user,
						map__group_chat.get(id));
				table_id.add(id);
			} else {
				int tmpnub = 0;
				for (String tmp : table_id) {
					if (tmp.equals(id)) {
						System.out.println("群已在聊天框");
						group_chat_frame.getTabbedPane().setSelectedIndex(
								tmpnub);
						break;
					} else {
						if (tmpnub >= (group_chat_frame.getTabbedPane()
								.getTabCount() - 1)) {
//							List<User> temp_user = map__group_chat_list.get(id);
							List<User> temp_user =getUserList(id);
							// Group_chat_all_panel all_panel = new
							// Group_chat_all_panel(temp_user,id,name);
							Group_chat_all_panel all_panel = new Group_chat_all_panel(
									temp_user, map__group_chat.get(id));
							group_chat_frame.getTabbedPane().addTab(name, null,
									all_panel);
							group_chat_frame.getTabbedPane().setSelectedIndex(
									group_chat_frame.getTabbedPane()
											.getTabCount() - 1);
							table_id.add(id);
							break;
						}
						System.out.println(tmpnub);
					}
					tmpnub++;
					System.out.println(tmpnub);
				}
			}
			System.out.println("双击群" + object.getUsername().getText());
		}
	}

	public void Update_Group_Info(String id, String g_name, String g_notice) {
		// TODO 自动生成的方法存根
		for (GroupChat tg : list_group_chat) {
			if (tg.getID().equals(id)) {
				tg.setG_name(g_name);
				tg.setG_notice(g_notice);
				System.out.println("群消息更新成功");
				break;
			}
		}
		fritree = addtree_list();
		model.reload();
		model.setRoot(fritree);
		jtree.setModel(model);
		// jtree.updateUI();
		// setStyle();
	}// Update_Group_Info

	public void Add_Group(GroupChat group_chat) {
		// TODO 自动生成的方法存根
		System.out.println("getID:" + group_chat.getID());
		list_group_chat.add(group_chat);
		fritree = addtree_list();
		map__group_chat.put(group_chat.getID(), group_chat);
		String[] g_list = group_chat.getG_list().split(",");
		String[] g_user_name_list = group_chat.getG_user_name_list().split(",");
		List<User> temp_list_user = new ArrayList<User>();
		for (int i = 0; i < g_list.length; i++) {
			User temp_user = new User();
			temp_user.setU_phone(g_list[i]);
			temp_user.setU_name(g_user_name_list[i]);
			temp_user.setU_dept("群");
			temp_user.setU_satus(1);
			if (temp_user.getU_phone().equals(group_chat.getG_main_id() + "")) {
				temp_user.setU_satus(3);
			}
			// temp_user.setU_dept(g_user_name_list[i]);
			temp_list_user.add(temp_user);
		}
		map__group_chat_list.put(group_chat.getID() + "", temp_list_user);
		model.reload();
		model.setRoot(fritree);
		jtree.setModel(model);
		// jtree.updateUI();
		// setStyle();
	}// Add_Group

	public void Remove_Group(String g_id) {
		// TODO 自动生成的方法存根
		map__group_chat.remove(g_id);
		for (GroupChat temp : list_group_chat) {
			if (temp.getID().equals(g_id)) {
				list_group_chat.remove(temp);
				System.out.println("找到");
				break;
			}
		}
		fritree = addtree_list();
		model.reload();
		model.setRoot(fritree);
		jtree.setModel(model);
		if(null!=group_chat_frame){
			group_chat_frame.remove_group(g_id);	
		}
	}// Remove_Group

	public void Remove_User(String id, String u_phone) {
		int i = 0;
		for (String t_id : table_id) {
			// 如果聊天窗口已经被打开
			if (t_id.equals(id)) {
				Group_chat_all_panel t_p = (Group_chat_all_panel) group_chat_frame
						.getTabbedPane().getComponent(i);
				t_p.Remove_User(u_phone);
				return;
			}
			i++;
		}
		// 如果没有被打开
		List<User> t_list_user = map__group_chat_list.get(id);
		for (User t_u : t_list_user) {
			if (t_u.getU_phone().equals(u_phone)) {
				t_list_user.remove(t_u);
				break;
			}
		}
	}// Remove_User

	public void Add_User(String id, String u_phone, String u_name) {
		int i = 0;
		for (String t_id : table_id) {
			// 如果聊天窗口已经被打开
			if (t_id.equals(id)) {
				Group_chat_all_panel t_p = (Group_chat_all_panel) group_chat_frame
						.getTabbedPane().getComponent(i);
				t_p.Add_User(u_phone, u_name);
				return;
			}
			i++;
		}
		// 如果没有被打开
		List<User> t_list_user = map__group_chat_list.get(id);
		for(User tu:t_list_user){
			if(tu.getU_phone().equals(u_phone)){
				break;//防止重复添加
			}
		}
		User temp_user = new User();
		temp_user.setU_phone(u_phone);
		temp_user.setU_name(u_name);
		temp_user.setU_dept("群");
		temp_user.setId(Integer.parseInt(u_phone));
		t_list_user.add(temp_user);
	}// Add_User

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

	public static List<User> getUserList(String id) {
		GroupChat tmp = map__group_chat.get(id);
		String[] g_list = tmp.getG_list().split(",");
		List<User> temp_list_user = new ArrayList<User>();
		for (int i = 0; i < g_list.length; i++) {
			User temp_user = new User();
			temp_user.setU_phone(g_list[i]);
			String name = Friend_panel.user_fritree.get(temp_user.getU_phone())
					.getUsername().getText();
			temp_user.setU_name(name);
			temp_user.setU_dept("群");
			temp_user.setU_satus(1);
			if (temp_user.getU_phone().equals(tmp.getG_main_id() + "")) {
				temp_user.setU_satus(3);
			}
			temp_list_user.add(temp_user);
		}
		map__group_chat_list.put(tmp.getID() + "", temp_list_user);
		return map__group_chat_list.get(id);
	}

}
