package tmp.rmi;

import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

	public static void main(String args[])throws Exception{
		//System.setProperty("java.rmi.server.hostname" , "192.168.5.154" ); //服务端多IP则必须设置
		RMISocketFactory.setSocketFactory(new MyRMISocket(1000)); //通信端口, 不指定就随机
		Registry registry = LocateRegistry.createRegistry(1099); //注册端口
		
		Hello helloImpl = new HelloImpl();
		System.out.println(helloImpl);
		
		UnicastRemoteObject.exportObject(helloImpl, 0);
		Naming.rebind("rmi://localhost:1099/RHello", helloImpl);
		printRegistry(registry);
		
		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
//		UnicastRemoteObject.unexportObject(helloImpl, true);
	}
	
	private static void printRegistry(Registry registry){
		System.out.println("==========================");
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
		System.out.println("==========================");
	}
	
}
