package coffee.weneed.utils.coding.steps.cyphering;

import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class FunkeyStep.
 */
public final class FunkeyStep implements ICodingStep {

	/** The code. */
	private byte[] code;

	/** The cols. */
	private byte[][] cols;

	/**
	 * Instantiates a new funkey step.
	 *
	 * @param code the code
	 * @param cols the cols
	 * @throws IllegalArgumentException the illegal argument exception
	 */
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

	/**
	 * Decode.
	 *
	 * @param in the in
	 * @return the byte[]
	 */
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

	/**
	 * Encode.
	 *
	 * @param in the in
	 * @return the byte[]
	 */
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 3;
	}
}
