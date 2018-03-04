package coffee.weneed.utils.data;

import java.awt.Point;
import java.io.IOException;

/**
 * 
 * @author Dalethium
 *
 */
public class LittleEndianAccessor {

	private final ByteArrayByteStream bs;

	private short opcode;

	public LittleEndianAccessor(ByteArrayByteStream bs) {
		this.bs = bs;
		this.setOpcode(readShort());
	}

	public LittleEndianAccessor(short opcode, ByteArrayByteStream bs) {
		this.bs = bs;
		this.setOpcode(opcode);
	}

	public final long available() {
		return this.bs.available();
	}

	public final long getBytesRead() {
		return this.bs.getBytesRead();
	}

	public short getOpcode() {
		return opcode;
	}

	public final long getPosition() {
		return this.bs.getPosition();
	}

	public final byte[] read(int num) {
		byte[] ret = new byte[num];
		for (int x = 0; x < num; x++) {
			ret[x] = readByte();
		}
		return ret;
	}

	public final String readAsciiString(int n) {
		char[] ret = new char[n];
		for (int x = 0; x < n; x++) {
			ret[x] = ((char) readByte());
		}
		return new String(ret);
	}

	public final byte readByte() {
		return (byte) this.bs.readByte();
	}

	public final char readChar() {
		return (char) readShort();
	}

	public final String readDarkAsciiString() {
		return readAsciiString(readShort());
	}

	public final double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	public final float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	public final int readInt() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		int byte3 = this.bs.readByte();
		int byte4 = this.bs.readByte();
		return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

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

	public final Point readPos() {
		int x = readShort();
		int y = readShort();
		return new Point(x, y);
	}

	public final short readShort() {
		int byte1 = this.bs.readByte();
		int byte2 = this.bs.readByte();
		return (short) ((byte2 << 8) + byte1);
	}

	public final int readUShort() {
		int quest = readShort();
		if (quest < 0) {
			quest += 65536;
		}
		return quest;
	}

	public final void seek(long offset) {
		try {
			this.bs.seek(offset);
		} catch (IOException e) {
			System.err.println("Seek failed" + e);
		}
	}

	public void setOpcode(short opcode) {
		this.opcode = opcode;
	}

	public final void skip(int num) {
		seek(getPosition() + num);
	}

	@Override
	public final String toString() {
		return this.bs.toString();
	}

	public final String toString(boolean b) {
		return this.bs.toString(b);
	}
}