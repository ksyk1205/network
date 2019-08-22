package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {

		try {
			while(true) {
				System.out.print(">");
				Scanner scanner = new Scanner(System.in);
				String domainname = scanner.nextLine();
				
				if(domainname.equals("exit")) {	
					break;
				}
				InetAddress[] inetAddresses =InetAddress.getAllByName(domainname);

				for(InetAddress inetAdress : inetAddresses) {

					System.out.println(domainname+" : "+inetAdress.getHostAddress());
				}
			}
		} catch (UnknownHostException e) {

			
			e.printStackTrace();
		}
	}
}









