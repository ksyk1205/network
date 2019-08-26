package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT =8800;
	public static final int BUFFER_SIZE =1024;
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			//UDP 소켓생성
			socket = new DatagramSocket(PORT);
			//data 수신
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			socket.receive(receivePacket);//blocking
			//data 처리
			byte[] data  = receivePacket.getData();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
			String date= format.format(new Date());
			
			System.out.println("[UDP Time Server]received:"+date);
			
			//data 전송
			byte[] sendData = date.getBytes("UTF-8");
			DatagramPacket sendPacket =new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
			socket.send(sendPacket);



		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

			if(socket !=null && socket.isClosed() ==false) {
				socket.close();
			}

		}

	}
}
