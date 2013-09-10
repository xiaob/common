package tmp.jms;

import java.util.List;

import com.yuan.common.shell.CommandExecutor;
import com.yuan.common.shell.Shell;


public class TopicTest {

	public static void main(String[] args)throws Exception {
		final JmsClient jmsClient = new JmsClient("TopicCF");
		
		Shell shell = new Shell("JMS消息客户端");
		shell.addCommand("send", "send message[, count]", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				Integer count = 1;
				if(args.size() > 1){
					count = Integer.parseInt(args.get(1));
				}
				for(int i=0; i<count; i++){
					jmsClient.send("LQ", args.get(0));
				}
			}
		});
		shell.addShutdownHook(new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				jmsClient.close();
			}
		});
		shell.start();
	}

}
