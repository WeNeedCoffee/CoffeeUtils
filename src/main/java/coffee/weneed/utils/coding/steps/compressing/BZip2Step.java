package coffee.weneed.utils.coding.steps.compressing;

import coffee.weneed.utils.CompressionUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

public class BZip2Step implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) throws Exception {
		return CompressionUtil.unbzip2(input);
	}

	@Override
	public byte[] encode(byte[] input) throws Exception {
		return CompressionUtil.bzip2(input);
	}

	@Override
	public int getID() {
		return 0;
	}

}
