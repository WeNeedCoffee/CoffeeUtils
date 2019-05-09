package coffee.weneed.utils.tests;

import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.coding.CodingProcess;
import coffee.weneed.utils.coding.steps.Base64Step;
import coffee.weneed.utils.coding.steps.CompressionStep;
import coffee.weneed.utils.coding.steps.HexStep;
import coffee.weneed.utils.storage.CoffeeHousingObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingTest.
 */
public class CoffeeHousingTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		CoffeeHousingObject p = new CoffeeHousingObject();
		p.setNumber("1", 1L);
		p.setString("2", "1L");
		p.setNumber("3", (byte) 0x01);
		p.setByteArray("4", "oof.ca".getBytes());
		CoffeeHousingObject p1 = new CoffeeHousingObject();
		p1.setNumber("1", 1.006d);
		p1.setString("2", "1L");
		p1.setNumber("3", (byte) 0x01);
		CoffeeHousingObject p2 = new CoffeeHousingObject();
		p2.setNumber("1", 1.04f);
		p2.setString("2", "1L");
		p2.setNumber("3", (byte) 0x01);
		CoffeeHousingObject p3 = new CoffeeHousingObject();
		p3.setNumber("1", 1f);
		p3.setString("2", "1L");
		p3.setNumber("3", (byte) 0x01);
		p1.setChild("p2", p2);
		p.setChild("p1", p1);
		p.setChild("p3", p3);
		byte[] pb = p.toByteArray();
		System.out.println(StringUtil.bytesToHex(pb));
		System.out.println(p.toJSON().toString());
		p = new CoffeeHousingObject();
		p.fromByteArray(pb);
		pb = p.toByteArray();
		System.out.println(StringUtil.bytesToHex(pb));
		System.out.println(p.toJSON().toString());

		CodingProcess proc = new CodingProcess(new CompressionStep(), new Base64Step(), new HexStep());
		pb = proc.encode(pb);
		System.out.println(new String(pb));
		pb = proc.decode(pb);
		System.out.println(StringUtil.bytesToHex(pb));
		p = new CoffeeHousingObject();
		p.fromByteArray(pb);
		System.out.println(p.toJSON().toString());
	}
}
