package tmp.net.xsocket;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSL {
	
	private KeyManager[] keyManagers;
	private TrustManager[] trustManagers;
	
	private KeyStore loadKeyStore(InputStream is, String password)throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(is, password.toCharArray());
		return keyStore;
	}
	
	public void setSelfCertificate(InputStream is, String password)throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore keyStore = loadKeyStore(is, password);
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(keyStore, password.toCharArray());
		
		keyManagers = keyManagerFactory.getKeyManagers();
	}
	
	public void setTrustCertificate(InputStream is, String password)throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		KeyStore keyStore = loadKeyStore(is, password);
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		trustManagerFactory.init(keyStore);
		
		trustManagers = trustManagerFactory.getTrustManagers();
	}
	
	public SSLContext getSSLContext()throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keyManagers, trustManagers, null);
		
		return sslContext;
	}
	
	public static void main(String args[]){
		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
	}

}
