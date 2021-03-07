package com.ui.test.loading;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ui.Txt_size;
import com.ui.font.SetFont;

import javax.swing.JTextArea;
import javax.swing.DropMode;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JInternalFrame;

import java.awt.BorderLayout;

import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.Box;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;

public class LoadingTemp extends JFrame implements Txt_size {

	private JPanel contentPane;
	Dimension screensize;
	int width = 400, height = 300;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	public ImageIcon icon=null;  
    public Image img=null; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingTemp frame = new LoadingTemp();
					frame.setVisible(true);
					// frame.txtpwd.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoadingTemp() {
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);

		setTitle("temp");
		setDefaultCloseOperation(3);
//		getContentPane().setLayout(null);
		getContentPane().add(new HomePanel());
//		init_panel();
	}

	private void init_panel() {
		// 设置字体
		new SetFont().SetDefault(Font.BOLD, 13);
		ImageIcon icon_load = new ImageIcon("./resource/img/loading2.gif");
		img = icon_load.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
		icon_load = new ImageIcon(img);
	}

	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		
	}
}
