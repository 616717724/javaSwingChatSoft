package com.ui.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @author 白芷
 * @Date 2017/03/12
 * @use 利用Java代码给图片加水印
 */
public class WaterMarkUtils {

	/**
	 * @param srcImgPath
	 *            源图片路径
	 * @param tarImgPath
	 *            保存的图片路径
	 * @param waterMarkContent
	 *            水印内容
	 * @param markContentColor
	 *            水印颜色
	 * @param font
	 *            水印字体
	 */
	public void addWaterMark(String srcImgPath, String tarImgPath,
			String waterMarkContent, Color markContentColor, Font font) {

		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);// 得到文件
			Image srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
			int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
			int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
			System.out.println("srcImgWidth=" + srcImgWidth);
			System.out.println("srcImgHeight=" + srcImgHeight);
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(markContentColor); // 根据图片的背景设置水印颜色
			 g.setFont(font); // 设置字体

			// 设置水印的坐标
			// int x = srcImgWidth - 2 * getWatermarkLength(waterMarkContent,
			// g);
			// int y = srcImgHeight - 2 * getWatermarkLength(waterMarkContent,
			// g);
			int x = srcImgWidth / 2-(waterMarkContent.length()*2);
			int y = srcImgHeight / 2;
			System.out.println("x=" + x);
			System.out.println("y=" + y);
			g.drawString(waterMarkContent, x, y); // 画出水印
			g.dispose();
			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
			ImageIO.write(bufImg, "JPG", outImgStream);
			System.out.println("添加水印完成");
			outImgStream.flush();
			outImgStream.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(
				waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public static void main(String[] args) {
		Font font = new Font("微软雅黑", Font.PLAIN, 50); // 水印字体
		String srcImgPath = "C:\\Users\\XWD\\Pictures\\神奇的海螺.jpg"; // 源图片地址
		String tarImgPath = "C:\\Users\\XWD\\Pictures\\神奇的海螺2.jpg"; // 待存储的地址
		String waterMarkContent = "图片来源：http://blog.csdn.net/zjq_1314520"; // 水印内容
		Color color = new Color(255, 255, 255, 128); // 水印图片色彩以及透明度
		new WaterMarkUtils().addWaterMark(srcImgPath, tarImgPath,
				waterMarkContent, color, font);

	}
}
