package com.ui.setIcon;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

import com.socket.Client;
import com.ui.SecondFrame;
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.panel.chat.Group_chat_all_panel;

public class EmojJDialog extends JDialog{
	JScrollPane jScrollPane;
	JPanel panel;
	Friend_chat_all_panel fp;
	Group_chat_all_panel gp;
	JLabel label[] = new JLabel[40];
	public EmojJDialog(Friend_chat_all_panel fp,int x,int y,int w,int h){
		setBounds(x, y+30, w, h);
		this.fp=fp;
		this.fp.sig.setText("has");
		panel=new JPanel();
		panel.setSize(w, h/2);
		init();
		initListener();
	}
	public EmojJDialog(Group_chat_all_panel gp,int x,int y,int w,int h){
		setBounds(x, y+30, w, h);
		this.gp=gp;
		this.gp.sig.setText("has");
		panel=new JPanel();
		panel.setSize(w, h/2);
		init();
		initListener();
	}
	void init(){
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		
		jScrollPane = new JScrollPane();
		panel.setLayout(new GridLayout(10, 10));
		panel.setBackground(Color.WHITE);
		jScrollPane.setViewportView(panel);
		jScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(jScrollPane);
		getEmoj();
		setVisible(true);
	}
	public JLabel[] getEmoj(){
		for (int i = 0; i < label.length; i++) {
//			ImageIcon icon = new ImageIcon("./resource/img/emoji/" + i+".png");
			ImageIcon icon = new ImageIcon(getClass().getResource("/com/resource/emoji/" +i+".png"));
//			ImageIcon icon = new ImageIcon("./src/com/resource/emoji/" +i+".png");
			label[i] = new JLabel(icon, SwingConstants.CENTER);
			label[i].setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
			label[i].setToolTipText("表情" + i);
			label[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel temp = (JLabel) e.getSource();
					temp.setBorder(BorderFactory.createLineBorder(new Color(13,196,254)));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JLabel temp = (JLabel) e.getSource();
					temp.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if(null!=fp){
						if(!"".equals(fp.sig.getText())){
							JLabel temp = (JLabel) e.getSource();
							fp.sig.setText("");	
							DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							Date d = new Date();
					        String time = sdf.format(d);  
							String u=SecondFrame.lbl_name_text+"  "+time+"\n";
//							fp.edit_receive.setText(u);
							try {
								fp.doc.insertString(fp.doc.getLength(), u, fp.my_attrib);
								fp.edit_receive.setCaretPosition(fp.edit_receive.getStyledDocument().getLength());
								fp.edit_receive.insertIcon(temp.getIcon());
								fp.doc.insertString(fp.doc.getLength(),"\n",null);
								String mesg="type:6,"+SecondFrame.lbl_user_id+"||"+SecondFrame.lbl_name_text
										+"||"+fp.lbl_user_id+"||"+fp.lbl_name_text+"||"+"";
								System.out.println("图片消息:"+mesg);
								Client.dato.writeUTF(mesg);
								Client.dato.flush();
								Client.obj_output.writeObject(temp.getIcon());
								Client.obj_output.flush();
								System.out.println("图片发送完毕");
//								try {
//									System.out.println("等待图片");
//									Object o=Client.obj_input.readObject();
//									System.out.println("发送图片对象:"+o);
//								} catch (ClassNotFoundException e1) {
//									// TODO 自动生成的 catch 块
//									e1.printStackTrace();
//								}
//								System.out.println("发送图片对象");
							} catch (BadLocationException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
						}
					}
					if(null!=gp){
						if(!"".equals(gp.sig.getText())){
							JLabel temp = (JLabel) e.getSource();
							gp.sig.setText("");
							DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							Date d = new Date();
					        String time = sdf.format(d);  
							String u=SecondFrame.lbl_name_text+"  "+time+"\n";
//							gp.edit_receive.setText(u);
							try {
								gp.doc.insertString(gp.doc.getLength(), u, gp.my_attrib);
								gp.edit_receive.setCaretPosition(gp.edit_receive.getStyledDocument().getLength());
								gp.edit_receive.insertIcon(temp.getIcon());
								String mesg = "type:7," + SecondFrame.lbl_user_id
										+ "||" + SecondFrame.lbl_name_text + "||"
										+ gp.lbl_user_id +"||"+gp.lbl_name_text+ "||"+gp.group_chat.getG_list()+"||"+ "";
								System.out.println("图片消息:"+mesg);
								Client.dato.writeUTF(mesg);
								Client.dato.flush();
								Client.obj_output.writeObject(temp.getIcon());
								Client.obj_output.flush();
								System.out.println("图片发送完毕");
								gp.doc.insertString(gp.doc.getLength(),"\n",null);
							} catch (BadLocationException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
						}	
					}
					dispose();
				}
			});
			panel.add(label[i]);
		}
		return label;
	}
	public void initListener() {
//		this.setFocusable(true);
		jScrollPane.addMouseListener(new MouseListener() {
			
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
//				System.out.println("mouseExited");
				if(null!=fp){
					fp.sig.setText("");	
				}
				if(null!=gp){
					gp.sig.setText("");	
				}
				dispose();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
//				System.out.println("mouseEntered");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
	}
	
	public static void main(String[] args) {
//		new EmojJDialog(null,300,400,300,300);
	}
}
