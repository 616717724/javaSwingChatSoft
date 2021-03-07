package com.ui.test.test.browser;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import org.eclipse.swt.browser.Browser;

public class BrowserPanel extends JPanel {

	private JWebBrowser browser;

	public JWebBrowser getBrowser() {

		return browser;

	}

	private void setBrowser(JWebBrowser browser) {

		this.browser = browser;

	}

	public BrowserPanel() {

		setLayout(new BorderLayout(0, 0));

		browser = new JWebBrowser();

		//browser.navigate("http://www.t1-network.com"); // 这一行是告诉browser浏览哪个网址​
//		browser.navigate("D:/HBuilder/tmp/J2eeTest/index.html");
		String url=this.getClass().getClassLoader().getResource("")+"com/ui/test/test/browser/index.html";
		browser.navigate(url);
		browser.setBarsVisible(false);
//		browser.executeJavascript("alert(do);");
		System.out.println(this.getClass().getClassLoader().getResource(""));

		browser.setButtonBarVisible(false);

		browser.setMenuBarVisible(false);

		add(browser, BorderLayout.CENTER);

	}

	public static void main(String[] args) {

		UIUtils.setPreferredLookAndFeel();

		NativeInterface.open();

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					JFrame frame = new JFrame();
					frame.add(new BrowserPanel());
					frame.setSize(300,300);
					frame.setDefaultCloseOperation(3);
					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		});
		NativeInterface.runEventPump();
	}
}
