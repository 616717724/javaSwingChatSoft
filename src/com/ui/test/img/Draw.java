package com.ui.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Draw {
	private static Font mFont = new Font("宋体", Font.PLAIN, 20);

	public static void main(String[] args) {

		File f1 = new File("C:\\Users\\XWD\\Pictures\\新建文本文档.txt");
		File f2 = new File(
				"C:\\Users\\XWD\\Pictures\\神奇的海螺2.jpg");
		File f3 = new File("C:\\Users\\XWD\\Pictures\\神奇的海螺.jpg");
		draw2(f1, f2, f3, true);

	}

	// 直接在一张已有的图片上写字，可指定文字颜色。如果背景图片参数为空或者""，则写张白图
	public static void draw2(File content, File outPictrue, File background,
			boolean b) {
		FileReader f = null;
		try {
			f = new FileReader(content);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(f);
		String line = "";

		int lineNum = 0; // 一共有多少行
		List<String> list = new ArrayList<String>();
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				list.add(line);
				++lineNum;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int[] iArray = new int[list.size()];
		int length = list.size();
		for (int k = 0; k < length; k++) {
			iArray[k] = format1(list.get(k));
		}
		Arrays.sort(iArray);
		int lineLength = iArray[length - 1];
		System.out.println("文本中最长的一行的长度是：" + lineLength);
		System.out.println("一共的行数是：" + lineNum);
		BufferedImage image = null;
		if (background.equals("") || background == null) {
			double h = 256 / 16;
			double w = 85 / 10;
			int width = (int) (w * lineLength) + 40, height = (int) (h * lineNum);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		} else {
			try {
				image = ImageIO.read(background);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Graphics g = image.getGraphics();
		// g.setColor(getRandColor(200,250));
		// g.fillRect(1, 1, width-1, height-1);
		if (b == false) {
			g.setColor(new Color(102, 102, 102)); // 设字体为黑色,否则就是白色
		}
		// g.drawRect(0, 0, width-1, height-1);
		g.setFont(mFont);
		int k = 0;
		Iterator<String> it = list.iterator();
//		while (it.hasNext()) {
//			g.drawString(it.next(), 30, 15 + 20 * k);
//			++k;
//		}
		g.drawString("asdasdasd", 30, 15 + 20 * k);
		try {
			ImageIO.write(image, "JPEG", outPictrue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 返回占位符的长度。
	public static int format1(String s) {
		int length = 0;
		for (int t = 0; t < s.length(); t++) {
			if (s.charAt(t) > 255) {
				length = length + 2;
			} else {
				length = length + 1;
			}
		}
		System.out.println(length);
		return length;
	}

	// 生成一张白图片，根据文本信息，来决定图片的长度和宽度。
	// 缺点：字体不同，字体样式不同，都会影响整个图片。
	public static void draw1() {
		FileReader f = null;
		try {
			f = new FileReader("C:\\Users\\XWD\\Pictures\\神奇的海螺.jpg");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(f);
		String line = "";

		int lineNum = 0; // 一共有多少行
		List<String> list = new ArrayList<String>();
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				list.add(line);
				++lineNum;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int[] iArray = new int[list.size()];
		int length = list.size();
		for (int k = 0; k < length; k++) {
			iArray[k] = format1(list.get(k));
		}
		Arrays.sort(iArray);
		int lineLength = iArray[length - 1];
		System.out.println("文本中最长的一行的长度是：" + lineLength);
		System.out.println("一共的行数是：" + lineNum);

		double h = 256 / 16;
		double w = 85 / 10;
		int width = (int) (w * lineLength) + 40, height = (int) (h * lineNum);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// g.setColor(getRandColor(200,250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(mFont);
		int k = 0;
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			g.drawString(it.next(), 30, 15 + 15 * k);
			++k;
		}
		try {
			ImageIO.write(image, "JPEG", new File(
					"C:\\Users\\XWD\\Pictures\\神奇的海螺2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
