package coffee.weneed.utils.data.input;

import java.io.IOException;

/**
 * 
 * @author Dalethium
 *
 */

public class GenericSeekableLittleEndianAccessor extends GenericLittleEndianAccessor implements ISeekableLittleEndianAccessor {

	private final ISeekableInputStreamBytestream bs;

	public GenericSeekableLittleEndianAccessor(ISeekableInputStreamBytestream bs) {
		super(bs);
		this.bs = bs;
	}

	@Override
	public final long getPosition() {
		try {
			return this.bs.getPosition();
		} catch (IOException e) {
			System.err.println("getPosition failed" + e);
		}
		return -1L;
	}

	@Override
	public final void seek(long offset) {
		try {
			this.bs.seek(offset);
		} catch (IOException e) {
			System.err.println("Seek failed" + e);
		}
	}

	@Override
	public final void skip(int num) {
		seek(getPosition() + num);
	}
}