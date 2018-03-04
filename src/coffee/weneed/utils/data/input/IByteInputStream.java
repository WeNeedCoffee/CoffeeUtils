package coffee.weneed.utils.data.input;

/**
 * 
 * @author Dalethium
 *
 */
public abstract interface IByteInputStream {

	public abstract long available();

	public abstract long getBytesRead();

	public abstract int readByte();

	public abstract String toString(boolean paramBoolean);
}