package coffee.weneed.utils.jsnow;

import coffee.weneed.utils.StringUtil;

// TODO: Auto-generated Javadoc
/***
 * Encode strings into invisible zero width strings Based off
 * https://github.com/beardog108/snow10/
 *
 * @author Daleth, beardog108 encode(s.getBytes(StandardCharsets.UTF_8)) new
 *         String(decode(s), StandardCharsets.UTF_8)
 */
public class JSnow10 {

	/** The zero. */
	public static char zero = '\u200B';

	/** The one. */
	public static char one = '\u200D';

	/** The z zero. */
	public static char z_zero = '\u200B';

	/** The z one. */
	public static char z_one = '\u200D';

	/** The w zero. */
	public static char w_zero = ' ';

	/** The w one. */
	public static char w_one = '\t';

	/**
	 * new String(decode(s), StandardCharsets.UTF_8)
	 *
	 * @param s the s
	 * @return the byte[]
	 */
	public static byte[] decode(String s) {
		return StringUtil.binToBytes(replaceAll(replaceAll(s, String.valueOf(one), "1"), String.valueOf(zero), "0"));
	}

	/**
	 * encode(s.getBytes(StandardCharsets.UTF_8))
	 *
	 * @param in the in
	 * @return the string
	 */
	public static String encode(byte[] in) {
		return replaceAll(replaceAll(StringUtil.bytesToBin(in), "1", String.valueOf(one)), "0", String.valueOf(zero));
	}

	/**
	 * Escape reg exp.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String escapeRegExp(String str) {
		return str.replace("/([.*+?^=!:${}()|\\[\\]\\/\\\\])/g", "\\1");
	}

	/**
	 * Replace all.
	 *
	 * @param str the str
	 * @param find the find
	 * @param replace the replace
	 * @return the string
	 */
	public static String replaceAll(String str, String find, String replace) {
		return str.replaceAll(escapeRegExp(find), replace);
	}
}
