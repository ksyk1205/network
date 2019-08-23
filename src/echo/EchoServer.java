package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class EchoServer {
	private static final int PORT =8000;

	public static void main(String[] args) {
		ServerSocket serverSocket =null;
		try {
			//1.서버소켓 생성
			serverSocket= new ServerSocket();
			
			//2.Binding : Socket에 SocketAddress(IPAddress + Port) 바인딩한다
			InetAddress inetAddress =InetAddress.getLocalHost();//ip주소를 얻어올 inetAddress
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,PORT);
			serverSocket.bind(inetSocketAddress);//
			log("binding "+inetAddress.getHostAddress()+":"+PORT);

			//3. accept: 클라이언트로 부터 연결요청(Connect)을 기다린다.
			Socket socket =serverSocket.accept();
			InetSocketAddress inetRemoteSocketAddress =(InetSocketAddress) socket.getRemoteSocketAddress();
			
			log("connected from client["+inetRemoteSocketAddress.getAddress().getHostAddress()+":"+inetRemoteSocketAddress.getPort()+"]");
			
			try {
				
				//4. I/O Stream생성
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
				
				
				while(true) {
					//5.데이터 읽기 (수신)
					String data =br.readLine();
					if(data == null) {
						log("closed by client");
						break;
					}
					log("received:" +data);
					
					//6.데이터 쓰기 (전송)
					pw.println(data);
					
				}

			}catch(SocketException e) {

				log("abnormal closed by client");

			}catch(IOException e) {
				e.printStackTrace();

			}finally {
				if(socket != null && socket.isClosed() ==false)
					socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket !=null && serverSocket.isClosed()==false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void log(String log) {
		System.out.println("[Echo Server]"+log);
	}

}
