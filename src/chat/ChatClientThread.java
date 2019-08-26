package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread{
	private BufferedReader bufferedReader;
	public ChatClientThread(BufferedReader br) {
		this.bufferedReader=br;
	}
	@Override
	public void run() {
		try {
			while(true){	

				String data =bufferedReader.readLine();
				if(data==null)
					break;
				System.out.println(data);
			} 
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

