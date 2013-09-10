package tmp.net.mina;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {

	public static void main(String[] args) throws Exception{
		IoAcceptor acceptor = new NioSocketAcceptor();
		
//		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
//		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		acceptor.setHandler(new TestHandler());
		
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 180);
		
		acceptor.bind(new InetSocketAddress(1000));
	}

}

class TestHandler extends IoHandlerAdapter{
	
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception{
		cause.printStackTrace();
	}
	
	public void sessionClosed(IoSession session) throws Exception{
		session.close(true);
	}
	
	public void messageReceived(IoSession session, Object message)throws Exception{
//		System.out.println(Thread.currentThread().getName() + ", message = " + message);
		IoBuffer readBuf = (IoBuffer)message;
		if(readLastByte(readBuf) != 10){
			setSessionBuffer(session, readBuf);
		}else{
			if(session.getAttribute("buf") != null){
				print(session, setSessionBuffer(session, readBuf));
			}else{
				print(session, readBuf);
			}
			session.removeAttribute("buf");
			
			IoBuffer writeBuf = IoBuffer.wrap(new Date().toLocaleString().getBytes());
			session.write(writeBuf);
		}
	}
	
	private byte readLastByte(IoBuffer buf){
		return buf.get(buf.limit() - 1);
	}
	
	private void print(IoSession session, IoBuffer buf){
		System.out.println("id = " + session.getId() + ", buf.limit() = " + buf.limit() + ", buf.position() = " + buf.position());
		System.out.println(new String(buf.array(), 0, buf.position() - 2 ));
	}
	
	private IoBuffer setSessionBuffer(IoSession session, IoBuffer buf){
		if(session.getAttribute("buf") == null){
			IoBuffer sessionbuf = IoBuffer.allocate(2048).setAutoExpand(true);
			sessionbuf.put(buf);
			session.setAttribute("buf", sessionbuf);
			return sessionbuf;
		}
		
		IoBuffer sessionbuf = (IoBuffer)session.getAttribute("buf");
		sessionbuf.put(buf);
		
		return sessionbuf;
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception{
		session.close(true);
	}
	
}
