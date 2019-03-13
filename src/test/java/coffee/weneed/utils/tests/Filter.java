package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import coffee.weneed.utils.ArrayUtil;
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

	public static void fixList(String file) throws MalformedURLException {
		List<String> s = ArrayUtil
				.sortList(new String(LogicUtil.downloadUrl(new File("./" + file).toURI().toURL())).split("\\n"));
		StringBuilder sb = new StringBuilder();
		for (String ssss : s) {
			sb.append(ssss);
			sb.append("\n");
		}
		File f = new File(file);
		f.delete();
		try {
			FileOutputStream st = new FileOutputStream(f);
			st.write(sb.substring(0, sb.length() - 1).replace("\r", "").replace("\t", "").getBytes());
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

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void main(String[] args) throws MalformedURLException {
		Filter.filter = new ProfanityFilter(true,
				// new
				// URL("https://raw.githubusercontent.com/WeNeedCoffee/CoffeeUtils/master/tree.json?_="
				// + System.currentTimeMillis()));
				new File("./tree.json").toURI().toURL());
		System.out.println(Filter.test + " - " + Filter.filter.filterBadWords(Filter.test));
		// System.out.println(new
		// String(HexUtil.getBytesFromHex(HexUtil.getHexFromBytes(Filter.test.getBytes()))));
		// Filter.fixList("badwords.txt");
		// Filter.fixList("endings.txt");
		// Filter.fixList("cleanwords.txt");

		/*
		 * for (String s : new String(LogicUtil.downloadUrl(new
		 * File("./badwords.txt").toURI().toURL())).split("\\n")) {
		 * Filter.filter.blacklistWord(s); }
		 *
		 * for (String s : new String(LogicUtil.downloadUrl(new
		 * File("./cleanwords.txt").toURI().toURL())).split("\\n")) {
		 * Filter.filter.whitelistWord(s); }
		 *
		 * Filter.save();
		 */

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
}