package coffee.weneed.utils;

import java.util.Arrays;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class MathUtil.
 */
public class MathUtil {

	/**
	 * Gets the mean.
	 *
	 * @param m the m
	 * @return the mean
	 */
	public static float getMean(float[] m) {
		float sum = 0;
		for (int i = 0; i < m.length; i++) {
			sum += m[i];
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
		return (copy.length % 2 != 0) ? copy[copy.length / 2] : (copy[copy.length / 2] + copy[(copy.length / 2) - 1]) / 2;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
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
}
