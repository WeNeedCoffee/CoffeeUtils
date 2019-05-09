package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import coffee.weneed.utils.ArrayUtil;
import coffee.weneed.utils.NetUtil;
import coffee.weneed.utils.StringUtil;

public class spam {
	static File ee = new File("emails_out.txt");
	static File ll = new File("lists_out.txt");
	static File ed = new File("emails_dead.txt");
	static File de = new File("domains_exists.txt");
	static File eme = new File("emails_exists.txt");
	static File dd = new File("domains_dead.txt");
	static List<String> em;
	static List<String> ls;
	static List<String> exists;
	static List<String> dead;
	static List<String> em1;
	static List<String> emd;
	static int i = 0;

	public static void check() {
		int i = 0;
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
		for (String s : em) {
			i++;
			boolean deadd = false;
			boolean existss = false;
			boolean em11 = false;
			boolean emdd = false;
			String domain = StringUtil.getEnd(s, "@");
			if (em1.contains(s) || emd.contains(s)) {
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
			String perc = Float.toString(i * 100.0f / em.size());
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
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// sortList(de.getPath());
		// sortList(dd.getPath());
		/// *
		em = ArrayUtil.toList(new String(NetUtil.downloadUrl(ee.toURI().toURL())).split("\\n"));
		em1 = ArrayUtil.toList(new String(NetUtil.downloadUrl(eme.toURI().toURL())).split("\\n"));
		emd = ArrayUtil.toList(new String(NetUtil.downloadUrl(ed.toURI().toURL())).split("\\n"));
		exists = ArrayUtil.toList(new String(NetUtil.downloadUrl(de.toURI().toURL())).split("\\n"));
		dead = ArrayUtil.toList(new String(NetUtil.downloadUrl(dd.toURI().toURL())).split("\\n"));
		check();
		sortList(de.getPath());
		sortList(dd.getPath());
		// */
	}

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