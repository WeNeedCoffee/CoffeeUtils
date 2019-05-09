package coffee.weneed.utils.coding.steps;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.ICodingStep;

public class HexStep implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) {
		return StringUtil.hexToBytes(new String(input));
	}

	@Override
	public byte[] encode(byte[] input) {
		return StringUtil.bytesToHex(input).getBytes();
	}

}
