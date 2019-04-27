package coffee.weneed.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	/**
	 * https://kalliphant.com/javamimetype_from_bytearr/
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
	 * https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
	 *
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
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
