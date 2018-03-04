package coffee.weneed.utils.data.input;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Dalethium
 *
 */

public class InputStreamByteStream implements IByteInputStream {

	private final InputStream is;

	private long read = 0L;

	public InputStreamByteStream(InputStream is) {
		this.is = is;
	}

	@Override
	public final long available() {
		try {
			return this.is.available();
		} catch (IOException e) {
			System.err.println("ERROR" + e);
		}
		return 0L;
	}

	@Override
	public final long getBytesRead() {
		return this.read;
	}

	@Override
	public final int readByte() {
		try {
			int temp = this.is.read();
			if (temp == -1) {
				throw new RuntimeException("EOF");
			}
			this.read += 1L;
			return temp;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final String toString(boolean b) {
		return toString();
	}
}