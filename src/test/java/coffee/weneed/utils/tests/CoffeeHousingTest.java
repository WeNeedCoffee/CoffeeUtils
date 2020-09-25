package coffee.weneed.utils.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import coffee.weneed.utils.NetUtil;
import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.TimeUtil;
import coffee.weneed.utils.coding.CodingProcess;
import coffee.weneed.utils.coding.steps.compressing.BZip2Step;
import coffee.weneed.utils.coding.steps.compressing.LZA4Step;
import coffee.weneed.utils.coding.steps.compressing.XZipStep;
import coffee.weneed.utils.storage.CoffeeHousingObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingTest.
 */
public class CoffeeHousingTest {

	/** The timing. */
	static Map<String, Long> timing = new HashMap<>();

	/** The json. */
	private static JSONObject json;

	/** The e. */
	private static CoffeeHousingObject e;

	/** The ee. */
	private static byte[] ee;

	/** The cbzip. */
	private static CodingProcess cbzip;

	/** The cxzip. */
	private static CodingProcess cxzip;

	/** The clz 4 a. */
	private static CodingProcess clz4a;

	/**
	 * Adds the timing.
	 *
	 * @param k the k
	 * @param v the v
	 */
	public static final void addTiming(String k, long v) {
		timing.put(k, v);
	}

	/**
	 * End.
	 */
	@AfterClass
	public static void end() {
		Comparator<Entry<String, Long>> valueComparator = new Comparator<Entry<String, Long>>() {
			@Override
			public int compare(Entry<String, Long> e1, Entry<String, Long> e2) {
				long v1 = e1.getValue();
				long v2 = e2.getValue();
				return Long.compare(v1, v2);
			}
		};
		List<Entry<String, Long>> listOfEntries = new ArrayList<>(timing.entrySet());
		Collections.sort(listOfEntries, valueComparator);

		for (Entry<String, Long> e : listOfEntries) {
			System.out.println(TimeUtil.getReadableMillisTiny(e.getValue()) + " on " + e.getKey());
		}

	}

