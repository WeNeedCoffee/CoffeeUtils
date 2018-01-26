package coffee.weneed.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a suite of utilities for manipulating strings.
 *
 * @author Dalethium, ?
 * @since Revision 0
 * @version 1.1
 *
 */
//TODO source and clean up
public class StringUtil {

	/**
	 * Gets a string padded from the left to
	 * <code>length</code> by
	 * <code>padchar</code>.
	 *
	 * @param in The input string to be padded.
	 * @param padchar The character to pad with.
	 * @param length The length to pad to.
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
	 * Gets a string padded from the right to
	 * <code>length</code> by
	 * <code>padchar</code>.
	 *
	 * @param in The input string to be padded.
	 * @param padchar The character to pad with.
	 * @param length The length to pad to.
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
	 * Detects if a string is numeric, via regex.
	 *
	 * @param in The string to check.
	 * @return True if the string is numeric, false otherwise.
	 */
	public static final boolean isNumeric(String in) {
		return in.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * Joins an array of strings starting from string
	 * <code>start</code> with a space.
	 *
	 * @param arr The array of strings to join.
	 * @param start Starting from which string.
	 * @return The joined strings.
	 */
	public static final String joinStringFrom(final String arr[], final int start) {
		return joinStringFrom(arr, start, " ");
	}

	/**
	 * Joins an array of strings starting from string
	 * <code>start</code> with
	 * <code>sep</code> as a seperator.
	 *
	 * @param arr The array of strings to join.
	 * @param start Starting from which string.
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
	 * Makes an enum name human readable (fixes spaces, capitalization, etc)
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
	 * Counts the number of
	 * <code>chr</code>'s in
	 * <code>str</code>.
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

	public static final String getReadableMillis(long startMillis, long endMillis) {
		StringBuilder sb = new StringBuilder();
		double elapsedSeconds = (endMillis - startMillis) / 1000.0;
		int elapsedSecs = ((int) elapsedSeconds) % 60;
		int elapsedMinutes = (int) (elapsedSeconds / 60.0);
		int elapsedMins = elapsedMinutes % 60;
		int elapsedHrs = elapsedMinutes / 60;
		int elapsedHours = elapsedHrs % 24;
		int elapsedDays = elapsedHrs / 24;
		if (elapsedDays > 0) {
			boolean mins = elapsedHours > 0;
			sb.append(elapsedDays);
			sb.append(" day" + (elapsedDays > 1 ? "s" : "") + (mins ? ", " : "."));
			if (mins) {
				boolean secs = elapsedMins > 0;
				if (!secs) {
					sb.append("and ");
				}
				sb.append(elapsedHours);
				sb.append(" hour" + (elapsedHours > 1 ? "s" : "") + (secs ? ", " : "."));
				if (secs) {
					boolean millis = elapsedSecs > 0;
					if (!millis) {
						sb.append("and ");
					}
					sb.append(elapsedMins);
					sb.append(" minute" + (elapsedMins > 1 ? "s" : "") + (millis ? ", " : "."));
					if (millis) {
						sb.append("and ");
						sb.append(elapsedSecs);
						sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
					}
				}
			}
		} else if (elapsedHours > 0) {
			boolean mins = elapsedMins > 0;
			sb.append(elapsedHours);
			sb.append(" hour" + (elapsedHours > 1 ? "s" : "") + (mins ? ", " : "."));
			if (mins) {
				boolean secs = elapsedSecs > 0;
				if (!secs) {
					sb.append("and ");
				}
				sb.append(elapsedMins);
				sb.append(" minute" + (elapsedMins > 1 ? "s" : "") + (secs ? ", " : "."));
				if (secs) {
					sb.append("and ");
					sb.append(elapsedSecs);
					sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
				}
			}
		} else if (elapsedMinutes > 0) {
			boolean secs = elapsedSecs > 0;
			sb.append(elapsedMinutes);
			sb.append(" minute" + (elapsedMinutes > 1 ? "s" : "") + (secs ? " " : "."));
			if (secs) {
				sb.append("and ");
				sb.append(elapsedSecs);
				sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
			}
		} else if (elapsedSeconds > 0) {
			sb.append((int) elapsedSeconds);
			sb.append(" second" + (elapsedSeconds > 1 ? "s" : "") + ".");
		} else {
			sb.append("None.");
		}
		return sb.toString();
	}

	public static final int getDaysAmount(long startMillis, long endMillis) {
		double elapsedSeconds = (endMillis - startMillis) / 1000.0;
		int elapsedMinutes = (int) (elapsedSeconds / 60.0);
		int elapsedHrs = elapsedMinutes / 60;
		int elapsedDays = elapsedHrs / 24;
		return elapsedDays;
	}

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

	public static boolean isNumber(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	public static String combine(String[] in, int index, char sep) {
		StringBuilder sb = new StringBuilder();
		for (int i = index; i < in.length; i++) {
			sb.append(in[i]).append(i + 1 >= in.length ? "" : sep);
		}
		return sb.toString();
	}
	
    public static String[] spliceFirst(String[] arr) {
    	List<String> list = new ArrayList<>();
		Collections.addAll(list, arr);
		list.remove(0);
		return list.toArray(new String[0]);
    }
    
    /**
     * Java equivilent of PHP's substr
     * @author https://stackoverflow.com/questions/28916163/java-equivalent-of-php-substr
     * @param string
     * @param from
     * @param to
     * @return substring
     */
    public static String substr(String string,int from,int to) {
	    if (from < 0 && to < 0) {
	        if (Math.abs(from) > Math.abs(to)) {
	            String s = string.substring(string.length()-Math.abs(from));
	            return s.substring(s.length()-Math.abs(to));
	        } else {
	            return "";
	        }
	   } else if (from >= 0 && to < 0) {
	        String s = string.substring(from);
	       if (Math.abs(to) >= s.length()) {
	           return "";
	       } else {
	           return s.substring(0,s.length()-Math.abs(to));
	       }


	    } else if (from < 0 && to >= 0) {
	        String s = string.substring(string.length()-Math.abs(from));
	        if(to >= s.length()) {
	        	return s;   
	        }
	        return s.substring(0,to);
	    } else {
	        String s = string.substring(Math.abs(from));
	        if(to >= s.length()) {
	            return s;
	        } else {
	            return s.substring(0, Math.abs(to));
	        }
	    }
	}
    
	public static Character getChar(String s) {
		return s.charAt(0);
	}
	
	public static String normalize(String s) { 
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", "");
	}
}
