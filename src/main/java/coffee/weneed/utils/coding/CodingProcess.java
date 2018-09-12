package coffee.weneed.utils.coding;

import java.util.ArrayList;
import java.util.List;

import coffee.weneed.utils.LogicUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class CryptProcess.
 */
public class CodingProcess {

	/** The steps. */
	private List<CodingStep> steps = new ArrayList<>();

	/** The rsteps. */
	private List<CodingStep> rsteps = new ArrayList<>();

	/**
	 * Instantiates a new crypt process.
	 *
	 * @param s the s
	 */
	public CodingProcess(CodingStep... s) {
		for (CodingStep e : s) {
			steps.add(e);
		}
		rsteps = LogicUtil.reverse(steps);
	}

	/**
	 * Decrypt.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	public byte[] decrypt(byte[] input) {
		byte[] h = input;
		for (CodingStep s : new ArrayList<>(rsteps)) {
			h = s.encode(h);
		}
		return h;
	}

	/**
	 * Encrypt.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	public byte[] encrypt(byte[] input) {
		byte[] h = input;
		for (CodingStep s : new ArrayList<>(steps)) {
			h = s.encode(h);
		}
		return h;
	}

}
