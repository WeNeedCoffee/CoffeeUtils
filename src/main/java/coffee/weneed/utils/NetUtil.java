package coffee.weneed.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class NetUtil {

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
}
