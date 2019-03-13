package coffee.weneed.utils.coding.steps;

import java.util.Base64;

import coffee.weneed.utils.coding.CodingStep;

// TODO: Auto-generated Javadoc
/**
 * The Class Base64Step.
 */
public class Base64Step implements CodingStep {

	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.crypt.IDecodable#decode(byte[])
	 */
	@Override
	public byte[] decode(byte[] input) {
		return Base64.getDecoder().decode(input);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.crypt.IEncodable#encode(byte[])
	 */
	@Override
	public byte[] encode(byte[] input) {
		return Base64.getEncoder().encode(input);
	}

}
