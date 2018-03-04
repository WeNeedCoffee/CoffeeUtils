package coffee.weneed.utils.data.output;

import java.io.ByteArrayOutputStream;

import coffee.weneed.utils.HexTool;

/**
 * 
 * @author Dalethium
 *
 */
public class DarkPacketLittleEndianWriter extends GenericLittleEndianWriter {

	private final ByteArrayOutputStream baos;

	public DarkPacketLittleEndianWriter() {
		this(32);
	}

	public DarkPacketLittleEndianWriter(int size) {
		this.baos = new ByteArrayOutputStream(size);
		setByteOutputStream(new BAOSByteOutputStream(this.baos));
	}

	public final byte[] getPacket() {
		return this.baos.toByteArray();
	}

	@Override
	public final String toString() {
		return HexTool.toHumanReadableString(this.baos.toByteArray());
	}
}