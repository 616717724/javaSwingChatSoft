package com.ui.test.Tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTreeUI;
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
public class TreeSwingNodeRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 8532405600839140757L;
	static Image image = SetIcon.getPicture("bg.png", 50, 50).getImage();
	static ImageIcon icon = new ImageIcon(image);
	static Image image2 = SetIcon.getPicture("bg.png", 30, 30).getImage();
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
		//DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		FriTreeSwingNode f = (FriTreeSwingNode) value;

		// 取得路徑
//		TreeNode[] paths = node.getPath();

		// 按路径层次赋予不同的图标
//		if (paths.length == 3) {
//			setIcon(tableIcon);
//		} else if (paths.length == 2) {
//			// 按展開情況再賦予不同的圖標
//			if (expanded) {
//				setIcon(tableSpaceOpenIcon);
//			} else {
//				setIcon(tableSpaceCloseIcon);
//			}
//		} else if (paths.length == 1) {
//			setIcon(databaseIcon);
//		}
		String text = "<html>" + f.getuName().getText() + "<br/>" + f.getText().getText()
				+ " <html/>";
		setText(text);// 设置JLable的文字
		/**************************** 设置JLable的图片 *****************/
		// 得到此图标的 Image,然后创建此图像的缩放版本。
//		Image img = f.getImg().getImage()
//				.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
//		setIcon(new ImageIcon(img));// 设置JLable的图片
//		setIconTextGap(15);// 设置JLable的图片与文字之间的距离
		if (expanded) {
			setIcon(tableSpaceOpenIcon);
		} else {
			setIcon(tableSpaceCloseIcon);
		}
//		return new JButton("asd");
		return this;
//		return this;
	}
	public static void main(String[] args) {
		test frame = new test();
		frame.setVisible(true);
	}
}
class test  extends JFrame{
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
		
		
		Image image = SetIcon.getPicture("bg.png", 50, 50).getImage();
		ImageIcon icon = new ImageIcon(image);
		JLabel name = new JLabel("name");
		name.setBackground(new Color(255,255,230));
		JLabel text = new JLabel("text");
		JLabel id = new JLabel("id");
		FriTreeSwingNode ftn = new FriTreeSwingNode(name, text, icon, id);
		for (int i = 0; i < 5; i++) {
			FriTreeSwingNode ftn2 = new FriTreeSwingNode(name, text, icon, id);
			for (int j = 0; j < 5; j++) {
				name = new JLabel("name" + j);
				text = new JLabel("text" + j);
				id = new JLabel("id" + j);
				ftn2.addchild(new FriTreeSwingNode(name, text, icon, id));
			}
			name = new JLabel("name" + i);
			text = new JLabel("text" + i);
			id = new JLabel("id" + i);
			ftn.addchild(ftn2);
		}
		
		model = new DefaultTreeModel(ftn);
		final JTree jTree=new JTree(model);
		
		BasicTreeUI ui=(BasicTreeUI)(jTree.getUI());
		ui.setCollapsedIcon(null);
		ui.setExpandedIcon(null);
		jTree.setCellRenderer(new TreeSwingNodeRenderer());
		JScrollPane scroll=new JScrollPane();
		contentPane.add(scroll);
		scroll.setViewportView(jTree);
//		textField.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				System.out.println("keyPressed");
//			}
//		});
		jTree.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO 自动生成的方法存根
				TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					FriTreeSwingNode object = (FriTreeSwingNode) path.getLastPathComponent();
					//System.out.println("mouseMoved");
//					System.out.println(object.getClass());
					System.out.println(object.getuName().getText());
//					object.getuName().setBackground(Color.blue);
//					object.getuName().setText("asdasd");
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
			}
		});
	}
}