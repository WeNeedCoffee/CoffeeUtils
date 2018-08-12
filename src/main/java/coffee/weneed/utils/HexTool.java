package coffee.weneed.utils;

import java.io.ByteArrayOutputStream;
// TODO: Auto-generated Javadoc

/**
 * The Class HexTool.
 *
 * @author Dalethium
 */
// TODO source and clean up
public class HexTool {

	/** The Constant HEX. */
	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Gets the byte array from hex string.
	 *
	 * @param hex the hex
	 * @return the byte array from hex string
	 */
	public static byte[] getByteArrayFromHexString(String hex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nexti = 0;
		int nextb = 0;
		boolean highoc = true;
		outer: while (true) {
			int number = -1;
			while (number == -1) {
				if (nexti == hex.length()) {
					break outer;
				}
				char chr = hex.charAt(nexti);
				if ((chr >= '0') && (chr <= '9')) {
					number = chr - '0';
				} else if ((chr >= 'a') && (chr <= 'f')) {
					number = chr - 'a' + 10;
				} else if ((chr >= 'A') && (chr <= 'F')) {
					number = chr - 'A' + 10;
				} else {
					number = -1;
				}
				nexti++;
			}
			if (highoc) {
				nextb = number << 4;
				highoc = false;
			} else {
				nextb |= number;
				highoc = true;
				baos.write(nextb);
			}
		}
		return baos.toByteArray();
	}

	/**
	 * Converts raw hex string to bytes.
	 *
	 * @param hexString the hex string
	 * @return bytes
	 * @see getHexFromBytes
	 */
	public static byte[] getBytesFromHex(String hexString) {
		if (hexString.length() % 2 == 1) throw new IllegalArgumentException("Invalid length");
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++) {
			int index = i * 2;
			bytes[i] = (byte) Integer.parseInt(hexString.substring(index, index + 2), 16);
		}
		return bytes;
	}

	/**
	 * Converts bytes to raw hex string.
	 *
	 * @param digest the digest
	 * @return Hex string
	 * @see getBytesFromHex
	 */
	public static String getHexFromBytes(byte[] digest) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			hexString.append(Integer.toHexString(0xFF & digest[i]));
		}
		return hexString.toString();
	}

	/**
	 * Gets the opcode to string.
	 *
	 * @param op the op
	 * @return the opcode to string
	 */
	public static String getOpcodeToString(int op) {
		return new StringBuilder().append("0x").append(StringUtil.getLeftPaddedStr(Integer.toHexString(op).toUpperCase(), '0', 4)).toString();
	}

	/**
	 * To human readable string.
	 *
	 * @param byteValue the byte value
	 * @return the string
	 */
	public static final String toHumanReadableString(byte byteValue) {
		int tmp = byteValue << 8;
		char[] retstr = { HEX[(tmp >> 12 & 0xF)], HEX[(tmp >> 8 & 0xF)] };
		return String.valueOf(retstr);
	}

	/**
	 * To human readable string.
	 *
	 * @param bytes the bytes
	 * @return the string
	 */
	public static final String toHumanReadableString(byte[] bytes) {
		StringBuilder hexed = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			hexed.append(toHumanReadableString(bytes[i]));
			hexed.append(' ');
		}
		return hexed.substring(0, hexed.length() - 1);
	}

	/**
	 * To human readable string.
	 *
	 * @param intValue the int value
	 * @return the string
	 */
	public static final String toHumanReadableString(int intValue) {
		return Integer.toHexString(intValue);
	}

	/**
	 * To human readable string from ascii.
	 *
	 * @param bytes the bytes
	 * @return the string
	 */
	public static final String toHumanReadableStringFromAscii(byte[] bytes) {
		char[] ret = new char[bytes.length];
		for (int x = 0; x < bytes.length; x++) {
			if ((bytes[x] < 32) && (bytes[x] >= 0)) {
				ret[x] = '.';
			} else {
				int chr = bytes[x] & 0xFF;
				ret[x] = ((char) chr);
			}
		}
		return String.valueOf(ret);
	}

	/**
	 * To human readable string padded string from ascii.
	 *
	 * @param bytes the bytes
	 * @return the string
	 */
	public static final String toHumanReadableStringPaddedStringFromAscii(byte[] bytes) {
		String str = toHumanReadableStringFromAscii(bytes);
		StringBuilder ret = new StringBuilder(str.length() * 3);
		for (int i = 0; i < str.length(); i++) {
			ret.append(str.charAt(i));
			ret.append("  ");
		}
		return ret.toString();
	}
}