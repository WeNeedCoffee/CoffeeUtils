package coffee.weneed.utils.dataholders;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IByteArrayDataHolder.
 */
public interface IByteArrayDataHolder {

	/**
	 * From byte array.
	 *
	 * @param b the b
	 * @throws IOException 
	 */
	void fromByteArray(byte[] b) throws IOException;

	/**
	 * To byte array.
	 *
	 * @return the byte[]
	 */
	byte[] toByteArray();
}
