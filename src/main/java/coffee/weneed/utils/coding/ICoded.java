package coffee.weneed.utils.coding;

import coffee.weneed.utils.dataholders.IByteArrayDataHolder;

public interface ICoded extends IByteArrayDataHolder {
	public CodingProcess getCodingProcess();
	
	public default byte[] encode() {
		return getCodingProcess().encode(toByteArray());
	}
	
	public default void decode(byte[] b) {
		fromByteArray(getCodingProcess().decode(b));
	}
	
}
