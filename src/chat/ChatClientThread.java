package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread{
	private BufferedReader bufferedReader;
	private Socket socket;
	public ChatClientThread(Socket socket,BufferedReader br) {
		this.bufferedReader=br;
		this.socket=socket;
	}
	@Override
	public void run() {
		try {
			while(true){	
				String data =bufferedReader.readLine();
				if("Bye".equals(data)) {
					break;
				}
				System.out.println(data);
			} 
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			//Server Socket 자원정리
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
}

