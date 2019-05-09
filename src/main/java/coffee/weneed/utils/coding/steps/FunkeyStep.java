package coffee.weneed.utils.coding.steps;

import coffee.weneed.utils.coding.ICodingStep;

public class FunkeyStep implements ICodingStep {

	private byte[] code;

	private byte[][] cols;

	// TODO broken
	private FunkeyStep(byte[] code, byte[][] cols) throws IllegalArgumentException {
		int len = code.length;
		for (byte[] col : cols) {
			if (col.length != len) {
				throw new IllegalArgumentException("All character array lengths must be the same.");
			}
		}
		this.code = code;
		this.cols = cols;
	}

	@Override
	public byte[] decode(byte[] in) {
		int col = 0;
		byte[] ret = new byte[in.length];
		int pos = 0;
		for (byte character : in) {
			for (int i = 0; i < 256; i++) {
				if (cols[col][i] == character) {
					ret[pos++] = code[i];
					break;
				}
			}
			col++;
			if (col >= cols.length) {
				col = 0;
			}
		}

		return ret;

	}

	@Override
	public byte[] encode(byte[] in) {
		int col = 0;
		byte[] ret = new byte[in.length];
		int pos = 0;
		for (byte character : in) {
			for (int i = 0; i < 256; i++) {
				if (code[i] == character) {
					ret[pos++] = cols[col][i];
					break;
				}
			}
			col++;
			if (col >= cols.length) {
				col = 0;
			}
		}
		return ret;
	}
}
