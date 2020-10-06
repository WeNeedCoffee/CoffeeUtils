package coffee.weneed.utils.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.util.SubnetUtils;
import coffee.weneed.utils.FileUtil;
import coffee.weneed.utils.toolkit.PrivateConstants;

public class IPSets {
	public static final String PREFIX = "      - ";
	public static final String MID = "." + PrivateConstants.BH +  ".\t1\tIN\tA\t";

	public static void main(String[] args) throws IOException {
		String baseip = "123.123.123.";
		int start = 0;
		int end = 10;
		int daemon = 111;
		int mask = 24;
		String ipset = baseip + "0/" + mask;

		List<String> one = new ArrayList<>();
		List<String> two = new ArrayList<>();
		List<String> three = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			one.add(daemon + "," + baseip + i);
			two.add(PREFIX + baseip + i + "/" + mask);
		}

		SubnetUtils utils = new SubnetUtils(ipset);
		utils.setInclusiveHostCount(false);

		for (String ip : utils.getInfo().getAllAddresses()) {
			three.add(ip + MID + ip);
		}

		FileUtil.listToFile("out1.txt", one);
		FileUtil.listToFile("out2.txt", two);
		FileUtil.listToFile("out3.txt", three);
	}
}
