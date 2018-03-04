package coffee.weneed.utils.data.output;

import java.awt.Point;
import java.nio.charset.Charset;

/**
 * 
 * @author Dalethium
 *
 */
public class GenericLittleEndianWriter implements ILittleEndianWriter {

	private static final Charset ASCII = Charset.forName("US-ASCII");

	private IByteOutputStream bos;

	protected GenericLittleEndianWriter() {
	}

	public GenericLittleEndianWriter(IByteOutputStream bos) {
		this.bos = bos;
	}

	protected void setByteOutputStream(IByteOutputStream bos) {
		this.bos = bos;
	}

	@Override
	public final void write(byte b) {
		this.bos.writeByte(b);
	}

	@Override
	public final void write(byte[] b) {
		for (int x = 0; x < b.length; x++) {
			this.bos.writeByte(b[x]);
		}
	}

	@Override
	public final void write(int b) {
		this.bos.writeByte((byte) b);
	}

	@Override
	public final void writeAsciiString(String s) {
		write(s.getBytes(ASCII));
	}

	@Override
	public final void writeAsciiString(String s, int max) {
		if (s.length() > max) {
			s = s.substring(0, max);
		}
		write(s.getBytes(ASCII));
		for (int i = s.length(); i < max; i++) {
			write(0);
		}
	}

	@Override
	public void writeDarkAsciiString(String s) {
		writeShort((short) s.length());
		writeAsciiString(s);
	}

	@Override
	public final void writeInt(int i) {
		this.bos.writeByte((byte) (i & 0xFF));
		this.bos.writeByte((byte) (i >>> 8 & 0xFF));
		this.bos.writeByte((byte) (i >>> 16 & 0xFF));
		this.bos.writeByte((byte) (i >>> 24 & 0xFF));
	}

	@Override
	public final void writeLong(long l) {
		this.bos.writeByte((byte) (int) (l & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 8 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 16 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 24 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 32 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 40 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 48 & 0xFF));
		this.bos.writeByte((byte) (int) (l >>> 56 & 0xFF));
	}

	@Override
	public final void writePos(Point s) {
		writeShort(s.x);
		writeShort(s.y);
	}

	@Override
	public final void writeShort(int i) {
		this.bos.writeByte((byte) (i & 0xFF));
		this.bos.writeByte((byte) (i >>> 8 & 0xFF));
	}

	@Override
	public final void writeShort(short i) {
		this.bos.writeByte((byte) (i & 0xFF));
		this.bos.writeByte((byte) (i >>> 8 & 0xFF));
	}

	@Override
	public final void writeZeroBytes(int i) {
		for (int x = 0; x < i; x++) {
			this.bos.writeByte((byte) 0);
		}
	}
}