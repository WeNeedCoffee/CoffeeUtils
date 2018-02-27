package coffee.weneed.utils.lang.filter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import coffee.weneed.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfanityFilter.
 *
 * @author Lyenliang, Dalethium
 */
// TODO register endings for words starting with the endings, for example registering Stroker from Stroke
// TODO check words for middle dupes between endings like rim and rimming
public class ProfanityFilter {

	/** The leets. */
	private static Map<Character, String[]> leets = new HashMap<Character, String[]>();

	static {
		// TODO finish
		// TODO load from file
		leets.put(StringUtil.getChar("a"), new String[] { "@", "4", "/\\", "/-\\", "/_\\" });
		leets.put(StringUtil.getChar("b"), new String[] { "13", "18", "l3", "8", "|:", "|8", "|b", "lo", "|o", "|3" });
		leets.put(StringUtil.getChar("c"), new String[] { "k", "s", "<", "(", "[", "{" });
		leets.put(StringUtil.getChar("d"), new String[] { "|)", "o|", "|>", "<|", "|}", "|]" });
		leets.put(StringUtil.getChar("e"), new String[] { "3" });
		leets.put(StringUtil.getChar("f"), new String[] { "ph", "|=", "|\"", "|#" });
		leets.put(StringUtil.getChar("g"), new String[] { "b", "(", "9", "6" });
		leets.put(StringUtil.getChar("k"), new String[] { "c", "|<", "1<", "|{", "/<", "\\<" });
		leets.put(StringUtil.getChar("l"), new String[] { "1_", "1", "|", "i", "|_", "i_" });
		leets.put(StringUtil.getChar("n"), new String[] { "|\\|", "/\\/", "/V" });
		leets.put(StringUtil.getChar("s"), new String[] { "c", "5", "$", "z" });
		leets.put(StringUtil.getChar("u"), new String[] { "v", "|_|", "L|", "\\/", "\\_/", "\\_\\", "/_/" });
		leets.put(StringUtil.getChar("w"), new String[] { "\\/\\/", "(/\\)", "\\^/", "|^|", "\\X/", "\\\\'", "'//", "VV" });
	}

	/**
	 * To skip.
	 *
	 * @param c the c
	 * @param leet the leet
	 * @param sub the sub
	 * @param node the node
	 * @return the int
	 */
	private static int toSkip(Character c, String leet, String sub, TreeNode node) {
		return toSkip(c, leet, sub, node, 0);
	}

	/**
	 * To skip.
	 *
	 * @param c the c
	 * @param leet the leet
	 * @param sub the sub
	 * @param node the node
	 * @param toSkip the to skip
	 * @return the int
	 */
	private static int toSkip(Character c, String leet, String sub, TreeNode node, int toSkip) {
		if (StringUtil.substr(sub, toSkip, sub.length()).startsWith(leet)) {
			toSkip += toSkip(c, leet, sub, node, toSkip + leet.length());
		}
		return toSkip;
	}

	/** The asterisk mark. */
	private boolean[] asteriskMark;

	/** The bad word end. */
	private int badWordEnd;

	/** The bad word start. */
	private int badWordStart;

	/** The is suspicion found. */
	private boolean isSuspicionFound;

	/** The root. */
	private TreeNode root;

	/**
	 * Instantiates a new profanity filter.
	 */
	public ProfanityFilter() {
		root = new TreeNode();
		buildDictionaryTree("badwords.txt");
	}

