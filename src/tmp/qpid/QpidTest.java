package tmp.qpid;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

public class QpidTest {

	public static void main(String[] args) throws Exception {
		Context context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("qpidConnectionfactory");
		connectionFactory.createConnection();
	}

}
