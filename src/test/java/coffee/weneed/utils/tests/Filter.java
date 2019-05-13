package coffee.weneed.utils.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import org.junit.Test;
import coffee.weneed.utils.lang.filter.ProfanityFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Filter {

	/** The filter. */
	static ProfanityFilter filter = null;

	/** The test. */
	static String test = "Fucky me is so F_  . _ u CCky! fuck! FuCk! FFFUCK! F _UCK! FUUUCCCCKK!! FUCKIFY I KEEP MY FUCC! F U C K! F__U   C.K";

	@Test
	public void filtertest() throws MalformedURLException {
		Filter.filter = new ProfanityFilter(new HashMap<>(), new File("./tree.json").toURI().toURL());
		System.out.println(Filter.test + " - " + Filter.filter.filterBadWords(Filter.test));
	}

}