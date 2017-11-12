package coffee.weneed.utils;

import java.util.Arrays;
import java.util.Random;

public class Logic {
	
	/** http://stackoverflow.com/questions/31582524/how-to-check-multiple-objects-for-nullity
	 * @author StackOverflow:kamwo
	 * @param objects
	 * @return
	 */
	public static Boolean isAnyObjectNull(Object... objects) {
	    for (Object o: objects) {
	        if (o == null) {
	            return Boolean.TRUE;
	        }
	    }
	    return Boolean.FALSE;
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
	
	/** http://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-mode-and-range-from-a-set-of-numbers
	 * @author Stackoverflow:nico-huysamen
	 * @param data
	 * @return
	 */
    public static float getMedian(float[] data) {
        float[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return (copy.length % 2 != 0) ? copy[copy.length / 2] : (copy[copy.length / 2] + copy[(copy.length / 2) - 1]) / 2;
    }
    
    public static float getMean(float[] m) {
    	float sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }
}
