package coffee.weneed.utils.coding.steps.serializing;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class BinStep.
 */
public class BinStep implements ICodingStep {

	/**
	 * Decode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	@Override
	public byte[] decode(byte[] input) {
		return StringUtil.binToBytes(new String(input));
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	@Override
	public byte[] encode(byte[] input) {
		return StringUtil.bytesToBin(input).getBytes();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 5;
	}

}
