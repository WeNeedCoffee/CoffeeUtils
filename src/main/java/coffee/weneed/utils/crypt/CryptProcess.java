package coffee.weneed.utils.crypt;

import java.util.ArrayList;
import java.util.List;

import coffee.weneed.utils.LogicUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class CryptProcess.
 */
public class CryptProcess {

	/** The steps. */
	private List<CryptStep> steps = new ArrayList<>();

	/** The rsteps. */
	private List<CryptStep> rsteps = new ArrayList<>();

	/**
	 * Instantiates a new crypt process.
	 *
	 * @param s the s
	 */
	public CryptProcess(CryptStep... s) {
		for (CryptStep e : s) {
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
		for (CryptStep s : new ArrayList<>(rsteps)) {
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
		for (CryptStep s : new ArrayList<>(steps)) {
			h = s.encode(h);
		}
		return h;
	}

}
