
package lab3;

import java.io.*;
import java.net.*;
import java.util.*;

class SocketServer extends Thread {
 int count=0;
 ServerSocket server;
 String str = null;
 String data = null;
 String showMsg = null;
 BufferedReader br,getMsg;
 List<Accept> socketList;

 public static void main(String args[]) {
  new SocketServer();
 }

 public SocketServer() {
  try {
   server = new ServerSocket(45537); // 建立一个服务器，用指定的端口port来创建一个侦听Socket
  } catch (IOException e) {
   System.out.println("服务器未能启动：" + e); // 捕获异常
  }
  System.out.println("服务器已启动!");
  this.start();                        
 }

 public void run() {
  socketList = new LinkedList<Accept>();
  try {
   while (true) {
    Socket client = server.accept(); 
    count++;                     
    Accept s = new Accept(client); 
    socketList.add(s);
   }
  } catch (IOException e) {
   System.out.println("服务器故障 !"); 
   return;
  }
 }

 void sendAll(String Message) {           
  synchronized (socketList) {
   for (Accept a : socketList) {
    a.send(Message);
   }
  }
 }

 public class Accept extends Thread {
  PrintStream ps;
  String name = null;  
  Socket s ;
  public Accept(Socket ClientSocket) {
   s = ClientSocket;

   try {                         
    ps = new PrintStream(s.getOutputStream());
    ps.println("请输入你的名字：(输入的名字不能为空)");
    getMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
    name = getMsg.readLine();
   } catch (IOException e) {              
    System.out.println("输入故障！");
   }
   ps.println("输入已成功！");
   this.start();
  }

  public void send(String Message) {
   ps.println(Message);
  }

  public void run() {
   try {
    while (true) {
     br = new BufferedReader(new InputStreamReader(s.getInputStream()));
     ps = new PrintStream(s.getOutputStream());
     data = br.readLine();
     if (data.equals("")||data==null) {               
      ps.println("发送消息不能为空！");
      continue;
     }
     else if(data.equals("quit")) {          
      showMsg = "用户" + name + "退出了此次对话！";
      count--;
     }
     else{
      showMsg = "用户" + name + "发出的消息：" + data;
     }
     sendAll(showMsg+"              目前在线用户人数："+count); 
     showMsg = null;
    }
   } catch (Exception e) {
   }
  }
 }
}