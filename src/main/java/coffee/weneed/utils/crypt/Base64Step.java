package coffee.weneed.utils.crypt;

import java.util.Base64;

public class Base64Step implements CryptStep {

	@Override
	public byte[] encode(byte[] input) {
		return Base64.getEncoder().encode(input);
	}

	@Override
	public byte[] decode(byte[] input) {
		return Base64.getDecoder().decode(input);
	}

}
