
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
   server = new ServerSocket(45537); // ����һ������������ָ���Ķ˿�port������һ������Socket
  } catch (IOException e) {
   System.out.println("������δ��������" + e); // �����쳣
  }
  System.out.println("������������!");
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
   System.out.println("���������� !"); 
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
    ps.println("������������֣�(��������ֲ���Ϊ��)");
    getMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
    name = getMsg.readLine();
   } catch (IOException e) {              
    System.out.println("������ϣ�");
   }
   ps.println("�����ѳɹ���");
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
      ps.println("������Ϣ����Ϊ�գ�");
      continue;
     }
     else if(data.equals("quit")) {          
      showMsg = "�û�" + name + "�˳��˴˴ζԻ���";
      count--;
     }
     else{
      showMsg = "�û�" + name + "��������Ϣ��" + data;
     }
     sendAll(showMsg+"              Ŀǰ�����û�������"+count); 
     showMsg = null;
    }
   } catch (Exception e) {
   }
  }
 }
}