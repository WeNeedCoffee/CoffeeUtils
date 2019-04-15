package coffee.weneed.utils.jsnow;

import coffee.weneed.utils.StringUtil;

/***
 * Encode strings into invisible zero width strings Based off
 * https://github.com/beardog108/snow10/
 *
 * @author Daleth
 *
 */
public class JSnow10 {

	public static char zero = '\u200B';

	public static char one = '\u200D';

	public static char z_zero = '\u200B';

	public static char z_one = '\u200D';

	public static char w_zero = ' ';

	public static char w_one = '\t';

	public static String decode(String s) {
		return StringUtil.binToText(replaceAll(replaceAll(s, String.valueOf(one), "1"), String.valueOf(zero), "0"));
	}

	public static String encode(String s) {
		return replaceAll(replaceAll(StringUtil.textToBin(s), "1", String.valueOf(one)), "0", String.valueOf(zero));
	}

	public static String escapeRegExp(String str) {
		return str.replace("/([.*+?^=!:${}()|\\[\\]\\/\\\\])/g", "\\1");
	}

	public static String replaceAll(String str, String find, String replace) {
		return str.replaceAll(escapeRegExp(find), replace);
	}
}
