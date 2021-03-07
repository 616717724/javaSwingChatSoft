package com.ui.test.io;

import java.io.File;
import java.util.LinkedList;

public class IOtest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		File dir = new File("./data");
		if (!dir.exists()) {
			dir.mkdir();
		}
		LinkedList<File> list = new LinkedList<File>();
		File[] files = dir.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
//				System.out.println("文件夹:" + file2.getAbsolutePath());
				list.add(file2);
				// foldeNum++;
			} else {
//				System.out.println("文件:" + file2.getAbsolutePath());
				System.out.println("文件:" + file2.getName());
				// fileNum++;
			}
		}

	}
}
