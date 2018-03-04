package coffee.weneed.utils.data.output;

import java.io.ByteArrayOutputStream;

/**
 * 
 * @author Dalethium
 *
 */
public class BAOSByteOutputStream implements IByteOutputStream {

	private ByteArrayOutputStream baos;

	public BAOSByteOutputStream(ByteArrayOutputStream baos) {
		this.baos = baos;
	}

	@Override
	public void writeByte(byte b) {
		this.baos.write(b);
	}
}