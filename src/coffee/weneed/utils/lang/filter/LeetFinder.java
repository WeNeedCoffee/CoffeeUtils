package coffee.weneed.utils.lang.filter;

import java.util.HashMap;
import java.util.Map;

import coffee.weneed.utils.StringUtil;

public class LeetFinder {
	static Map<Character, Character[]> leets = new HashMap<Character, Character[]>();
	static {
		leets.put(StringUtil.getChar("a"), new Character[] {StringUtil.getChar("@"), StringUtil.getChar("4")});
		leets.put(StringUtil.getChar("b"), new Character[] {StringUtil.getChar("8")});
		leets.put(StringUtil.getChar("c"), new Character[] {StringUtil.getChar("k"), StringUtil.getChar("s")});
		leets.put(StringUtil.getChar("e"), new Character[] {StringUtil.getChar("3")});
	}
	public TreeNode findLeet(Character letter, TreeNode node) {
		
		return node;
		
	}
}
