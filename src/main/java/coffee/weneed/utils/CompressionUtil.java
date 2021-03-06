package coffee.weneed.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.SingleXZInputStream;
import org.tukaani.xz.XZOutputStream;
import coffee.weneed.utils.io.ByteArrayByteStream;
import coffee.weneed.utils.io.CoffeeReader;
import coffee.weneed.utils.io.CoffeeWriter;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

// TODO: Auto-generated Javadoc
/**
 * The Class CompressionUtil.
 */
public class CompressionUtil {

	/**
	 * Bzip 2.
	 *
	 * @param uncompressed the uncompressed
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] bzip2(byte[] uncompressed) throws IOException {
		CoffeeReader ca = new CoffeeReader(new ByteArrayByteStream(uncompressed));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BZip2CompressorOutputStream bzout = new BZip2CompressorOutputStream(out);
		bzout.write(ca.getRemainingBytes());
		bzout.finish();
		byte[] ret = writeCompressed(out.toByteArray(), uncompressed.length); //TODO should this be out.len or uncomp.len?
		bzout.close();
		return ret;
	}

	public static byte[] writeCompressed(byte[] bo, int len) {
		CoffeeWriter cw = new CoffeeWriter();
		cw = new CoffeeWriter();
		cw.writeSmart(len);
		cw.write(bo);
		return cw.getByteArray();
	}

	/**
	 * Delz 4.
	 *
	 * @param in the in
	 * @return the byte[]
	 */
	public static byte[] delz4(byte[] in) {
		CoffeeReader ca = new CoffeeReader(new ByteArrayByteStream(in));
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4FastDecompressor decompressor = factory.fastDecompressor();
		int decompressedLength = ca.readSmart().intValue();
		byte[] restored = new byte[decompressedLength];
		decompressor.decompress(ca.getRemainingBytes(), 0, restored, 0, decompressedLength);
		return restored;
	}

	/**
	 * Lz 4.
	 *
	 * @param data the data
	 * @return the byte[]
	 */
	public static byte[] lz4(byte[] data) {
		CoffeeWriter cw = new CoffeeWriter();
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4Compressor compressor = factory.fastCompressor();
		int maxCompressedLength = compressor.maxCompressedLength(data.length);
		byte[] compressed = new byte[maxCompressedLength];
		int compressedLength = compressor.compress(data, 0, data.length, compressed, 0, maxCompressedLength);
		compressed = Arrays.copyOf(compressed, compressedLength);
		cw.writeSmart(data.length);
		cw.write(compressed);
		return cw.getByteArray();
	}

	/**
	 * Unbzip 2.
	 *
	 * @param in the in
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] unbzip2(byte[] in) throws IOException {
		CoffeeReader ca = new CoffeeReader(new ByteArrayByteStream(in));
		int len = ca.readSmart().intValue();
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(new ByteArrayInputStream(ca.getRemainingBytes()));
		byte[] buffer = new byte[len];
		bzIn.read(buffer, 0, len);
		bzIn.close();
		return buffer;
	}

	/**
	 * Debzip2s the compressed array and places the result into the decompressed
	 * array.
	 *
	 * @param compressed The compressed array, <strong>without</strong> the
	 *                   header.
	 * @return the byte[]
	 * @throws IOException If there is an error decompressing the array.
	 */
	public static byte[] unxzip(byte[] compressed) throws IOException {
		CoffeeReader ca = new CoffeeReader(new ByteArrayByteStream(compressed));
		int len = ca.readSmart().intValue();
		SingleXZInputStream xzInputStream = new SingleXZInputStream(new ByteArrayInputStream(ca.getRemainingBytes()));
		byte[] buffer = new byte[len];
		xzInputStream.read(buffer, 0, len);
		xzInputStream.close();
		return buffer;
	}

	/**
	 * Bzip2s the specified array, removing the header.
	 *
	 * @param uncompressed The uncompressed array.
	 * @return The compressed array.
	 * @throws IOException If there is an error compressing the array.
	 */
	public static byte[] xzip(byte[] uncompressed) throws IOException {
		CoffeeReader ca = new CoffeeReader(new ByteArrayByteStream(uncompressed));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XZOutputStream xzOut = new XZOutputStream(out, new LZMA2Options());
		xzOut.write(ca.getRemainingBytes());
		xzOut.finish();
		byte[] ret = writeCompressed(out.toByteArray(), uncompressed.length); //TODO should this be out.len or uncomp.len?
		xzOut.close();
		return ret;
	}
}
