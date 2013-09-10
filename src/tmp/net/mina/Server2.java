package tmp.net.mina;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server2 {

	public static void main(String[] args) throws Exception{
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BinaryCodecFactory()));
		acceptor.getFilterChain().addLast("exec", new ExecutorFilter()); //IO处理线程，与业务处理线程分开
		acceptor.setHandler(new TestHandler3());
		
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		acceptor.bind(new InetSocketAddress(1000));

	}

}


class TestHandler3 extends IoHandlerAdapter{
	
	public void messageReceived(IoSession session, Object message)throws Exception{
		String s = (String)message;
		System.out.println("s = " + s);
		
		session.write(new Date().toLocaleString());
	}
	
}

class TelnetCodecFactory implements ProtocolCodecFactory{

	@Override
	public ProtocolEncoder getEncoder(IoSession session)
			throws Exception {
		
		return new TelnetEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session)
			throws Exception {
		
		return new TelnetDecoder();
	}
	
}

class TelnetDecoder extends TailedProtocolDecoder{

	@Override
	protected ByteBuffer getDelimiter() {
		ByteBuffer buf = ByteBuffer.allocate(2);
		buf.put((byte)'\r');
		buf.put((byte)'\n');
		return buf;
	}

	@Override
	protected Object doDecode(IoSession session, IoBuffer in) {
		
		return new String(in.array(), 0, in.limit());
	}
	
}

class TelnetEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message instanceof String){
			String s = (String)message;
			System.out.println("s = " + s);
			IoBuffer buf = IoBuffer.wrap(s.getBytes());
			out.write(buf);
		}
	}
	
}

class BinaryCodecFactory implements ProtocolCodecFactory{

	@Override
	public ProtocolEncoder getEncoder(IoSession session)
			throws Exception {
		
		return new BinaryEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session)
			throws Exception {
		
		return new BinaryDecoder();
	}
	
}

class BinaryDecoder extends PrefixLengthProtocolDecoder{

	@Override
	protected int getPrefixLength() {
		return 1;
	}

	@Override
	protected int getMinLength() {
		return 0;
	}

	@Override
	protected Object doDecode(IoSession session, IoBuffer in) {
		
		return new String(getRemaining(in));
	}
	
	private static byte[]getRemaining(IoBuffer buf){
		byte[] data = new byte[buf.remaining()];
		for(int i=0; buf.hasRemaining(); i++){
			data[i] = buf.get();
		}
		
		buf.rewind();
		return data;
	}
	
}

class BinaryEncoder extends ProtocolEncoderAdapter{
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message instanceof String){
			String s = (String)message;
			System.out.println("s = " + s);
			IoBuffer buf = IoBuffer.wrap(s.getBytes());
			out.write(buf);
		}
	}
	
}