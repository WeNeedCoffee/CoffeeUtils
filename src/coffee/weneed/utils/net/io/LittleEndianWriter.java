package coffee.weneed.utils.net.io;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import coffee.weneed.utils.HexTool;

// TODO: Auto-generated Javadoc
/**
 * The Class LittleEndianWriter.
 *
 * @author Dalethium
 */
public class LittleEndianWriter {

	/** The Constant ASCII. */
	private static final Charset ASCII = Charset.forName("US-ASCII");

	/** The baos. */
	private ByteArrayOutputStream baos;

	/**
	 * Instantiates a new little endian packet writer.
	 */
	public LittleEndianWriter() {
		this(32);
	}

	/**
	 * Instantiates a new little endian packet writer.
	 *
	 * @param baos the baos
	 */
	public LittleEndianWriter(ByteArrayOutputStream baos) {
		setBaos(baos);
	}

	/**
	 * Instantiates a new little endian packet writer.
	 *
	 * @param size the size
	 */
	public LittleEndianWriter(int size) {
		new ByteArrayOutputStream(size);
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
	 * Gets the packet.
	 *
	 * @return the packet
	 */
	public final byte[] getPacket() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return HexTool.toHumanReadableString(getPacket());
	}

	/**
	 * Write.
	 *
	 * @param b the b
	 */
	public final void write(byte b) {
		this.getBaos().write(b);
	}

	/**
	 * Write.
	 *
	 * @param b the b
	 */
	public final void write(byte[] b) {
		for (int x = 0; x < b.length; x++) {
			write(b[x]);
		}
	}

	/**
	 * Write.
	 *
	 * @param b the b
	 */
	public final void write(int b) {
		write((byte) b);
	}

	/**
	 * Write ascii string.
	 *
	 * @param s the s
	 */
	public final void writeAsciiString(String s) {
		write(s.getBytes(ASCII));
	}

	/**
	 * Write ascii string.
	 *
	 * @param s the s
	 * @param max the max
	 */
	public final void writeAsciiString(String s, int max) {
		if (s.length() > max) {
			s = s.substring(0, max);
		}
		write(s.getBytes(ASCII));
		for (int i = s.length(); i < max; i++) {
			write(0);
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
	public void writeSignedAsciiString(String s) {
		writeShort((short) s.length());
		writeAsciiString(s);
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