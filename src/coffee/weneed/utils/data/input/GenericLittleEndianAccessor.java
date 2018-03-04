package coffee.weneed.utils.data.input;

import java.awt.Point;

/**
 * 
 * @author Dalethium
 *
 */
public class GenericLittleEndianAccessor implements ILittleEndianAccessor {

	private final IByteInputStream bs;

	public GenericLittleEndianAccessor(IByteInputStream bs) {
		this.bs = bs;
	}

	@Override
	public final long available() {
		return this.bs.available();
	}

	@Override
	public final long getBytesRead() {
		return this.bs.getBytesRead();
	}

	@Override
	public final byte[] read(int num) {
		byte[] ret = new byte[num];
		for (int x = 0; x < num; x++) {
			ret[x] = readByte();
		}
		return ret;
	}

	@Override
	public final String readAsciiString(int n) {
		char[] ret = new char[n];
		for (int x = 0; x < n; x++) {
			ret[x] = ((char) readByte());
		}
		return new String(ret);
	}

	@Override
	public final byte readByte() {
		return (byte) this.bs.readByte();
	}

	@Override
	public final int readByteAsInt() {
		return this.bs.readByte();
	}

	@Override
	public final char readChar() {
		return (char) readShort();
	}

	@Override
	public final String readDarkAsciiString() {
		return readAsciiString(readShort());
	}

	@Override
	public final double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public final float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public final int readInt() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		int byte3 = this.bs.readByte();
		int byte4 = this.bs.readByte();
		return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

	@Override
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

	@Override
	public final Point readPos() {
		int x = readShort();
		int y = readShort();
		return new Point(x, y);
	}

	@Override
	public final short readShort() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		return (short) ((byte2 << 8) + byte1);
	}

	@Override
	public void skip(int num) {
		for (int x = 0; x < num; x++) {
			readByte();
		}
	}

	@Override
	public final String toString() {
		return this.bs.toString();
	}

	@Override
	public final String toString(boolean b) {
		return this.bs.toString(b);
	}
}