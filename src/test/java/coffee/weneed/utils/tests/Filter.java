package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import coffee.weneed.utils.HexUtil;
import coffee.weneed.utils.LogicUtil;
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

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		Filter.filter = new ProfanityFilter(true,
				new URL("https://raw.githubusercontent.com/WeNeedCoffee/CoffeeUtils/master/tree.json?_=" + System.currentTimeMillis()));
		System.out.println(Filter.test + " - " + Filter.filter.filterBadWords(Filter.test));
		System.out.println(new String(HexUtil.getBytesFromHex(HexUtil.getHexFromBytes(Filter.test.getBytes()))));
		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-long.txt"))
						.split("\\n")) {
			Filter.filter.removeWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-medium.txt"))
						.split("\\n")) {
			Filter.filter.removeWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-short.txt"))
						.split("\\n")) {
			Filter.filter.removeWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears.txt"))
						.split("\\n")) {
			Filter.filter.removeWord(s);
		}

		for (String s : new String(LogicUtil
				.downloadUrl("https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-no-swears.txt"))
						.split("\\n")) {
			Filter.filter.removeWord(s);
		}
		Filter.save();

	}

	public static void save() {
		File f = new File("tree.json");
		f.delete();
		try {
			FileOutputStream s = new FileOutputStream(f);
			s.write(Filter.filter.getRoot().toJSON().toString(1).getBytes());
			s.flush();
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}