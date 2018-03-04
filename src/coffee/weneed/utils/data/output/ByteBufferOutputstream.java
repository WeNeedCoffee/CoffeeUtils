package coffee.weneed.utils.data.output;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 
 * @author Dalethium
 *
 */
public class ByteBufferOutputstream implements IByteOutputStream {

	private IoBuffer bb;

	public ByteBufferOutputstream(IoBuffer bb) {
		this.bb = bb;
	}

	@Override
	public void writeByte(byte b) {
		this.bb.put(b);
	}
}