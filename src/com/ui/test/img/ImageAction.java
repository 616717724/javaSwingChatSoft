package com.ui.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;


@SuppressWarnings("serial")
public class ImageAction  {
	private ByteArrayInputStream inputStream;

	public String execute() throws Exception {
		// 在内存中创建图象
		int width = 300, height = 200;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景色
		g.setColor(new Color(250, 250, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("宋体", Font.BOLD, 18));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(new Color(0, 0, 0));

		// g.drawLine(0,0,50,50);

		// 取随机产生的认证码(6位数字)
		g.drawString("中国", 10, 30);
		g.drawString("中国", 60, 30);
		g.drawString("中国", 110, 30);
		g.drawString("中国", 160, 30);
		g.drawString("中国", 210, 30);
		g.drawString("山东", 10, 60);
		g.drawString("山东", 60, 60);
		g.drawString("山东", 110, 60);
		g.drawString("山东", 160, 60);
		g.drawString("山东", 210, 60);
		g.drawString("济南", 10, 90);
		g.drawString("济南", 60, 90);
		g.drawString("济南", 110, 90);
		g.drawString("济南", 160, 90);
		g.drawString("济南", 210, 90);
		g.drawString("洪楼", 10, 120);
		g.drawString("洪楼", 60, 120);
		g.drawString("洪楼", 110, 120);
		g.drawString("洪楼", 160, 120);
		g.drawString("洪楼", 210, 120);
		g.drawString("花园", 10, 150);
		g.drawString("花园", 60, 150);
		g.drawString("花园", 110, 150);
		g.drawString("花园", 160, 150);
		g.drawString("花园", 210, 150);
		// 图象生效
		g.dispose();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		ImageIO.write(image, "JPEG", imageOut);
		imageOut.close();
		ByteArrayInputStream input = new ByteArrayInputStream(
				output.toByteArray());
		this.setInputStream(input);
		return "SUCCESS";
	}

	/* 
     *  
     */

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
}
