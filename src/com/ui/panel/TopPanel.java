package com.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ui.FristFrame;
import com.ui.Txt_size;
import com.ui.dialog.TipDialog;
import com.ui.panel.list.Friend_panel;
import com.ui.panel.list.Group_panel;
import com.ui.setIcon.SetIcon;

public class TopPanel extends JPanel implements Txt_size {
	JPanel top_panel;
	private JLabel lblclose, lblmax, lblmin;
	JDialog frame;
	ImageIcon icon;
	Image img;
	int kind=0;
	TipDialog top_dialog;
	/**
	 * @wbp.parser.constructor
	 */
	public TopPanel(JDialog frame,int kind) {
		this.frame = frame;
		this.kind=kind;
//		System.out.println("kind:"+kind);
		init();
		initListener();
	}
	public TopPanel(TipDialog top_dialog,int kind) {
		this.top_dialog = top_dialog;
		this.frame = top_dialog;
		this.kind=kind;
//		System.out.println("kind:"+kind);
		init();
		initListener();
	}

	public void init() {
		top_panel = new BgPanel("top_bg2.gif");
		top_panel.setLayout(null);

		lblclose = new JLabel("X");
		lblclose.setIcon(SetIcon.getPicture("close.png", 40, 20));

		lblmin = new JLabel("-");
		lblmin.setIcon(SetIcon.getPicture("minimize.png", 40, 20));

		lblmax = new JLabel("[_]");
		lblmax.setIcon(SetIcon.getPicture("close.png", 40, 20));
//		System.out.println("TopP_frame.getWidth()"+frame.getWidth());
		lblclose.setBounds(frame.getWidth()-lblwitdh, 0, lblwitdh, lblheight);
//		lblmax.setBounds(320, 0, lblwitdh, lblheight);
		lblmin.setBounds(frame.getWidth()-(lblwitdh*2), 0, lblwitdh, lblheight);
		top_panel.setPreferredSize(new Dimension(0, 20));
		top_panel.add(lblclose);
//		top_panel.add(lblmax);
		top_panel.add(lblmin);
		top_panel.setBackground(new Color(13,196,254));
		this.setLayout(new BorderLayout());
		this.add(top_panel);
	}

	public void initListener() {
		// 主窗体事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("碰到了顶部面板");
				point.x = e.getX();
				point.y = e.getY();
				// System.out.println(point.y );
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = frame.getLocation();
//				System.out.println(p.x + e.getX() - point.x);
				frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY()
						- point.y);
				//用于聊天框的移动
				frame.getOwner().setLocation(p.x + e.getX() - point.x, p.y + e.getY()
						- point.y);
			}
		});

		// 退出按钮事件
		lblclose.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblclose.setIcon(SetIcon.getPicture("close_active.png", 40, 20));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblclose.setIcon(SetIcon.getPicture("close.png", 40, 20));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblclose.setIcon(SetIcon.getPicture("close_move.png", 40, 20));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
//				System.out.println("kind:"+kind);
				if(0==kind){
					//如果是好友列表则关闭程序
					int tn=JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出提示", JOptionPane.YES_NO_OPTION); 
					if(tn==JOptionPane.YES_OPTION){
						System.exit(0);	
					}
				}
				else{
					//如果是窗口表则关闭窗口
					if(null!=Friend_panel.table_id&&1==kind){
						//System.out.println(Friend_panel.table_id.size());
						int tn=JOptionPane.showConfirmDialog(null, "聊天记录尚未保存,确定全部要关闭吗？", "关闭聊天窗口提示", JOptionPane.YES_NO_OPTION); 
						if(tn==JOptionPane.YES_OPTION){
							Friend_panel.table_id.clear();
							((Frame)frame.getOwner()).dispose();	
						} 
					}
					if(null!=Group_panel.table_id&&2==kind){
						//System.out.println(Friend_panel.table_id.size());
						int tn=JOptionPane.showConfirmDialog(null, "聊天记录尚未保存,确定全部要关闭吗？", "关闭群窗口提示", JOptionPane.YES_NO_OPTION); 
						if(tn==JOptionPane.YES_OPTION){
							Group_panel.table_id.clear();
							((Frame)frame.getOwner()).dispose();	
						} 
					}
					if(3==kind){
						top_dialog.clean();
					}
//					((Frame)frame.getOwner()).dispose();
				}
			}
		});

		// 最小化按钮事件
		lblmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblmin.setIcon(SetIcon.getPicture("minimize.png",40,20));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblmin.setIcon(SetIcon.getPicture("minimize_active.png",40,20));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				frame.setVisible(false);
//				((Frame)frame.getOwner()).setExtendedState(Frame.MAXIMIZED_BOTH);//设置最大化
				((Frame)frame.getOwner()).setExtendedState(Frame.ICONIFIED );//设置最小化
				if(3==kind){
					top_dialog.clean();
				}
			}
		});
	}
	public void paint(Graphics g){
		super.paint(g);
//		paintComponent(g);
	}
	public void paintComponent(Graphics g)  
	{  
//		System.out.println("paintComponent");
	    super.paintComponent(g);
	    Image image=SetIcon.getPicture("bg.png",400,20).getImage();
	    g.drawImage(image,0,0,this);  
	} 
}
