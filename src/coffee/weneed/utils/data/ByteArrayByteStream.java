package coffee.weneed.utils.data;

import java.io.IOException;

import coffee.weneed.utils.HexTool;

/**
 * 
 * @author Dalethium
 *
 */
public class ByteArrayByteStream {

	private final byte[] arr;

	private long bytesRead = 0L;

	private int pos = 0;

	public ByteArrayByteStream(byte[] arr) {
		this.arr = arr;
	}

	public long available() {
		return this.arr.length - this.pos;
	}

	public long getBytesRead() {
		return this.bytesRead;
	}

	public long getPosition() {
		return this.pos;
	}

	public int readByte() {
		this.bytesRead += 1L;
		return this.arr[(this.pos++)] & 0xFF;
	}

	public void seek(long offset) throws IOException {
		this.pos = ((int) offset);
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean b) {
		String nows = "";
		if (this.arr.length - this.pos > 0) {
			byte[] now = new byte[this.arr.length - this.pos];
			System.arraycopy(this.arr, this.pos, now, 0, this.arr.length - this.pos);
			nows = HexTool.toHumanReadableString(now);
		}
		if (b) {
			return "All: " + HexTool.toHumanReadableString(this.arr) + "\nNow: " + nows;
		}
		return "Data: " + nows;
	}
}