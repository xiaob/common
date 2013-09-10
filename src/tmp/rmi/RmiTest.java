package tmp.rmi;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiTest {

	public static void main(String[] args) throws RemoteException {
		findRmiService("127.0.0.1", 9100);
		findRmiService("127.0.0.1", 9200);
	}
	
	public static void test1() throws RemoteException{
		Registry registry = LocateRegistry.getRegistry();
		
		printRegistry(registry);
	}
	
	private static void printRegistry(Registry registry){
		try {
			String[] list = registry.list();
			for(String r : list){
				System.out.println(r);
			}
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2() throws RemoteException{
		Registry registry = LocateRegistry.createRegistry(1099);
		printRegistry(registry);
	}
	
	public static void findRmiService(String host, int port){
		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
			String[] list = registry.list();
			for(String r : list){
				System.out.println(r);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
