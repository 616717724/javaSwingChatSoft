package com.ui.test.EditPanel.t;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;

public class JTextPaneAdapter implements ActionListener {
	private Clipboard system;
	private JTextPane textPane;

	public JTextPaneAdapter(JTextPane myJTable) {
		textPane = myJTable;
		KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK, false);
		// 确定复制按键用户可以对其进行修改
		// 以实现其它按键组合的复制功能。
		textPane.registerKeyboardAction(this, "Copy", copy,
				JComponent.WHEN_FOCUSED);
	}

	/**
	 * 此适配器运行图表的公共读方法。
	 */
	public JTextPane getTextPane() {
		return textPane;
	}

	/**
	 * 在我们监听此实现的按键上激活这种方法。 此处，它监听复制和粘贴 ActionCommands。 而且此后复制动作无法执行。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().compareTo("Copy") == 0) {
			String text = textPane.getSelectedText();
			if (text.indexOf("/", 10) > 0) {
				text = text.replace(" /", "/");
				if (text.indexOf("://") > 0) {
					StringSelection stsel = new StringSelection(text);
					system = Toolkit.getDefaultToolkit().getSystemClipboard();
					system.setContents(stsel, stsel);
					return;
				}
			}
			StringSelection stsel = new StringSelection(
					textPane.getSelectedText());
			system = Toolkit.getDefaultToolkit().getSystemClipboard();
			system.setContents(stsel, stsel);
		}
	}
}
