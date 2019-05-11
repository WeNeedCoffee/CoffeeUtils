package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLUtil.
 */
public class XMLUtil {

	/**
	 * Escape xml.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String escapeXml(String s) {
		return s.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
	}

	/**
	 * Unescape xml.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String unescapeXml(String s) {
		return s.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
	}
}
