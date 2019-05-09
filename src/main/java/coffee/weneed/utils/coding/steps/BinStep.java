package coffee.weneed.utils.coding.steps;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.ICodingStep;

public class BinStep implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) {
		return StringUtil.binToArr(new String(input));
	}

	@Override
	public byte[] encode(byte[] input) {
		return StringUtil.arrToBin(input).getBytes();
	}

}
