package coffee.weneed.utils.data.input;

import java.io.IOException;

/**
 * 
 * @author Dalethium
 *
 */
public abstract interface ISeekableInputStreamBytestream extends IByteInputStream {

	public abstract long getPosition() throws IOException;

	public abstract void seek(long paramLong) throws IOException;

	@Override
	public abstract String toString(boolean paramBoolean);
}