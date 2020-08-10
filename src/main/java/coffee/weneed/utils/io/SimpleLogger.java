/*
 * ~~Part of the JavaPenguin project~~ Any use of this software must strictly
 * adhere to the LICENSE file.
 */
package coffee.weneed.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 */
public class SimpleLogger {

	/**
	 * The Enum LEVEL.
	 */
	public enum LEVEL {

		/** The debug. */
		DEBUG("Debug"),

		/** The normal. */
		NORMAL("Normal"),

		/** The other. */
		OTHER("Other"),

		/** The severe. */
		SEVERE("Severe"),

		/** The console. */
		CONSOLE("Console");

		/** The name. */
		private String name;

		/**
		 * Instantiates a new level.
		 *
		 * @param name the name
		 */
		private LEVEL(String name) {
			this.name = name;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

	/** The def. */
	private LEVEL def;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new logger.
	 *
	 * @param name log name
	 * @param def  default log level
	 */
	public SimpleLogger(String name, LEVEL def) {
		setName(name);
		this.def = def;
		log("Started Logging at " + new Date(System.currentTimeMillis()).toString());
	}

	/**
	 * Debug.
	 *
	 * @param message the message
	 */
	public void debug(String message) {
		this.log(message, LEVEL.DEBUG);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the stream.
	 *
	 * @return the printstream at default log level
	 */
	public PrintStream getStream() {
		return getStream(def);
	}

	/**
	 * Gets the stream.
	 *
	 * @param lvl the lvl
	 * @return PrintStream for this logger at the specified level
	 */
	public PrintStream getStream(LEVEL lvl) {
		if (lvl.equals(LEVEL.CONSOLE)) {
			lvl = LEVEL.NORMAL;
		}
		File file = new File("./logs/" + getName() + "-" + lvl.getName() + ".log");
		if (!file.exists()) {
			new File("./logs/").mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			return new PrintStream(new FileOutputStream(file, true));
		} catch (FileNotFoundException e) {
			return System.err;
		}
	}

	/**
	 * Log.
	 *
	 * @param message the message
	 */
	public void log(String message) {
		this.log(message, def);
	}

	/**
	 * Log.
	 *
	 * @param message the message
	 * @param lvl     the lvl
	 */
	public void log(String message, LEVEL lvl) {
		if (!lvl.equals(LEVEL.CONSOLE)) {
			File file = new File("./logs/" + getName() + "-" + lvl.getName() + ".log");
			if (!file.exists()) {
				new File("./logs/").mkdirs();
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			PrintStream os = getStream(lvl);
			os.println("[" + new Date(System.currentTimeMillis()).toString() + "] > " + message);
			os.flush();
			os.close();
		}
		System.out.println(message);
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
