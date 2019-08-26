package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class ChatServer {
	public static final int PORT =8880;
	public static List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
	public static void main(String [] args) {
		ServerSocket serverSocket = null;
		
		try {
			//서버소켓 생성
			serverSocket = new ServerSocket();
			//binding
			InetAddress inetAddress =InetAddress.getLocalHost(); //ip 주소 
			InetSocketAddress inetSocketAddress =new InetSocketAddress(inetAddress,PORT);//ip 소켓 주소(ip 주소 + 포트 번호)
			serverSocket.bind(inetSocketAddress);// 포트 바인딩
			log("binding"+inetAddress+":"+PORT);
			
			//요청 대기
			while(true) {
				Socket socket = serverSocket.accept();//blocking
				new ChatServerThread(socket,listWriters).start();
			}
			
	
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//Server Socket 자원정리
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[Echo Server#" + Thread.currentThread().getId() + "] " + log);
	}

}
