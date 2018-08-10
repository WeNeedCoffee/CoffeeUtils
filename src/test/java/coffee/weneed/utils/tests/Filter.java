package coffee.weneed.utils.tests;

import coffee.weneed.utils.HexTool;
import coffee.weneed.utils.lang.filter.ProfanityFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Filter {

	/** The filter. */
	static ProfanityFilter filter = new ProfanityFilter(true);

	/** The test. */
	static String test = "Fucky me is so F_  . _ u CCky! fuck! FuCk! FFFUCK! F _UCK! FUUUCCCCKK!! FUCKIFY I KEEP MY FUCC! F U C K! F__U   C.K";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(test + " - " + filter.filterBadWords(test));
		System.out.println(new String(HexTool.getBytesFromHex(HexTool.getHexFromBytes(test.getBytes()))));
	}
}