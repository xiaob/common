package tmp.ssh;

import java.io.IOException;
import java.security.PublicKey;
import java.util.EnumSet;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.ForwardingFilter;
import org.apache.sshd.common.Session;
import org.apache.sshd.common.SshdSocketAddress;
import org.apache.sshd.common.util.OsUtils;
import org.apache.sshd.common.util.SecurityUtils;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.keyprovider.PEMGeneratorHostKeyProvider;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ProcessShellFactory;

public class MySshServer {

	public static void main(String[] args) throws IOException {
		SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPort(22);
		if (SecurityUtils.isBouncyCastleRegistered())
	      sshd.setKeyPairProvider(new PEMGeneratorHostKeyProvider("key.pem"));
	    else {
	      sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("key.ser"));
	    }
		if (OsUtils.isUNIX()) {
			sshd.setShellFactory(new ProcessShellFactory(new String[] { "/bin/sh", "-i", "-l" }, EnumSet
					.of(ProcessShellFactory.TtyOptions.ONlCr)));
		} else {
			sshd.setShellFactory(new ProcessShellFactory(new String[] { "cmd.exe " }, EnumSet.of(
					ProcessShellFactory.TtyOptions.Echo, ProcessShellFactory.TtyOptions.ICrNl,
					ProcessShellFactory.TtyOptions.ONlCr)));
		}
		sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
			@Override
			public boolean authenticate(String username, String password, ServerSession session) {
				return true;
			}
		});
		sshd.setPublickeyAuthenticator(new PublickeyAuthenticator() {
			@Override
			public boolean authenticate(String username, PublicKey key, ServerSession session) {
				return true;
			}
		});
		sshd.setTcpipForwardingFilter(new ForwardingFilter() {
			@Override
			public boolean canConnect(SshdSocketAddress address, Session session) {
				return true;
			}

			@Override
			public boolean canForwardAgent(Session session) {
				return true;
			}

			@Override
			public boolean canForwardX11(Session session) {
				return true;
			}

			@Override
			public boolean canListen(SshdSocketAddress address, Session session) {
				return true;
			}
		});
		sshd.start();
	}

}
