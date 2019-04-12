package coffee.weneed.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	 * @throws IOException 
	 * @see coffee.weneed.utils.LogicUtil#downloadUrl(URL)
	 */
	public static byte[] downloadUrl(String toDownload) throws IOException {
		URL url = new URL(toDownload);
		return LogicUtil.downloadUrl(url);
	}
	/**https://kalliphant.com/javamimetype_from_bytearr/
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String getMimeType(byte data[]) throws Exception {		
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		return mimeType;
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
