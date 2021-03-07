package com.ui.panel.JTree;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.ui.setIcon.SetIcon;

public class FriTreeNodeRenderer extends DefaultTreeCellRenderer {
	static ImageIcon tableSpaceCloseIcon = new ImageIcon(SetIcon.getPicture("r.png", 15, 15).getImage());
	static ImageIcon tableSpaceOpenIcon = new ImageIcon(SetIcon.getPicture("d.png", 15, 15).getImage());
	static Map<String,FriTreeNode> expand_map=new HashMap<String,FriTreeNode>();

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		// 取得结点
		FriTreeNode f = (FriTreeNode) value;
		if(!f.isLeaf()){
			if (expanded) {
				expand_map.put(f.getUsername().getText(),f);
//				System.out.println(f.getUsername().getText());
				setIcon(tableSpaceOpenIcon);
			} else {
//				expand_map.remove(f.getUsername().getText());
//				System.out.println("remove:"+f.getUsername().getText());
				setIcon(tableSpaceCloseIcon);
			}	
		}
		if (f.isLeaf()) {
			return f.getView();
		} else {
			setText(f.getUsername().getText()+"列表");
			return this;
		}
	}
	public Map<String,FriTreeNode> get_expand_list(){
		return expand_map;
	}
	public void clrean(){
		expand_map.clear();
	}
}
