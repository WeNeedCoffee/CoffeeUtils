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
	 */
	public default void decode(byte[] b) {
		fromByteArray(getCodingProcess().decode(b));
	}

	/**
	 * Encode.
	 *
	 * @return the byte[]
	 */
	public default byte[] encode() {
		return getCodingProcess().encode(toByteArray());
	}

	/**
	 * Gets the coding process.
	 *
	 * @return the coding process
	 */
	public CodingProcess getCodingProcess();

}
