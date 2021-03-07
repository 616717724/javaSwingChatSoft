package com.ui.panel;
import java.awt.Graphics;  
import java.awt.Image;  
import java.awt.event.ActionEvent;  
  
import javax.swing.ImageIcon;  
import javax.swing.JPanel;  
public class BgPanel extends JPanel {  
    ImageIcon icon;  
    Image img;
    int w=this.getWidth(),h=this.getHeight();
    /**
     * @wbp.parser.constructor
     */
    public BgPanel(String image_name) {
        //  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片  
        icon=new ImageIcon(getClass().getResource("/com/resource/img/"+image_name));  
        img=icon.getImage();  
        w=this.getWidth();
        h=this.getHeight();
    }  
    public BgPanel(String image_name,int w,int h) {  
        //  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片  
        icon=new ImageIcon(getClass().getResource("/com/resource/img/"+image_name));  
        this.w=w;
        this.h=h;
        img=icon.getImage();  
    }  
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小  
        g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);  
    }  
  
}  
