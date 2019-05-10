package coffee.weneed.utils.coding.steps.serializing;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

public class BinStep implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) {
		return StringUtil.binToBytes(new String(input));
	}

	@Override
	public byte[] encode(byte[] input) {
		return StringUtil.bytesToBin(input).getBytes();
	}

	@Override
	public int getID() {
		return 5;
	}

}
