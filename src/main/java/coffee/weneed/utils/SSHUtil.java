package coffee.weneed.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtil {

	private String SSH_HOST;
	private String SSH_LOGIN;
	private String SSH_PASSWORD;
	private Session session;

	public SSHUtil(String host, String user, String pw) throws JSchException {
		this.SSH_HOST = host;
		this.SSH_LOGIN = user;
		this.SSH_PASSWORD = pw;
		session = setupSshSession();
		session.connect();
	}

	public List<String> runCommand(String command) throws JSchException {

		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		try {
			channel.setCommand("command");
			channel.setInputStream(null);
			InputStream output = channel.getInputStream();
			channel.connect();

			String result = CharStreams.toString(new InputStreamReader(output));
			return ArrayUtil.toList(result.split("\n"));

		} catch (JSchException | IOException e) {
			closeConnection(channel, session);
			throw new RuntimeException(e);

		}
	}

	private Session setupSshSession() throws JSchException {
		Session session = new JSch().getSession(SSH_LOGIN, SSH_HOST, 22);
		session.setPassword(SSH_PASSWORD);
		session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
		session.setConfig("StrictHostKeyChecking", "no"); // disable check for RSA key
		return session;
	}

	public void closeConnection(ChannelExec channel, Session session) {
		try {
			channel.disconnect();
		} catch (Exception ignored) {
		}
		session.disconnect();
	}
}
