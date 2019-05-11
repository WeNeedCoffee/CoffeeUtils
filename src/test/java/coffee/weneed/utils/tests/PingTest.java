package coffee.weneed.utils.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import coffee.weneed.utils.ArrayUtil;
import coffee.weneed.utils.NetUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class PingTest.
 */
public class PingTest {

	/**
	 * Fix list.
	 *
	 * @param file the file
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void fixList(String file) throws MalformedURLException {
		List<String> s = null;
		try {
			s = ArrayUtil.sortList(new String(NetUtil.downloadUrl(new File("./" + file).toURI().toURL())).split("\\n"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void main(String[] args) throws MalformedURLException {
		fixList("ips_in.txt");
		List<String> ips = null;
		try {
			ips = ArrayUtil.sortList(new String(NetUtil.downloadUrl(new File("./" + "ips_in.txt").toURI().toURL())).split("\\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> reachable = new ArrayList<>();
		List<String> unreachable = new ArrayList<>();
		for (String s : ips) {
			int port = 25565;
			String ip = s;
			if (s.contains(":")) {
				port = Integer.valueOf(s.split(":")[1]);
				ip = s.split(":")[0];
			}

			String st = ip + ":" + port;
			System.out.print("Pinging " + st + "... ");
			if (NetUtil.isReachable(ip, port, 250)) {
				reachable.add(st);
				System.out.print("Success! ");

			} else {
				unreachable.add(st);
				System.out.print("Failure. ");
			}
			System.out.print("Valid ips: " + reachable.size() + " - Invalid Ips: " + unreachable.size() + " - Remaining: " + (ips.size() - (reachable.size() + unreachable.size())));
			System.out.println();
		}

		listToFile("ips_reachable.txt", reachable);
		listToFile("ips_unreachable.txt", unreachable);
	}
}
