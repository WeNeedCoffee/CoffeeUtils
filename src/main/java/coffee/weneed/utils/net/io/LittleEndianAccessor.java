package coffee.weneed.utils.net.io;

import java.awt.Point;
import java.io.IOException;

import coffee.weneed.utils.ByteArrayByteStream;

// TODO: Auto-generated Javadoc
/**
 * The Class LittleEndianAccessor.
 *
 * @author Dalethium
 */
public class LittleEndianAccessor {

	/** The bs. */
	private final ByteArrayByteStream bs;

	/**
	 * Instantiates a new little endian accessor.
	 *
	 * @param bs the bs
	 */
	public LittleEndianAccessor(ByteArrayByteStream bs) {
		this.bs = bs;
	}

	/**
	 * Available.
	 *
	 * @return the long
	 */
	public final long available() {
		return this.bs.available();
	}

	/**
	 * Gets the bytes read.
	 *
	 * @return the bytes read
	 */
	public final long getBytesRead() {
		return this.bs.getBytesRead();
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public final long getPosition() {
		return this.bs.getPosition();
	}

	/**
	 * Read.
	 *
	 * @param num the num
	 * @return the byte[]
	 */
	public final byte[] read(int num) {
		byte[] ret = new byte[num];
		for (int x = 0; x < num; x++) {
			ret[x] = readByte();
		}
		return ret;
	}

	/**
	 * Read ascii string.
	 *
	 * @param n the n
	 * @return the string
	 */
	public final String readAsciiString(int n) {
		char[] ret = new char[n];
		for (int x = 0; x < n; x++) {
			ret[x] = ((char) readByte());
		}
		return new String(ret);
	}

	/**
	 * Read byte.
	 *
	 * @return the byte
	 */
	public final byte readByte() {
		return (byte) this.bs.readByte();
	}

	/**
	 * Read char.
	 *
	 * @return the char
	 */
	public final char readChar() {
		return (char) readShort();
	}

	/**
	 * Read double.
	 *
	 * @return the double
	 */
	public final double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	/**
	 * Read float.
	 *
	 * @return the float
	 */
	public final float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	/**
	 * Read int.
	 *
	 * @return the int
	 */
	public final int readInt() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		int byte3 = this.bs.readByte();
		int byte4 = this.bs.readByte();
		return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

	/**
	 * Read long.
	 *
	 * @return the long
	 */
	public final long readLong() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		int byte3 = this.bs.readByte();
		int byte4 = this.bs.readByte();
		long byte5 = this.bs.readByte();
		long byte6 = this.bs.readByte();
		long byte7 = this.bs.readByte();
		long byte8 = this.bs.readByte();

		return (byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) + (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

	/**
	 * Read pos.
	 *
	 * @return the point
	 */
	public final Point readPos() {
		int x = readShort();
		int y = readShort();
		return new Point(x, y);
	}

	/**
	 * Read short.
	 *
	 * @return the short
	 */
	public final short readShort() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		return (short) ((byte2 << 8) + byte1);
	}

	/**
	 * Read signed ascii string.
	 *
	 * @return the string
	 */
	public final String readSignedAsciiString() {
		return readAsciiString(readShort());
	}

	/**
	 * Read U short.
	 *
	 * @return the int
	 */
	public final int readUShort() {
		int quest = readShort();
		if (quest < 0) {
			quest += 65536;
		}
		return quest;
	}

	/**
	 * Seek.
	 *
	 * @param offset the offset
	 */
	public final void seek(long offset) {
		try {
			this.bs.seek(offset);
		} catch (IOException e) {
			System.err.println("Seek failed" + e);
		}
	}

	/**
	 * Skip.
	 *
	 * @param num the num
	 */
	public final void skip(int num) {
		seek(getPosition() + num);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return this.bs.toString();
	}

	/**
	 * To string.
	 *
	 * @param b the b
	 * @return the string
	 */
	public final String toString(boolean b) {
		return this.bs.toString(b);
	}
}