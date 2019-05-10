package coffee.weneed.utils.io;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.Charset;

// TODO: Auto-generated Javadoc
/**
 * The Class LittleEndianAccessor.
 *
 * @author Dalethium
 */
public class CoffeeAccessor {

	/** The bs. */
	private final ByteArrayByteStream bs;

	/**
	 * Instantiates a new little endian accessor.
	 *
	 * @param bs the bs
	 */
	public CoffeeAccessor(byte[] in) {
		this(new ByteArrayByteStream(in));
	}

	/**
	 * Instantiates a new little endian accessor.
	 *
	 * @param bs the bs
	 */
	public CoffeeAccessor(ByteArrayByteStream bs) {
		this.bs = bs;
	}

	/**
	 * Available.
	 *
	 * @return the long
	 */
	public final long available() {
		return bs.available();
	}

	/**
	 * Gets the bytes read.
	 *
	 * @return the bytes read
	 */
	public final long getBytesRead() {
		return bs.getBytesRead();
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public final long getPosition() {
		return bs.getPosition();
	}

	public final byte[] getRemainingBytes() {
		return bs.getRemainingBytes();
	}

	/**
	 * Read byte.
	 *
	 * @return the byte
	 */
	public final byte readByte() {
		return (byte) bs.readByte();
	}

	/**
	 * Read.
	 *
	 * @param num the num
	 * @return the byte[]
	 */
	public final byte[] readBytes(int num) {
		byte[] ret = new byte[num];
		for (int x = 0; x < num; x++) {
			ret[x] = readByte();
		}
		return ret;
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
		int byte1 = bs.readByte();
		int byte2 = bs.readByte();
		int byte3 = bs.readByte();
		int byte4 = bs.readByte();
		return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

	/**
	 * Read long.
	 *
	 * @return the long
	 */
	public final long readLong() {
		int byte1 = bs.readByte();
		int byte2 = bs.readByte();
		int byte3 = bs.readByte();
		int byte4 = bs.readByte();
		long byte5 = bs.readByte();
		long byte6 = bs.readByte();
		long byte7 = bs.readByte();
		long byte8 = bs.readByte();

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
		int byte1 = bs.readByte();
		int byte2 = bs.readByte();
		return (short) ((byte2 << 8) + byte1);
	}

	public final Number readSmart() {
		switch (readByte()) {
			case 0: {
				return readByte();
			}
			case 1: {
				return readShort();
			}
			case 2: {
				return readInt();
			}
			case 3: {
				return readLong();
			}
			case 4: {
				return readFloat();
			}
			case 5: {
				return readDouble();
			}
			default: {
				return Long.MAX_VALUE + Long.MAX_VALUE;
			}
		}
	}

	/**
	 * Read ascii string.
	 *
	 * @return the string
	 */
	public final String readString() {
		return new String(readBytes(readSmart().intValue()), Charset.forName("UTF-8"));
	}

	/**
	 * Seek.
	 *
	 * @param offset the offset
	 */
	public final void seek(long offset) {
		try {
			bs.seek(offset);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return bs.toString();
	}

	/**
	 * To string.
	 *
	 * @param b the b
	 * @return the string
	 */
	public final String toString(boolean b) {
		return bs.toString(b);
	}
}