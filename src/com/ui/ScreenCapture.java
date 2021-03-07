package com.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

import com.socket.Client;
import com.ui.panel.chat.Friend_chat_all_panel;
import com.ui.panel.chat.Group_chat_all_panel;

/**
 * 一个简单的屏幕抓图
 * 
 **/

public class ScreenCapture {
	// test main
	public static void main(String[] args) throws Exception {
		new ScreenCapture().startATime();
	}

	private Friend_chat_all_panel fp;
	private Group_chat_all_panel gp;


	public void startATime() {
		ScreenCapture capture = ScreenCapture.getScreenCapture();
		capture.captureImage();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel imagebox = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(BorderLayout.CENTER, imagebox);
		imagebox.setIcon(capture.getPickedIcon());
		
//		String name = String.valueOf(System.currentTimeMillis());
//		File f = new File(FileManager.getDirPath(StateManager.getStateManager()
//				.getUserID(), true)
//				+ name + ".png");
//		try {
//			capture.saveAsJPEG(f);
//			c.insertPic(f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("[Over]");
	}
	public void startATime(Friend_chat_all_panel fp) {
		this.fp=fp;
		ScreenCapture capture = ScreenCapture.getScreenCapture();
		capture.captureImage();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel imagebox = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(BorderLayout.CENTER, imagebox);
		imagebox.setIcon(capture.getPickedIcon());
		send_img(imagebox,1);
	}
	public void startATime(Group_chat_all_panel gp) {
		this.gp=gp;
		ScreenCapture capture = ScreenCapture.getScreenCapture();
		capture.captureImage();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel imagebox = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(BorderLayout.CENTER, imagebox);
		imagebox.setIcon(capture.getPickedIcon());
		send_img(imagebox,2);
	}

	public ScreenCapture() {

		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("Internal Error: " + e);
			e.printStackTrace();
		}
		JPanel cp = (JPanel) dialog.getContentPane();
		cp.setLayout(new BorderLayout());
		labFullScreenImage.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent evn) {
				isFirstPoint = true;
				pickedImage = fullScreenImage.getSubimage(recX, recY, recW,
						recH);
				dialog.setVisible(false);
			}

			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				x1 = e.getX();
				y1 = e.getY();
				isFirstPoint = false;
			}
		});
		labFullScreenImage.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evn) {
				x2 = evn.getX();
				y2 = evn.getY();
				int maxX = Math.max(x1, x2);
				int maxY = Math.max(y1, y2);
				int minX = Math.min(x1, x2);
				int minY = Math.min(y1, y2);
				recX = minX;
				recY = minY;
				recW = maxX - minX;
				recH = maxY - minY;
				labFullScreenImage.drawRectangle(recX, recY, recW, recH);
			}

			public void mouseMoved(MouseEvent e) {
				labFullScreenImage.drawCross(e.getX(), e.getY());
			}
		});

		cp.add(BorderLayout.CENTER, labFullScreenImage);
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		dialog.setAlwaysOnTop(true);
		dialog.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		dialog.setUndecorated(true);
		dialog.setSize(dialog.getMaximumSize());
		dialog.setModal(true);
	}

	// Singleton Pattern
	public static ScreenCapture getScreenCapture() {
		return defaultCapturer;
	}

	/** 捕捉全屏慕 */
	public Icon captureFullScreen() {
		fullScreenImage = robot.createScreenCapture(new Rectangle(Toolkit
				.getDefaultToolkit().getScreenSize()));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		return icon;
	}

	/** 捕捉屏幕的一个矫形区域 */
	public void captureImage() {
		fullScreenImage = robot.createScreenCapture(new Rectangle(Toolkit
				.getDefaultToolkit().getScreenSize()));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		labFullScreenImage.setIcon(icon);
		dialog.setVisible(true);
	}

	/** 得到捕捉后的BufferedImage */
	public BufferedImage getPickedImage() {
		return pickedImage;
	}

	/** 得到捕捉后的Icon */
	public ImageIcon getPickedIcon() {
		return new ImageIcon(getPickedImage());
	}

	/**
	 * 储存为一个文件,为PNG格式
	 * 
	 * @deprecated replaced by saveAsPNG(File file)
	 **/
	@Deprecated
	public void saveToFile(File file) throws IOException {
		ImageIO.write(getPickedImage(), defaultImageFormater, file);
	}

	/** 储存为一个文件,为PNG格式 */
	public void saveAsPNG(File file) throws IOException {
		ImageIO.write(getPickedImage(), "png", file);
	}

	/** 储存为一个JPEG格式图像文件 */
	public void saveAsJPEG(File file) throws IOException {
		ImageIO.write(getPickedImage(), "JPEG", file);
	}

	/** 写入一个OutputStream */
	public void write(OutputStream out) throws IOException {
		ImageIO.write(getPickedImage(), defaultImageFormater, out);
	}
	
	void send_img(JLabel temp,int kind){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date d = new Date();
        String time = sdf.format(d);  
		String u=SecondFrame.lbl_name_text+"  "+time+"\n";
		try {
			String mesg="";
			if(1==kind){
				mesg="type:6,"+SecondFrame.lbl_user_id+"||"+SecondFrame.lbl_name_text
						+"||"+fp.lbl_user_id+"||"+fp.lbl_name_text+"||"+"";
				fp.doc.insertString(fp.doc.getLength(), u, fp.my_attrib);
				fp.edit_receive.setCaretPosition(fp.edit_receive.getStyledDocument().getLength());
				fp.edit_receive.insertIcon(temp.getIcon());
				fp.doc.insertString(fp.doc.getLength(),"\n",null);
			}else{
				mesg = "type:7," + SecondFrame.lbl_user_id
						+ "||" + SecondFrame.lbl_name_text + "||"
						+ gp.lbl_user_id +"||"+gp.lbl_name_text+ "||"+gp.group_chat.getG_list()+"||"+ "";
				gp.doc.insertString(gp.doc.getLength(), u, gp.my_attrib);
				gp.edit_receive.setCaretPosition(gp.edit_receive.getStyledDocument().getLength());
				gp.edit_receive.insertIcon(temp.getIcon());
				gp.doc.insertString(gp.doc.getLength(),"\n",null);	
			}
			System.out.println("图片消息:"+mesg);
			Client.dato.writeUTF(mesg);
			Client.dato.flush();
			Client.obj_output.writeObject(temp.getIcon());
			Client.obj_output.flush();
			System.out.println("图片发送完毕");
		} catch (BadLocationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	// singleton design pattern
	private static ScreenCapture defaultCapturer = new ScreenCapture();
	private int x1, y1, x2, y2;
	private int recX, recY, recH, recW; // 截取的图像
	private boolean isFirstPoint = true;
	private BackgroundImage labFullScreenImage = new BackgroundImage();
	private Robot robot;
	private BufferedImage fullScreenImage;
	private BufferedImage pickedImage;
	private String defaultImageFormater = "png";
	private JDialog dialog = new JDialog();
}

/** 显示图片的Label */
class BackgroundImage extends JLabel {
	private static final long serialVersionUID = -5544483959572191701L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(x, y, w, h);
		String area = Integer.toString(w) + " * " + Integer.toString(h);
		g.drawString(area, x + (int) w / 2 - 15, y + (int) h / 2);
		g.drawLine(lineX, 0, lineX, getHeight());

		g.drawLine(0, lineY, getWidth(), lineY);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		h = height;
		w = width;
		repaint();
	}

	public void drawCross(int x, int y) {
		lineX = x;
		lineY = y;
		repaint();
	}

	int lineX, lineY;
	int x, y, h, w;

}
