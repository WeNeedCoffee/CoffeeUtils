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

public class ListTest {

	public static final String starter = "Eve";

	public static void fixList(String file) throws MalformedURLException {
		List<String> s = null;
		try {
			s = ArrayUtil.sortList(new String(NetUtil.downloadUrl(new File("./" + file).toURI().toURL())).split("\\n"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	 * @throws MalformedURLException the malformed URL exception
	 */
	public static void main(String[] args) throws MalformedURLException {
		fixList("names_in.txt");
		List<String> nms = null;
		try {
			nms = ArrayUtil.sortList(new String(NetUtil.downloadUrl(new File("./" + "names_in.txt").toURI().toURL())).split("\\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> names = new ArrayList<>();
		for (String name : nms) {
			if (name.toLowerCase().startsWith(starter.toLowerCase())) {
				names.add(name);
			}
		}

		listToFile("names_out.txt", names);
	}
}