package coffee.weneed.utils.coding;

import java.util.ArrayList;
import java.util.List;
import coffee.weneed.utils.ArrayUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class CryptProcess.
 */
public class CodingProcess {

	/** The steps. */
	private List<ICodingStep> steps = new ArrayList<>();

	/** The rsteps. */
	private List<ICodingStep> rsteps = new ArrayList<>();

	/**
	 * Instantiates a new crypt process.
	 *
	 * @param s the s
	 */
	public CodingProcess(ICodingStep... s) {
		for (ICodingStep e : s) {
			steps.add(e);
		}
		rsteps = ArrayUtil.reverse(steps);
	}

	/**
	 * Decrypt.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	public byte[] decode(byte[] input) {
		byte[] h = input;
		for (ICodingStep s : new ArrayList<>(rsteps)) {
			h = s.decode(h);
		}
		return h;
	}

	/**
	 * Encrypt.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	public byte[] encode(byte[] input) {
		byte[] h = input;
		for (ICodingStep s : new ArrayList<>(steps)) {
			h = s.encode(h);
		}
		return h;
	}

}
