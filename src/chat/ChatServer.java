package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class ChatServer {
	public static final int PORT =8900;
	public static String SERVER_IP ="127.0.0.1";
	public static List<Writer> listWriters = new ArrayList<Writer>();
	public static void main(String [] args) {
		ServerSocket serverSocket = null;
		
		try {
			//서버소켓 생성
			serverSocket = new ServerSocket();
			
			//binding
			//String inetAddress =InetAddress.getLocalHost().getHostAddress(); //ip 주소 
			InetSocketAddress inetSocketAddress =new InetSocketAddress(SERVER_IP,PORT);//ip 소켓 주소(ip 주소 + 포트 번호)
			serverSocket.bind(inetSocketAddress);// 포트 바인딩
			log("binding"+SERVER_IP+":"+PORT);
			
			//요청 대기
			while(true) {
				Socket socket = serverSocket.accept();//blocking
				//ChatServerThread 시작
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
		System.out.println("[Chat Server#" + Thread.currentThread().getId() + "] " + log);
	}

}
