package tmp.jms;

import java.util.concurrent.TimeUnit;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TopicTest2 {

	public static void main(String[] args)throws Exception {
		JmsClient jmsClient = new JmsClient("TopicCF");
		jmsClient.registReceiveListeter("topic1", new MessageHandle());
		jmsClient.registReceiveListeter("LQ", new MessageHandle());

	}

}

class MessageHandle implements MessageListener, ExceptionListener{

	private Long count = 0L;
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			TextMessage textMessage = (TextMessage)message;
			try {
				count++;
				System.out.println(count + ":" + textMessage.getText());
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void onException(JMSException jmsException) {
		jmsException.printStackTrace();
	}
	
}
