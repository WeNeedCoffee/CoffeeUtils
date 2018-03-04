package coffee.weneed.utils.data.input;

import java.awt.Point;

/**
 * 
 * @author Dalethium
 *
 */
public abstract interface ILittleEndianAccessor {

	public abstract long available();

	public abstract long getBytesRead();

	public abstract byte[] read(int paramInt);

	public abstract String readAsciiString(int paramInt);

	public abstract byte readByte();

	public abstract int readByteAsInt();

	public abstract char readChar();

	public abstract String readDarkAsciiString();

	public abstract double readDouble();

	public abstract float readFloat();

	public abstract int readInt();

	public abstract long readLong();

	public abstract Point readPos();

	public abstract short readShort();

	public abstract void skip(int paramInt);

	public abstract String toString(boolean paramBoolean);
}