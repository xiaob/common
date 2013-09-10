package tmp.net.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class AddressDemo {

	public static void main(String[] args)throws Exception {
		testAddress();
	}
	
	public static void testInterface()throws Exception{
		Enumeration<NetworkInterface> networkInterfaceEnum = NetworkInterface.getNetworkInterfaces();
		while(networkInterfaceEnum.hasMoreElements()){
			NetworkInterface networkInterface = networkInterfaceEnum.nextElement();
			System.out.println(networkInterface.getName() + ", " + networkInterface.getDisplayName() + ", " + toHexString(networkInterface.getHardwareAddress()));
			
			Enumeration<InetAddress> inetAddressEnum = networkInterface.getInetAddresses();
			while(inetAddressEnum.hasMoreElements()){
				InetAddress inetAddress = inetAddressEnum.nextElement();
				System.out.println(inetAddress.getHostAddress() + ", " + inetAddress.getHostName() + ", " + inetAddress.getClass().getName() + ", " + inetAddress.getCanonicalHostName());
				System.out.println(inetAddress.toString());
			}
			System.out.println("=====================");
		}
		
		InetAddress[] inetAddressList = InetAddress.getAllByName("localhost");
		for(InetAddress inetAddress : inetAddressList){
			System.out.println(inetAddress.getHostAddress() + ", " + inetAddress.getHostName() + ", " + inetAddress.getClass().getName());
		}
	}

	private static String toHexString(byte[] macAddress){
		StringBuilder sb = new StringBuilder();
		if(macAddress != null){
			for(byte addr : macAddress){//无符号字节
				String section = Integer.toHexString(addr & 0xff).toUpperCase();
				if(section.length() > 2){
					sb.append(section.substring(0, 2));
				}else{
					sb.append(section);
				}
			}
		}
		return sb.toString();
	}
	
	public static void testAddress()throws Exception{
		InetAddress inetAddress = InetAddress.getLocalHost();
		System.out.println(inetAddress.getHostAddress());		
		System.out.println("inetAddress.isAnyLocalAddress() = " + inetAddress.isAnyLocalAddress());		
		System.out.println("inetAddress.isLinkLocalAddress() = " + inetAddress.isLinkLocalAddress());		
		System.out.println("inetAddress.isLoopbackAddress() = " + inetAddress.isLoopbackAddress());		
		System.out.println("inetAddress.isMulticastAddress() = " + inetAddress.isMulticastAddress());		
		System.out.println("inetAddress.isMCGlobal() = " + inetAddress.isMCGlobal());		
		System.out.println("inetAddress.isMCLinkLocal() = " + inetAddress.isMCLinkLocal());		
		System.out.println("inetAddress.isMCNodeLocal() = " + inetAddress.isMCNodeLocal());		
		System.out.println("inetAddress.isMCOrgLocal() = " + inetAddress.isMCOrgLocal());		
		System.out.println("inetAddress.isMCSiteLocal() = " + inetAddress.isMCSiteLocal());		
		
		System.out.println(inetAddress.isReachable(1000));
	}
}
