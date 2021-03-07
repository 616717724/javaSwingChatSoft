package com.ui.panel.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.dao.GroupChat;
import com.dao.User;
import com.socket.Client;
import com.ui.Friends_ChatFrame;
import com.ui.SecondFrame;
import com.ui.Txt_size;
import com.ui.panel.JTree.FriTreeNode;
import com.ui.panel.JTree.FriTreeNodeRenderer;
import com.ui.panel.chat.Friend_chat_all_panel;

public class Friend_panel extends JPanel implements Txt_size {
	List<User> list_user;
	JPanel userinfo_panel;
	FriTreeNode fritree;
	public static Map<String, FriTreeNode> user_fritree = new HashMap<String, FriTreeNode>();

	DefaultTreeModel model;
	JPanel friend_panel;
//	private JLabel lbl_usericon;
//	private JPanel username_panel;
//	private JLabel lbl_usernumb;
	public JTree jtree;
	public JScrollPane friscroll;
	Color clickcolor = new Color(248, 226, 138);
	Color movecolor = new Color(247, 226, 191);
	public static Friends_ChatFrame fri_chat_frame;
	FriTreeNode move_temp_fritree = null, click_temp_fritree = null;
	private JPopupMenu popupMenu;
	private JLabel lbl_out;
	FriTreeNode temp_out_node;
	String group_main_id = "";
	String group_id = "";
	private FriTreeNodeRenderer tree_node_render;
	public static List<String> table_id = new ArrayList<String>();

	public FriTreeNode getFritree() {
		return fritree;
	}

	public void setFritree(FriTreeNode fritree) {
		this.fritree = fritree;
	}

	public void Update_User_Satus(String u_phone, String u_satus) {
		FriTreeNode tmpe_fritree = user_fritree.get(u_phone);
		// 如果不是新的用户
		if (null != tmpe_fritree) {
			System.out.println("更新下线");
			tmpe_fritree.update_background(u_satus);
		}
		search_expand_node();
		// updateTree();
	}

	public void Add_User(String u_phone, String u_name) {
		User tmp_user = new User();
		// tmp_user.setId(Integer.parseInt(u_phone));
		tmp_user.setU_phone(u_phone);
		tmp_user.setU_name(u_name);
		tmp_user.setU_dept("暂无");
		tmp_user.setU_satus(0);
		list_user.add(tmp_user);
		int last = fritree.get_children_list().size() - 1;
		DefaultMutableTreeNode chosen = (DefaultMutableTreeNode) fritree
				.get_children_list().get(last);
		FriTreeNode temp_firtree = new FriTreeNode(u_phone, u_name, u_phone);
		// model.insertNodeInto(temp_firtree, chosen, 0);
		((FriTreeNode) chosen).addchild(temp_firtree);
		if(null==user_fritree.get(u_phone)){
			user_fritree.put(u_phone, temp_firtree);	
		}else{
			temp_firtree=user_fritree.get(u_phone);
		}
		temp_firtree.update_background("0");
		search_expand_node();
		// updateTree();
	}// Add_User

	public void Add_User(String u_phone, String u_name, int kind) {
		User tmp_user = new User();
		// tmp_user.setId(Integer.parseInt(u_phone));
		tmp_user.setU_phone(u_phone);
		tmp_user.setU_name(u_name);
		tmp_user.setU_satus(kind);
		list_user.add(tmp_user);
		int last = fritree.get_children_list().size() - 1;
		DefaultMutableTreeNode chosen = (DefaultMutableTreeNode) fritree
				.get_children_list().get(last);
		FriTreeNode temp_firtree = new FriTreeNode(u_phone, u_name, u_phone);
		// model.insertNodeInto(temp_firtree, chosen, 0);
		((FriTreeNode) chosen).addchild(temp_firtree);
//		user_fritree.put(u_phone, temp_firtree);
		if(null==user_fritree.get(u_phone)){
			user_fritree.put(u_phone, temp_firtree);	
		}else{
			temp_firtree=user_fritree.get(u_phone);
		}
		temp_firtree.update_background(kind + "");
		search_expand_node();
		// updateTree();
	}// Add_User

	public void Remove_User(String u_phone) {
		for (User t : list_user) {
			if (t.getU_phone().equals(u_phone)) {
				list_user.remove(t);
				break;
			}
		}
		fritree = addtree_list();
		model.reload();
		model.setRoot(fritree);
		jtree.setModel(model);
		jtree.expandRow(0);
	}// Remove_User

