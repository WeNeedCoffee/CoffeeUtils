package coffee.weneed.utils.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import coffee.weneed.utils.ArrayUtil;
import coffee.weneed.utils.NetUtil;
import coffee.weneed.utils.StringUtil;

public class spam {
	static String emailregex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

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
			String perc = Float.toString((i * 100.0f) / em.size());
			perc = perc.length() >= 6 ?perc.substring(0, 6) : perc;
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

	public static void dl(File f) throws IOException {
		if (f.isDirectory()) {
			for (File ff : f.listFiles()) {
				try {
					dl(ff);
				} catch (Exception e) {
					continue;
				}
			}
		} else if (!f.getName().toLowerCase().endsWith(".txt".toLowerCase()) && !f.getName().toLowerCase().endsWith(".lorien".toLowerCase()) && !f.getName().toLowerCase().endsWith(".ranger1".toLowerCase()) && !f.getName().toLowerCase().endsWith(".ivanova".toLowerCase()) && !f.getName().toLowerCase().endsWith(".ivanova_2,S".toLowerCase()) && !f.getName().toLowerCase().endsWith(".ivanova_2,".toLowerCase()) && !f.getName().toLowerCase().endsWith(".ivanova_2".toLowerCase()) && !f.getName().toLowerCase().endsWith(".lorien_2,S".toLowerCase()) && !f.getName().toLowerCase().endsWith(".lorien_2,F".toLowerCase()) && !f.getName().toLowerCase().endsWith(".lorien_2,".toLowerCase()) && !f.getName().toLowerCase().endsWith(".delenn_2,S".toLowerCase()) && !f.getName().toLowerCase().endsWith(".delenn_2,".toLowerCase()) && !f.getName().toLowerCase().endsWith(".delenn_2".toLowerCase()) && !f.getName().toLowerCase().endsWith(".delenn".toLowerCase()) && !f.getName().toLowerCase().endsWith(".(NONE)".toLowerCase()) && !f.getName().toLowerCase().endsWith(".RANGER".toLowerCase())) {
			return;
		}
		boolean delete = false;
		boolean a = false;
		boolean s = false;
		BufferedReader brTest = new BufferedReader(new FileReader(f));
		int j = 0;
		String line;
		while ((line = brTest.readLine()) != null && j < 50) {
			j++;
			if (line.toLowerCase().startsWith("List-ID:".toLowerCase())) {
				String c = "";
				if (line.contains("<")) {
					String[] e = line.split("<");
					c = e[e.length - 1].replace(">", "");
				} else {
					c = line.toLowerCase().replace("from:".toLowerCase(), "");
				}
				if (ls.contains(c)) {
					continue;
				}
				ls.add(c);
				FileWriter fr = new FileWriter(ll, true);
				fr.write("\n" + c);
				fr.close();
				s = true;
			} else if (line.toLowerCase().startsWith("FROM:".toLowerCase())) {
				String c = "";
				if (line.contains("<")) {
					String[] e = line.split("<");
					c = e[e.length - 1].replace(">", "");
				} else {
					c = line.toLowerCase().replace("from:".toLowerCase(), "");
				}

				a = true;
				delete = true;
				if (em.contains(c)) {
					continue;
				}
				em.add(c);
				FileWriter fr = new FileWriter(ee, true);
				fr.write("\n" + c);
				fr.close();
			} else if (line.toLowerCase().startsWith("Content-Length:".toLowerCase()) || s && a) {
				break;
			}
		}
		brTest.close();
		System.out.println(i++);
		if (delete && !f.isDirectory()) {
			f.delete();
		}

	}

	public static void fixList(String file) throws IOException {
		List<String> s = new ArrayList<>();
		System.out.println("fixing...");
		try {
			for (String c : new String(NetUtil.downloadUrl(new File(file).toURI().toURL())).split("\\n")) {

				System.out.println(s.size());
				if (c.contains(",")) {
					for (String b : c.split(",")) {
						b = b.split("\\(")[0].trim();
						if (!s.contains(b) && b.matches(emailregex)) {
							s.add(b);
						}
					}
				} else {
					c = c.split("\\(")[0].trim();
					if (!s.contains(c) && !c.isEmpty() && c.matches(emailregex)) {
						s.add(c);
					}
				}
			}

			System.out.println("sorting...");
			Collections.sort(s);
			System.out.println("writing...");
			StringBuilder sb = new StringBuilder();
			for (String ssss : s) {
				sb.append(ssss);
				sb.append("\n");
			}
			File f = new File(file);
			f.delete();
			try {
				FileOutputStream st = new FileOutputStream(f);
				st.write(sb.substring(0, sb.length() - 1).getBytes());
				st.flush();
				st.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

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
	
	public static void fixListb(String file) throws IOException {
		List<String> s = new ArrayList<>();
		System.out.println("fixing...");
		try {
			for (String c : new String(NetUtil.downloadUrl(new File(file).toURI().toURL())).split("\\n")) {
				System.out.println(s.size());
				c = c.split("\\(")[0].trim();
				if (!s.contains(c)) {
					s.add(c);
				}
			}

			System.out.println("sorting...");
			Collections.sort(s);
			System.out.println("writing...");
			StringBuilder sb = new StringBuilder();
			for (String ssss : s) {
				sb.append(ssss);
				sb.append("\n");
			}
			File f = new File(file);
			f.delete();
			try {
				FileOutputStream st = new FileOutputStream(f);
				st.write(sb.substring(0, sb.length() - 1).getBytes());
				st.flush();
				st.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
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
		em = ArrayUtil.toList(new String(NetUtil.downloadUrl(ee.toURI().toURL())).split("\\n"));
		em1 = ArrayUtil.toList(new String(NetUtil.downloadUrl(eme.toURI().toURL())).split("\\n"));
		emd = ArrayUtil.toList(new String(NetUtil.downloadUrl(ed.toURI().toURL())).split("\\n"));
		exists = ArrayUtil.toList(new String(NetUtil.downloadUrl(de.toURI().toURL())).split("\\n"));
		dead = ArrayUtil.toList(new String(NetUtil.downloadUrl(dd.toURI().toURL())).split("\\n"));
		check();
		sortList(de.getPath());
		sortList(dd.getPath());
	}
}