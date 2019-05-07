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
		writeInt(bs.length);
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