package coffee.weneed.utils.coding.steps.compressing;

import coffee.weneed.utils.CompressionUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class BZip2Step.
 */
public class BZip2Step implements ICodingStep {

	/**
	 * Decode.
	 *
	 * @param input the input
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	@Override
	public byte[] decode(byte[] input) throws Exception {
		return CompressionUtil.unbzip2(input);
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	@Override
	public byte[] encode(byte[] input) throws Exception {
		return CompressionUtil.bzip2(input);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 0;
	}

}
