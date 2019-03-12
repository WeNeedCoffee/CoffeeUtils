package coffee.weneed.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	/**
	 * Download url.
	 *
	 * @author Dalethium
	 * @param toDownload the to download
	 * @return byte array
	 * @throws MalformedURLException
	 * @see coffee.weneed.utils.LogicUtil#downloadUrl(URL)
	 */
	public static byte[] downloadUrl(String toDownload) throws MalformedURLException {
		URL url = new URL(toDownload);
		return LogicUtil.downloadUrl(url);
	}

	/**
	 * https://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
	 *
	 * @author StackOverflow:ron-reiter
	 * @param toDownload the to download
	 * @return byte array
	 */
	public static byte[] downloadUrl(URL toDownload) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;

			URLConnection con = toDownload.openConnection();
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			InputStream stream = con.getInputStream();
			while ((bytesRead = stream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return outputStream.toByteArray();
	}

	/**
	 * http://stackoverflow.com/questions/31582524/how-to-check-multiple-objects-for-nullity
	 *
	 * @author StackOverflow:kamwo
	 * @param objects the objects
	 * @return true if any object in the arguments is null
	 */
	public static Boolean isAnyObjectNull(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
