package tmp.net.xsocket.udp;

import java.util.List;

import org.xsocket.datagram.MulticastEndpoint;
import org.xsocket.datagram.UserDatagram;

import com.yuan.common.shell.CommandExecutor;
import com.yuan.common.shell.Shell;

public class MulticastUDP2 {

	public static void main(String[] args) throws Exception{
		final MulticastEndpoint endpoint = new MulticastEndpoint("224.0.0.1", 1000, 100, new BaseHandler());
		Shell shell = new Shell("组播客户端");
		shell.addCommand("send", "send message", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				UserDatagram request = new UserDatagram(100);
				request.write(args.get(0));
				endpoint.send(request);
			}
		});
		shell.addShutdownHook(new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				endpoint.close();
			}
		});
		shell.start();
	}

}
