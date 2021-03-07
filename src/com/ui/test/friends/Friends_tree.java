package com.ui.test.friends;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;



public class Friends_tree extends DefaultTreeCellRenderer{
	public Friends_tree(){
		init();
	}
	void init(){
//		this.setUserObject("好友列表");
//		for(int i=0;i<5;i++){
//			this.add(new DefaultMutableTreeNode(new Friend_panel()));
//		}
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		//super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//		System.out.println("getTreeCellRendererComponent");
		if (value instanceof Friend_panel) {
			return ((Friend_panel)value).getView();
		}
		return this;
	}
}
