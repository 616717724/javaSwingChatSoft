package com.ui.panel.JTree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.dao.User;

public class FriTreeNode extends DefaultMutableTreeNode implements TreeNode  {
	
	User user;
	private String ID;// 该节点的ID号
	String name;
	String text;
	JPanel userPanel, infoPanel;
	public FriTreeNode() {

	}
	/**
	 * @param iD
	 *            the iD to set
	 */
	public FriTreeNode(String ID, String name, String text) {
		this.ID = ID;
		this.name=name;
		this.text=text;
		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(300, 50));
		userPanel.setBackground(Color.WHITE);
		userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
//		userPanel.setLayout(new BorderLayout());
		infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		if(name.length()>1){
			userImage = new JLabel(name.substring(0, 1));	
		}else{
			userImage= new JLabel(name);	
		}
		userImage.setHorizontalAlignment(SwingConstants.CENTER);
		userImage.setPreferredSize(new Dimension(35, 35));
		userImage.setForeground(Color.WHITE);
		userImage.setFont(new Font("宋体", Font.PLAIN, 25));
		userImage.setOpaque(true);
		userImage.setBorder(new CompoundBorder(new EtchedBorder(),
				new LineBorder(Color.white)));
		userImage.setBackground(new Color(13, 196, 254));
		username = new JLabel(name);
		username.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		userinfo = new JLabel(text);
		userPanel.add(userImage);
//		userPanel.add(userImage,BorderLayout.WEST);
		infoPanel.add(username);
		infoPanel.add(userinfo);
		userPanel.add(infoPanel);
//		userPanel.add(infoPanel,BorderLayout.CENTER);
	}
	///
	//两个构造函数还是有些不同
	//
	public FriTreeNode(User user) {
		this.user=user;
		this.ID = user.getU_phone();
		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(300, 50));
		userPanel.setBackground(Color.WHITE);
		userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
//		userPanel.setLayout(new BorderLayout());
		infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		userImage = new JLabel(user.getU_name().substring(0, 1));
		userImage.setHorizontalAlignment(SwingConstants.CENTER);
		userImage.setPreferredSize(new Dimension(35, 35));
		userImage.setForeground(Color.WHITE);
		userImage.setFont(new Font("宋体", Font.PLAIN, 25));
		userImage.setOpaque(true);
		userImage.setBorder(new CompoundBorder(new EtchedBorder(),
				new LineBorder(Color.white)));
		username = new JLabel(user.getU_name());
		username.setFont(new Font("微软雅黑", Font.BOLD, 15));
		userinfo = new JLabel(user.getU_phone());
//		if(1==user.getU_satus()){
//			userImage.setBackground(new Color(13, 196, 254));
//		}else{
//			userImage.setBackground(new Color(118,130,137));
//		}
		switch(user.getU_satus()){
		case 1:
			userImage.setBackground(new Color(13, 196, 254));
			break;
		case 2:
			userImage.setBackground(Color.green);
			break;
		case 3:
			userImage.setBackground(Color.yellow);
			username.setText(username.getText());
			break;
		default:
			userImage.setBackground(new Color(118,130,137));
			break;
		}
		userPanel.add(userImage);
//		userPanel.add(userImage,BorderLayout.WEST);
		infoPanel.add(username);
		infoPanel.add(userinfo);
		userPanel.add(infoPanel);
//		userPanel.add(infoPanel,BorderLayout.CENTER);
	}
	public void update_background(String satus){
		if("0".equals(satus)){
			userImage.setBackground(new Color(118,130,137));
		}else{
			userImage.setBackground(new Color(13, 196, 254));	
		}
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

	JLabel userImage, username, userinfo;

	private ArrayList<TreeNode> children;// 孩子节点
	private TreeNode parent;// 父亲节点

	public FriTreeNode(String ID) {
		this.ID = ID;
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
	public void setParent(FriTreeNode parent) {
		this.parent = parent;
	}

	public void addchild(FriTreeNode aChild) {

		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(aChild);
		aChild.parent = this;
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
		return children.get(childIndex);
	}
	
	public void remove(String id){
		for(TreeNode t:children){
//			System.out.println("Name:"+((FriTreeNode)t).getUsername().getText()+" ID:"+((FriTreeNode)t).getID());
			if(id.equals(((FriTreeNode)t).getID())){
				children.remove(t);
				break;
			}
		}
	}

	@Override
	public int getChildCount() {
		if (children == null) {
			return -1;
		}
		return children.size();
	}

//	@Override
	public TreeNode getParent() {
		return parent;
	}

//	@Override
	public int getIndex(TreeNode aChild) {
		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			return -1;
		}
		return children.indexOf(aChild);
	}

//	@Override
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
	
	public ArrayList<TreeNode> get_children_list(){
		return children;
	}
}