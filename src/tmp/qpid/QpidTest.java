package tmp.qpid;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QpidTest {

	public static void main(String[] args) throws NamingException {
		Context context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("qpidConnectionfactory");
		
	}

}
