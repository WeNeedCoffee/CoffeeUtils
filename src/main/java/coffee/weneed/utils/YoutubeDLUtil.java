package coffee.weneed.utils;

import java.io.File;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;

public class YoutubeDLUtil {

	public static File downloadMP3(String exec, String video, String path, String filename) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		YoutubeDL.setExecutablePath(exec);
		YoutubeDLRequest request = new YoutubeDLRequest(video, path);
		request.setOption("format", "(bestaudio[ext=m4a])[protocol^=http]");
		request.setOption("output", filename + ".mp3");
		request.setOption("merge-output-format", "mp3");
		try {
			YoutubeDL.execute(request);
		} catch (YoutubeDLException e) {
			e.printStackTrace();
		}
		return new File(path + filename + ".mp3");
	}
}
