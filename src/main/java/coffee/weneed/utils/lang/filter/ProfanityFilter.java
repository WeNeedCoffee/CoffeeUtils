package coffee.weneed.utils.lang.filter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import coffee.weneed.utils.NetUtil;
import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfanityFilter.
 *
 * @author Lyenliang, Dalethium
 */
// TODO check words for middle dupes between endings like rim and rimming
public class ProfanityFilter implements IJSONObjectDataHolder {

	/** The active leets. */
	private static Map<Character, String[]> activeLeets = new HashMap<>();
	/** The leets. */
	public static Map<Character, String[]> leets = new HashMap<>();

	/** The ascii leets. */
	public static Map<Character, String[]> asciiLeets = new HashMap<>();

	static {
		// TODO finish
		// TODO load from file
		// TODO determine if more than 3 chars significantly affects speed
		ProfanityFilter.leets.put(StringUtil.getChar("a"), new String[] { "@", "4", "/\\", "/-\\", "/_\\" });
		ProfanityFilter.leets.put(StringUtil.getChar("b"), new String[] { "13", "18", "l3", "8", "|:", "|8", "|b", "lo", "|o", "|3" });
		ProfanityFilter.leets.put(StringUtil.getChar("c"), new String[] { "k", "s", "<", "(", "[", "{" });
		ProfanityFilter.leets.put(StringUtil.getChar("d"), new String[] { "|)", "o|", "|>", "<|", "|}", "|]" });
		ProfanityFilter.leets.put(StringUtil.getChar("e"), new String[] { "3" });
		ProfanityFilter.leets.put(StringUtil.getChar("f"), new String[] { "ph", "|=", "|\"", "|#" });
		ProfanityFilter.leets.put(StringUtil.getChar("g"), new String[] { "b", "(", "9", "6" });
		ProfanityFilter.leets.put(StringUtil.getChar("k"), new String[] { "c", "|<", "1<", "|{", "/<", "\\<" });
		ProfanityFilter.leets.put(StringUtil.getChar("h"), new String[] { "#", "|-|", "l-l", "1-1", "i-i" });
		ProfanityFilter.leets.put(StringUtil.getChar("i"), new String[] { "1", "l", "|", "!", "y" });
		ProfanityFilter.leets.put(StringUtil.getChar("l"), new String[] { "1_", "1", "|", "i", "|_", "i_" });
		ProfanityFilter.leets.put(StringUtil.getChar("n"), new String[] { "|\\|", "/\\/", "/V" });
		ProfanityFilter.leets.put(StringUtil.getChar("m"), new String[] { "/\\/\\", "|\\/|", "|\\/|", "|V|", "/V\\" });
		ProfanityFilter.leets.put(StringUtil.getChar("s"), new String[] { "c", "5", "$", "z" });
		ProfanityFilter.leets.put(StringUtil.getChar("u"), new String[] { "v", "|_|", "L|", "\\/", "\\_/", "\\_\\", "/_/" });
		ProfanityFilter.leets.put(StringUtil.getChar("w"), new String[] { "\\/\\/", "(/\\)", "\\^/", "|^|", "\\X/", "\\\\'", "'//", "VV" });
		ProfanityFilter.leets.put(StringUtil.getChar("x"), new String[] { "*", "><", "cks", "ecks" });
		ProfanityFilter.leets.put(StringUtil.getChar("z"), new String[] { "s" });
		// TODO be able to register ks as x as well as x as ks
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("c"), new String[] { "k", "s" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("f"), new String[] { "ph" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("g"), new String[] { "b" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("h"), new String[] { "wh" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("k"), new String[] { "c" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("i"), new String[] { "l", "y" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("l"), new String[] { "i" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("s"), new String[] { "c", "z" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("u"), new String[] { "v" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("x"), new String[] { "cks", "ecks", "ks" });
		ProfanityFilter.asciiLeets.put(StringUtil.getChar("z"), new String[] { "s" });
	}

	/** The asterisk mark. */
	private boolean[] asteriskMark;

	/** The bad word end. */
	private int badWordEnd;

	/** The bad word start. */
	private int badWordStart;

	/** The is suspicion found. */
	private boolean isSuspicionFound;

	/** The blacklist. */
	private TreeNode blacklist;

	/** The whitelist. */
	private TreeNode whitelist;

	/**
	 * Instantiates a new profanity filter.
	 *
	 * @param ascii the ascii
	 */
	public ProfanityFilter(boolean ascii) {
		this(ascii ? asciiLeets : leets, null);
	}

	/**
	 * Instantiates a new profanity filter.
	 *
	 * @param activeLeets the active leets
	 * @param tree         the tree
	 */
	public ProfanityFilter(Map<Character, String[]> activeLeets, URL tree) {
		try {
			fromJSON(new JSONObject(new String(NetUtil.downloadURL(tree))));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Adds the to tree.
	 *
	 * @param badWordLine    the bad word line
	 * @param characterIndex : index of each letter in a bad word
	 * @param node           that iterates through the tree
	 */
	private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
		if (characterIndex < badWordLine.length()) {
			Character c = badWordLine.charAt(characterIndex);
			if (!node.containsChild(c)) {
				node.addChild(c);
			}
			node = node.getChildByLetter(c);
			if (characterIndex == badWordLine.length() - 1) {
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
			if (asteriskMark[i]) {
				filteredBadWords.setCharAt(i, '*');
			}
		}
		return filteredBadWords.toString();
	}

	/**
	 * Adds the word.
	 *
	 * @param word the word
	 */
	public void blacklistWord(String word) {
		addToTree(word, 0, blacklist);
		unwhitelistWord(word);
		finishTree(word);
	}

	/**
	 * Builds the dictionary tree from JSONURL.
	 *
	 * @param json the json
	 * @return the tree node
	 */
	public TreeNode buildDictionaryTreeFromJSON(JSONObject json) {
		TreeNode n = new TreeNode();
		n.fromJSON(json);
		return n;
	}

	/**
	 * Check white list.
	 *
	 * @param input the input
	 * @param start the start
	 */
	private void checkWhiteList(String input, int start) {
		if (input.isEmpty() || input.length() < start) {
        } else if (whitelist.isEmpty() || !whitelist.containsChild(StringUtil.getChar(input.substring(0, 1)))) {
        } else {
			checkWhiteList(input, whitelist, start, 0, input);
		}
	}

	/**
	 * Check white list.
	 *
	 * @param input the input
	 * @param n     the n
	 * @param start the start
	 * @param spot  the spot
	 * @param orig  the orig
	 */
	private void checkWhiteList(String input, TreeNode n, int start, int spot, String orig) {
		if (n.isEnd()) {
			unmarkAsterisk(start, start + spot - 1);
			checkWhiteList(input.substring(spot), start + spot);
		}
		if (spot >= input.length() || n.isEmpty()) {
			return;
		}
		char e = StringUtil.getChar(input.substring(spot, 1 + spot));
		if (n.containsChild(e)) {
			checkWhiteList(input, n.getChildByLetter(e), start, spot + 1, orig);
		} else {
			checkWhiteList(input.substring(spot), start + spot);
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
			searchAlongTree(userInputLC, i, blacklist);
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
			while (in != null && (line = in.readLine()) != null) {
				addToTree(badWordLine + line.toLowerCase(), 0, blacklist);
				unwhitelistWord(badWordLine + line.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
	@Override
	public void fromJSON(JSONObject json) {
		blacklist = buildDictionaryTreeFromJSON(json.getJSONObject("blacklist"));
		whitelist = buildDictionaryTreeFromJSON(json.getJSONObject("whitelist"));
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public TreeNode getRoot() {
		return blacklist;
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
	 * @param badWordEnd   the bad word end
	 */
	private void markAsterisk(int badWordStart, int badWordEnd) {
		for (int i = badWordStart; i <= badWordEnd; i++) {
			asteriskMark[i] = true;
		}
	}

	/**
	 * Search.
	 *
	 * @param input          the user input
	 * @param characterIndex the character index
	 * @param node           the node
	 * @param update         the update
	 * @return true, if successful
	 */
	private boolean search(String input, int characterIndex, TreeNode node, boolean update) {
		char letter = input.charAt(characterIndex);
		if (node.containsChild(letter)) {
			if (update) {
				updateNode(letter, input, characterIndex, node, 1);
			}
			return true;
		} else return searchLeet(input, characterIndex, node, update);
    }

	/**
	 * Search along tree.
	 *
	 * @param input          the user input
	 * @param characterIndex the character index
	 * @param node           the node
	 */
	private void searchAlongTree(String input, int characterIndex, TreeNode node) {
		if (characterIndex < input.length()) {
			char letter = input.charAt(characterIndex);
			if (search(input, characterIndex, node, true)) {
				return;
			}
			if (!node.isEnd()) {
				if (Character.toString(letter).matches("[\\W_]")) {
					searchAlongTree(input, characterIndex + 1, node);
					return;
				}
			}
		}
		isSuspicionFound = false;
		badWordStart = -1;
		badWordEnd = -1;
	}

	/**
	 * Search leet.
	 *
	 * @param input          the user input
	 * @param characterIndex the character index
	 * @param node           the node
	 * @param update         the update
	 * @return true, if successful
	 */
	private boolean searchLeet(String input, int characterIndex, TreeNode node, boolean update) {
		String sub = StringUtil.substr(input, characterIndex, input.length());
		for (Entry<Character, String[]> entry : activeLeets.entrySet()) {
			char c = entry.getKey();
			if (!node.containsChild(c)) {
				continue;
			}
			String[] ss = entry.getValue();
			for (String leet : ss) {
				if (sub.startsWith(leet)) {
					if (update) {
						updateNode(c, input, characterIndex, node, leet.length());
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("blacklist", blacklist.toJSON());
		json.put("whitelist", whitelist.toJSON());
		return json;
	}

	/**
	 * Identify the letters of userInput that should be marked as "*".
	 *
	 * @param whitelistStart the whitelist start
	 * @param whitelistEnd   the whitelist end
	 */
	private void unmarkAsterisk(int whitelistStart, int whitelistEnd) {
		for (int i = whitelistStart; i <= whitelistEnd; i++) {
			asteriskMark[i] = false;
		}
	}

	/**
	 * Removes the word.
	 *
	 * @param word the word
	 */
	public void unwhitelistWord(String word) {
		if (!filterBadWords(word).contains("*")) {
			return;
		}
		TreeNode n = whitelist;
		for (char c : word.toCharArray()) {
			if (n.getChildByLetter(c) == null) {
				n.addChild(c);
			}
			n = n.getChildByLetter(c);
		}
		n.setEnd(false);
	}

	/**
	 * Update node.
	 *
	 * @param ch             the ch
	 * @param input          the user input
	 * @param characterIndex the character index
	 * @param node           the node
	 * @param toSkip         the to skip
	 */
	private void updateNode(char ch, String input, int characterIndex, TreeNode node, int toSkip) {
		if (!isSuspicionFound) {
			isSuspicionFound = true;
			badWordStart = characterIndex;
		}
		if (node.getChildByLetter(ch).isEnd()) {
			if (characterIndex > 0 && characterIndex + 1 < input.length() && ch == input.charAt(characterIndex + 1)) {
				searchAlongTree(input, characterIndex + 1, node);
				return;
			}
			badWordEnd = characterIndex + toSkip - 1;
			markAsterisk(badWordStart, badWordEnd);
			checkWhiteList(input, 0); // after loop in filterbadwords?
		}
		searchAlongTree(input, characterIndex + toSkip, node.getChildByLetter(ch));
	}

	/**
	 * Removes the word.
	 *
	 * @param word the word
	 */
	public void whitelistWord(String word) {
		if (!filterBadWords(word).contains("*")) {
			return;
		}
		TreeNode n = whitelist;
		for (char c : word.toCharArray()) {
			if (n.getChildByLetter(c) == null) {
				n.addChild(c);
			}
			n = n.getChildByLetter(c);
		}
		n.setEnd(true);
		n = blacklist;
		for (char c : word.toCharArray()) {
			if (n.getChildByLetter(c) == null) {
				n.addChild(c);
			}
			n = n.getChildByLetter(c);
		}
		n.setEnd(false);
	}
}