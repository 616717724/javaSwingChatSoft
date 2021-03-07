package com.iflytek;


import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;

public class Iflytek {
	SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();

	public Iflytek() {
		SpeechUtility.createUtility(SpeechConstant.APPID + "= XXXXXXXX ");
		init();
	}
	public void init() {
		// 1.创建 SpeechSynthesizer 对象
		mTts = SpeechSynthesizer.createSynthesizer();
		// 2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围 0~100
		// 设置合成音频保存位置（可自定义保存位置），保存在“./tts_test.pcm”
		// 如果不需要保存合成音频，注释该行代码
		// mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");
		// 3.开始合成
	}
	public void start(String s) {
		mTts.startSpeaking(s, null);
	}
	public static void main(String[] args) {
//		SpeechUtility.createUtility(SpeechConstant.APPID + "= XXXXXXXX ");
		//new test().start("语音合成测试程序");
		Iflytek t=new Iflytek();
		t.start("语音合成测试程序");
	}
}
