package coffee.weneed.utils.coding.steps;

public class FunkeyStep extends Base64Step {

	private char[] code;

	private char[][] cols;

	public FunkeyStep(char[] code, char[][] cols) {
		int len = code.length;
		for (char[] col : cols) {
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
		char[] arr = new String(super.decode(in)).toCharArray();
		char[] ret = new char[arr.length];
		int pos = 0;
		for (char character : arr) {
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

		return super.decode(new String(ret).getBytes());

	}

	@Override
	public byte[] encode(byte[] in) {
		int col = 0;
		char[] arr = new String(super.encode(in)).toCharArray();
		char[] ret = new char[arr.length];
		int pos = 0;
		for (char character : arr) {
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
		return super.encode(new String(ret).getBytes());
	}
}
