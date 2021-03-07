package com.ui.test.EditPanel;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.event.*;

public class JEditorPane4 {

	public static void main(String[] args){
		
		JEditorPane editPane = null;
		try{
		  File thef = new File ("D:/HBuilder/tmp/J2eeTest/index.html");
	    	  String str = thef.getAbsolutePath();
	    	  str = "file:"+str;
	    	  editPane = new JEditorPane();
	    	  editPane.setPage(str);
	    	}
		catch(IOException ioe){
		  ioe.printStackTrace(System.err);
		  System.exit(0);
		}
		editPane.setEditable(false);
		
		final JEditorPane thePane = editPane;
        //采用inner class的方式编写触发超级链接事件时的对应操作类
		editPane.addHyperlinkListener(new HyperlinkListener(){
		  public void hyperlinkUpdate(HyperlinkEvent hle){//覆写hyperlinkUpdate()方法，当超级链接事件触发时会进入这
//个区段运行.
		    try{
               //判断是否为超级链接运行操作。若操作为真，则将新的HTML文件放到JEditorPane中,
               //操作为(thePane.setPage(hle.getURL());)
		      if (hle.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
		    	  thePane.setPage(hle.getURL());
		    	  System.out.println("超链接");
		      }
		        
		    }
		    catch(IOException ioe){
		      ioe.printStackTrace(System.err);
		    }
		  }
		});
		
		JFrame f = new JFrame("JEditorPane4");
		f.setContentPane(new JScrollPane(editPane));
		f.setSize(200,250);
    		f.show(); 
    		f.addWindowListener(new WindowAdapter() {            
    		  public void windowClosing(WindowEvent e) {                    
    		 	  System.exit(0);            
    		  }        
    		});    
	}//end of main()
}// end of class JEditPane1
