package com.ui.test.EditPanel.t;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JTextPane;

public class TestJTextPane extends Frame {
	/**
*
*/
	private static final long serialVersionUID = -4287324146188872577L;
	private BorderLayout borderLayout1 = new BorderLayout();
	private JTextPane jTable1;

	public static void main(String args[]) {
		TestJTextPane myframe = new TestJTextPane();
		myframe.setSize(new Dimension(250, 250));
		myframe.setVisible(true);
	}

	public TestJTextPane() {
		super();
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		jTable1 = new JTextPane();
		jTable1.setContentType("text/html");
		jTable1.setText("<html>http:/:/dfsaaaaaaaaaaaaaaaaajkjkldsj<wbr>/</wbr>akdfjsakljgfdsajlgkdjsakljfldsjalfjdsal</html>");
		this.setTitle("Excel Lent JTABLE");
		this.setLayout(borderLayout1);
		this.setSize(new Dimension(400, 300));
		this.setBackground(Color.white);
		this.add(jTable1, BorderLayout.CENTER);
		// 这就是添加复制和粘贴功能的那一行！
		JTextPaneAdapter myAd = new JTextPaneAdapter(jTable1);
	}
}
