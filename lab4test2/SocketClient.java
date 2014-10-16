package lab3;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;


import java.awt.event.*;

public class SocketClient 
{
    Socket Client;
    BufferedReader getMsg;
    BufferedReader br;
    PrintStream OutputS;
    JFrame frame;
    TextField textsend;
    TextArea textget;
 String str=null,data,name;
// String ServerIp="192.168.0.111";      
 public SocketClient() {
	 
	 textsend =new TextField();
	 textget = new TextArea();
	 frame =new JFrame();
	 frame.getContentPane().setLayout(new BorderLayout(5,10));
	 frame.getContentPane().add(textsend,BorderLayout.SOUTH);
	 frame.getContentPane().add(textget);
	 frame.pack();
	 frame.setVisible(true);
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 textget.setEditable(false);
	 
	 
	 
	 textsend.addKeyListener(new KeyListener(){

  		@Override
  		public void keyTyped(KeyEvent e) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void keyPressed(KeyEvent e) {
  			// TODO Auto-generated method stub
  			if (e.getKeyCode()==KeyEvent.VK_ENTER)
  			{
  				str=textsend.getText();
  				
  			}
  			if(textsend.getText()==null||textsend.getText()=="")
  				str = "";
  		}

  		@Override
  		public void keyReleased(KeyEvent e) {
  			// TODO Auto-generated method stub
  			
  		}
  		 
  	 });
	 
	 
	 
	 
	 
      try 
      {
        Client = new Socket("localhost",45537);     
        textget.append("���ӳɹ�!\n");
        
      }
      catch(IOException e)
      {
        textget.append("�������ӵ���������"+e+"\n");
        return;
      } 
     try{
    new Accept();                          
    new Send();                       
     }
     catch(Exception e){                      
      textget.append("��Ϣ�޷����ջ򴫵ݣ�\n");
     }
   }
 
 
 
 public class Accept extends Thread{         
  public Accept(){
   this.start();      
  }
    public void run(){           
      try       
         {         
        getMsg = new BufferedReader(new InputStreamReader(Client.getInputStream()));
           while (true)        
           {
           data = getMsg.readLine();   
           textget.append(data+"\n");
           if(data.equals(""))   break;      
           else
             {
             continue;  
             }
           }
         }
         catch(IOException e)                 
         {
           textget.append("����ʧ�ܣ�\n");
         }
    }
  }

  public class Send extends Thread{                  
     public Send(){
         this.start();
             }
       public void run(){
         try       
            {  
          while (true)
              { 
              OutputS = new PrintStream(Client.getOutputStream());
              
              
              
              
              
              
              
              
              if(str==""||(str==null))      
                   {
                 continue;
                   }
              else if(str.equals("quit")){       
               OutputS.println(str);
               break;
              }
              else {
               OutputS.println(str);
               textsend.setText("");
               str = "";
                }
            }
            }
            catch(IOException e)                 
            {
              textget.append("����ʧ�ܣ�");
            } 
            try
            {
              textget.append("���ӽ�����");
              Client.close();
            }
            catch(IOException e)                  
            {
              textget.append("ϵͳ���ϣ�");
            } 
  }
}
  
  
  public static void main(String args[])
  {
    new SocketClient();          
  }
}
