package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeUtil.
 */
public class TimeUtil {

	/** The Constant ONE_SECOND_MS. */
	public static final int ONE_SECOND_MS = 1000;

	/** The Constant ONE_MINUTE_MS. */
	public static final int ONE_MINUTE_MS = TimeUtil.ONE_SECOND_MS * 60;

	/** The Constant ONE_HOUR_MS. */
	public static final int ONE_HOUR_MS = TimeUtil.ONE_MINUTE_MS * 60;

	/** The Constant ONE_DAY_MS. */
	public static final int ONE_DAY_MS = TimeUtil.ONE_HOUR_MS * 24;

	/** The Constant ONE_WEEK_MS. */
	public static final int ONE_WEEK_MS = TimeUtil.ONE_DAY_MS * 7;

	/** The Constant ONE_YEAR_MS. */
	public static final int ONE_YEAR_MS = TimeUtil.ONE_DAY_MS * 365;

	/**
	 * Returns the elapsed days between <code>startMillis</code> and
	 * <code>endMillis</code>.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return Elapsed days between <code>startMillis</code> and
	 *         <code>endMillis</code>.
	 */
	public static int getDaysBetween(long startMillis, long endMillis) {
		return TimeUtil.getHoursBetween(startMillis, endMillis) / 24;
	}

	/**
	 * Gets the hours between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the hours between
	 */
	public static int getHoursBetween(long startMillis, long endMillis) {
		return TimeUtil.getMinutesBetween(startMillis, endMillis) / 60;
	}

	/**
	 * Gets the millis between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the millis between
	 */
	public static long getMillisBetween(long startMillis, long endMillis) {
		return endMillis - startMillis;
	}

	/**
	 * Gets the minutes between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the minutes between
	 */
	public static int getMinutesBetween(long startMillis, long endMillis) {
		return (int) (TimeUtil.getSecondsBetween(startMillis, endMillis) / 60.0);
	}

	/**
	 * Creates a human readable string based on the time elapsed between
	 * <code>startMillis</code> and <code>endMillis</code>.
	 * 
	 * @param startMillis The start time.
	 * @param endMillis   The end time.
	 * @return Readable string of the time between <code>startMillis</code> and
	 *         <code>endMillis</code>.
	 */
	public static final String getReadableMillis(long startMillis, long endMillis) {
		StringBuilder sb = new StringBuilder();
		double elapsedSeconds = TimeUtil.getSecondsBetween(startMillis, endMillis);
		int elapsedSecs = TimeUtil.getSecondsBetween(startMillis, endMillis) % 60;
		int elapsedMinutes = TimeUtil.getMinutesBetween(startMillis, endMillis);
		int elapsedMins = TimeUtil.getMinutesBetween(startMillis, endMillis) % 60;
		int elapsedHours = TimeUtil.getHoursBetween(startMillis, endMillis) % 60;
		int elapsedDays = TimeUtil.getDaysBetween(startMillis, endMillis);
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

	/**
	 * Gets the rounded days between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the rounded days between
	 */
	public static int getRoundedDaysBetween(long startMillis, long endMillis) {
		return TimeUtil.getDaysBetween(startMillis, endMillis) % 24 > 11
				? TimeUtil.getDaysBetween(startMillis, endMillis) + 24
						- TimeUtil.getDaysBetween(startMillis, endMillis) % 24
				: TimeUtil.getDaysBetween(startMillis, endMillis)
						- TimeUtil.getDaysBetween(startMillis, endMillis) % 24;

	}

	/**
	 * Gets the rounded hours between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the rounded hours between
	 */
	public static int getRoundedHoursBetween(long startMillis, long endMillis) {
		return TimeUtil.getHoursBetween(startMillis, endMillis) % 60 > 29
				? TimeUtil.getHoursBetween(startMillis, endMillis) + 60
						- TimeUtil.getHoursBetween(startMillis, endMillis) % 60
				: TimeUtil.getHoursBetween(startMillis, endMillis)
						- TimeUtil.getHoursBetween(startMillis, endMillis) % 60;
	}

	/**
	 * Gets the rounded minutes between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the rounded minutes between
	 */
	public static int getRoundedMinutesBetween(long startMillis, long endMillis) {
		return TimeUtil.getMinutesBetween(startMillis, endMillis) % 60 > 29
				? TimeUtil.getMinutesBetween(startMillis, endMillis) + 60
						- TimeUtil.getMinutesBetween(startMillis, endMillis) % 60
				: TimeUtil.getMinutesBetween(startMillis, endMillis)
						- TimeUtil.getMinutesBetween(startMillis, endMillis) % 60;
	}

	/**
	 * Gets the rounded seconds between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the rounded seconds between
	 */
	public static int getRoundedSecondsBetween(long startMillis, long endMillis) {
		return TimeUtil.getSecondsBetween(startMillis, endMillis) % 60 > 29
				? TimeUtil.getSecondsBetween(startMillis, endMillis) + 60
						- TimeUtil.getSecondsBetween(startMillis, endMillis) % 60
				: TimeUtil.getSecondsBetween(startMillis, endMillis)
						- TimeUtil.getSecondsBetween(startMillis, endMillis) % 60;
	}

	/**
	 * Gets the seconds between.
	 *
	 * @param startMillis the start millis
	 * @param endMillis   the end millis
	 * @return the seconds between
	 */
	public static int getSecondsBetween(long startMillis, long endMillis) {
		return (int) (TimeUtil.getMillisBetween(startMillis, endMillis) / 1000);
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
	 * @param after  the after
	 * @param before the before
	 * @return true, if is after
	 */
	public static boolean isAfter(long after, long before) {
		if (after < before) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is after now.
	 *
	 * @param after the after
	 * @return true, if is after
	 */
	public static boolean isAfterNow(long after) {
		return TimeUtil.isAfter(after, System.currentTimeMillis());
	}

	/**
	 * Used to determine if the given input is in seconds or milliseconds.
	 *
	 * @param input a timestamp, in seconds or millis
	 * @return true if input is less than Integer.MAX_VALUE
	 */
	public static boolean isUnixTime(long input) {
		if (input < Integer.MAX_VALUE) {
			return true;
		}
		return false;
	}

}
