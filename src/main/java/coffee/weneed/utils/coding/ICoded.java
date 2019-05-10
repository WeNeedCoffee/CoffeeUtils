package coffee.weneed.utils.coding;

import coffee.weneed.utils.dataholders.IByteArrayDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICoded.
 */
public interface ICoded extends IByteArrayDataHolder {

	/**
	 * Decode.
	 *
	 * @param b the b
	 * @throws Exception
	 */
	default void decode(byte[] b) throws Exception {
		fromByteArray(getCodingProcess().decode(b));
	}

	/**
	 * Encode.
	 *
	 * @return the byte[]
	 * @throws Exception
	 */
	default byte[] encode() throws Exception {
		return getCodingProcess().encode(toByteArray());
	}

	/**
	 * Gets the coding process.
	 *
	 * @return the coding process
	 */
	CodingProcess getCodingProcess();

}
