package coffee.weneed.utils;

import java.io.IOException;

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
		return this.arr.length - this.pos;
	}

	/**
	 * Gets the bytes read.
	 *
	 * @return the bytes read
	 */
	public long getBytesRead() {
		return this.bytesRead;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public long getPosition() {
		return this.pos;
	}

	/**
	 * Read byte.
	 *
	 * @return the int
	 */
	public int readByte() {
		this.bytesRead += 1L;
		return this.arr[(this.pos++)] & 0xFF;
	}

	/**
	 * Seek.
	 *
	 * @param offset the offset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void seek(long offset) throws IOException {
		this.pos = ((int) offset);
	}

	/* (non-Javadoc)
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
		if (this.arr.length - this.pos > 0) {
			byte[] now = new byte[this.arr.length - this.pos];
			System.arraycopy(this.arr, this.pos, now, 0, this.arr.length - this.pos);
			nows = HexTool.toHumanReadableString(now);
		}
		if (b) {
			return "All: " + HexTool.toHumanReadableString(this.arr) + "\nNow: " + nows;
		}
		return "Data: " + nows;
	}
}