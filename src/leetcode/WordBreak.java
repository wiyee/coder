package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wiyee on 2018/5/7.
 * Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 * <p>
 * For example, given
 * s ="leetcode",
 * dict =["leet", "code"].
 * <p>
 * Return true because"leetcode"can be segmented as"leet code".
 */
public class WordBreak {
    public static void main(String[] args) {
        String s = "leetcodegg";
        Set<String> dict = new HashSet<>();
        dict.add("leet");
        dict.add("code");
        WordBreak wordBreak = new WordBreak();
        System.out.println(wordBreak.wordBreak(s, dict));
    }

    public boolean wordBreak(String s, Set<String> dict) {
        int length = s.length();
        boolean[] dp = new boolean[length + 1];
        dp[0] = true;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] == true && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                }
            }
        }
        return dp[length];
    }
}
