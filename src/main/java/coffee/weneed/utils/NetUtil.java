package coffee.weneed.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.json.JSONException;
import org.json.JSONObject;

public class NetUtil {

	public static double checkProxy(String ip, String contact) throws IOException {
		JSONObject json = null;
		try {
			json = new JSONObject(new String(NetUtil.downloadUrl("http://check.getipintel.net/check.php?ip=" + ip + "&contact=" + contact + "&flags=f&format=json")));
		} catch (JSONException e) {
			return -1;
		}
		if (!json.optString("status", "failure").equalsIgnoreCase("success")) {
			return -1;
		}
		return Double.valueOf(json.optString("result", "1"));
	}

	/**
	 * Download url.
	 *
	 * @author Dalethium
	 * @param toDownload the to download
	 * @return byte array
	 * @throws IOException
	 * @see coffee.weneed.utils.NetUtil#downloadUrl(URL)
	 */
	public static byte[] downloadUrl(String toDownload) throws IOException {
		URL url = new URL(toDownload);
		return NetUtil.downloadUrl(url);
	}

	/**
	 * https://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
	 *
	 * @author StackOverflow:ron-reiter
	 * @param toDownload the to download
	 * @return byte array
	 * @throws IOException
	 */
	public static byte[] downloadUrl(URL toDownload) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] chunk = new byte[4096];
		int bytesRead;

		URLConnection con = toDownload.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		con.setUseCaches(false);
		con.setDefaultUseCaches(false);
		con.connect();
		InputStream stream = con.getInputStream();
		while ((bytesRead = stream.read(chunk)) > 0) {
			outputStream.write(chunk, 0, bytesRead);
		}
		stream.close();
		return outputStream.toByteArray();
	}

	/***
	 * https://www.rgagnon.com/javadetails/java-0452.html
	 *
	 * @param hostName
	 * @return
	 * @throws NamingException
	 */
	public static ArrayList<String> getMX(String hostName) throws NamingException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(hostName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");
		if (attr == null || attr.size() == 0) {
			attrs = ictx.getAttributes(hostName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null) {
				throw new NamingException("No match for name '" + hostName + "'");
			}
		}
		ArrayList<String> res = new ArrayList<>();
		NamingEnumeration<?> en = attr.getAll();

		while (en.hasMore()) {
			String mailhost;
			String x = (String) en.next();
			String f[] = x.split(" ");
			if (f.length == 1) {
				mailhost = f[0];
			} else if (f[1].endsWith(".")) {
				mailhost = f[1].substring(0, f[1].length() - 1);
			} else {
				mailhost = f[1];
			}
			res.add(mailhost);
		}
		return res;
	}

	/***
	 * https://www.rgagnon.com/javadetails/java-0452.html
	 *
	 * @param hostName
	 * @return
	 * @throws NamingException
	 */
	public static boolean hasMX(String hostName) throws NamingException {
		return getMX(hostName) != null && getMX(hostName).size() > 0;
	}

	private static int hear(BufferedReader in) throws IOException {
		String line = null;
		int res = 0;
		while ((line = in.readLine()) != null) {
			String pfx = line.substring(0, 3);
			try {
				res = Integer.parseInt(pfx);
			} catch (Exception ex) {
				res = -1;
			}
			if (line.charAt(3) != '-') {
				break;
			}
		}
		return res;
	}

	/***
	 * https://www.rgagnon.com/javadetails/java-0452.html
	 *
	 * @param address
	 * @return
	 */
	public static boolean isEmailValid(String address) {
		int pos = address.indexOf('@');
		if (pos == -1) {
			return false;
		}
		String domain = address.substring(++pos);
		ArrayList<String> mxList = null;
		try {
			mxList = getMX(domain);
		} catch (NamingException ex) {
			return false;
		}
		if (mxList.size() == 0) {
			return false;
		}
		for (int mx = 0; mx < mxList.size(); mx++) {
			boolean valid = false;
			try {
				int res;
				Socket skt = new Socket(mxList.get(mx), 25);
				BufferedReader rdr = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				BufferedWriter wtr = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
				res = hear(rdr);
				if (res != 220) {
					rdr.close();
					wtr.close();
					skt.close();
					throw new Exception("Invalid header");
				}
				say(wtr, "EHLO lilith.cloud");

				res = hear(rdr);
				if (res != 250) {
					rdr.close();
					wtr.close();
					skt.close();
					throw new Exception("Not ESMTP");
				}
				say(wtr, "MAIL FROM: <spam@lilith.cloud>");
				res = hear(rdr);
				if (res != 250) {
					rdr.close();
					wtr.close();
					skt.close();
					throw new Exception("Sender rejected");
				}
				say(wtr, "RCPT TO: <" + address + ">");
				res = hear(rdr);
				say(wtr, "RSET");
				hear(rdr);
				say(wtr, "QUIT");
				hear(rdr);
				if (res != 250) {
					rdr.close();
					wtr.close();
					skt.close();
					throw new Exception("Address is not valid!");
				}
				valid = true;
				rdr.close();
				wtr.close();
				skt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (valid) {
					return true;
				}
			}
		}
		return false;
	}

	/***
	 * https://stackoverflow.com/a/34228756
	 *
	 * @author https://stackoverflow.com/users/3368518/sourabh-bhat
	 * @param addr
	 * @param openPort
	 * @param timeOutMillis
	 * @return
	 */
	public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
		try {
			try (Socket soc = new Socket()) {
				soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
			}
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	/**
	 * https://www.dev2qa.com/check-valid-www-domain-and-hostname-java-examples/
	 * addStr : A domain string. return : true means addStr is a valid domain name,
	 * false means addStr is not a valide domain name.
	 */
	public static boolean isValidDomain(String addStr) {
		boolean ret = true;
		if ("".equals(addStr) || addStr == null) {
			ret = false;
		} else if (addStr.startsWith("-") || addStr.endsWith("-")) {
			ret = false;
		} else if (addStr.indexOf(".") == -1) {
			ret = false;
		} else {
			String domainEle[] = addStr.split("\\.");
			int size = domainEle.length;
			for (int i = 0; i < size; i++) {
				String domainEleStr = domainEle[i];
				if ("".equals(domainEleStr.trim())) {
					return false;
				}
			}
			char[] domainChar = addStr.toCharArray();
			size = domainChar.length;
			for (int i = 0; i < size; i++) {
				char eleChar = domainChar[i];
				String charStr = String.valueOf(eleChar);
				if (!".".equals(charStr) && !"-".equals(charStr) && !charStr.matches("[a-zA-Z]") && !charStr.matches("[0-9]")) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}

	private static void say(BufferedWriter wr, String text) throws IOException {
		wr.write(text + "\r\n");
		wr.flush();
		return;
	}
}
