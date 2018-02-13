package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeUtil.
 */
public class TimeUtil {

	/** The Constant ONE_SECOND_MS. */
	public static final int ONE_SECOND_MS = 1000;

	/** The Constant ONE_MINUTE_MS. */
	public static final int ONE_MINUTE_MS = 60000;

	/**
	 * Checks if is before.
	 *
	 * @param before the before
	 * @param after the after
	 * @return true, if is before
	 */
	public static boolean isBefore(long before, long after) {
		if (before < after) return true;
		return false;
	}

	/**
	 * Checks if is after.
	 *
	 * @param after the after
	 * @param before the before
	 * @return true, if is after
	 */
	public static boolean isAfter(long after, long before) {
		if (after < before) return true;
		return false;
	}
}
