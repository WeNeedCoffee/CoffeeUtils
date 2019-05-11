package coffee.weneed.utils.coding.steps.serializing;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class HexStep.
 */
public class HexStep implements ICodingStep {

	/**
	 * Decode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	@Override
	public byte[] decode(byte[] input) {
		return StringUtil.hexToBytes(new String(input));
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	@Override
	public byte[] encode(byte[] input) {
		return StringUtil.bytesToHex(input).getBytes();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 6;
	}

}
