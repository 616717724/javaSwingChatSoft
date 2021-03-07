package com.ui.test.Tree.NewTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.ui.setIcon.SetIcon;

/**
 * 树节点渲染器
 * 
 * @author: sitinspring(junglesong@gmail.com)
 * @date: 2008-2-12
 */
public class MySwingNodeRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 8532405600839140757L;
	static Image image = SetIcon.getPicture("d.png", 15, 15).getImage();
	static ImageIcon icon = new ImageIcon(image);
	static Image image2 = SetIcon.getPicture("r.png", 15, 15).getImage();
	static ImageIcon icon2 = new ImageIcon(image2);
	// 數據庫圖標,頂層節點用
	private static final Icon databaseIcon = icon;

	// 表圖標,第三層節點用
	private static final Icon tableIcon = icon;

	// 表空間關閉狀態圖標,關閉狀態的第二層節點用
	private static final Icon tableSpaceCloseIcon = icon2;

	// 表空間關閉狀態圖標,打開狀態的第二層節點用
	private static final Icon tableSpaceOpenIcon = icon;

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		// 取得節點
		MyDefaultMutableTreeNode f = (MyDefaultMutableTreeNode) value;
		if (expanded) {
			setIcon(tableSpaceOpenIcon);
		} else {
			setIcon(tableSpaceCloseIcon);
		}
		if (f.isLeaf()) {
			return f.getView();
		} else {
			setText("好友列表");
			return this;
		}
	}
}