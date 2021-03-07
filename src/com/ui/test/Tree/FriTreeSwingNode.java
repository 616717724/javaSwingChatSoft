package com.ui.test.Tree;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class FriTreeSwingNode implements TreeNode {

	private JLabel ID;// 该节点的ID号
	private ImageIcon img;// 节点存放图片
	private JLabel uName;// 第一行文字（显示名字）
	private JLabel text;// 第二行文字（显示签名）

	private ArrayList<TreeNode> children;// 孩子节点
	private TreeNode parent;// 父亲节点

	public FriTreeSwingNode(JLabel ID) {
		this.ID = ID;
	}

	public FriTreeSwingNode() {

	}

	public FriTreeSwingNode(JLabel name, JLabel text, ImageIcon img, JLabel ID) {
		this.uName = name;
		this.text = text;
		this.img = img;
		this.ID = ID;
	}

	/**
	 * @return the iD
	 */
	public JLabel getID() {
		return ID;
	}
	public FriTreeSwingNode getView(){
		return this;
	}
	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(JLabel iD) {
		ID = iD;
	}

	/**
	 * @return the uName
	 */
	public JLabel getuName() {
		return uName;
	}

	/**
	 * @param uName
	 *            the uName to set
	 */
	public void setuName(JLabel uName) {
		this.uName = uName;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(FriTreeSwingNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the img
	 */
	public ImageIcon getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(ImageIcon img) {
		this.img = img;
	}

	/**
	 * @return the text
	 */
	public JLabel getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(JLabel text) {
		this.text = text;
	}

	public void addchild(FriTreeSwingNode aChild) {

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