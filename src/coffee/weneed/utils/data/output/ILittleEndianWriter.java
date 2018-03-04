package coffee.weneed.utils.data.output;

import java.awt.Point;

/**
 * 
 * @author Dalethium
 *
 */
public abstract interface ILittleEndianWriter {

	public abstract void write(byte paramByte);

	public abstract void write(byte[] paramArrayOfByte);

	public abstract void write(int paramInt);

	public abstract void writeAsciiString(String paramString);

	public abstract void writeAsciiString(String paramString, int paramInt);

	public abstract void writeDarkAsciiString(String paramString);

	public abstract void writeInt(int paramInt);

	public abstract void writeLong(long paramLong);

	public abstract void writePos(Point paramPoint);

	public abstract void writeShort(int paramInt);

	public abstract void writeShort(short paramShort);

	public abstract void writeZeroBytes(int paramInt);
}