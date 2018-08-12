package coffee.weneed.utils.crypt;

import java.util.ArrayList;
import java.util.List;

import coffee.weneed.utils.LogicUtil;

public class CryptProcess {
	private List<CryptStep> steps = new ArrayList<CryptStep>();
	private List<CryptStep> rsteps = new ArrayList<CryptStep>();
	public CryptProcess(CryptStep... s) {
		for (CryptStep e : s) steps.add(e);
		rsteps = LogicUtil.reverse(steps);
	}
	
	public byte[] encrypt(byte[] input) {
		byte[] h = input;
		for (CryptStep s : new ArrayList<>(steps)) h = s.encode(h);
		return h;
	}
	
	public byte[] decrypt(byte[] input) {
		byte[] h = input;
		for (CryptStep s : new ArrayList<>(rsteps)) h = s.encode(h);
		return h;
	}
	
}
