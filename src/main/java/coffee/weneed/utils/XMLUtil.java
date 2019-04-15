package coffee.weneed.utils;

public class XMLUtil {

	public static String escapeXml(String s) {
		return s.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
	}

	public static String unescapeXml(String s) {
		return s.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
	}
}