	public void Update_User_Info(String u_phone, String u_name, String dep) {
		int last = fritree.get_children_list().size() - 1;
		// 获取根节点
		DefaultMutableTreeNode chosen = (DefaultMutableTreeNode) fritree
				.get_children_list().get(last).getParent();
		// 先删除以前的结点
		searchAll_Delete((FriTreeNode) chosen, u_phone);
		// 然后再更新修改的结点
		searchAll_Insert((FriTreeNode) chosen, u_phone, u_name, dep);
		FriTreeNode temp = user_fritree.get(u_phone);
		temp.getUsername().setText(u_name);
		search_expand_node();
		// updateTree();
	}// Update_User_Info

	// public void Reload(){
	// //model.reload();
	// this.list_user=list_user;
	// // fritree = addtree_list();
	// updateTree();
	// // jtree.updateUI();
	// // //隐藏JTree句柄
	// // BasicTreeUI ui=(BasicTreeUI)(jtree.getUI());
	// // ui.setCollapsedIcon(null);
	// // ui.setExpandedIcon(null);
	// }
	// public void Reload(List<User> list_user){
	// System.out.println("刷新");
	// this.list_user=list_user;
	// // fritree = addtree_list();
	// // model.reload();
	// // model.setRoot(fritree);
	// // jtree.setModel(model);
	// updateTree();
	// // jtree.updateUI();
	// //隐藏JTree句柄
	// // BasicTreeUI ui=(BasicTreeUI)(jtree.getUI());
	// // ui.setCollapsedIcon(null);
	// // ui.setExpandedIcon(null);
	// }

	public Friend_panel(List<User> list_user) {
		// if(null==table_id){
		// table_id = new ArrayList<String>();
		// }
		this.list_user = list_user;
		init();
		initListener();
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		setStyle();
		setLayout(new BorderLayout());
		fritree = addtree_list();
		// fritree = addtree_test();
		model = new DefaultTreeModel(fritree);
		model.addTreeModelListener(null);// 添加动态事件
		jtree = new JTree(model);
		jtree.setToggleClickCount(1);// 点击次数
		// jTree.setCellEditor(new DefaultCellEditor(textField));//设置可编辑时的文本框
		// jTree.setInvokesStopCellEditing(true);// 修改节点文字之后生效
		jtree.setRootVisible(false);
		BasicTreeUI ui = (BasicTreeUI) (jtree.getUI());
		ui.setCollapsedIcon(null);
		ui.setExpandedIcon(null);
		jtree.putClientProperty("JTree.lineStyle", "None");
		tree_node_render = new FriTreeNodeRenderer();
		jtree.setCellRenderer(new FriTreeNodeRenderer());
		// jtree.setShowsRootHandles(false);
		// System.out.println(jtree.getShowsRootHandles());

		friscroll = new JScrollPane();
		friscroll.setViewportView(jtree);
		// 屏蔽横向滚动条
		friscroll
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(friscroll, BorderLayout.CENTER);

		popupMenu = new JPopupMenu();
		lbl_out = new JLabel("请出该群");
		popupMenu.add(lbl_out);
	}

