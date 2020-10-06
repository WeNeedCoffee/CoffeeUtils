package coffee.weneed.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtil.
 */
public class FileUtil {
	public static boolean areSame(File file1, File file2) throws IOException {
		final boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) {
			return false;
		}

		if (!file1Exists) {
			return true;
		}

		if (file1.isDirectory() || file2.isDirectory()) {
			throw new IOException("Can't compare directories, only files");
		}

		if (file1.length() != file2.length()) {
			return false;
		}

		if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
			return true;
		}
		if (file1.getName().split("\\.").length > 1 && file2.getName().split("\\.").length > 1 && file1.getName().split("\\.")[1].equalsIgnoreCase(file2.getName().split("\\.")[1])) {
			switch (file1.getName().split("\\.")[1].toLowerCase()) {
				case "txt":
				case "json":
				case "cfg":
				case "yml":
				case "yaml":
				case "conf":
					return areTextSame(file1, file2);
				default:
					return contentEquals(file1, file2);
			}
		}
		return contentEquals(file1, file2);
	}

	public static boolean areTextSame(File file1, File file2) throws IOException {
		final boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) {
			return false;
		}

		if (!file1Exists) {
			return true;
		}

		if (file1.isDirectory() || file2.isDirectory()) {
			throw new IOException("Can't compare directories, only files");
		}

		if (file1.length() != file2.length()) {
			return false;
		}

		if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
			return true;
		}
		BufferedReader reader1 = new BufferedReader(new FileReader(file1));

		BufferedReader reader2 = new BufferedReader(new FileReader(file2));

		String line1 = reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;

		//int lineNum = 1;

		while (line1 != null || line2 != null) {
			if (line1 == null || line2 == null) {
				areEqual = false;

				break;
			} else if (!line1.equalsIgnoreCase(line2)) {
				areEqual = false;

				break;
			}

			line1 = reader1.readLine();

			line2 = reader2.readLine();

			//lineNum++;
		}
		reader1.close();
		reader2.close();
		return areEqual;
	}

	/**
	 * Can write.
	 *
	 * @param file the file
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean canWrite(File file) throws IOException {
		if (file.isDirectory()) {
			throw new IOException("File is directory.");
		} else if (file.exists()) {
			throw new IOException("File already exists");
		} else if (!file.canWrite()) {
			throw new IOException("Can't write to the file");
		}
		return true;
	}

	/**
	 * Compares the contents of two files to determine if they are equal or not.
	 * <p>
	 * This method checks to see if the two files are different lengths or if
	 * they point to the same file, before resorting to byte-by-byte comparison
	 * of the contents.
	 * <p>
	 * Code origin: Avalon, org.apache.commons.io
	 *
	 * @param file1 the first file
	 * @param file2 the second file
	 * @return true if the content of the files are equal or they both don't
	 *         exist, false otherwise
	 * @throws IOException in case of an I/O error
	 */
	public static boolean contentEquals(final File file1, final File file2) throws IOException {
		final boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) {
			return false;
		}

		if (!file1Exists) {
			return true;
		}

		if (file1.isDirectory() || file2.isDirectory()) {
			throw new IOException("Can't compare directories, only files");
		}

		if (file1.length() != file2.length()) {
			return false;
		}

		if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
			return true;
		}

		try (InputStream input1 = new FileInputStream(file1); InputStream input2 = new FileInputStream(file2)) {
			return contentEquals(input1, input2);
		}
	}

	/**
	 * Compares the contents of two Streams to determine if they are equal or
	 * not.
	 * <p>
	 * This method buffers the input internally using
	 * <code>BufferedInputStream</code> if they are not already buffered. Code
	 * origin: org.apache.commons.io
	 *
	 * @param input1 the first stream
	 * @param input2 the second stream
	 * @return true if the content of the streams are equal or they both don't
	 *         exist, false otherwise
	 * @throws IOException          if an I/O error occurs
	 * @throws NullPointerException if either input is null
	 */
	public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
		if (input1 == input2) {
			return true;
		}
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			final int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		final int ch2 = input2.read();
		return ch2 == -1;
	}

	/**
	 * Delete duplicate.
	 *
	 * @param directory the directory
	 * @param f         the f
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void deleteDuplicate(File directory, File f) throws IOException {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (!file.getName().equals(f.getName()) && contentEquals(f, file)) {
					file.delete();
				}
			}
		}
	}

	/**
	 * Delete duplicates.
	 *
	 * @param directory the directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void deleteDuplicates(File directory) throws IOException {
		for (File d : directory.listFiles()) {
			if (d.isDirectory()) {
				deleteDuplicates(d);
			} else {
				deleteDuplicate(directory, d);
			}
		}
	}

	public static byte[] downloadFile(File file) throws MalformedURLException, IOException {
		return NetUtil.downloadURL(file.toURI().toURL());
	}

	public static byte[] downloadFile(String file) throws MalformedURLException, IOException {
		return FileUtil.downloadFile(new File(file));
	}

	public static List<String> fileToList(String file) throws IOException {
		List<String> list = new ArrayList<>();
		String s = new String(NetUtil.downloadURL(new File(file).toURI().toURL()));
		String[] ss = s.split("\n");
		for (String e : ss) {
			list.add(e);
		}

		return list;

	}

	/**
	 * List to file.
	 *
	 * @param file the file
	 * @param s    the s
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
			st.write(sb.substring(0, sb.length() - 1).getBytes());
			st.flush();
			st.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/***
	 * https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readStream(InputStream inputStream, Charset encoding) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString(encoding.name());
	}

	public static void toFile(byte[] data, File file) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath())) {
			fos.write(data);
		}
	}

}
