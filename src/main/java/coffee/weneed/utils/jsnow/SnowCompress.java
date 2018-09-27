package coffee.weneed.utils.jsnow;
/*
 * Compression routines for SNOW.
 * Uses simple Huffman coding.
 *
 * Written by Matthew Kwan - April 1997
 */

class SnowCompress extends BitFilter {

	private boolean encode_flag = false;

	private boolean quiet_flag = false;

	private BitFilter next_filter = null;

	private int bit_count;

	private int value;

	private int bits_in;

	private int bits_out;

	private boolean bit_array[];

	// Huffman codes. #include where are you?
	private boolean huffcodes[][] = {
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, true, true, true,
					true },
			{ true, false, true, true, false, false, false, true, false, true, false, true }, { false, true, false, false, true, false, false },
			{ true, false, true, true, false, true }, { false, true, false, false, true, true, true, false, true, true, true, false, false, true,
					true, false, true, false, false, false, false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, true, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, false, true,
					false },
			{ true, true, true }, { false, true, false, false, true, false, true, false, false, false },
			{ true, false, true, true, false, false, true, false, false }, { true, false, true, true, true, true, true, true, true, true, true },
			{ true, false, true, true, true, true, false, true, false, false, true, false },
			{ true, false, true, true, false, false, false, true, false, true, false, false, false },
			{ false, false, true, false, true, false, false, false, true, false, true, false, true },
			{ false, false, true, false, true, false, true, true }, { true, false, true, true, true, true, true, true, false },
			{ false, false, true, false, false, false, true, true }, { false, true, false, false, true, false, true, false, true },
			{ true, false, true, true, true, true, false, true, false, false, true, true }, { true, false, true, false, true, true, false },
			{ true, false, true, true, true, true, true, false }, { true, false, true, false, false, false },
			{ true, false, true, true, true, true, false, false, true }, { false, false, true, false, false, false, false },
			{ false, true, false, false, true, false, true, true }, { true, false, true, true, false, false, true, false, true },
			{ false, false, true, false, true, false, true, false, true }, { false, false, true, false, true, false, false, true, true },
			{ true, false, true, true, true, true, false, true, true, true },
			{ true, false, true, true, false, false, true, true, false, false },
			{ false, true, false, false, true, false, true, false, false, true },
			{ true, false, true, false, false, true, true, false, false, true }, { false, false, true, false, true, false, false, false, false },
			{ true, false, true, true, true, true, false, false, false }, { true, false, true, true, true, true, true, true, true, true, false },
			{ false, true, false, false, true, true, true, false, true, true, false },
			{ true, false, true, false, false, true, false, true, false, true, false },
			{ true, false, true, true, true, true, false, true, false, false, false },
			{ true, false, true, false, false, true, false, true, false, false },
			{ false, false, true, false, true, false, false, false, true, true }, { false, true, false, false, true, true, true, true },
			{ true, false, true, true, true, true, false, true, true, false }, { true, false, true, true, false, false, false, true, true },
			{ true, false, true, false, false, true, true, false, true }, { false, false, true, false, false, false, true, false },
			{ false, false, true, false, true, false, false, true, false },
			{ true, false, true, true, false, false, false, false, false, false },
			{ true, false, true, true, false, false, true, true, false, true }, { false, true, true, true, false, false, false },
			{ true, false, true, true, false, false, false, false, false, true, true },
			{ true, false, true, true, false, false, false, true, false, true, true },
			{ false, false, true, false, true, false, true, false, false }, { true, false, true, true, false, false, true, true, true },
			{ true, false, true, false, false, true, false, true, true }, { true, false, true, true, false, false, false, false, true },
			{ false, true, false, false, true, true, true, false, false },
			{ true, false, true, false, false, true, true, true, true, false, false, false, true },
			{ true, false, true, false, false, true, true, true, false }, { true, false, true, false, false, true, false, false },
			{ true, false, true, false, true, true, true, false }, { true, false, true, true, true, true, false, true, false, true },
			{ true, false, true, false, false, true, true, true, true, false, true },
			{ true, false, true, true, false, false, false, true, false, false },
			{ true, false, true, true, false, false, false, false, false, true, false },
			{ false, true, false, false, true, true, true, false, true, false },
			{ false, true, false, false, true, true, true, false, true, true, true, true },
			{ true, false, true, false, false, true, true, true, true, false, false, true },
			{ false, false, true, false, true, false, false, false, true, false, true, true },
			{ true, false, true, false, false, true, false, true, false, true, true, true },
			{ true, false, true, true, false, false, false, true, false, true, false, false, true },
			{ true, false, true, true, true, true, true, true, true, false, false },
			{ false, false, true, false, true, false, false, false, true, false, false }, { false, true, false, true },
			{ false, false, true, false, false, true }, { true, true, false, true, true, false }, { false, true, false, false, false },
			{ true, true, false, false }, { true, false, true, false, true, false }, { false, true, true, true, false, true },
			{ true, false, false, false, true }, { false, false, true, true }, { true, false, true, false, false, true, true, true, true, true },
			{ false, true, false, false, true, true, false }, { false, true, true, true, true }, { true, false, true, true, true, false },
			{ false, false, false, true }, { false, true, true, false }, { true, false, false, false, false, true },
			{ true, false, true, true, true, true, true, true, true, false, true }, { true, true, false, true, false },
			{ false, false, false, false }, { true, false, false, true }, { true, true, false, true, true, true },
			{ false, true, true, true, false, false, true }, { false, false, true, false, true, true },
			{ true, false, true, false, true, true, true, true }, { true, false, false, false, false, false },
			{ true, false, true, false, false, true, true, false, false, false },
			{ true, false, true, false, false, true, true, true, true, false, false, false, false },
			{ true, false, true, false, false, true, false, true, false, true, true, false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, true },
			{ false, false, true, false, true, false, false, false, true, false, true, false, false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, false, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, false, true, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, false, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, false, false, true, true, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, false, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, false, true, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, false, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, false, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, false, true, true, true, true, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, false, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, false, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, false, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, false, true,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, true, false,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, true, false,
					true },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, true, true,
					false },
			{ false, true, false, false, true, true, true, false, true, true, true, false, false, true, true, false, false, false, true, true,
					true } };

	SnowCompress(boolean encode, boolean quiet, BitFilter output) {
		encode_flag = encode;
		quiet_flag = quiet;
		next_filter = output;

		bit_count = 0;
		value = 0;
		bits_in = 0;
		bits_out = 0;

		if (!encode_flag) {
			bit_array = new boolean[256];
		}
	}

	@Override
	public boolean flush() {
		if (encode_flag) {
			if (bit_count != 0 && !quiet_flag) {
				System.err.println("Warning: residual of " + bit_count + " bits not compressed.");
			}

			if (bits_out > 0 && !quiet_flag) {
				double cpc = (double) (bits_in - bits_out) / (double) bits_in * 100.0;

				cpc = Math.rint(cpc * 100.0) / 100.0; // Rounding
				if (cpc < 0.0) {
					System.err.println("Compression enlarged data by " + -cpc + "% recommend not using compression.");
				} else {
					System.err.println("Compressed by " + cpc + "%.");
				}
			}
		} else {
			if (bit_count > 2 && !quiet_flag) {
				System.err.println("Warning: residual of " + bit_count + " bits not uncompressed.");
			}
		}

		return next_filter.flush();
	}

	// Find a huffman code that matches the array of bits.
	// If no match, return -1
	private int huffcode_find(boolean bits[], int len) {
		for (int i = 0; i < 256; i++) {
			boolean found = true;

			if (huffcodes[i].length != len) {
				continue;
			}

			for (int j = 0; j < len; j++) {
				if (bits[j] != huffcodes[i][j]) {
					found = false;
					break;
				}
			}

			if (found) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean receive_bit(boolean bit) {
		if (encode_flag) {
			bits_in++;
			value = value << 1 | (bit ? 1 : 0);

			if (++bit_count == 8) {
				boolean s[] = huffcodes[value];

				for (boolean element : s) {
					if (!next_filter.receive_bit(element)) {
						return false;
					}
					bits_out++;
				}

				value = 0;
				bit_count = 0;
			}
		} else {
			int code;

			bit_array[bit_count++] = bit;

			if ((code = huffcode_find(bit_array, bit_count)) >= 0) {
				for (int i = 0; i < 8; i++) {
					boolean b = (code & 128 >> i) != 0;

					if (!next_filter.receive_bit(b)) {
						return true;
					}
				}

				bit_count = 0;
			}

			if (bit_count >= 255) {
				System.err.println("Error: Huffman uncompress buffer overflow.");
				return false;
			}
		}

		return true;
	}
}
