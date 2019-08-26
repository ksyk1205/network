package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import echo.EchoServer;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<PrintWriter> listWriters;
	public ChatServerThread(Socket socket,List<PrintWriter> listWriters) {
		this.socket =socket;
		this.listWriters = listWriters;
	}
	private PrintWriter pw;
	@Override
	public void run() {
		//Remote Host Information
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		EchoServer.log("connected from client[" + 
				inetRemoteSocketAddress.getAddress().getHostAddress() + 
				":" + inetRemoteSocketAddress.getPort() + "]");

		try {
			//Stream
			BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
			pw= new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));

			//요청 처리 
			while(true) {
				String request = br.readLine();
				if(request==null) {
					log("클라이언트로 부터 연결 끊김");
					doQuit(pw);
					break;
				}
				//프로토콜 분석
				String[] tokens = request.split(":");
				if("join".equals(tokens[0])) {
					dojoin(tokens[1],pw);
				}else if("message".equals(tokens[0])) {
					doMessage(tokens[1]);			
				}else if("quit".equals(tokens[0])) {
					doQuit(pw);
					break;
				}else {
					ChatServer.log("error:알 수 없는 요청("+tokens[0]+")");
				}
			}


		}catch(IOException e) {
			e.printStackTrace();
		}





	}

	private void dojoin(String nickname, PrintWriter writer) {
		this.nickname =nickname;
		
		String data = nickname+"님이 참여하였습니다.";
		broadcast(data);

		//wirter pool에 저장
		addWriter(writer);
		
//		ack
		pw.println("join:ok");
		pw.flush();



	}
	private void addWriter(PrintWriter writer) {
		synchronized(listWriters) {
			listWriters.add(writer);	
		}
	}
	private void broadcast(String data) {
	synchronized(listWriters) {
			for(PrintWriter writer :listWriters) {
				PrintWriter printWriter =(PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
		

	}
	private void doMessage(String message) {
		String s ="message:"+message+"\n";
		broadcast(s);
	}
	private void doQuit(PrintWriter writer ) {
		String data =nickname+"님이 퇴장하였습니다.";
		broadcast(data);
		removeWriter(writer);
		
		
	}
	private void removeWriter(PrintWriter writer) {
		synchronized(listWriters) {
			listWriters.remove(writer);	
		}
		
	}

	public static void log(String log) {
		System.out.println("[Echo Server#" + Thread.currentThread().getId() + "] " + log);
	}
}
