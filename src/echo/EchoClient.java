package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP="192.168.1.13";
	private static final int PORT =8000;

	public static void main(String[] args) {
		Socket socket = null;
		
		try {

			socket =new Socket();
			
			InetSocketAddress inetsocketAddress =new InetSocketAddress(SERVER_IP,PORT);

			socket.connect(inetsocketAddress);
			
			System.out.println("[TCPClient] connected");
			

			InputStream is=socket.getInputStream();
			OutputStream os =socket.getOutputStream();
			
			Scanner scanner = new Scanner(System.in);

			String data = scanner.nextLine();
			os.write(data.getBytes("UTF-8"));
			

			byte [] buffer = new byte[256];
			int readByteCount =is.read(buffer);
			if(readByteCount == -1) {
				System.out.println("[TCPServer] closed by client");
				return;
			}

			data = new String(buffer,0, readByteCount,"UTF-8");
			System.out.println("[TCPClient] received:"+data);
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket !=null && socket.isClosed() ==false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
