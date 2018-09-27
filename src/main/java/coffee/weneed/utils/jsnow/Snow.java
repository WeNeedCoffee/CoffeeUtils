package coffee.weneed.utils.jsnow;
/*
 * This class implements the main routine of SNOW.
 * SNOW is a command-line program for hiding and extracting messages
 * within the whitespace of text files.
 *
 * Usage: snow [-C][-Q][-S][-p passwd][-l line-len] [-f file | -m message]
 *
 *	-C : Use compression
 *	-Q : Be quiet
 *	-S : Calculate the space available in the file
 *	-l : Maximum line length allowable
 *	-p : Specify the password to encrypt the message
 *
 *	-f : Insert the message contained in the file
 *	-m : Insert the message given
 *
 * If the program is executed without either of the -f or -m options
 * then the program will attempt to extract a concealed message.
 * The output will go to outfile if specified, stdout otherwise.
 *
 * Written by Matthew Kwan - April 1997
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class Snow {

	private static BufferedReader stream_in;

	private static OutputStream stream_out;

	private static InputStream stream_message = null;

	private static boolean compress_flag = false;

	private static boolean quiet_flag = false;

	private static boolean space_flag = false;

	private static String passwd_string = null;

	private static int line_length = 80;

	// Entry point to the program.
	public static void main(String argv[]) {
		if (!Snow.parse_args(argv)) {
			System.err.print("Usage: Snow.class [-C][-Q][-S]");
			System.err.print("[-p passwd][-l line-len]");
			System.err.println(" [-f file][-m message]");
			System.err.println("\t\t\t\t\t[infile [outfile]]");
			System.exit(1);
		}

		if (Snow.space_flag) {
			SnowEncode se = new SnowEncode(true, Snow.quiet_flag, null, Snow.stream_in, null, Snow.line_length);

			se.space_calculate();
		} else if (Snow.stream_message != null) {
			PrintWriter pw = new PrintWriter(Snow.stream_out);
			BitFilter bf = new SnowEncode(true, Snow.quiet_flag, null, Snow.stream_in, pw, Snow.line_length);

			if (Snow.passwd_string != null) {
				bf = new SnowEncrypt(true, Snow.quiet_flag, bf, Snow.passwd_string);
			}
			if (Snow.compress_flag) {
				bf = new SnowCompress(true, Snow.quiet_flag, bf);
			}
			if (!Snow.message_encode(bf)) {
				System.exit(1);
			}

			pw.close();
		} else {
			BitFilter bf = new SnowOutput(Snow.quiet_flag, Snow.stream_out);
			SnowEncode se;

			if (Snow.compress_flag) {
				bf = new SnowCompress(false, Snow.quiet_flag, bf);
			}
			if (Snow.passwd_string != null) {
				bf = new SnowEncrypt(false, Snow.quiet_flag, bf, Snow.passwd_string);
			}

			se = new SnowEncode(false, Snow.quiet_flag, bf, Snow.stream_in, null, Snow.line_length);
			if (!se.decode()) {
				System.exit(1);
			}

			try {
				Snow.stream_out.close();
			} catch (IOException e) {
				System.err.println("Problem closing output file.");
				System.exit(1);
			}
		}

		try {
			Snow.stream_in.close();
		} catch (IOException e) {
			System.err.println("Problem closing input file.");
			System.exit(1);
		}
	}

	// Send the contents of the message stream to the bit filter.
	private static boolean message_encode(BitFilter bf) {
		try {
			int v;

			while ((v = Snow.stream_message.read()) != -1) {
				for (int i = 0; i < 8; i++) {
					if (!bf.receive_bit((v & 128 >> i) != 0)) {
						return false;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Failed to read from message stream.");
			return false;
		}

		return bf.flush();
	}

	// Parse the command-line arguments.
	private static boolean parse_args(String argv[]) {
		int optind;

		for (optind = 0; optind < argv.length && argv[optind].charAt(0) == '-'; optind++) {
			String optarg;

			if (argv[optind].length() < 2) {
				return false;
			}

			switch (argv[optind].charAt(1)) {
				case 'C':
					Snow.compress_flag = true;
					break;
				case 'Q':
					Snow.quiet_flag = true;
					break;
				case 'S':
					Snow.space_flag = true;
					break;
				case 'f':
					if (argv[optind].length() > 2) {
						optarg = argv[optind].substring(2);
					} else if (++optind == argv.length) {
						return false;
					} else {
						optarg = argv[optind];
					}

					if (Snow.stream_message != null) {
						System.err.println("Multiple message inputs defined.");
						return false;
					}

					try {
						Snow.stream_message = new FileInputStream(optarg);
					} catch (FileNotFoundException e) {
						System.err.println("No such file: " + optarg);
						return false;
					}
					break;
				case 'l':
					if (argv[optind].length() > 2) {
						optarg = argv[optind].substring(2);
					} else if (++optind == argv.length) {
						return false;
					} else {
						optarg = argv[optind];
					}

					try {
						Snow.line_length = Integer.parseInt(optarg);
					} catch (NumberFormatException e) {
						System.err.println("Illegal line length: " + optarg);
						return false;
					}
					break;
				case 'm':
					if (argv[optind].length() > 2) {
						optarg = argv[optind].substring(2);
					} else if (++optind == argv.length) {
						return false;
					} else {
						optarg = argv[optind];
					}

					if (Snow.stream_message != null) {
						System.err.println("Multiple message inputs defined.");
						return false;
					}

					Snow.stream_message = new ByteArrayInputStream(optarg.getBytes());

					break;
				case 'p':
					if (argv[optind].length() > 2) {
						optarg = argv[optind].substring(2);
					} else if (++optind == argv.length) {
						return false;
					} else {
						optarg = argv[optind];
					}

					Snow.passwd_string = optarg;
					break;
				default:
					System.err.println("Illegal option: " + argv[optind]);
					return false;
			}
		}

		if (optind < argv.length - 2) {
			return false;
		}

		if (optind < argv.length) {
			try {
				Snow.stream_in = new BufferedReader(new FileReader(argv[optind]));
			} catch (FileNotFoundException e) {
				System.err.println("No such file: " + argv[optind]);
				return false;
			}
		} else {
			Snow.stream_in = new BufferedReader(new InputStreamReader(System.in));
		}

		if (!Snow.space_flag && optind + 1 < argv.length) {
			try {
				Snow.stream_out = new FileOutputStream(argv[optind + 1]);
			} catch (IOException e) {
				System.err.println("Could not open file for writing: " + argv[optind + 1]);
				return false;
			}
		} else {
			Snow.stream_out = System.out;
		}

		return true;
	}
}
