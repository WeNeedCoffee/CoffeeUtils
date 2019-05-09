package coffee.weneed.utils.coding.steps;

import coffee.weneed.utils.LogicUtil;
import coffee.weneed.utils.coding.ICodingStep;

public class CompressionStep implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) {
		return LogicUtil.decompress(input);
	}

	@Override
	public byte[] encode(byte[] input) {
		return LogicUtil.compress(input);
	}

}
