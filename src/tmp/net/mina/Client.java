package tmp.net.mina;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.yuan.common.shell.CommandExecutor;
import com.yuan.common.shell.Shell;

public class Client {

	public static void main(String[] args) {
		final IoConnector connector = new NioSocketConnector();
		connector.setHandler(new TestHandler2());
		connector.getSessionConfig().setBothIdleTime(3);
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 1000));
		future.awaitUninterruptibly();
		final IoSession session = future.getSession();
		System.out.println("session = " + session);
		
		Shell shell = new Shell("mina");
		shell.addCommand("send", "send message", new CommandExecutor(){
			public void exec(List<String> args) throws Exception {
//				byte[] data = args.get(0).getBytes();
//				IoBuffer buf = IoBuffer.allocate(data.length + 2);
//				buf.put(data);
//				buf.put((byte)'\r');
//				buf.put((byte)'\n');
//				buf.rewind();
//				
//				session.write(buf);
//				
//				IoBuffer buf2 = IoBuffer.allocate(data.length + 2);
//				buf2.put(data);
//				buf2.put((byte)'\r');
//				buf2.put((byte)'\n');
//				buf2.rewind();
//				session.write(buf2);
				
				session.write(makeBuf());
				session.write(makeBuf());
			}
		});
		shell.addShutdownHook(new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				connector.dispose();
			}
		});
		shell.start();
	}
	
	private static IoBuffer makeBuf(){
		IoBuffer buf = IoBuffer.allocate(4);
		buf.put((byte)3);
		buf.put((byte)'a');
		buf.put((byte)'a');
		buf.put((byte)'a');
		
		buf.rewind();
		return buf;
	}

}

class TestHandler2 extends IoHandlerAdapter{
	
	public void messageReceived(IoSession session, Object message)throws Exception{
		IoBuffer buf = (IoBuffer)message;
		System.out.println(session.getId() + "data = " + new String(buf.array(), 0, buf.limit()));
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception{
		System.out.println("==============");
	}
	
}
