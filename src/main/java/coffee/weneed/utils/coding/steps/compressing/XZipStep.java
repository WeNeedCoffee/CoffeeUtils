package coffee.weneed.utils.coding.steps.compressing;

import java.io.IOException;
import coffee.weneed.utils.CompressionUtil;
import coffee.weneed.utils.coding.steps.ICodingStep;

public class XZipStep implements ICodingStep {

	@Override
	public byte[] decode(byte[] input) throws IOException {
		return CompressionUtil.dexzip(input);
	}

	@Override
	public byte[] encode(byte[] input) throws IOException {
		return CompressionUtil.xzip(input);
	}

	@Override
	public int getID() {
		return 2;
	}

}