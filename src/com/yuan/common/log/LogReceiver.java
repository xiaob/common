package com.yuan.common.log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.LogManager;
import org.apache.log4j.net.SocketNode;

public class LogReceiver {

	public static void main(String[] args) {
		try {
			Log4j.load(LogReceiver.class, "src");
			Socket socket = new Socket("sandbox.ppe.huoxing.com", 605);
			new Thread(new SocketNode(socket, LogManager.getLoggerRepository()), "RoomSocketServer").start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
