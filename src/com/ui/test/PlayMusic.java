package com.ui.test;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

public class PlayMusic {
	public static AudioClip loadSound(String filename) {
		try {
			return JApplet.newAudioClip(new File(filename).toURL());
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	public void play() {
		AudioClip christmas = loadSound("./resource/bgm/mustknow.wav");
		christmas.play();
	}

	public static void main(String[] args) {
		new Thread(){
			public void run(){
				PlayMusic p = new PlayMusic();
				p.play();
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}.start();
	}
}