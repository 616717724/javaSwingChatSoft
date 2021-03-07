package com.ui.test.EditPanel;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class JEditorPane3 {
	public static void main(String[] args) {
		JEditorPane editPane = null;
		try {
			URL address = new URL("http://www.sina.com.cn");
			editPane = new JEditorPane(address);
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL:" + e);
		} catch (IOException e) {
			System.out.println("IOException:" + e);
		}
		editPane.setEditable(false);
		JFrame f = new JFrame("JEditorPane3");
		f.setContentPane(new JScrollPane(editPane));
		f.setSize(200, 250);
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
