package coffee.weneed.utils;

import java.io.UnsupportedEncodingException;
// TODO: Auto-generated Javadoc

/**
 * The Class Crypto.
 *
 * @author Dalethium
 */
public class Crypto {

	/**
	 * Md5.
	 *
	 * @param md5 the md 5
	 * @return the string
	 */
	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
