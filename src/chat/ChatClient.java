package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
//	private static String SERVER_IP ="192.168.1.13";
	public static void main(String []args) {
//		Scanner scanner =null;
		Socket socket =null;

		try {
			//키보드 연결
			Scanner scanner= new Scanner(System.in);

			//socket 생성
			socket = new Socket();

			//연결
			socket.connect(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), ChatServer.PORT));
			//socket.connect(new InetSocketAddress(SERVER_IP,ChatServer.PORT));
			log("connected");

			//reader/writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			//join프로토콜
			System.out.print("닉네임>>");
			String nickaname =scanner.nextLine();
			pw.println("join:"+nickaname);
			pw.flush();

			//ChatClientReceiveThread 시작
			new ChatClientThread(socket,br).start();

			//키보드 입력 처리
			while(true) {
				System.out.print(">>");
				String input = scanner.nextLine();

				if("quit".equals(input)) {
					pw.println("quit:");
					break;
				}
				if(input.equals("")) {
					input =" ";
				}
				pw.println("message:"+input);

			}


		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void log(String log) {
		System.out.println("[Chat Client#" + Thread.currentThread().getId() + "] " + log);
	}

}

