package coffee.weneed.utils.coding.steps.serializing;

import java.util.Base64;
import coffee.weneed.utils.coding.steps.ICodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class Base64Step.
 */
public class Base64Step implements ICodingStep {

	/**
	 * Decode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.crypt.IDecodable#decode(byte[])
	 */
	@Override
	public byte[] decode(byte[] input) {
		return Base64.getDecoder().decode(input);
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.crypt.IEncodable#encode(byte[])
	 */
	@Override
	public byte[] encode(byte[] input) {
		return Base64.getEncoder().encode(input);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getID() {
		return 4;
	}

}
