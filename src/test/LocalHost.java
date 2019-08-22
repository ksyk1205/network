package test;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class LocalHost {

	public static void main(String[] args)  {
		try {
			InetAddress inetAddress =InetAddress.getLocalHost();

			String hostname =inetAddress.getHostName();
			String hostAddress =inetAddress.getHostAddress();


			System.out.println(hostname);
			System.out.println(hostAddress);


			byte[] ipAddresses =inetAddress.getAddress(); //4바이트
			for(byte ipAddress: ipAddresses){
				System.out.print(ipAddress & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
