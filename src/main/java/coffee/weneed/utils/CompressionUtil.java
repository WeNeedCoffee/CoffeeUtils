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
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class CompressionUtil {
	public static byte[] bzip2(byte[] uncompressed) throws IOException {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(uncompressed));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BZip2CompressorOutputStream bzout = new BZip2CompressorOutputStream(out);
		bzout.write(ca.getRemainingBytes());
		bzout.finish();
		CoffeeWriter cw = new CoffeeWriter();
		byte[] bo = out.toByteArray();
		cw = new CoffeeWriter();
		cw.writeSmart(uncompressed.length);
		cw.write(bo);
		bzout.close();
		return cw.getByteArray();
	}

	public static byte[] delz4(byte[] in) {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4FastDecompressor decompressor = factory.fastDecompressor();
		int decompressedLength = ca.readSmart().intValue();
		byte[] restored = new byte[decompressedLength];
		decompressor.decompress(ca.getRemainingBytes(), 0, restored, 0, decompressedLength);
		return restored;
	}

	/**
	 * Debzip2s the compressed array and places the result into the decompressed
	 * array.
	 *
	 * @param compressed The compressed array, <strong>without</strong> the
	 *                   header.
	 * @throws IOException If there is an error decompressing the array.
	 */
	public static byte[] dexzip(byte[] compressed) throws IOException {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(compressed));
		int len = ca.readSmart().intValue();
		SingleXZInputStream xzInputStream = new SingleXZInputStream(new ByteArrayInputStream(ca.getRemainingBytes()));
		byte[] buffer = new byte[len];
		xzInputStream.read(buffer, 0, len);
		xzInputStream.close();
		return buffer;
	}

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

	public static byte[] unbzip2(byte[] in) throws IOException {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		int len = ca.readSmart().intValue();
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(new ByteArrayInputStream(ca.getRemainingBytes()));
		byte[] buffer = new byte[len];
		bzIn.read(buffer, 0, len);
		bzIn.close();
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
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(uncompressed));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XZOutputStream xzOut = new XZOutputStream(out, new LZMA2Options());
		xzOut.write(ca.getRemainingBytes());
		xzOut.finish();
		CoffeeWriter cw = new CoffeeWriter();
		byte[] bo = out.toByteArray();
		cw = new CoffeeWriter();
		cw.writeSmart(uncompressed.length);
		cw.write(bo);
		xzOut.close();
		return cw.getByteArray();
	}
}
