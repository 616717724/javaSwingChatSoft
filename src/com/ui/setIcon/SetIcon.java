package com.ui.setIcon;

import java.awt.Image;

import javax.swing.ImageIcon;

public class SetIcon {
	public static ImageIcon getPicture(String name,int w,int h) {
//		getClass().getResource("/com/img/"+image_name)
//		ImageIcon icon = new ImageIcon("./resource/img/bg2.png");
		ImageIcon icon = new ImageIcon(new SetIcon().getClass().getResource("/com/resource/img/"+name));
//		System.out.println(new SetIcon().getClass().getResource("/com/resource/img/"+name));
		Image img = icon.getImage();
		img = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
		icon= new ImageIcon(img);
		return icon;
	}
	public ImageIcon getPicture2(String name,int w,int h) {
//		getClass().getResource("/com/img/"+image_name)
//		ImageIcon icon = new ImageIcon("./resource/img/bg2.png");
		ImageIcon icon = new ImageIcon(getClass().getResource("/com/resource/img/"+name));
//		System.out.println(new SetIcon().getClass().getResource("/com/resource/img/"+name));
		Image img = icon.getImage();
		img = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
		icon= new ImageIcon(img);
		return icon;
	}
	public static void main(String[] args) {
		new SetIcon();
	}
}
