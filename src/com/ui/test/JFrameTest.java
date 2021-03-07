package com.ui.test;

import javax.swing.JFrame;

public class JFrameTest {
    private JFrame frame = null; 
    public JFrameTest() { 
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
        try { 
            // 这里等待3秒钟，你打开其程序，
            //主要目的是使frame不是当前的聚焦窗体
            Thread.sleep(3000);
        }catch (InterruptedException e1){ 
            e1.printStackTrace();
        } 
        frame.setVisible(true);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        new JFrameTest();
    }
 
}
