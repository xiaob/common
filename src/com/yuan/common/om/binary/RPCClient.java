package com.yuan.common.om.binary;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class RPCClient {

	public static void main(String[] args) {
		final Client client = new Client();
		client.start();
		Network.register(client);
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if(object instanceof Student){
					Student stu = (Student)object;
					System.out.println(stu.getName());
					System.out.println(stu.getAge());
				}
				if(object instanceof BufferedImage){
					BufferedImage bi = (BufferedImage)object;
					System.out.println(bi);
				}
				if(object instanceof byte[]){
					ByteArrayInputStream bais = new ByteArrayInputStream((byte[])object);
					try {
						BufferedImage bi = ImageIO.read(bais);
						System.out.println(bi);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
//				client.close();
			}

			public void disconnected (Connection connection) {
				
			}
		});
		try {
			client.connect(10 * 1000, "127.0.0.1", 1000);
			client.setKeepAliveTCP(10 * 1000);
//			InetAddress serverHostAddress = client.discoverHost(1000, 5000);
//			System.out.println("serverHostAddress : " + serverHostAddress.getHostAddress());
//			client.sendTCP(new Student("hellworld", 100));
			
			client.sendTCP(new Student("hellworld2", 200));
			
			
//			client.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static <T> T getRemoteService(String ip, int port, Class<T> clazz){
		Client client = new Client();
		client.start();
		Network.register(client);
		try {
			client.connect(5000, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ObjectSpace.registerClasses(client.getKryo());
		return ObjectSpace.getRemoteObject(client, 1, clazz);
	}

}
