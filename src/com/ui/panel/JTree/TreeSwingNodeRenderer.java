package com.ui.panel.JTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ui.setIcon.SetIcon;

/**
 * 树节点渲染器
 * 
 * @author: sitinspring(junglesong@gmail.com)
 * @date: 2008-2-12
 */
public class TreeSwingNodeRenderer extends DefaultTreeCellRenderer {
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
		FriTreeNode f = (FriTreeNode) value;
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

	public static void main(String[] args) {
		test frame = new test();
		frame.setVisible(true);
	}
}

class test extends JFrame {
	private JPanel contentPane;
	JTextField textField;
	private DefaultTreeModel model;

	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		String id = "id";
		String name = "name";
		String text = "text";
		FriTreeNode ftn = new FriTreeNode(id, name, text);
		for (int i = 0; i < 5; i++) {
			id = id + i;
			name = name + i;
			text = text + i;
			FriTreeNode ftn2 = new FriTreeNode(id, name, text);
			for (int j = 0; j < 5; j++) {
				id = id + j;
				name = name + j;
				text = text + j;
				ftn2.addchild(new FriTreeNode(id, name, text));
			}
			ftn.addchild(ftn2);
		}

		model = new DefaultTreeModel(ftn);
		final JTree jTree = new JTree(model);

		jTree.setCellRenderer(new TreeSwingNodeRenderer());
		jTree.setRootVisible(false);
		jTree.putClientProperty("JTree.lineStyle","None"); 
		JScrollPane scroll = new JScrollPane();
		contentPane.add(scroll);
		scroll.setViewportView(jTree);
		jTree.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO 自动生成的方法存根
				TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					FriTreeNode object = (FriTreeNode) path
							.getLastPathComponent();
					// System.out.println("mouseMoved");
					// System.out.println(object.getClass());
					// System.out.println(object.getUsername().getText());
					object.getUsername().setForeground(Color.blue);
					model.reload(object);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO 自动生成的方法存根
				System.out.println("mouseDragged");
			}
		});

		jTree.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				System.out.println("mouseExited");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				System.out.println("mouseClicked");
				TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					FriTreeNode object = (FriTreeNode) path
							.getLastPathComponent();
					// System.out.println("mouseMoved");
					// System.out.println(object.getClass());
					// System.out.println(object.getUsername().getText());
					object.getUsername().setForeground(Color.red);
					model.reload(object);
				}
			}
		});
	}
}