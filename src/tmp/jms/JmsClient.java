package tmp.jms;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsClient {

	private static final Logger log = LoggerFactory.getLogger(JmsClient.class);
	
	private InitialContext context;
	private Connection connection;
	private Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
	
	public JmsClient(String connFactoryClass){
		try {
			context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)context.lookup(connFactoryClass);
			connection = factory.createConnection();
			connection.start();
		} catch (NamingException e) {
			log.warn(e.getMessage(), e);
		} catch (JMSException e) {
			log.warn(e.getMessage(), e);
		}
	}
	
	public void close(){
		closeSession();
		if(connection != null){
			try {
				connection.stop();
				connection.close();
			} catch (JMSException e) {
				log.warn(e.getMessage(), e);
			}
		}
		if(context != null){
			try {
				context.close();
			} catch (NamingException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	private Session getSession(String name) throws JMSException{
		Session session = null;
		if(sessionMap.containsKey(name)){
			session = sessionMap.get(name);
		}else{
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sessionMap.put(name, session);
		}
		return session;
	}
	private void closeSession(){
		Collection<Session> sessionSet = sessionMap.values();
		for(Session session : sessionSet){
			try {
				session.close();
			} catch (JMSException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	public void send(String name, String message){
		try {
			Session session = getSession(name);
			Destination destination = (Destination)context.lookup(name);
			MessageProducer producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage(message);
			producer.send(textMessage);
//			producer.send(textMessage, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, 1800000);
		} catch (NamingException e) {
			log.warn(e.getMessage(), e);
		} catch (JMSException e) {
			log.warn(e.getMessage(), e);
		}
	}
	
	public void registReceiveListeter(String name, MessageListener messageListener){
		try {
			Session session = getSession(name);
			Destination destination = (Destination)context.lookup(name);
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(messageListener);
		} catch (JMSException e) {
			log.warn(e.getMessage(), e);
		} catch (NamingException e) {
			log.warn(e.getMessage(), e);
		}  
	}
	
	public Message receive(String name, long timeout){
		try {
			Session session = getSession(name);
			Destination destination = (Destination)context.lookup(name);
			MessageConsumer consumer = session.createConsumer(destination);
			return consumer.receive(timeout);
		} catch (NamingException e) {
			log.warn(e.getMessage(), e);
		} catch (JMSException e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}
}
