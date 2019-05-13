package coffee.weneed.utils.coding.steps.compressing;

import java.io.IOException;
import coffee.weneed.utils.CompressionUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class XZipStep.
 */
public class XZipStep implements ICodingStep {

	/**
	 * Decode.
	 *
	 * @param input the input
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public byte[] decode(byte[] input) throws IOException {
		return CompressionUtil.unxzip(input);
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public byte[] encode(byte[] input) throws IOException {
		return CompressionUtil.xzip(input);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 2;
	}

}