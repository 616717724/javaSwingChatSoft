package com.ui.test.EditPanel;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class JEditorPane2 {
	public static void main(String[] args) {
		String str = new String(
				"This is a test.\nthis is Line2!\nThis is Line 3!");
		JEditorPane editPane = new JEditorPane("text/plain", str);
		editPane.setEditable(false);

		JFrame f = new JFrame("JEditorPane2");
		f.setContentPane(new JScrollPane(editPane));
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
