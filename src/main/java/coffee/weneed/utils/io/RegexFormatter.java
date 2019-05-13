package coffee.weneed.utils.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

// TODO: Auto-generated Javadoc
/**
 * The Class RegexFormatter.
 */
public class RegexFormatter extends Formatter {

	/** The format. */
	// format string for printing the log record
	private String format;

	/** The dat. */
	private final Date dat = new Date();

	/**
	 * Instantiates a new regex formatter.
	 *
	 * @param format the format
	 */
	public RegexFormatter(String format) {
		this.format = format;
	}

	/**
	 * Format.
	 *
	 * @param record the record
	 * @return the string
	 */
	@Override
	public synchronized String format(LogRecord record) {
		dat.setTime(record.getMillis());
		String source;
		if (record.getSourceClassName() != null) {
			source = record.getSourceClassName();
			if (record.getSourceMethodName() != null) {
				source += " " + record.getSourceMethodName();
			}
		} else {
			source = record.getLoggerName();
		}
		String message = formatMessage(record);
		String throwable = "";
		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			record.getThrown().printStackTrace(pw);
			pw.close();
			throwable = sw.toString();
		}
		return String.format(format, dat, source, record.getLoggerName(), record.getLevel().getLocalizedName(), message, throwable);
	}

}
