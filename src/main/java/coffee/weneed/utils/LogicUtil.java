package coffee.weneed.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	/**
	 * https://github.com/haha01haha01/MapleLib/blob/master/WzLib/Util/WzTool.cs
	 *  Creates an array with b appended to the end of a.
	 *
	 * @author C#: Snow, haha01haha01
	 * @author Java: Dalethium
	 * @param a first array
	 * @param b second array
	 * @return An array with b appended to the end of a.
	 */
	public static byte[] combine(byte[] a, byte[] b) {
		byte[] result = new byte[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	/**
	 * Download url.
	 *
	 * @author Dalethium
	 * @param toDownload the to download
	 * @return byte array
	 * @see coffee.weneed.utils.LogicUtil#downloadUrl(URL)
	 */
	public static byte[] downloadUrl(String toDownload) {
		URL url = null;
		try {
			url = new URL(toDownload);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @param <T> the generic type
	 * @param list the list
	 * @return reversed list
	 */
	public static <T> List<T> reverse(final List<T> list) {
		final List<T> result = new ArrayList<>(list);
		Collections.reverse(result);
		return result;
	}

	/**
	 * Splice
	 *
	 * @author Dalethium
	 * Removes the given item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the given item.
	 */
	public static <T> T[] splice(T[] arr, int pos) {
		List<T> list = new ArrayList<>();
		Collections.addAll(list, arr);
		list.remove(pos); // IndexOutOfBoundsException
		return list.toArray(LogicUtil.toArray(list));
	}

	/**
	 * Splice first.
	 *
	 * @author Dalethium
	 * Removes the first item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the first item.
	 */
	public static <T> T[] spliceFirst(T[] arr) {
		return LogicUtil.splice(arr, 0);
	}

	/**
	 * https://stackoverflow.com/questions/6522284/convert-a-generic-list-to-an-array
	 * 
	 * @author StackOverflow:atreys
	 * @param list
	 * @return array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list) {
		T[] toR = (T[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
		for (int i = 0; i < list.size(); i++) {
			toR[i] = list.get(i);
		}
		return toR;
	}
}
