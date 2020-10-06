package coffee.weneed.utils.io;

import java.io.IOException;
import coffee.weneed.utils.ArrayUtil;
import coffee.weneed.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteArrayByteStream.
 *
 * @author Dalethium
 */
public class ByteArrayByteStream {

	/** The arr. */
	private final byte[] arr;

	/** The bytes read. */
	private long bytesRead = 0L;

	/** The pos. */
	private int pos = 0;

	/**
	 * Instantiates a new byte array byte stream.
	 *
	 * @param arr the arr
	 */
	public ByteArrayByteStream(byte[] arr) {
		this.arr = arr;
	}

	/**
	 * Available.
	 *
	 * @return the long
	 */
	public long available() {
		return arr.length - pos;
	}

	/**
	 * Gets the bytes read.
	 *
	 * @return the bytes read
	 */
	public long getBytesRead() {
		return bytesRead;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public long getPosition() {
		return pos;
	}

	/**
	 * Gets the remaining bytes.
	 *
	 * @return the remaining bytes
	 */
	public byte[] getRemainingBytes() {
		return ArrayUtil.copyOf(arr, pos);
	}

	/**
	 * Read byte.
	 *
	 * @return the int
	 */
	public int readByte() {
		bytesRead += 1L;
		return arr[pos++] & 0xFF;
	}

	/**
	 * Seek.
	 *
	 * @param offset the offset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void seek(long offset) throws IOException {
		pos = (int) offset;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(false);
	}

	/**
	 * To string.
	 *
	 * @param b the b
	 * @return the string
	 */
	public String toString(boolean b) {
		String nows = "";
		if (arr.length - pos > 0) {
			byte[] now = new byte[arr.length - pos];
			System.arraycopy(arr, pos, now, 0, arr.length - pos);
			nows = StringUtil.bytesToHex(now);
		}
		if (b) {
			return "All: " + StringUtil.bytesToHex(arr) + "\nNow: " + nows;
		}
		return "Data: " + nows;
	}
}