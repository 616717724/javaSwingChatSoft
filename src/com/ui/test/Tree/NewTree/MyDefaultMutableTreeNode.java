package com.ui.test.Tree.NewTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class MyDefaultMutableTreeNode extends DefaultMutableTreeNode {
	private String ID;// 该节点的ID号
	JPanel userPanel, infoPanel;
	JLabel userImage, username, userinfo;
	private ArrayList<TreeNode> children;// 孩子节点
	private TreeNode parent;// 父亲节点
	public MyDefaultMutableTreeNode(Object o) {
		super(o);
	}
	public MyDefaultMutableTreeNode(String ID, String name, String text) {
		this.ID = ID;
		userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		userImage = new JLabel("李");
		userImage.setForeground(Color.WHITE);
		userImage.setFont(new Font("宋体", Font.PLAIN, 35));
		userImage.setOpaque(true);
		userImage.setBorder(new CompoundBorder(new EtchedBorder(),
				new LineBorder(Color.white)));
		userImage.setBackground(new Color(13, 196, 254));
		username = new JLabel(name);
		userinfo = new JLabel(text);
		userPanel.add(userImage);
		infoPanel.add(username);
		infoPanel.add(userinfo);
		userPanel.add(infoPanel);
	}
	public JPanel getUserPanel() {
		return userPanel;
	}

	public void setUserPanel(JPanel userPanel) {
		this.userPanel = userPanel;
	}

	public JPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(JPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public JLabel getUserImage() {
		return userImage;
	}

	public void setUserImage(JLabel userImage) {
		this.userImage = userImage;
	}

	public JLabel getUsername() {
		return username;
	}

	public void setUsername(JLabel username) {
		this.username = username;
	}

	public JLabel getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(JLabel userinfo) {
		this.userinfo = userinfo;
	}


	public Component getView(){
		return userPanel;
	}
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(MyDefaultMutableTreeNode parent) {
		this.parent = parent;
	}

	public void addchild(MyDefaultMutableTreeNode aChild) {

		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(aChild);
		aChild.parent = this;
//		this.add((MutableTreeNode)aChild);
	}

	/***
	 * 判断是否为根节点
	 * 
	 * @return
	 */
	public boolean isroot() {

		return (getParent() == null);

	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}
		TreeNode t=children.get(childIndex);
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		if (children == null) {
			return -1;
		}
		return children.size();
	}

	@Override
	public TreeNode getParent() {

		return parent;
	}

	@Override
	public int getIndex(TreeNode aChild) {

		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			return -1;
		}
		return children.indexOf(aChild);
	}

	@Override
	public boolean getAllowsChildren() {

		return true;
	}

	/**
	 * 判断是否是叶子节点
	 */
	@Override
	public boolean isLeaf() {

		return (getChildCount() == -1) && (getParent() != null);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children() {
		return null;
	}
	public ArrayList<TreeNode> get_children_list(){
		return children;
	}

	public boolean isNodeChild(TreeNode aNode) {
		boolean retval;

		if (aNode == null) {
			retval = false;
		} else {
			if (getChildCount() == 0) {
				retval = false;
			} else {
				retval = (aNode.getParent() == this);
			}
		}

		return retval;
	}
	
}
