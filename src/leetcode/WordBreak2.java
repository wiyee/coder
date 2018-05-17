package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wiyee on 2018/5/7.
 * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * <p>
 * Return all such possible sentences.
 * <p>
 * For example, given
 * s ="catsanddog",
 * dict =["cat", "cats", "and", "sand", "dog"].
 * A solution is["cats and dog", "cat sand dog"].
 * <p>
 * // 动态规划
 * // 回溯（bfs）
 */
public class WordBreak2 {

//    /**
//     * 动态规划+bfs  结果正确显示超时
//     * @param s
//     * @param dict
//     * @return
//     */
//    public ArrayList<String> wordBreak(String s, Set<String> dict) {
//        int len = s.length();
//        boolean[][] dp = new boolean[len + 1][len + 1];
//        for (int i = 0; i < len; i ++){
//            for (int j = i; j <= len; j ++){
//                if (dict.contains(s.substring(i,j))){
//                    dp[i][j] = true;
//                }
//            }
//        }
//        ArrayList<String> result = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        dfs(s,dict,0,dp,sb,result);
//        return result;
//    }
//
//    public static void dfs(String s, Set<String> dict, int index, boolean[][] dp, StringBuffer sb, ArrayList<String> result){
//        if (index >= s.length()){
//            result.add(sb.toString().trim());
//            return;
//        }
//        for (int i = index; i <= s.length(); i ++){
//            if (dp[index][i] == true){
//                sb.append(" ");
//                sb.append(s.substring(index,i));
//                dfs(s,dict,i,dp,sb,result);
//                sb.delete(sb.length() - i + index -1,sb.length());
//            }
//        }
//
//    }

    //回溯
//    public static ArrayList<String> res=new ArrayList<String>();
//    public ArrayList<String> wordBreak(String s, Set<String> dict) {
//        int len = s.length();
//        dfs(s,dict,s.length(),"");
//        return res;
//    }
//
//    public static void dfs(String s, Set<String> dict, int index, String subString){
//        if (index <= 0){
//            if (subString.length() > 0){
//                res.add(subString.trim());
//            }
//        }
//        for (int i = index; i >= 0; i --){
//            if (dict.contains(s.substring(i,index))){
//                dfs(s,dict,i,s.substring(i,index) + " " + subString);
//            }
//        }
//    }

    public ArrayList<String> res = new ArrayList<String>();

    public void dfs(String s, int index, String cur_string, Set<String> dict) {
        if (index <= 0) {
            if (cur_string.length() > 0)
                res.add(cur_string.substring(0, cur_string.length() - 1));
        }
        for (int i = index; i >= 0; i--) {
            if (dict.contains(s.substring(i, index))) {
                dfs(s, i, s.substring(i, index) + " " + cur_string, dict);
            }
        }
    }

    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        dfs(s, s.length(), "", dict);
        return res;
    }

    public static void main(String[] args) {
        String s = "a";
        Set<String> dict = new HashSet<>();
        dict.add("b");
        dict.add("cats");
        dict.add("and");
        dict.add("dog");
        dict.add("sand");
        WordBreak2 wordBreak2 = new WordBreak2();
        System.out.println(wordBreak2.wordBreak(s, dict));
    }
}
