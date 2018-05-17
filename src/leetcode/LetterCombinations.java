package leetcode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wiyee on 2018/4/12.
 * Given a digit string, return all possible letter combinations that the number could represent.
 * <p>
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
 */
public class LetterCombinations {
    public static void main(String[] args) {
        LetterCombinations letterCombinations = new LetterCombinations();
        System.out.println(letterCombinations.letterCombinations("35678"));
    }

    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> list = new ArrayList<>();
        if (digits.length() < 1 || digits == null) {
            list.add("");
            return list;
        }
        String[] keyboard = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz",};
        dfs(keyboard, digits, 0, new StringBuilder(), list);
        return list;
    }

    private static void dfs(String[] keyboard, String digits, int index, StringBuilder temp, ArrayList<String> res) {
        if (index == digits.length()) {
            res.add(temp.toString());
            return;
        }
        int num = digits.charAt(index) - '0';
        for (int i = 0; i < keyboard[num].length(); i++) {
            temp.append(keyboard[num].charAt(i));
            dfs(keyboard, digits, index + 1, temp, res);
            temp.deleteCharAt(temp.length() - 1);
        }
    }

}
