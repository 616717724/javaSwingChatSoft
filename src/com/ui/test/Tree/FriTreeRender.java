package com.ui.test.Tree;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import com.ui.setIcon.SetIcon;

public class FriTreeRender extends JLabel implements TreeCellRenderer {
	Image image = SetIcon.getPicture("bg.png", 30, 30).getImage();
	ImageIcon icon = new ImageIcon(image);
	ImageIcon Arrow_right=icon;
	ImageIcon Arrow_down=icon;
//	ImageIcon Arrow_right = new ImageIcon(this.getClass().getResource(
//			"Arrow_right.png"));// 节点折叠时的图片
//	ImageIcon Arrow_down = new ImageIcon(this.getClass().getResource(
//			"Arrow_down.png"));// 节点展开式的图片

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		FriTreeNode f = (FriTreeNode) value;// 把value转换为节点
		if (leaf && f.getParent() != tree.getModel().getRoot()) {// 节点需要不为根节点，和根节点的孩子节点

			/***************** 设置JLable的文字 ****************************/
			String text = "<html>" + f.getuName() + "<br/>" + f.getText()
					+ " <html/>";
			setText(text);// 设置JLable的文字

			/**************************** 设置JLable的图片 *****************/
			// 得到此图标的 Image,然后创建此图像的缩放版本。
			Image img = f.getImg().getImage()
					.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(img));// 设置JLable的图片
			setIconTextGap(15);// 设置JLable的图片与文字之间的距离
		} else { // 非叶子节点的文字为节点的ID
			setText(f.getID());// 设置JLable的文字
			if (expanded)// 节点展开
				setIcon(Arrow_down);
			else
				setIcon(Arrow_right);// 设置JLable的图片
		}
		return this;
	}

}
