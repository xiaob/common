package tmp.net.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Hex;

public class NetTest1 {

	public static void main(String[] args)throws Exception {
		Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
		while(networkInterfaceEnumeration.hasMoreElements()){
			NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
			System.out.println("==== " + networkInterface.getName());
			Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
			while(inetAddressEnumeration.hasMoreElements()){
				InetAddress inetAddress = inetAddressEnumeration.nextElement();
				System.out.println(inetAddress.getHostAddress() + " : " + inetAddress.getHostName());
			}
			printMacAddress(networkInterface);
		}
		
		System.out.println(networkInterfaceEnumeration.toString());
		System.out.println(networkInterfaceEnumeration.hashCode());
	}
	
	private static void printMacAddress(NetworkInterface networkInterface){
		try {
			byte[] macAddress = networkInterface.getHardwareAddress();
			if(macAddress != null){
				System.out.println(Hex.encodeHexString(macAddress).toUpperCase());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
