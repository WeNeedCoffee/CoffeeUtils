package coffee.weneed.utils.toolkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.net.util.SubnetUtils;
import coffee.weneed.utils.FileUtil;

public class IPSets {
	public static final String PREFIX = "      - ";
	public static final String MID = "." + PrivateConstants.BH + ".\t1\tIN\tA\t";

	static int stage = 0;
	static boolean dns = false;
	static boolean init = false;

	public static void main(String[] args) throws IOException {

		String baseip = "123.123.123.";
		int start = 0;
		int end = 10;
		String daemon = "111";
		String mask = "24";
		System.out.println("dns? y/n");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			System.out.println("======>\"" + line + "\"");
			if (!init) {
				if (line.contains("y")) {
					dns = true;
				} else {
					dns = false;
				}
				System.out.println("Base ip? ex (including trailing dot): 123.123.123.");
				init = true;
				continue;
			}
			switch (stage) {
				case 0: {
					baseip = line.trim();
					System.out.println("mask? ex: 24");
					stage++;
					continue;
				}
				case 1: {
					mask = line.trim();
					if (dns) {
						break;
					} else {
						System.out.println("start ip? ex: 0");
						stage++;
						continue;
					}
				}
				case 2: {
					start = Integer.valueOf(line.trim());
					System.out.println("end ip? ex: 255");
					stage++;
					continue;
				}
				case 3: {
					end = Integer.valueOf(line.trim());
					System.out.println("Daemon? ex: 111");
					stage++;
					continue;
				}
				case 4: {
					daemon = line.trim();
					System.out.println("Hold on...");
					stage++;
					break;
				}
			}
			if ((dns && stage == 1) || stage == 5) {
				break;
			}
			if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
				break;
			}
		}
		sc.close();
		String ipset = baseip + "0/" + mask;
		if (dns) {
			List<String> three = new ArrayList<>();
			SubnetUtils utils = new SubnetUtils(ipset);
			utils.setInclusiveHostCount(false);

			for (String ip : utils.getInfo().getAllAddresses()) {
				three.add(ip + MID + ip);
			}

			FileUtil.listToFile("DNS.txt", three);
		} else {

			List<String> one = new ArrayList<>();
			List<String> two = new ArrayList<>();
			for (int i = start; i <= end; i++) {
				one.add(daemon + "," + baseip + i);
				two.add(PREFIX + baseip + i + "/" + mask);
			}

			FileUtil.listToFile("out1.txt", one);
			FileUtil.listToFile("out2.txt", two);
		}

	}
}
