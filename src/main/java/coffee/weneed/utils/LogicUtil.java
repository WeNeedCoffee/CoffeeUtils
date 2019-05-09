package coffee.weneed.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLConnection;
import java.util.Arrays;
import coffee.weneed.utils.io.ByteArrayByteStream;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	public static byte[] compress(byte[] data) {
		CoffeeWriter cw = new CoffeeWriter();
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4Compressor compressor = factory.fastCompressor();
		int maxCompressedLength = compressor.maxCompressedLength(data.length);
		byte[] compressed = new byte[maxCompressedLength];
		int compressedLength = compressor.compress(data, 0, data.length, compressed, 0, maxCompressedLength);
		compressed = Arrays.copyOf(compressed, compressedLength);
		cw.writeSmart(compressed.length);
		cw.writeSmart(data.length);
		cw.write(compressed);
		return cw.getByteArray();
	}

	public static byte[] decompress(byte[] in) {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4FastDecompressor decompressor = factory.fastDecompressor();
		int compressedLength = ca.readSmart().intValue();
		int decompressedLength = ca.readSmart().intValue();
		byte[] restored = new byte[decompressedLength];
		decompressor.decompress(ca.readBytes(compressedLength), 0, restored, 0, decompressedLength);
		return restored;
	}

	/**
	 * https://kalliphant.com/javamimetype_from_bytearr/
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String getMimeType(byte data[]) throws Exception {
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		return mimeType;
	}

	/**
	 * https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
	 *
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}

	/**
	 * http://stackoverflow.com/questions/31582524/how-to-check-multiple-objects-for-nullity
	 *
	 * @author StackOverflow:kamwo
	 * @param objects the objects
	 * @return true if any object in the arguments is null
	 */
	public static Boolean isAnyObjectNull(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
