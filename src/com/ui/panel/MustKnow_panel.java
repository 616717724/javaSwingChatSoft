package com.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.bgm.Bgm;
import com.iflytek.Iflytek;
import com.ui.FristFrame;
import com.ui.font.SetFont;
import com.ui.setIcon.SetIcon;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class MustKnow_panel extends JPanel {
	private JLabel btntrue;
	private JLabel btnfalse;
	private JTextArea textArea;
	public JPanel mustKnow_panel;
	String content="***系统注册协议须知:\r\n\r1、在本系统注册的会员，必须遵守《互联网电子公告服务管理规定》,不得在本系统发表诽谤他人，侵犯他人隐私，侵犯他人知识产权，传播病毒，政治言论，商业讯息等信息。\r\n\r\r\n2、在登记过程中，您将选择注册名和密码。注册名的选择应遵守法律法规及社会公德。您必须对您的密码保密，您将对您注册名和密码下发生的所有活动承担责任。\r";
	private int nub;
	public MustKnow_panel() {
		mustKnow_panel = init_panel();
		add_listener();
	}

	public JPanel init_panel() {
		this.setLayout(null);
		//new SetFont().SetDefault(Font.PLAIN, 15);
		//设置语言
		//new Iflytek().start(content);

		btntrue = new JLabel("");
		btntrue.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/agrr_button.png")));
		btntrue.setHorizontalAlignment(SwingConstants.CENTER);
		btntrue.setToolTipText("注册");
		btntrue.setBounds(90, 207, 100, 50);
		this.add(btntrue);

		btnfalse = new JLabel("");
		btnfalse.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/noagrr_button.png")));
		btnfalse.setHorizontalAlignment(SwingConstants.CENTER);
		btnfalse.setToolTipText("取消");
		btnfalse.setBounds(200, 207, 100, 50);
		this.add(btnfalse);

		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setText(content);
		textArea.setBounds(8, 0, 384, 204);
		textArea.setOpaque(false);
		this.add(textArea);
		new Thread() {
			int time = 3;

			public void run() {
				btntrue.setEnabled(false);
				while (true) {
					try {
//						btntrue.setText("同意" + time);
						sleep(1000);
						time--;
						System.out.println(time);
						if (1 > time) {
//							btntrue.setText("同意");
							btntrue.setFocusable(true);
							break;
						}
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				btntrue.setEnabled(true);
			}
		}.start();
		return this;
	}

	public void add_listener() {
		btntrue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				FristFrame.f.change_panel("注册");
			}
		});
		btntrue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				if (btntrue.isEnabled()) {
					FristFrame.f.change_panel("注册");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btntrue.isEnabled()){
					btntrue.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/agrr_active.png")));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btntrue.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/agrr_button.png")));
			}
		});
		btnfalse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				FristFrame.f.change_panel("登录");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnfalse.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/noagrr_active.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnfalse.setIcon(new ImageIcon(MustKnow_panel.class.getResource("/com/resource/img/noagrr_button.png")));
			}
		});
	}
	public void paint(Graphics g){
		super.paint(g);
		btntrue.requestFocus();
	}
	public void paintComponent(Graphics g)  
	{  
//		System.out.println("paintComponent");
	    super.paintComponent(g);
	    Image image=SetIcon.getPicture("bg.png",this.getWidth(),this.getHeight()).getImage();
	    g.drawImage(image,0,0,this);
	    if(0==nub){
	    	new Bgm().clip1.play();
//	    	try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			}
	    	nub++;
	    }
	}
}