	/**
	 * Setup.
	 */
	@BeforeClass
	public static void setup() {
		e = new CoffeeHousingObject();
		try {
			json = new JSONObject(NetUtil.downloadUrl(new File("CoffeeHousingTest.json").toURI().toURL()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.fromJSON(json);
		ee = e.toByteArray();
		cbzip = new CodingProcess(new BZip2Step());
		cxzip = new CodingProcess(new XZipStep());
		clz4a = new CodingProcess(new LZA4Step());
	}

	/** The stopwatch. */
	@Rule
	public Stopwatch stopwatch = new Stopwatch() {
		@Override
		protected void finished(long nanos, Description description) {
			addTiming(description.getMethodName(), nanos);
		}
	};

	String aa = "";
	public String aaaa() {
		if (StringUtil.isBlank(aa)) {
		String a = "";
		for (int i = 1; i<100 ; i++) {
			System.out.println(i);
			a+="aaaaaaaaaa";
		}
		aa = a;
		}
			return aa;
		
	}
	/**
	 * Generate coffee housing.
	 *
	 * @param amount the amount
	 * @param ra     the ra
	 * @param rb     the rb
	 * @param rc     the rc
	 * @return the coffee housing object
	 */
	public CoffeeHousingObject generateCoffeeHousing(int amount, int ra, int rb, int rc) {
		CoffeeHousingObject p = new CoffeeHousingObject();
		p.setNumber("1", 1L);
		p.setString("2", "test");
		p.setNumber("3", (byte) 0x01);
		p.setNumber("3", 1.0d);
		p.setByteArray("4", "test.test".getBytes());
		for (int i = 0; i < amount; i++) {
			
			CoffeeHousingObject p1 = new CoffeeHousingObject();
			p1.setNumber(RandomStringUtils.randomAlphabetic(ra), RandomUtils.nextDouble());
			p1.setString(RandomStringUtils.randomAlphabetic(ra), new String(RandomStringUtils.randomAlphabetic(rb)));
			p1.setNumber(RandomStringUtils.randomAlphabetic(ra), RandomUtils.nextInt());
			p1.setByteArray(RandomStringUtils.randomAlphabetic(ra), RandomUtils.nextBytes(rc));
			p.setChild(RandomStringUtils.randomAlphabetic(ra) + i, p1);
			/*
			CoffeeHousingObject p1 = new CoffeeHousingObject();
			p1.setNumber(aaaa(), RandomUtils.nextDouble());
			p1.setString(aaaa(), aaaa());
			p1.setNumber(aaaa(), RandomUtils.nextInt());
			p1.setByteArray(aaaa(), RandomUtils.nextBytes(rc));
			p.setChild(aaaa() + i, p1);*/
		}
		return p;
	}

	/**
	 * Generate random.
	 */
	@Test
	public void generateRandom() {
		generateCoffeeHousing(1024, 8, 16, 32);
	}

	/**
	 * Test bytes.
	 * @throws IOException 
	 */
	@Test
	public void testBytes() throws IOException {
		CoffeeHousingObject p = new CoffeeHousingObject();
		p.fromByteArray(ee);
	}

	/**
	 * Test BZIP.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testBZIP() throws Exception {
		cbzip.decode(cbzip.encode(ee));
	}

	/**
	 * Test JSON.
	 */
	@Test
	public void testJSON() {
		CoffeeHousingObject p = new CoffeeHousingObject();
		p.fromJSON(json);
	}

	/**
	 * Test LZ 4 A.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLZ4A() throws Exception {
		clz4a.decode(clz4a.encode(ee));
	}

	/**
	 * Test to bytes.
	 */
	@Test
	public void testToBytes() {
		e.toByteArray();
	}

	/**
	 * Test to JSON.
	 */
	@Test
	public void testToJSON() {
		e.toJSON();
	}

	/**
	 * Test XZIP.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testXZIP() throws Exception {
		cxzip.decode(cxzip.encode(ee));
	}

	/**
	 * Zips.
	 */
	@Test
	public void zips() {
		int rt = 0;
		int bt = 0;
		int xt = 0;
		int lt = 0;
		long rl = 0;
		long bl = 0;
		long xl = 0;
		long ll = 0;
		long rv = 0;
		long bv = 0;
		long xv = 0;
		long lv = 0;
		for (int n = 0; n < 16; n++) {
			rt++;
			long time = System.currentTimeMillis();
			CoffeeHousingObject p = generateCoffeeHousing(1024, 8, 16, 32);
			byte[] pb = p.toByteArray();
			rl += System.currentTimeMillis() - time;
			try {
				bt++;
				xt++;
				lt++;
				int r = pb.length;
				rv += r;
				time = System.currentTimeMillis();
				int b = new CodingProcess(new BZip2Step()).encode(pb).length;
				bl += System.currentTimeMillis() - time;
				bv += b;
				time = System.currentTimeMillis();
				int x = new CodingProcess(new XZipStep()).encode(pb).length;
				xl += System.currentTimeMillis() - time;
				xv += x;
				time = System.currentTimeMillis();
				int l = new CodingProcess(new LZA4Step()).encode(pb).length;
				ll += System.currentTimeMillis() - time;
				lv += l;
				//CoffeeHousingObject e = new CoffeeHousingObject();
				//e.fromByteArray(pb);
				//assertEquals(p, e);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.gc();
		}
		double bat = 1.0d * bl / bt;
		double xat = 1.0d * xl / xt;
		double lat = 1.0d * ll / lt;
		double rat = 1.0d * rl / rt;

		float ba = 1.0f * bv / bt;
		float xa = 1.0f * xv / xt;
		float la = 1.0f * lv / lt;
		float ra = 1.0f * rv / rt;
		System.out.println("Raw average: " + ra + " in " + rat + "ms");
		System.out.println("Bzip2 average: " + ba + " in " + bat + "ms");
		System.out.println("XZip average: " + xa + " in " + xat + "ms");
		System.out.println("LZA4 average: " + la + " in " + lat + "ms");
	}
}
