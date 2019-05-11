package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import coffee.weneed.utils.ArrayUtil;
import coffee.weneed.utils.NetUtil;
import coffee.weneed.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class spam.
 */
public class spam {
	
	/** The ee. */
	static File ee = new File("emails_out.txt");
	
	/** The ll. */
	static File ll = new File("lists_out.txt");
	
	/** The ed. */
	static File ed = new File("emails_dead.txt");
	
	/** The de. */
	static File de = new File("domains_exists.txt");
	
	/** The eme. */
	static File eme = new File("emails_exists.txt");
	
	/** The dd. */
	static File dd = new File("domains_dead.txt");
	
	/** The em. */
	static List<String> em;
	
	/** The ls. */
	static List<String> ls;
	
	/** The exists. */
	static List<String> exists;
	
	/** The dead. */
	static List<String> dead;
	
	/** The em 1. */
	static List<String> em1;
	
	/** The emd. */
	static List<String> emd;
	
	/** The i. */
	static int i = 0;

	/**
	 * Check.
	 */
	public static void check() {
		int i = em1.size() + emd.size();
		FileWriter frde = null;
		FileWriter frdd = null;
		FileWriter freme = null;
		FileWriter fred = null;
		try {
			frde = new FileWriter(de, true);
			frdd = new FileWriter(dd, true);
			freme = new FileWriter(eme, true);
			fred = new FileWriter(ed, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int se = em.size();
		em = em.subList(i - 10, em.size());
		for (String s : em) {
			i++;
			boolean deadd = false;
			boolean existss = false;
			boolean em11 = false;
			boolean emdd = false;
			String domain = StringUtil.getEnd(s, "@");
			if (em1.contains(s) || emd.contains(s)) {
				String perc = Float.toString(i * 100.0f / em.size());
				perc = perc.length() >= 6 ? perc.substring(0, 6) : perc;
				System.out.println("Total processed: " + i + " (" + perc + "%) Total Valid Emails: " + em1.size() + " Total Dead Emails: " + emd.size() + " Total Valid Domains: " + exists.size() + " Total Dead Domains: " + dead.size() + " " + (emdd ? "Email was Dead. " : em11 ? "Email was valid. " : "Email was Dead. "));
				continue;
			}
			if (dead.contains(domain)) {
				if (!emd.contains(s)) {
					emd.add(s);
					emdd = true;
				}
			} else if (exists.contains(domain)) {
				em1.add(s);
				em11 = true;
			} else if (!NetUtil.isValidDomain(domain) || !NetUtil.isHostnameValid(domain) || !NetUtil.hasMX(domain)) {
				if (!dead.contains(domain)) {
					dead.add(domain);
					deadd = true;
				}
				emd.add(s);
				emdd = true;
			} else {
				if (!exists.contains(domain)) {
					exists.add(domain);
					existss = true;
				}
				em1.add(s);
				em11 = true;
			}
			if (existss) {
				try {
					frde.write("\n" + domain);
					frde.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (deadd) {

				try {
					frdd.write("\n" + domain);
					frdd.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (em11) {
				try {
					freme.write("\n" + s);
					freme.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (emdd) {
				try {
					fred.write("\n" + s);
					fred.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String perc = Float.toString(i * 100.0f / se);
			perc = perc.length() >= 6 ? perc.substring(0, 6) : perc;
			System.out.println("Total processed: " + i + " (" + perc + "%) Total Valid Emails: " + em1.size() + " Total Dead Emails: " + emd.size() + " Total Valid Domains: " + exists.size() + " Total Dead Domains: " + dead.size() + " " + (emdd ? "Email was Dead. " : em11 ? "Email was valid. " : "Email was Dead. "));
		}
		try {
			frde.close();
			frdd.close();
			freme.close();
			fred.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * List to file.
	 *
	 * @param file the file
	 * @param s the s
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void listToFile(String file, List<String> s) throws MalformedURLException {

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {

		// sortList(de.getPath());
		// sortList(dd.getPath());
		/// *
		System.out.println("Total processed: 1");

		em = ArrayUtil.toList(new String(NetUtil.downloadUrl(ee.toURI().toURL()), StandardCharsets.UTF_8).split("\\n"));

		System.out.println("Total processed: 2");
		em1 = ArrayUtil.toList(new String(NetUtil.downloadUrl(eme.toURI().toURL()), StandardCharsets.UTF_8).split("\\n"));

		System.out.println("Total processed: 3");
		emd = ArrayUtil.toList(new String(NetUtil.downloadUrl(ed.toURI().toURL()), StandardCharsets.UTF_8).split("\\n"));

		System.out.println("Total processed: 4");
		exists = ArrayUtil.toList(new String(NetUtil.downloadUrl(de.toURI().toURL()), StandardCharsets.UTF_8).split("\\n"));

		System.out.println("Total processed: 5");
		dead = ArrayUtil.toList(new String(NetUtil.downloadUrl(dd.toURI().toURL()), StandardCharsets.UTF_8).split("\\n"));

		System.out.println("Total processed: 6");
		check();
		sortList(de.getPath());
		sortList(dd.getPath());
		// */
	}

	/**
	 * Sort list.
	 *
	 * @param file the file
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void sortList(String file) throws MalformedURLException {
		List<String> s = null;
		try {
			s = ArrayUtil.sortList(new String(NetUtil.downloadUrl(new File("./" + file).toURI().toURL())).split("\\n"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listToFile(file, s);
	}
}