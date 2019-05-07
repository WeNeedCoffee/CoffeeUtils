package coffee.weneed.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Provides a suite of utilities for manipulating strings.
 *
 * @author Dalethium, ?
 * @version 1.2
 * @since Revision 0
 */
// TODO credit/source
public class StringUtil {

	public static String arrToBin(byte[] in) {
		String ret = "";
		for (byte b : in) {
			ret += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		}
		return ret;
	}

	public static byte[] binToArr(String s) {
		byte[] ret = new byte[s.length() / 8];
		int i = 0;
		for (String ss : explode(s, 8)) {
			ret[i] = (byte) Integer.parseUnsignedInt(ss, 2);
			i++;
		}
		return ret;
	}

	/***
	 * Convert a string of 1's and 0's to a string of UTF-8 text
	 *
	 * @param s string of 1's and 0's
	 * @return decoded string
	 * @author Daleth
	 */
	public static String binToText(String s) {
		try {
			return new String(binToArr(s), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Combine.
	 *
	 * @param in    the in
	 * @param index the index
	 * @param sep   the sep
	 * @return the string
	 */
	public static String combine(String[] in, int index, char sep) {
		StringBuilder sb = new StringBuilder();
		for (int i = index; i < in.length; i++) {
			sb.append(in[i]).append(i + 1 >= in.length ? "" : sep);
		}
		return sb.toString();
	}

	/**
	 * Counts the number of <code>chr</code>'s in <code>str</code>.
	 *
	 * @param str The string to check for instances of <code>chr</code>.
	 * @param chr The character to check for.
	 * @return The number of times <code>chr</code> occurs in <code>str</code>.
	 */
	public static final int countCharacters(final String str, final char chr) {
		int ret = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == chr) {
				ret++;
			}
		}
		return ret;
	}

	/***
	 * Explode a string into a list of smaller strings of a given size
	 *
	 * @author Daleth
	 * @param in        String to explode
	 * @param chunksize size of chunks
	 * @return exploded string list
	 */
	public static List<String> explode(String in, int chunksize) {
		List<String> ret = new ArrayList<>();
		int t = 0;
		double n = in.length() / chunksize;
		n -= n % 1; // TODO Math.abs?
		for (int i = 0; i <= n; i++) {
			int e = i == n ? in.length() % chunksize : chunksize;
			if (e == 0) {
				break;
			}
			ret.add(substr(in, t, e));
			t += e;
		}
		return ret;
	}

	/**
	 * Gets the char.
	 *
	 * @author Dalethium Gets the first character of the provided string.
	 * @param s The string.
	 * @return The first character of the provided string.
	 * @see Character
	 */
	public static Character getChar(String s) {
		return s.charAt(0);
	}

	public static String getEnd(String in, String delimiter) {
		String[] ss = in.split(delimiter);
		return ss[ss.length - 1];
	}

	/**
	 * Gets a string padded from the left to <code>length</code> by
	 * <code>padchar</code>.
	 *
	 * @param in      The input string to be padded.
	 * @param padchar The character to pad with.
	 * @param length  The length to pad to.
	 * @return The padded string.
	 */
	public static final String getLeftPaddedStr(final String in, final char padchar, final int length) {
		StringBuilder builder = new StringBuilder(length);
		for (int x = in.length(); x < length; x++) {
			builder.append(padchar);
		}
		builder.append(in);
		return builder.toString();
	}

	/**
	 * Gets the optional int arg.
	 *
	 * @param splitted the splitted
	 * @param position the position
	 * @param def      the def
	 * @return the optional int arg
	 */
	public static int getOptionalIntArg(String[] splitted, int position, int def) {
		if (splitted.length > position) {
			try {
				return Integer.parseInt(splitted[position]);
			} catch (NumberFormatException nfe) {
				return def;
			}
		}
		return def;
	}

	/**
	 * Gets a string padded from the right to <code>length</code> by
	 * <code>padchar</code>.
	 *
	 * @param in      The input string to be padded.
	 * @param padchar The character to pad with.
	 * @param length  The length to pad to.
	 * @return The padded string.
	 */
	public static final String getRightPaddedStr(final String in, final char padchar, final int length) {
		StringBuilder builder = new StringBuilder(in);
		for (int x = in.length(); x < length; x++) {
			builder.append(padchar);
		}
		return builder.toString();
	}

	/**
	 * Checks if a given input is a number via the Character interface.
	 *
	 * @param s the s
	 * @return True or false based on whether or not the input is a number.
	 * @see Character.isDigit
	 */
	public static boolean isNumber(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Detects if a string is numeric, via regex.
	 *
	 * @param in The string to check.
	 * @return True if the string is numeric, false otherwise.
	 */
	public static final boolean isNumeric(String in) {
		return in.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * Joins an array of strings starting from string <code>start</code> with a
	 * space.
	 *
	 * @param arr   The array of strings to join.
	 * @param start Starting from which string.
	 * @return The joined strings.
	 */
	public static final String joinStringFrom(final String arr[], final int start) {
		return StringUtil.joinStringFrom(arr, start, " ");
	}

	/**
	 * Joins an array of strings starting from string <code>start</code> with
	 * <code>sep</code> as a seperator.
	 *
	 * @param arr   The array of strings to join.
	 * @param start Starting from which string.
	 * @param sep   the sep
	 * @return The joined strings.
	 */
	public static final String joinStringFrom(final String arr[], final int start, final String sep) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < arr.length; i++) {
			builder.append(arr[i]);
			if (i != arr.length - 1) {
				builder.append(sep);
			}
		}
		return builder.toString();
	}

	/**
	 * Makes an enum name human readable (fixes spaces, capitalization, etc).
	 *
	 * @param enumName The name of the enum to neaten up.
	 * @return The human-readable enum name.
	 */
	public static final String makeEnumHumanReadable(final String enumName) {
		StringBuilder builder = new StringBuilder(enumName.length() + 1);
		for (String word : enumName.split("_")) {
			if (word.length() <= 2) {
				builder.append(word); // assume that it's an abbrevation
			} else {
				builder.append(word.charAt(0));
				builder.append(word.substring(1).toLowerCase());
			}
			builder.append(' ');
		}
		return builder.substring(0, enumName.length());
	}

	/**
	 * Normalizes <code>string</code> by removing all non ascii characters.
	 *
	 * @param string The string to normalize.
	 * @return Normalized ascii string.
	 */
	public static String normalize(String string) {
		return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", "");
	}

	/**
	 * Java equivilent of PHP's substr.
	 *
	 * @author https://stackoverflow.com/questions/28916163/java-equivalent-of-php-substr
	 * @param string the string
	 * @param from   the from
	 * @param to     the to
	 * @return substring
	 */
	public static String substr(String string, int from, int to) {
		if (from < 0 && to < 0) {
			if (Math.abs(from) > Math.abs(to)) {
				String s = string.substring(string.length() - Math.abs(from));
				return s.substring(s.length() - Math.abs(to));
			} else {
				return "";
			}
		} else if (from >= 0 && to < 0) {
			String s = string.substring(from);
			if (Math.abs(to) >= s.length()) {
				return "";
			} else {
				return s.substring(0, s.length() - Math.abs(to));
			}

		} else if (from < 0 && to >= 0) {
			String s = string.substring(string.length() - Math.abs(from));
			if (to >= s.length()) {
				return s;
			}
			return s.substring(0, to);
		} else {
			String s = string.substring(Math.abs(from));
			if (to >= s.length()) {
				return s;
			} else {
				return s.substring(0, Math.abs(to));
			}
		}
	}

	/***
	 * Convert a UTF-8 String to a string of 1's and 0's
	 *
	 * @param in string to encode
	 * @return string of 1's and 0's
	 */
	public static String textToBin(String in) {
		return arrToBin(in.getBytes(StandardCharsets.UTF_8));
	}
}
