package coffee.weneed.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

	/**
	 * Compares the contents of two files to determine if they are equal or not.
	 * <p>
	 * This method checks to see if the two files are different lengths or if they
	 * point to the same file, before resorting to byte-by-byte comparison of the
	 * contents.
	 * <p>
	 * Code origin: Avalon, org.apache.commons.io
	 *
	 * @param file1 the first file
	 * @param file2 the second file
	 * @return true if the content of the files are equal or they both don't exist,
	 *         false otherwise
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
	 * Compares the contents of two Streams to determine if they are equal or not.
	 * <p>
	 * This method buffers the input internally using
	 * <code>BufferedInputStream</code> if they are not already buffered. Code
	 * origin: org.apache.commons.io
	 *
	 * @param input1 the first stream
	 * @param input2 the second stream
	 * @return true if the content of the streams are equal or they both don't
	 *         exist, false otherwise
	 * @throws NullPointerException if either input is null
	 * @throws IOException          if an I/O error occurs
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

	public void deleteDuplicate(File directory, File f) throws IOException {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (!file.getName().equals(f.getName()) && contentEquals(f, file)) {
					file.delete();
				}
			}
		}
	}

	public void deleteDuplicates(File directory) throws IOException {
		for (File d : directory.listFiles()) {
			if (d.isDirectory()) {
				deleteDuplicates(d);
			} else {
				deleteDuplicate(directory, d);
			}
		}
	}

}
