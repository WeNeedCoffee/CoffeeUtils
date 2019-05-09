package coffee.weneed.utils.io;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import coffee.weneed.utils.HexUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class LittleEndianWriter.
 *
 * @author Dalethium
 */
public class CoffeeWriter {

	/** The baos. */
	private ByteArrayOutputStream baos;

	/**
	 * Instantiates a new little endian writer.
	 */
	public CoffeeWriter() {
		this(32);
	}

	/**
	 * Instantiates a new little endian writer.
	 *
	 * @param baos the baos
	 */
	public CoffeeWriter(ByteArrayOutputStream baos) {
		setBaos(baos);
	}

	/**
	 * Instantiates a new little endian writer.
	 *
	 * @param size the size
	 */
	public CoffeeWriter(int size) {
		setBaos(new ByteArrayOutputStream(size));
	}

	/**
	 * Gets the baos.
	 *
	 * @return the baos
	 */
	public ByteArrayOutputStream getBaos() {
		return baos;
	}

	/**
	 * Gets the Byte Array.
	 *
	 * @return the Byte Array
	 */
	public final byte[] getByteArray() {
		return getBaos().toByteArray();
	}

	/**
	 * Sets the baos.
	 *
	 * @param baos the new baos
	 */
	public void setBaos(ByteArrayOutputStream baos) {
		this.baos = baos;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return HexUtil.bytesToHex(getByteArray());
	}

	/**
	 * Write.
	 *
	 * @param b the b
	 */
	public final void write(byte b) {
		getBaos().write(b);
	}

	/**
	 * Write.
	 *
	 * @param b the b
	 */
	public final void write(byte[] b) {
		for (byte element : b) {
			write(element);
		}
	}

	/**
	 * Write char.
	 *
	 * @param c the c
	 */
	public final void writeChar(char c) {
		writeShort((short) c);
	}

	/**
	 * Write float.
	 *
	 * @param d the d
	 */
	public final void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}

	/**
	 * Write float.
	 *
	 * @param f the f
	 */
	public final void writeFloat(float f) {
		writeInt(Float.floatToIntBits(f));
	}

	public final void writeSmart(Number n) {
		if (n.doubleValue() % 1 == 0) {
			if (n.longValue() >= Byte.MIN_VALUE && n.longValue() <= Byte.MAX_VALUE) {
				write((byte) 0);
				write(n.byteValue());
			} else if (n.longValue() >= Short.MIN_VALUE && n.longValue() <= Short.MAX_VALUE) {
				write((byte) 1);
				writeShort(n.shortValue());
			} else if (n.longValue() >= Integer.MIN_VALUE && n.longValue() <= Integer.MAX_VALUE) {
				write((byte) 2);
				writeInt(n.intValue());
			} else if (n.longValue() >= Long.MIN_VALUE && n.longValue() <= Long.MAX_VALUE) {
				write((byte) 3);
				writeLong(n.longValue());
			}
		} else if (n.doubleValue() >= Float.MIN_VALUE && n.doubleValue() <= Float.MAX_VALUE) {
			write((byte) 4);
			writeFloat(n.floatValue());
		} else if (n.doubleValue() >= Double.MIN_VALUE && n.doubleValue() <= Double.MAX_VALUE) {
			write((byte) 5);
			writeDouble(n.doubleValue());
		}
	}

	/**
	 * Write int.
	 *
	 * @param i the i
	 */
	public final void writeInt(int i) {
		write((byte) (i & 0xFF));
		write((byte) (i >>> 8 & 0xFF));
		write((byte) (i >>> 16 & 0xFF));
		write((byte) (i >>> 24 & 0xFF));
	}

	/**
	 * Write long.
	 *
	 * @param l the l
	 */
	public final void writeLong(long l) {
		write((byte) (int) (l & 0xFF));
		write((byte) (int) (l >>> 8 & 0xFF));
		write((byte) (int) (l >>> 16 & 0xFF));
		write((byte) (int) (l >>> 24 & 0xFF));
		write((byte) (int) (l >>> 32 & 0xFF));
		write((byte) (int) (l >>> 40 & 0xFF));
		write((byte) (int) (l >>> 48 & 0xFF));
		write((byte) (int) (l >>> 56 & 0xFF));
	}

	/**
	 * Write pos.
	 *
	 * @param s the s
	 */
	public final void writePos(Point s) {
		writeShort(s.x);
		writeShort(s.y);
	}

	/**
	 * Write rect.
	 *
	 * @param s the s
	 */
	public final void writeRect(Rectangle s) {
		writeInt(s.x);
		writeInt(s.y);
		writeInt(s.x + s.width);
		writeInt(s.y + s.height);
	}

	/**
	 * Write reversed long.
	 *
	 * @param l the l
	 */
	public final void writeReversedLong(long l) {
		write((byte) (int) (l >>> 32 & 0xFF));
		write((byte) (int) (l >>> 40 & 0xFF));
		write((byte) (int) (l >>> 48 & 0xFF));
		write((byte) (int) (l >>> 56 & 0xFF));
		write((byte) (int) (l & 0xFF));
		write((byte) (int) (l >>> 8 & 0xFF));
		write((byte) (int) (l >>> 16 & 0xFF));
		write((byte) (int) (l >>> 24 & 0xFF));
	}

	/**
	 * Write short.
	 *
	 * @param i the i
	 */
	public final void writeShort(int i) {
		write((byte) (i & 0xFF));
		write((byte) (i >>> 8 & 0xFF));
	}

	/**
	 * Write short.
	 *
	 * @param i the i
	 */
	public final void writeShort(short i) {
		write((byte) (i & 0xFF));
		write((byte) (i >>> 8 & 0xFF));
	}

	/**
	 * Write signed ascii string.
	 *
	 * @param s the s
	 */
	public void writeString(String s) {
		byte[] bs = s.getBytes(Charset.forName("UTF-8"));
		writeSmart(bs.length);
		write(bs);
	}

	/**
	 * Write zero bytes.
	 *
	 * @param i the i
	 */
	public final void writeZeroBytes(int i) {
		for (int x = 0; x < i; x++) {
			write((byte) 0);
		}
	}
}