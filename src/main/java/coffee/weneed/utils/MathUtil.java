package coffee.weneed.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class MathUtil.
 */
public class MathUtil {

	public static int countDigits(double d) {
		return BigDecimal.valueOf(d).toPlainString().replace(".", "").length();
	}

	/**
	 * Returns a number representing <code>(i - (i % len)) / len</code>
	 * @param len
	 * @param i
	 * @return
	 */
	public static long getIndex(long len, long i) {
		return (i - i % len) / len;
	}

	public static long removeFirstDigit(long i) { return i % (long) Math.pow(10, (long) Math.log10(i));}
	public static long removeXthDigit(long e, int x) {
		long l = e;
		for (int i = 0; i < x; x++) {
			l = removeFirstDigit(l);
		}
		return l;
	}
	public static String shortString(long i, int chargoal) {
		int c = 100;
		int k = 1000;
		int m = 1000000;
		int b = (int) Math.pow(10, 9);
		long t = (long) Math.pow(10, 12);
		long q = (long) Math.pow(10, 15);
		long index = 0;
		String e = "";
		long v = 0;
		if (String.valueOf(i).length() <= chargoal) {
			return String.valueOf(i);
		} else if (i >= q) {
			index = getIndex(q, i);
			e = "Q";
			v = removeXthDigit(i, String.valueOf(index).length());
		} else if (i >= t) {
			index = getIndex(t, i);
			e = "T";
			v = removeXthDigit(i, String.valueOf(index).length());
		} else if (i >= b) {
			index = getIndex(b, i);
			e = "B";
			v = removeXthDigit(i, String.valueOf(index).length());
		} else if (i >= m) {
			index = getIndex(m, i);
			e = "M";
			v = removeXthDigit(i, String.valueOf(index).length());
		} else if (i >= k) {
			index = getIndex(k, i);
			e = "K";
			v = removeXthDigit(i, String.valueOf(index).length());
		} else if (i >= c) {
			index = getIndex(c, i);
			e = "C";
			v = removeXthDigit(i, String.valueOf(index).length());
		}
		return index + decimalPoint(v, chargoal - String.valueOf(index).length() - 1) + e;

	}
	private static String decimalPoint(long i, int chargoal){
		String s = "";
		if (chargoal < 1) return s;
		String l = String.valueOf(i);
		if (l.length() <= chargoal)  return "." + l;
		return "." + l.substring(0, chargoal);
	}
	/**
	 * Gets the mean.
	 *
	 * @param m the m
	 * @return the mean
	 */
	public static float getMean(float[] m) {
		float sum = 0;
		for (float element : m) {
			sum += element;
		}
		return sum / m.length;
	}

	/**
	 * Gets the median.
	 *
	 * @author Stackoverflow:nico-huysamen
	 * @param data the data
	 * @return Median of float array
	 * @source http://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-mode-and-range-from-a-set-of-numbers
	 */
	public static float getMedian(float[] data) {
		float[] copy = Arrays.copyOf(data, data.length);
		Arrays.sort(copy);
		return copy.length % 2 != 0 ? copy[copy.length / 2] : (copy[copy.length / 2] + copy[copy.length / 2 - 1]) / 2;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	/**
	 * Round.
	 *
	 * @author Stackoverflow:anivaler
	 * @param d the d
	 * @return input rounded to nearest int
	 * @source https://stackoverflow.com/questions/2654839/rounding-a-double-to-turn-it-into-an-int-java
	 */
	public static int round(double d) {
		double dAbs = Math.abs(d);
		int i = (int) dAbs;
		double result = dAbs - i;
		if (result < 0.5) {
			return d < 0 ? -i : i;
		} else {
			return d < 0 ? -(i + 1) : i + 1;
		}
	}

	/**
	 * Writes a number in the lowest possible storage format.
	 *
	 * For example, 254 would be stored as a byte, 256 as a short, so on and so forth.
	 *
	 * @param n the number to be written
	 */
	public static Object smartNumber(Number n) {
		if (n.doubleValue() % 1 == 0) {
			if (n instanceof Float) {
				if (n.floatValue() >= Byte.MIN_VALUE && n.floatValue() <= Byte.MAX_VALUE) {
					return n.byteValue();
				} else if (n.floatValue() >= Short.MIN_VALUE && n.floatValue() <= Short.MAX_VALUE) {
					return n.shortValue();
				} else {
					return n.floatValue();
				}
			} else if (n instanceof Double) {
				if (n.doubleValue() >= Byte.MIN_VALUE && n.doubleValue() <= Byte.MAX_VALUE) {
					return n.byteValue();
				} else if (n.doubleValue() >= Short.MIN_VALUE && n.doubleValue() <= Short.MAX_VALUE) {
					return n.shortValue();
				} else if (n.doubleValue() >= Integer.MIN_VALUE && n.doubleValue() <= Integer.MAX_VALUE) {
					return n.intValue();
				} else {
					return n.doubleValue();
				}
			} else if (n.longValue() >= Byte.MIN_VALUE && n.longValue() <= Byte.MAX_VALUE) {
				return n.byteValue();
			} else if (n.longValue() >= Short.MIN_VALUE && n.longValue() <= Short.MAX_VALUE) {
				return n.shortValue();
			} else if (n.longValue() >= Integer.MIN_VALUE && n.longValue() <= Integer.MAX_VALUE) {
				return n.intValue();
			} else {
				return n.longValue();
			}
		} else if (n instanceof Float || n.doubleValue() >= Float.MIN_VALUE && n.doubleValue() <= Float.MAX_VALUE && MathUtil.countDigits(n.doubleValue()) <= 8) {
			return n instanceof Float ? n.floatValue() : Float.valueOf(new BigDecimal(Double.toString(n.doubleValue()), MathContext.UNLIMITED).toPlainString());
		} else if (n instanceof Double) {
			return n.doubleValue();
		} else {
			return null;
		}
	}
}
