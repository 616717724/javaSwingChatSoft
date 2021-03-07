package com.ui.test.chat;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowFactory;

import com.ui.Txt_size;
import com.ui.font.SetFont;
import com.ui.panel.TopPanel;
import com.ui.test.loading.HomePanel;

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

import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.Box;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;

import java.awt.FlowLayout;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;

import com.ui.panel.list.Friend_panel;

public class Friends_ChatTemp extends JFrame implements Txt_size {

	private JPanel contentPane;
	Dimension screensize;
	int width = 700, height = 550;// 默认大小
	int windx = 500, windy = 200;// 默认位置
	public ImageIcon icon=null;  
    public Image img=null;
    JTabbedPane tabbedPane;
    JPanel all_panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Friends_ChatTemp frame = new Friends_ChatTemp();
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
	public Friends_ChatTemp() {
		// 获取屏幕大小
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置为屏幕中央
		windx = ((int) screensize.getWidth() - width) / 2;
		windy = ((int) screensize.getHeight() - height) / 2;
		setBounds(windx, windy, width, height);

		setTitle("temp");
		setDefaultCloseOperation(3);
		setUndecorated(true);
		init();
//		init_panel();
	}

	private void init_panel() {
		// 设置字体
	}

	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		JDialog tmp_d=new JDialog(this,"");
		tmp_d.setSize(this.getWidth(),this.getHeight());
		//通过d去设置JFrame
		JPanel top=new TopPanel(tmp_d,1);
		getContentPane().add(top,BorderLayout.NORTH);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		all_panel=new JPanel();
		tabbedPane.addTab("好友一",null,all_panel);
		all_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel top_panel = new JPanel();
		all_panel.add(top_panel, BorderLayout.NORTH);
		top_panel.setBackground(Color.WHITE);
		top_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lbl_friend_icon = new JLabel("李");
		lbl_friend_icon.setOpaque(true);
		lbl_friend_icon.setForeground(Color.WHITE);
		lbl_friend_icon.setFont(new Font("宋体", Font.PLAIN, 35));
		lbl_friend_icon.setBorder(new CompoundBorder(new EtchedBorder(),
						new LineBorder(Color.white)));
		lbl_friend_icon.setBackground(new Color(13, 196, 254));
		top_panel.add(lbl_friend_icon);
		
		JPanel friend_info_panel = new JPanel();
		friend_info_panel.setBackground(Color.WHITE);
		top_panel.add(friend_info_panel);
		friend_info_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lbl_friend_name = new JLabel("群户名");
		lbl_friend_name.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_name);
		
		JLabel lbl_friend_nub = new JLabel("账号");
		lbl_friend_nub.setHorizontalAlignment(SwingConstants.LEFT);
		friend_info_panel.add(lbl_friend_nub);
		
		JScrollPane center_panel = new JScrollPane();
		all_panel.add(center_panel, BorderLayout.CENTER);
//		center_panel.setLayout(new BorderLayout(0, 0));
		
		JEditorPane edit_receive = new JEditorPane();
		center_panel.setViewportView(edit_receive);
//		center_panel.add(edit_receive, BorderLayout.CENTER);
		
		JPanel foot_panel = new JPanel();
		all_panel.add(foot_panel, BorderLayout.SOUTH);
		foot_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel tool_panel = new JPanel();
		foot_panel.add(tool_panel, BorderLayout.NORTH);
		tool_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel tool_panel2 = new JPanel();
		tool_panel.add(tool_panel2, BorderLayout.WEST);
		
		JButton btn_emoj = new JButton("表情");
		tool_panel2.add(btn_emoj);
		
		JButton btn_font = new JButton("文字");
		tool_panel2.add(btn_font);
		
		JButton btn_print = new JButton("截图");
		tool_panel2.add(btn_print);
		
		JButton btn_file = new JButton("文件");
		tool_panel2.add(btn_file);
		
		JButton btn_history = new JButton("消息记录");
		tool_panel.add(btn_history, BorderLayout.EAST);
		
		JScrollPane scrollPane = new JScrollPane();
		foot_panel.add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane edit_send = new JEditorPane();
		edit_send.setPreferredSize(new Dimension(200, 100));
		scrollPane.setViewportView(edit_send);
		
		JPanel send_panel = new JPanel();
		foot_panel.add(send_panel, BorderLayout.SOUTH);
		send_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnNewButton = new JButton("关闭");
		send_panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("发送");
		send_panel.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane panel_1 = new JScrollPane();
		panel.add(panel_1, BorderLayout.NORTH);
//		panel_1.setLayout(new BorderLayout(0, 0));
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setText("公告：\r\n今天下午4：30一楼会议室进行第一阶段审核");
		editorPane.setEditable(false);
		editorPane.setPreferredSize(new Dimension(200,100));
//		panel_1.add(editorPane, BorderLayout.NORTH);
		panel_1.setViewportView(editorPane);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("成员列表(8/20)");
		panel_2.add(lblNewLabel, BorderLayout.NORTH);
		
		//Friend_panel friend_panel = new Friend_panel();
		//panel_2.add(friend_panel);
	}

	@Override
	public void initListener() {
		// TODO 自动生成的方法存根
		
	}
}
