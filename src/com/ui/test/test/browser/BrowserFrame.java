package com.ui.test.test.browser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.JButton;





import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BrowserFrame extends JFrame {

	private JPanel contentPane;
	JWebBrowser browser;
	String body="document.getElementsByTagName('body')[0]";
	static Image IMG=null;//缓冲面板
	int Ww=800,Wh=600;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIUtils.setPreferredLookAndFeel();
					NativeInterface.open();
					
					BrowserFrame frame = new BrowserFrame();
					frame.setVisible(true);
					
					NativeInterface.runEventPump();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BrowserFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		browser=new JWebBrowser();
		String url=this.getClass().getClassLoader().getResource("")+"com/ui/test/test/browser/index.html";
		browser.navigate(url);
		browser.setBarsVisible(false);//隐藏浏览器工具栏
//		browser.setDefaultPopupMenuRegistered(false);//禁止右键
		getContentPane().add(browser, BorderLayout.CENTER);
		
		browser.executeJavascript("var b=document.getElementsByTagName('body');");
//		browser.executeJavascript("alert(1)");
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(BrowserFrame.class.getResource("/com/resource/img/bg2.png"));
				browser.executeJavascript(body+".innerHTML="+body+".innerHTML+'123<img src=\"\"/>';");
//				browser.executeJavascript(body+".innerHTML="+body+".innerHTML+'123<img src=\"G:/eclipse-standard-kepler-SR2-win32/eclipse/workspace/MyChatSoft/bin/com/resource/img/bg2.png\"/>';");
//				browser.executeJavascript("javascript:document.body.contentEditable='true'; document.designMode='on'; void(0);");
				//browser.executeJavascript("alert(2)");
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
	}
}
