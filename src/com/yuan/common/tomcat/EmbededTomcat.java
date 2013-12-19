package com.yuan.common.tomcat;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbededTomcat {

	private static final Logger LOG = LoggerFactory.getLogger(EmbededTomcat.class);
	private static final String SHUTDOWN = "EmbededTomcat_SHUTDOWN";
	
	private static final String CONTEXT_PATH = "";
	private static String webAppPath;
	private String catalinaHome;
	private Tomcat tomcat = new Tomcat();
	private int port;
	private int shutdownPort;

	public EmbededTomcat(int port) {
		this.port = port;
		this.shutdownPort = port + 5;
		
		try {
			File f = new File("").getCanonicalFile();
			catalinaHome = f.getAbsolutePath();
			webAppPath = new File(f, "webapp").getAbsolutePath();
		} catch (IOException e) {
			LOG.warn(e.getMessage(), e);
		}
	}

	public void start() throws Exception {
		tomcat.setPort(port);
		tomcat.setBaseDir(catalinaHome);
		
		StandardServer server = (StandardServer) tomcat.getServer();
		server.addLifecycleListener(new AprLifecycleListener());
		server.setPort(shutdownPort);
		server.setShutdown(SHUTDOWN);
		
		tomcat.addWebapp(CONTEXT_PATH, webAppPath);
		
		tomcat.start();
		tomcat.getServer().await();
		
		LOG.info("Tomcat started.");
	}

	public void stop() throws Exception {
		tomcat.stop();
		LOG.info("Tomcat stoped");
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}
	
	public void shutdown() throws Exception {
        Socket socket = new Socket("localhost", shutdownPort);
        OutputStream stream = socket.getOutputStream();
        String shutdown = SHUTDOWN;
        for(int i = 0;i < shutdown.length();i++)
            stream.write(shutdown.charAt(i));
        stream.flush();
        stream.close();
        socket.close();
    }

	public static void main(String[] args) throws Exception {
		EmbededTomcat embededTomcat = new EmbededTomcat(2000);
		embededTomcat.start();
	}

}
