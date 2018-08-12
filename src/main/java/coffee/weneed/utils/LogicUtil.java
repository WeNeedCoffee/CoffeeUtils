package coffee.weneed.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	/**
	 * https://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
	 * 
	 * @param toDownload
	 * @author StackOverflow:ron-reiter
	 * @return byte array
	 */
	public static byte[] downloadUrl(URL toDownload) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;
			InputStream stream = toDownload.openStream();

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
	 *  http://stackoverflow.com/questions/31582524/how-to-check-multiple-objects-for-nullity
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

	/**
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 * 
	 * @author StackOverflow:naomimyselfandi
	 * @param list
	 * @return reversed list
	 */
	public static <T> List<T> reverse(final List<T> list) {
		final List<T> result = new ArrayList<>(list);
		Collections.reverse(result);
		return result;
	}
}
