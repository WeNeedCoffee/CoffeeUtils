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

/**
 * @author Lyenliang, Dalethium
 *
 */
//TODO register endings for words starting with the endings, for example registering Stroker from Stroke
//TODO check words for middle dupes between endings like rim and rimming
public class ProfanityFilter {
	private TreeNode root;
	private int badWordStart;
	private int badWordEnd;
	private boolean isSuspicionFound;
	private boolean[] asteriskMark;
	
	public ProfanityFilter() {
		root = new TreeNode();
		buildDictionaryTree("badwords.txt");
	}

	/**
	 * Setup a tree for profanity filter
	 * 
	 * @param fileName
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
				// for each bad word
				addToTree(line.toLowerCase(), 0, root);
				finishTree(line.toLowerCase());
			}

		} catch (FileNotFoundException e) { // FileReader
			e.printStackTrace();
		} catch (IOException e) { // readLine
			e.printStackTrace();
		} finally {
			try {
				if (in !=null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param badWordLine
	 * @param characterIndex
	 *            : index of each letter in a bad word
	 * @param node
	 *            that iterates through the tree
	 */
	private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
		if (characterIndex < badWordLine.length()) {
			Character c = badWordLine.charAt(characterIndex);
			if (!node.containsChild(c)) {
				node.addChild(c);
			}
			node = node.getChildByLetter(c);
			// check if this is the last letter
			if (characterIndex == (badWordLine.length() - 1)) {
				// mark this letter as the end of a bad word
				node.setEnd(true);
			} else {
				// add next letter
				addToTree(badWordLine, characterIndex + 1, node);
			}
		}
	}

	
	private void finishTree(String badWordLine) {
		String line;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/endings.txt")));
		} catch (NullPointerException e) {
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream("endings.txt")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			while ((line = in.readLine()) != null) {
				// for each bad word
				addToTree(badWordLine + line.toLowerCase(), 0, root);
			}

		} catch (FileNotFoundException e) { // FileReader
			e.printStackTrace();
		} catch (IOException e) { // readLine
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param userInput
	 * @return string with bad words filtered
	 */
	public String filterBadWords(String userInput) {
		userInput = userInput.toLowerCase();
		init(userInput.length());
		// for each character in a bad word
		for (int i = 0; i < userInput.length(); i++) {
			searchAlongTree(userInput, i, root);
		}
		return applyAsteriskMark(userInput);
	}

	private void init(int length) {
		asteriskMark = new boolean[length];
		for (int i = 0; i < length; i++) {
			asteriskMark[i] = false;
		}
		badWordStart = -1;
		badWordEnd = -1;
		isSuspicionFound = false;
	}
	static Map<Character, String[]> leets = new HashMap<Character, String[]>();
	static {
		leets.put(StringUtil.getChar("a"), new String[] {"@", "4", "/\\", "/-\\"});
		leets.put(StringUtil.getChar("b"), new String[] {"13", "18", "l3", "8"});
		leets.put(StringUtil.getChar("c"), new String[] {"k", "s", "<", "("});
		leets.put(StringUtil.getChar("d"), new String[] {"|)", "o|", "|>", "<|"});
		leets.put(StringUtil.getChar("e"), new String[] {"3"});
		leets.put(StringUtil.getChar("g"), new String[] {"b", "(", "9", "6"});
		leets.put(StringUtil.getChar("k"), new String[] {"c", "s"});
		leets.put(StringUtil.getChar("l"), new String[] {"1_", "1", "|", "i", "|_", "i_"}); //test
		leets.put(StringUtil.getChar("s"), new String[] {"k", "c"});
	}
	private static int toSkip(Character c, String leet, String sub, TreeNode node) {
		return toSkip(c, leet, sub, node, 0);
	}
	private static int toSkip(Character c, String leet, String sub, TreeNode node, int toSkip) {
		if (StringUtil.substr(sub, toSkip, sub.length()).startsWith(leet)) {
			toSkip += toSkip(c, leet, sub, node, toSkip + leet.length());
		}
		return toSkip;
	}
	private boolean isLeetMatch(Character c, String pUserInput, int characterIndex) {
		String sub = StringUtil.substr(pUserInput, characterIndex, pUserInput.length());
		if (!leets.containsKey(c)) return false;
		for (String s : leets.get(c)) {
			if (sub.startsWith(s)) {
				return true;
			}
		}
		return false;
	}
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
	private boolean search(String pUserInput, int characterIndex, TreeNode node, boolean update) {
		return search(pUserInput, characterIndex, node, update, false);
	}
	private boolean search(String pUserInput, int characterIndex, TreeNode node, boolean update, boolean recur) {
		Character letter = pUserInput.charAt(characterIndex);
		if (node.containsChild(letter)) {
			if (update) updateNode(letter, pUserInput, characterIndex, node, 1);
			return true;
		} else if (searchLeet(pUserInput, characterIndex, node, update)){
			return true;
		} /*else if (!recur && characterIndex > 0 && (characterIndex + 1) < pUserInput.length()) {
			if ((letter.equals(pUserInput.charAt(characterIndex - 1)) || letter.equals(pUserInput.charAt(characterIndex + 1))) ||
					isLeetMatch(letter, pUserInput, characterIndex + 1) || 
						search(pUserInput, characterIndex + 1, node, false, true)) {
				if (update) searchAlongTree(pUserInput, characterIndex + 1, node);
				return true;
			}
		} */
		return false;
	}
	private boolean searchLeet(String pUserInput, int characterIndex, TreeNode node, boolean update) {
		List<Character> leetmatch = matchLeet(pUserInput, characterIndex);
		for (Character ch : leetmatch) {
			if (node.containsChild(ch)) {
				String[] ssss = leets.get(ch);
				Arrays.sort(ssss, (b, a)->Integer.compare(a.length(), b.length()));
				
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
	
	private void updateNode(Character ch, String pUserInput, int characterIndex, TreeNode node, int toSkip) {
		if (isSuspicionFound == false) {
			isSuspicionFound = true;
			badWordStart = characterIndex;
		}
		// if this is the final letter of a bad word
		if (node.getChildByLetter(ch).isEnd()) {
			if (characterIndex > 1 && (characterIndex + 1) < pUserInput.length() && ((ch.equals(pUserInput.charAt(characterIndex - 1)) ||
					isLeetMatch(ch, pUserInput, characterIndex + 1)) && node.containsChild(pUserInput.charAt(characterIndex - 1)) || 
						search(pUserInput, characterIndex + 1, node, false))) {
				searchAlongTree(pUserInput, characterIndex + 1, node);
				return;
			}
			badWordEnd = characterIndex + toSkip - 1;
			markAsterisk(badWordStart, badWordEnd);
		}
		node = node.getChildByLetter(ch);
		searchAlongTree(pUserInput, characterIndex + toSkip, node);
	}
	//TODO detect things like: "Nazzzi"
	private void searchAlongTree(String pUserInput, int characterIndex, TreeNode node) {
		if (characterIndex < pUserInput.length()) {
			if (search(pUserInput, characterIndex, node, true)) {
				return;
			}
			// initialize some parameters
			isSuspicionFound = false;
			badWordStart = -1;
			badWordEnd = -1;
		}
	}

	/**
	 * Replace some of the letters in userInput as * according to asteriskMark
	 * 
	 * @param userInput
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
	 * Identify the letters of userInput that should be marked as "*"
	 * 
	 * @param badWordStart
	 * @param badWordEnd
	 */
	private void markAsterisk(int badWordStart, int badWordEnd) {
		for (int i = badWordStart; i <= badWordEnd; i++) {
			asteriskMark[i] = true;
		}
	}
}
