package coffee.weneed.utils.coding.steps.compressing;

import coffee.weneed.utils.CompressionUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

public class LZA4Step implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) throws Exception {
		return CompressionUtil.delz4(input);
	}

	@Override
	public byte[] encode(byte[] input) throws Exception {
		return CompressionUtil.lz4(input);
	}

	@Override
	public int getID() {
		return 1;
	}

}