	/**
	 * Adds the to tree.
	 *
	 * @param badWordLine the bad word line
	 * @param characterIndex            : index of each letter in a bad word
	 * @param node            that iterates through the tree
	 */
	private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
		if (characterIndex < badWordLine.length()) {
			Character c = badWordLine.charAt(characterIndex);
			if (!node.containsChild(c)) {
				node.addChild(c);
			}
			node = node.getChildByLetter(c);
			if (characterIndex == (badWordLine.length() - 1)) {
				node.setEnd(true);
			} else {
				addToTree(badWordLine, characterIndex + 1, node);
			}
		}
	}

	/**
	 * Replace some of the letters in userInput as * according to asteriskMark.
	 *
	 * @param userInput the user input
	 * @return string with bad words filtered
	 */
	private String applyAsteriskMark(String userInput) {
		StringBuilder filteredBadWords = new StringBuilder(userInput);
		for (int i = 0; i < asteriskMark.length; i++) {
			if (asteriskMark[i] == true) {
				filteredBadWords.setCharAt(i, '*');
			}
		}
		return filteredBadWords.toString();
	}

	/**
	 * Setup a tree for profanity filter.
	 *
	 * @param fileName the file name
	 */
	public void buildDictionaryTree(String fileName) {
		String line;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + fileName)));
		} catch (NullPointerException e) {
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			while ((line = in.readLine()) != null) {
				addToTree(line.toLowerCase(), 0, root);
				finishTree(line.toLowerCase());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Filter bad words.
	 *
	 * @param userInput the user input
	 * @return string with bad words filtered
	 */
	public String filterBadWords(String userInput) {
		String userInputLC = userInput.toLowerCase();
		init(userInputLC.length());
		for (int i = 0; i < userInputLC.length(); i++) {
			searchAlongTree(userInputLC, i, root);
		}
		return applyAsteriskMark(userInput);
	}

	/**
	 * Finish tree.
	 *
	 * @param badWordLine the bad word line
	 */
	private void finishTree(String badWordLine) {
		String line;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/endings.txt")));
		} catch (NullPointerException e) {
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream("endings.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		try {
			while ((line = in.readLine()) != null) {
				addToTree(badWordLine + line.toLowerCase(), 0, root);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Inits the.
	 *
	 * @param length the length
	 */
	private void init(int length) {
		asteriskMark = new boolean[length];
		for (int i = 0; i < length; i++) {
			asteriskMark[i] = false;
		}
		badWordStart = -1;
		badWordEnd = -1;
		isSuspicionFound = false;
	}

	/**
	 * Identify the letters of userInput that should be marked as "*".
	 *
	 * @param badWordStart the bad word start
	 * @param badWordEnd the bad word end
	 */
	private void markAsterisk(int badWordStart, int badWordEnd) {
		for (int i = badWordStart; i <= badWordEnd; i++) {
			asteriskMark[i] = true;
		}
	}

	/**
	 * Match leet.
	 *
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @return the list
	 */
	private List<Character> matchLeet(String pUserInput, int characterIndex) {
		List<Character> leetmatch = new ArrayList<Character>();
		String sub = StringUtil.substr(pUserInput, characterIndex, pUserInput.length());
		for (Entry<Character, String[]> entry : leets.entrySet()) {
			Character c = entry.getKey();
			String[] ss = entry.getValue();
			for (String s : ss) {
				if (leetmatch.contains(c)) {
					break;
				}
				if (sub.startsWith(s)) {
					leetmatch.add(c);
				}
			}
		}
		return leetmatch;
	}

	/**
	 * Search.
	 *
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @param node the node
	 * @param update the update
	 * @return true, if successful
	 */
	private boolean search(String pUserInput, int characterIndex, TreeNode node, boolean update) {
		return search(pUserInput, characterIndex, node, update, false);
	}

	/**
	 * Search.
	 *
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @param node the node
	 * @param update the update
	 * @param recur the recur
	 * @return true, if successful
	 */
	private boolean search(String pUserInput, int characterIndex, TreeNode node, boolean update, boolean recur) {
		Character letter = pUserInput.charAt(characterIndex);
		if (node.containsChild(letter)) {
			if (update) updateNode(letter, pUserInput, characterIndex, node, 1);
			return true;
		} else if (searchLeet(pUserInput, characterIndex, node, update)) {
			return true;
		}
		return false;
	}

	/**
	 * Search along tree.
	 *
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @param node the node
	 */
	private void searchAlongTree(String pUserInput, int characterIndex, TreeNode node) {
		if (characterIndex < pUserInput.length()) {
			Character letter = pUserInput.charAt(characterIndex);
			if (search(pUserInput, characterIndex, node, true)) {
				return;
			} else if (characterIndex > 0 && (characterIndex + 1) < pUserInput.length()) {
				if ((letter.equals(pUserInput.charAt(characterIndex - 1)) || letter.equals(pUserInput.charAt(characterIndex + 1))
						|| matchLeet(pUserInput, characterIndex + 1).contains(letter)
						|| matchLeet(pUserInput, characterIndex - 1).contains(letter))) {
					searchAlongTree(pUserInput, characterIndex + 1, node);
					return;
				}
				try {
					if (!node.isEnd()) (letter + "").replaceAll("[\\W_]", "").charAt(0);
				} catch (IndexOutOfBoundsException e) {
					searchAlongTree(pUserInput, characterIndex + 1, node);
					return;
				}
			}
			isSuspicionFound = false;
			badWordStart = -1;
			badWordEnd = -1;
		}
	}

	/**
	 * Search leet.
	 *
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @param node the node
	 * @param update the update
	 * @return true, if successful
	 */
	private boolean searchLeet(String pUserInput, int characterIndex, TreeNode node, boolean update) {
		List<Character> leetmatch = matchLeet(pUserInput, characterIndex);
		for (Character ch : leetmatch) {
			if (node.containsChild(ch)) {
				String[] ssss = leets.get(ch);
				Arrays.sort(ssss, (b, a) -> Integer.compare(a.length(), b.length()));
				for (String leet : ssss) {
					int toSkip = toSkip(ch, leet, StringUtil.substr(pUserInput, characterIndex, pUserInput.length()), node);
					if (toSkip > 0) {
						if (update) updateNode(ch, pUserInput, characterIndex, node, toSkip);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Update node.
	 *
	 * @param ch the ch
	 * @param pUserInput the user input
	 * @param characterIndex the character index
	 * @param node the node
	 * @param toSkip the to skip
	 */
	private void updateNode(Character ch, String pUserInput, int characterIndex, TreeNode node, int toSkip) {
		if (isSuspicionFound == false) {
			isSuspicionFound = true;
			badWordStart = characterIndex;
		}
		if (node.getChildByLetter(ch).isEnd()) {
			if (characterIndex > 0 && (characterIndex + 1) < pUserInput.length()
					&& ((ch.equals(pUserInput.charAt(characterIndex + 1)) || matchLeet(pUserInput, characterIndex + 1).contains(ch))
							|| search(pUserInput, characterIndex + 1, node, false))) {
				searchAlongTree(pUserInput, characterIndex + 1, node);
				return;
			}
			badWordEnd = characterIndex + toSkip - 1;
			markAsterisk(badWordStart, badWordEnd);
		}
		node = node.getChildByLetter(ch);
		searchAlongTree(pUserInput, characterIndex + toSkip, node);
	}
}
