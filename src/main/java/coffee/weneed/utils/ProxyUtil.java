package coffee.weneed.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import sockslib.client.Socks5;
import sockslib.client.SocksProxy;
import sockslib.client.SocksSocket;
import sockslib.common.SocksException;
import sockslib.common.UsernamePasswordCredentials;

public class ProxyUtil {

	class Socks5Connection {
		String user;
		String pass;
		InetSocketAddress address;

		public Socks5Connection(InetSocketAddress address, String username, String password) {
			user = username;
			pass = password;
			this.address = address;
		}

		public InetSocketAddress getAddress() {
			return address;
		}

		public String getPass() {
			return pass;
		}

		public String getUser() {
			return user;
		}
	}

	public static Socket createSocks5ChainSocket(List<Socks5Connection> sockets) throws SocksException, IOException {
		List<SocksProxy> proxies = new ArrayList<>();
		for (Socks5Connection s : sockets) {
			SocksProxy proxy = new Socks5(s.getAddress());
			proxy.setCredentials(new UsernamePasswordCredentials(s.getUser(), s.getPass()));
			proxies.add(proxy);
		}
		SocksProxy s = null;
		for (SocksProxy proxy : ArrayUtil.reverse(proxies)) {
			if (s == null) {
				s = proxy;
				continue;
			}
			s.setChainProxy(proxy);
		}
		return new SocksSocket(s);
	}

	public static Socket createSocks5Socket(InetSocketAddress address, String username, String password) throws SocksException, IOException {
		SocksProxy proxy = new Socks5(address);
		proxy.setCredentials(new UsernamePasswordCredentials(username, password));
		return new SocksSocket(proxy);
	}

	public static Socket createSocks5Socket(Socks5Connection socket) throws SocksException, IOException {
		SocksProxy proxy = new Socks5(socket.getAddress());
		proxy.setCredentials(new UsernamePasswordCredentials(socket.getUser(), socket.getPass()));
		return new SocksSocket(proxy);
	}
}
