package com.ui.test.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * 
 * This program demonstrates the creation of a JDialog from a super-window.
 * 
 * The created dialog is on the mode "Modal".
 * 
 * @author han
 * 
 * 
 */

public class SwingJDialog {

	public SwingJDialog() {

		final JFrame jf = new JFrame("弹出窗体实验");

		// Some methods defined by Toolkit query the native operating system
		// directly.

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

		int Swing1x = 500;

		int Swing1y = 300;

		jf.setBounds(screensize.width / 2 - Swing1x / 2, screensize.height / 2
				- Swing1y / 2, Swing1x, Swing1y);

		jf.setUndecorated(true);
		
		jf.setVisible(true);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = jf.getContentPane();

		c.setBackground(Color.pink);

		c.setLayout(null);

		Dimension Swing1size = jf.getSize();

		JButton jb = new JButton("弹出对话窗");

		int jbx = 100;

		int jby = 30;

		jb.setBounds(Swing1size.width / 2 - jbx / 2, Swing1size.height / 2
				- jby / 2, jbx, jby);

		// jb.setBounds(Swing1x/2-jbx/2,Swing1y/2-jby/2,jbx,jby);

		c.add(jb);

		class Dialog1 {

			JDialog jd = new JDialog(jf, "JDialog窗体", true);

			Dialog1() {

//				jd.setSize(300, 200);
				jd.setSize(200, 200);
				Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
				int windx = ((int) screensize.getWidth() - 200);
				int windy = ((int) screensize.getHeight() - 200);
				jd.setLocation(windx, windy);

				Container c2 = jd.getContentPane();
				jd.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO 自动生成的方法存根
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO 自动生成的方法存根
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO 自动生成的方法存根
						System.out.println("mouseExited");
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO 自动生成的方法存根
						System.out.println("mouseEntered");
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO 自动生成的方法存根
						
					}
				});

				c2.setLayout(null);

				JLabel jl = new JLabel("只是一个对话框");

				jl.setBounds(0, -20, 100, 100);

				JButton jbb = new JButton("确定");

				jbb.setBounds(100, 100, 60, 30);

				c2.add(jl);

				c2.add(jbb);

				jbb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						jd.dispose();

						// System.exit(0);

					}

				});

				System.out.println("OK");

				jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			}

		}

		jb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				new Dialog1().jd.setVisible(true);// 弹出对话框

				System.out.println("OK2");

			}

		});

		System.out.println("OK3");

	}

	public static void main(String[] args) {

		new SwingJDialog();

	}

}
