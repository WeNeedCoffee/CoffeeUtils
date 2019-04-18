package coffee.weneed.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public void deleteDuplicate(File directory, File f) throws IOException {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (!file.getName().equals(f.getName()) && FileUtils.contentEquals(f, file)) {
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
