package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeUtil.
 */
public class TimeUtil {

	/** The Constant ONE_SECOND_MS. */
	public static final int ONE_SECOND_MS = 1000;

	/** The Constant ONE_MINUTE_MS. */
	public static final int ONE_MINUTE_MS = ONE_SECOND_MS * 60;

	/** The Constant ONE_HOUR_MS. */
	public static final int ONE_HOUR_MS = ONE_MINUTE_MS * 60;

	/** The Constant ONE_DAY_MS. */
	public static final int ONE_DAY_MS = ONE_HOUR_MS * 24;

	/** The Constant ONE_WEEK_MS. */
	public static final int ONE_WEEK_MS = ONE_DAY_MS * 7;

	/** The Constant ONE_YEAR_MS. */
	public static final int ONE_YEAR_MS = ONE_DAY_MS * 365;

	/**
	 * Gets the days between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the days between
	 */
	public static int getDaysBetween(long startMillis, long endMillis) {
		return getHoursBetween(startMillis, endMillis) / 24;
	}

	/**
	 * Gets the hours between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the hours between
	 */
	public static int getHoursBetween(long startMillis, long endMillis) {
		return getMinutesBetween(startMillis, endMillis) / 60;
	}

	/**
	 * Gets the minutes between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the minutes between
	 */
	public static int getMinutesBetween(long startMillis, long endMillis) {
		return (int) (getSecondsBetween(startMillis, endMillis) / 60.0);
	}

	/**
	 * Gets the rounded days between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the rounded days between
	 */
	public static int getRoundedDaysBetween(long startMillis, long endMillis) {
		return getHoursBetween(startMillis, endMillis) % 24;
	}

	/**
	 * Gets the rounded hours between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the rounded hours between
	 */
	public static int getRoundedHoursBetween(long startMillis, long endMillis) {
		return getHoursBetween(startMillis, endMillis) % 60;
	}

	/**
	 * Gets the rounded minutes between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the rounded minutes between
	 */
	public static int getRoundedMinutesBetween(long startMillis, long endMillis) {
		return getMinutesBetween(startMillis, endMillis) % 60;
	}

	/**
	 * Gets the rounded seconds between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the rounded seconds between
	 */
	public static int getRoundedSecondsBetween(long startMillis, long endMillis) {
		return ((int) getSecondsBetween(startMillis, endMillis)) % 60;
	}

	/**
	 * Gets the seconds between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis the end millis
	 * @return the seconds between
	 */
	public static double getSecondsBetween(long startMillis, long endMillis) {
		return (endMillis - startMillis) / 1000.0;
	}

	/**
	 * Gets the unix time.
	 *
	 * @return the unix time
	 */
	public static int getUnixTime() {
		return Math.round(System.currentTimeMillis() / 1000);
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

}
