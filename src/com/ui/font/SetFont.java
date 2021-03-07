package com.ui.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class SetFont {
	public SetFont(String name, int style, int size) {
		InitGlobalFont(new Font(name, style, size));
		UIManager.put("Menu.font", new Font(name, style, size));// 设置Menubar的字体
		UIManager.put("MenuItem.font", new Font(name, style, size));// 设置MenuItem的字体
	}
	public SetFont(){
		
	}
	public void SetDefault(int style, int size){
		//			File f=new File("./resource/font/default.TTF");
		//			java.io.FileInputStream fi = new java.io.FileInputStream(f);
		//			java.io.BufferedInputStream fb = new java.io.BufferedInputStream(fi);
		//			Font defaultFont = Font.createFont(Font.TRUETYPE_FONT, fb);
		//			defaultFont = defaultFont.deriveFont(style, size);
					Font defaultFont =new Font("微软雅黑", Font.BOLD, 20);
					defaultFont = defaultFont.deriveFont(style, size);
					InitGlobalFont(defaultFont);
					UIManager.put("Menu.font",defaultFont);// 设置Menubar的字体
					UIManager.put("MenuItem.font",defaultFont);// 设置MenuItem的字体
	}
	static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
}
