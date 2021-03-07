package com.ui.test.Tree.NewTree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.ui.panel.JTree.FriTreeNodeRenderer;

/**
 * @author 作者:Audragon
 * @version 创建时间：2013-10-11
 * @todo 功能：刷新树，保存树的刷新前的展开状态
 */
public class MyTree extends JFrame {
	JTree tree;
	DefaultTreeModel defaultTreeModel;
	MyDefaultMutableTreeNode root;

	JPanel btnPanel;
	private JButton addbutton;

	public MyTree() {
//		root = new MyDefaultMutableTreeNode("树");
		root = new MyDefaultMutableTreeNode("树","树","树");
		defaultTreeModel = new DefaultTreeModel(root);
		addNode(root, 4);
		tree = new JTree(defaultTreeModel);
		tree.setRootVisible(false);
		tree.setCellRenderer(new MySwingNodeRenderer());

		btnPanel = new JPanel();
		JButton refresh = new JButton("刷新");
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTree();
			}
		});
		btnPanel.add(refresh);
		getContentPane().add(new JScrollPane(tree));
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		
		addbutton = new JButton("添加");
		addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				root.insert(new MyDefaultMutableTreeNode("书123123","书123123","书123123"), 0);
				int last=root.get_children_list().size()-1;
				DefaultMutableTreeNode chosen=(DefaultMutableTreeNode) root.get_children_list().get(last);
				defaultTreeModel.insertNodeInto(new MyDefaultMutableTreeNode("书123123","书123123","书123123"), chosen, 0);
				((MyDefaultMutableTreeNode)chosen).addchild(new MyDefaultMutableTreeNode("书123123","书123123","书123123"));
				updateTree();
//				root=(MyDefaultMutableTreeNode) defaultTreeModel.getRoot();
			}
		});
		btnPanel.add(addbutton);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void addNode(MyDefaultMutableTreeNode node, int n) {
		for (int i = 1; i <= n; i++) {
			MyDefaultMutableTreeNode newChild = new MyDefaultMutableTreeNode("书"+i,"书"+i,"书"+i);
			for (int m = 1; m <= n; m++) {
				MyDefaultMutableTreeNode newChild2 = new MyDefaultMutableTreeNode("asdas书"+m,"asdas书"+m,"dasdas书"+m);
//				for (int j = 1; j <= n; j++) {
//					DefaultMutableTreeNode newChild3 = new DefaultMutableTreeNode(
//							m);
//					newChild2.add(newChild3);
//				}
				newChild.addchild(newChild2);
			}
			node.addchild(newChild);
		}
	}

	/***
	 * 刷新树，不更改树原来的展开状态
	 */
	public void updateTree() {
		Vector<TreePath> v = new Vector<TreePath>();
		getExpandNode(root, v);
//		root.removeAllChildren();
//		addNode(root, 3);
//		addNode(root, 4);
		defaultTreeModel.reload();

		int n = v.size();
		for (int i = 0; i < n; i++) {
			Object[] objArr = v.get(i).getPath();
			Vector<Object> vec = new Vector<Object>();
			int len = objArr.length;
			for (int j = 0; j < len; j++) {
				vec.add(objArr[j]);
			}
			expandNode(tree, root, vec);
		}
	}

	public Vector<TreePath> getExpandNode(MyDefaultMutableTreeNode node, Vector<TreePath> v) {
		if (node.getChildCount() > 0) {
			System.out.println("getChildCount:"+node.getChildCount());
//			System.out.println((node.children()));
			System.out.println("node:"+(node==null));
			TreePath treePath = new TreePath(
					defaultTreeModel.getPathToRoot(node));
			if (tree.isExpanded(treePath)){
				System.out.println("treePath:"+(treePath==null));
				v.add(treePath);	
			}
//			System.out.println(node.children().hasMoreElements());
			int i=0;
			//if(!node.isLeaf())
				for(TreeNode t:node.get_children_list()){
					i++;
					System.out.println(i+":"+((MyDefaultMutableTreeNode)t).getID());
					MyDefaultMutableTreeNode n = (MyDefaultMutableTreeNode) t;
					System.out.println("v:"+(v==null));
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
	void expandNode(JTree myTree, MyDefaultMutableTreeNode currNode,
			Vector<Object> vNode) {
		if (currNode.getParent() == null) {
			vNode.removeElementAt(0);
		}
		if (vNode.size() <= 0)
			return;

		int childCount = currNode.getChildCount();
		currNode.setUsername(new JLabel("dasdasdasd"));
		System.out.println("vNode.elementAt(0):"+(vNode.elementAt(0)==null));
		//String strNode = vNode.elementAt(0).toString();
//		MyDefaultMutableTreeNode strNode=(MyDefaultMutableTreeNode)vNode.elementAt(0);
		String strNode=((MyDefaultMutableTreeNode)vNode.elementAt(0)).getID();
		MyDefaultMutableTreeNode child = null;
		boolean flag = false;
		for (int i = 0; i < childCount; i++) {
			child = (MyDefaultMutableTreeNode) currNode.getChildAt(i);
			System.out.println("child:"+(child==null));
//			System.out.println(child);
			System.out.println(strNode);
			System.out.println(child.getID());
//			if (strNode.equals(child.toString())) {
//				flag = true;
//				break;
//			}
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
	}

	public static void main(String args[]) {
		new MyTree();
	}
}
