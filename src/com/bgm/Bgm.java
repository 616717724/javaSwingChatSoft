package com.bgm;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Bgm {
    public AudioClip clip = null;
    public AudioClip clip1 = null;
    public AudioClip clip2 = null;
    public AudioClip clip3 = null;
    public AudioClip clip4 = null;
	public Bgm() {
		try {
			//clip=Applet.newAudioClip((new File("./bgm/bgm1.wav")).toURL());
			clip1=Applet.newAudioClip((new File("./resource/bgm/mustknow.wav")).toURL());
//			clip2=Applet.newAudioClip((new File("./bgm/bgm2.wav")).toURL());
//			clip3=Applet.newAudioClip((new File("./bgm/bgm3.wav")).toURL());
//			clip4=Applet.newAudioClip((new File("./bgm/bgm4.wav")).toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
    public AudioClip getAudioClip() {
        return this.clip;
    }
 
    public void setAudioClip(AudioClip clip) {
        this.clip = clip;
    }
     
    public void play() {
        if (getAudioClip() != null) {
            getAudioClip().play();
        }
    }
 
    public void loop() {
        if (getAudioClip() != null) {
            getAudioClip().loop();
        }
    }
 
    public void stop() {//
        if (getAudioClip() != null) {
            getAudioClip().stop();
        }
    }
    public static void main(String[] args) {
    	Bgm  mac = new Bgm();
//        try {
//            mac.setAudioClip(Applet
//                    .newAudioClip((new File("./bgm/bgm3.wav")).toURL()));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    	mac.clip1.loop();
    	
    }
}