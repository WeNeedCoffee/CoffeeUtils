package coffee.weneed.utils.jsnow;

import coffee.weneed.utils.StringUtil;

/***
 * Encode strings into invisible zero width strings Based off
 * https://github.com/beardog108/snow10/
 *
 * @author Daleth, beardog108 encode(s.getBytes(StandardCharsets.UTF_8)) new
 *         String(decode(s), StandardCharsets.UTF_8)
 */
public class JSnow10 {

	public static char zero = '\u200B';

	public static char one = '\u200D';

	public static char z_zero = '\u200B';

	public static char z_one = '\u200D';

	public static char w_zero = ' ';

	public static char w_one = '\t';

	/**
	 * new String(decode(s), StandardCharsets.UTF_8)
	 *
	 * @param s
	 * @return
	 */
	public static byte[] decode(String s) {
		return StringUtil.binToArr(replaceAll(replaceAll(s, String.valueOf(one), "1"), String.valueOf(zero), "0"));
	}

	/**
	 * encode(s.getBytes(StandardCharsets.UTF_8))
	 *
	 * @param in
	 * @return
	 */
	public static String encode(byte[] in) {
		return replaceAll(replaceAll(StringUtil.arrToBin(in), "1", String.valueOf(one)), "0", String.valueOf(zero));
	}

	public static String escapeRegExp(String str) {
		return str.replace("/([.*+?^=!:${}()|\\[\\]\\/\\\\])/g", "\\1");
	}

	public static String replaceAll(String str, String find, String replace) {
		return str.replaceAll(escapeRegExp(find), replace);
	}
}
