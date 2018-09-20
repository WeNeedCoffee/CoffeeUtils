package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void main(String[] args) throws MalformedURLException {
		Filter.filter = new ProfanityFilter(true,
				// new URL("https://raw.githubusercontent.com/WeNeedCoffee/CoffeeUtils/master/tree.json?_=" + System.currentTimeMillis()));
				new File("./tree.json").toURI().toURL());
		System.out.println(Filter.test + " - " + Filter.filter.filterBadWords(Filter.test));
		System.out.println(new String(HexUtil.getBytesFromHex(HexUtil.getHexFromBytes(Filter.test.getBytes()))));
		Filter.sortBaddies();
		for (String s : new String(LogicUtil.downloadUrl(new File("./badwords.txt").toURI().toURL())).split("\\n")) {
			Filter.filter.blacklistWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-long.txt"))
						.split("\\n")) {
			Filter.filter.whitelistWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-medium.txt"))
						.split("\\n")) {
			Filter.filter.whitelistWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears-short.txt"))
						.split("\\n")) {
			Filter.filter.whitelistWord(s);
		}

		for (String s : new String(LogicUtil.downloadUrl(
				"https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-usa-no-swears.txt"))
						.split("\\n")) {
			Filter.filter.whitelistWord(s);
		}

		for (String s : new String(LogicUtil
				.downloadUrl("https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-no-swears.txt"))
						.split("\\n")) {
			Filter.filter.whitelistWord(s);
		}

		Filter.save();

	}

	/**
	 * Save.
	 */
	public static void save() {
		File f = new File("tree.json");
		f.delete();
		try {
			FileOutputStream s = new FileOutputStream(f);
			s.write(Filter.filter.toJSON().toString(1).getBytes());
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

	public static void sortBaddies() throws MalformedURLException {
		List<String> s = new ArrayList<>();
		String[] sss = new String(LogicUtil.downloadUrl(new File("./badwords.txt").toURI().toURL())).split("\\n");
		Arrays.sort(sss);
		for (String ss : sss) {
			if (!s.contains(ss)) {
				s.add(ss);
			}
		}

		File f = new File("badwords.txt");
		f.delete();
		try {
			FileOutputStream st = new FileOutputStream(f);
			for (String ssss : s) {
				st.write((ssss + "\n").getBytes());
			}
			st.flush();
			st.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}