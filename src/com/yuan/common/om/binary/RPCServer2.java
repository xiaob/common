package com.yuan.common.om.binary;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

public class RPCServer2 {

	public void start(){
		Server server = new Server();
		Network.register(server);
		
		try {
			server.bind(1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
	}
	
	public static void main(String[] args) {
		new RPCServer2().start();

	}

}