	private void setStyle() {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public FriTreeNode addtree_test() {
		String[] nameString = { "李狗蛋", "王大厨", "张沙发", "大煞笔", "己二三", "巳自己", "甲发",
				"乙真人", "嘿嘿嘿" };
		String id = "id";
		String name = "name";
		String text = "text";
		FriTreeNode ftn = new FriTreeNode(id, name, text);
		for (int i = 0; i < 5; i++) {
			id = id + i;
			Random r = new Random();
			FriTreeNode ftn2 = new FriTreeNode(id, nameString[r.nextInt(9)],
					text);
			for (int j = 0; j < 5; j++) {
				id = id + j;
				text = text + j;
				ftn2.addchild(new FriTreeNode(id, nameString[r.nextInt(9)],
						text));
			}
			ftn.addchild(ftn2);
		}
		return ftn;
	}

	public FriTreeNode addtree_list() {
		String id = "id";
		String name = "name";
		String text = "text";
		String[] dept;
		Map<String, FriTreeNode> Map_dept = new HashMap<String, FriTreeNode>();
		FriTreeNode ftn = new FriTreeNode(id, name, text);
		for (User t : list_user) {
			id = t.getU_phone();
			name = t.getU_name();
			text = t.getU_phone();
			// System.out.println("t.getU_dept():"+t.getU_dept());
			if(null==t.getU_dept()){
				t.setU_dept("群");
			}
			dept = t.getU_dept().split("\\|\\|");
			// FriTreeNode leaf = new FriTreeNode(id, name, text);
			FriTreeNode leaf = new FriTreeNode(t);
			user_fritree.put(t.getU_phone(), leaf);
			// System.out.println(t.getU_dept());
			FriTreeNode tmp = ftn;
			// System.out.println("dept.length:"+dept.length);
			for (int i = 0; i < dept.length; i++) {
				FriTreeNode ftn3;
				ftn3 = new FriTreeNode("", dept[i], text);
				if (null == Map_dept.get(dept[i])) {
					// System.out.println(dept[i]);
					Map_dept.put(dept[i], ftn3);
					tmp.addchild(ftn3);
					tmp = ftn3;
				} else {
					// System.out.println("相同："+dept[i]);
					tmp = Map_dept.get(dept[i]);
					// System.out.println(dept[i]);
				}
				// System.out.println(dept[i]);
				// tmp.addchild(ftn3);
				// tmp = ftn3;
				if (i == dept.length - 1) {
					tmp.addchild(leaf);
				}
			}
			// ftn.addchild(tmp);
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
					if (object.isLeaf()) {
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
								// System.out.println(object.getUsername().getText());
								// new
								// Iflytek().start(object.getUsername().getText());
							}
						}
						move_temp_fritree = object;
						model.reload(object);
					}
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
						if (object.isLeaf()) {
							if (null != click_temp_fritree) {
								click_temp_fritree.getUserPanel()
										.setBackground(Color.WHITE);
								click_temp_fritree.getInfoPanel()
										.setBackground(Color.WHITE);
								model.reload(click_temp_fritree);
							}
							object.getUserPanel().setBackground(clickcolor);
							object.getInfoPanel().setBackground(clickcolor);
							click_temp_fritree = object;
							System.out.println("用户名:"
									+ object.getUsername().getText() + " ID:"
									+ object.getID());
							model.reload(object);
						}
					}
				}
				if (2 == e.getClickCount()
						&& e.getButton() == MouseEvent.BUTTON1) {
					TreePath path = jtree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						FriTreeNode object = (FriTreeNode) path
								.getLastPathComponent();
						if (object.isLeaf()) {
							Friend_click(object);
						}
					}
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					TreePath path = jtree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						FriTreeNode object = (FriTreeNode) path
								.getLastPathComponent();
						myJPopupMenu(e, object);
					}
				}
			}// mouseClicked
		});

		lbl_out.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// System.out.println("请出" + temp_out_node.getID());
				String user_id = temp_out_node.getID();
				String user_name = temp_out_node.getUsername().getText();
				String tmpe_list = "";
				for (User tu : list_user) {
					tmpe_list = tmpe_list + tu.getU_phone() + ",";
				}
				String mesg = "type:4,group_out||" + group_id + "||" + user_id
						+ "||" + user_name + "||" + tmpe_list;
				System.out.println("完整消息:" + mesg);
				try {
					int tn = JOptionPane.showConfirmDialog(null, "确定要请出该群吗？",
							"请出群提示", JOptionPane.YES_NO_OPTION);
					if (tn == JOptionPane.YES_OPTION) {
						Client.dato.writeUTF(mesg);
						Client.dato.flush();
						JOptionPane.showMessageDialog(new JLabel(""), "请出成功");
						Remove_User(temp_out_node.getID());
						for(User tu:list_user){
							if(tu.getU_phone().equals(user_id)){
								list_user.remove(tu);
								break;
							}
						}
						GroupChat tmp = Group_panel.map__group_chat.get(group_id);
//						System.out.println(tmp.getG_list());
						String temp_list=tmp.getG_list();
						temp_list=temp_list.replace(","+user_id, "");
						tmp.setG_list(temp_list);
//						System.out.println(tmp.getG_list());
					}
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					JOptionPane.showMessageDialog(new JLabel(""), "请出失败");
					e1.printStackTrace();
				}
			}
		});

	}

	void Friend_click(FriTreeNode object) {
		if (object.isLeaf()) {
			String icon = object.getUsername().getText().substring(0, 1);
			String name = object.getUsername().getText();
			String id = object.getID();
			// System.out.println("table_size" + Friend_panel.table_id.size());
			if (0 == Friend_panel.table_id.size()) {
				fri_chat_frame = new Friends_ChatFrame(icon, name, id);
				table_id.add(id);
			} else {
				int tmpnub = 0;
				for (String tmp : table_id) {
					if (tmp.equals(id)) {
						System.out.println("好友已在聊天框");
						fri_chat_frame.getTabbedPane().setSelectedIndex(tmpnub);
						break;
					} else {
						if (tmpnub >= (fri_chat_frame.getTabbedPane()
								.getTabCount() - 1)) {
							Friend_chat_all_panel all_panel = new Friend_chat_all_panel(
									icon, name, id);
							fri_chat_frame.getTabbedPane().addTab(name, null,
									all_panel);
							fri_chat_frame.getTabbedPane().setSelectedIndex(
									fri_chat_frame.getTabbedPane()
											.getTabCount() - 1);
							table_id.add(id);
							Client.panel_map.put(id,all_panel);
							break;
						}
						System.out.println(tmpnub);
					}
					tmpnub++;
					System.out.println(tmpnub);
				}
			}
			System.out.println("双击" + object.getUsername().getText());
		}
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

	/***
	 * 刷新树，不更改树原来的展开状态
	 */
	/***
	 * 刷新树，不更改树原来的展开状态
	 */
	public void updateTree() {
		Vector<TreePath> v = new Vector<TreePath>();// 动态数组
		getExpandNode(fritree, v);
		// root.removeAllChildren();
		// addNode(root, 3);
		// addNode(root, 4);
		// model.setRoot(fritree);
		// fritree.addchild(fritree);
		model.reload();

		int n = v.size();
		for (int i = 0; i < n; i++) {
			Object[] objArr = v.get(i).getPath();
			Vector<Object> vec = new Vector<Object>();
			int len = objArr.length;
			for (int j = 0; j < len; j++) {
				vec.add(objArr[j]);
			}
			expandNode(jtree, fritree, vec);
		}
	}

	public Vector<TreePath> getExpandNode(FriTreeNode node, Vector<TreePath> v) {
		if (node.getChildCount() > 0) {
			// System.out.println("getChildCount:"+node.getChildCount());
			// System.out.println((node.children()));
			// System.out.println("node:"+(node==null));
			TreePath treePath = new TreePath(model.getPathToRoot(node));
			if (jtree.isExpanded(treePath)) {
				// System.out.println("treePath:"+(treePath==null));
				v.add(treePath);
			}
			// System.out.println(node.children().hasMoreElements());
			int i = 0;
			// if(!node.isLeaf())
			for (TreeNode t : node.get_children_list()) {
				i++;
				// System.out.println(i+":"+((FriTreeNode)t).getID());
				FriTreeNode n = (FriTreeNode) t;
				// System.out.println("v:"+(v==null));
				getExpandNode(n, v);
			}
		}
		return v;
	}

	/**
	 * 
	 * @param myTree
	 *            树
	 * @param currNode
	 *            展开节点的父节点
	 * @param vNode
	 *            展开节点，路径字符串|路径Node组成的Vector，按从根节点开始，依次添加到Vector
	 */
	void expandNode(JTree myTree, FriTreeNode currNode, Vector<Object> vNode) {
		if (currNode.getParent() == null) {
			vNode.removeElementAt(0);
		}
		if (vNode.size() <= 0)
			return;

		int childCount = currNode.getChildCount();
		// System.out.println("vNode.elementAt(0):"+(vNode.elementAt(0)==null));
		// String strNode = vNode.elementAt(0).toString();
		// FriTreeNode strNode=(FriTreeNode)vNode.elementAt(0);
		String strNode = ((FriTreeNode) vNode.elementAt(0)).getID();
		FriTreeNode child = null;
		boolean flag = false;
		for (int i = 0; i < childCount; i++) {
			child = (FriTreeNode) currNode.getChildAt(i);
			// System.out.println("child:"+(child==null));
			// System.out.println(child);
			// System.out.println(strNode);
			// System.out.println(child.getID());
			if (strNode.equals(child.getID())) {
				flag = true;
				break;
			}
		}
		if (child != null && flag) {
			vNode.removeElementAt(0);
			if (vNode.size() > 0) {
				expandNode(myTree, child, vNode);
			} else {
				myTree.expandPath(new TreePath(child.getPath()));
			}
		}
	}// expandNode

	void searchAll(FriTreeNode chosen) {
		for (TreeNode temp : chosen.get_children_list()) {
			// System.out.println(((FriTreeNode)temp).getUsername().getText());
			if (null != ((FriTreeNode) temp).get_children_list())
				searchAll((FriTreeNode) temp);
		}
	}

	void searchAll_Delete(FriTreeNode chosen, String id) {
		for (TreeNode temp : chosen.get_children_list()) {
			// System.out.println(((FriTreeNode)temp).getUsername().getText());
			if (id.equals(((FriTreeNode) temp).getID())) {
				chosen.remove(id);
				return;
			}
			if (null != ((FriTreeNode) temp).get_children_list())
				searchAll_Delete((FriTreeNode) temp, id);
		}// for
	}

	boolean searchAll_Insert(FriTreeNode chosen, String id, String u_name,
			String dep) {
		String[] d = dep.split("\\|\\|");
		boolean flag = true;
		System.out.println("chosen:" + chosen.getUsername().getText());
		for (TreeNode temp : chosen.get_children_list()) {
			if (!((FriTreeNode) temp).isLeaf()) {
				System.out
						.println(((FriTreeNode) temp).getUsername().getText());
				// 如果找到了则在此遍历
				if (((FriTreeNode) temp).getUsername().getText().equals(d[0])) {
					flag = false;
					System.out.println("d[0]:" + d[0]);
					dep = "";
					for (int i = 1; i < d.length; i++) {
						dep = dep + d[i] + "||";
					}
					// searchAll_Insert((FriTreeNode)temp,id,u_name,dep);
					if (!searchAll_Insert((FriTreeNode) temp, id, u_name, dep)) {
						// System.out.println("chosen2:"+((FriTreeNode)temp).getUsername().getText());
						FriTreeNode temp2 = (FriTreeNode) temp;
						for (int i = 1; i <= d.length; i++) {
							FriTreeNode f = null;
							if (i != d.length) {
								f = new FriTreeNode("", d[i], "");
							} else {
								// 如果是最后一个
								f = new FriTreeNode(id, u_name, id);
							}
							temp2.addchild(f);
							temp2 = f;
						}
						return true;
					}
					// break;
					return true;
				} else {
					// 否则继续遍历
					if (null != ((FriTreeNode) temp).get_children_list()) {
						searchAll_Insert((FriTreeNode) temp, id, u_name, dep);
					}
				}
			}
		}// for
			// 遍历完了都没有找到一个就创建新的
		if ((flag && chosen.isroot()) || !flag) {
			System.out.println("chosen2:" + chosen.getUsername().getText());
			FriTreeNode temp = chosen;
			for (int i = 0; i <= d.length; i++) {
				FriTreeNode f = null;
				if (i != d.length) {
					f = new FriTreeNode("", d[i], "");
				} else {
					// 如果是最后一个
					f = new FriTreeNode(id, u_name, id);
				}
				temp.addchild(f);
				temp = f;
			}
			return true;
		}
		return false;
	}// searchAll_Insert

	void myJPopupMenu(MouseEvent event, FriTreeNode object) {
		if (!"".equals(group_main_id)
				&& !object.getID().equals(SecondFrame.lbl_user_id)
				&& SecondFrame.lbl_user_id.equals(group_main_id)) {
			popupMenu.show(event.getComponent(), event.getX(), event.getY());
			temp_out_node = object;
		}
	}

	public String getGroup_Main_id() {
		return group_main_id;
	}

	public void setGroup_Main_id(String group_main_id) {
		this.group_main_id = group_main_id;
	}

	public void search_expand_node() {
		model.reload();
		System.out.println("search_expand_node");
		for (Map.Entry<String, FriTreeNode> entry : tree_node_render
				.get_expand_list().entrySet()) {
			 System.out.println("1"+entry.getKey());
			jtree.expandPath(new TreePath(entry.getValue().getPath()));
		}
		// tree_node_render.updateUI();
		tree_node_render.clrean();
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

}
