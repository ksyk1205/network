package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String SERVER_IP ="192.168.1.13";

	public static void main(String[] args) {
		Scanner scanner =null;
		DatagramSocket socket = null;
		try {
			//키보드 연결
			scanner= new Scanner(System.in);
			//소켓생성
			socket = new DatagramSocket();
			while(true) {
				//사용자 입력을 받음
				String message =scanner.nextLine();
				//"quit"를 입력 받으면 종료
				if("quit".equals(message)) {
					break;

				}
				//메시지 전송
				byte [] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket =new DatagramPacket(sendData,
						sendData.length,
						new InetSocketAddress(SERVER_IP,UDPTimeServer.PORT));
				socket.send(sendPacket);
				//메시지 수신
				DatagramPacket receivePacket =new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE],UDPTimeServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				//만약 입력이 "" 이라면 message 출력
				if("".equals(message)) {
				byte[] data  = receivePacket.getData();
				int length = receivePacket.getLength();
				String date = new String (data, 0,length,"UTF-8");
				System.out.println(date);
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(scanner!=null) {
				scanner.close();
			}
			if(socket != null && socket.isClosed() ==false) {
				socket.close();
			}
		}



	}
}
