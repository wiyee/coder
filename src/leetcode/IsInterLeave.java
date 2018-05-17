package leetcode;

/**
 * Created by wiyee on 2018/4/24.
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 * <p>
 * For example,
 * Given:
 * s1 ="aabcc",
 * s2 ="dbbca",
 * <p>
 * When s3 ="aadbbcbcac", return true.
 * When s3 ="aadbbbaccc", return false.
 */
public class IsInterLeave {
    public static void main(String[] args) {
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(isInterleave(s1, s2, s3));
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();

        if (len1 + len2 != len3)
            return false;

        char[] chs1 = s1.toCharArray();
        char[] chs2 = s2.toCharArray();
        char[] chs3 = s3.toCharArray();

        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        dp[0][0] = true;

        for (int i = 1; i < len2 + 1; i++) {
            dp[0][i] = dp[0][i - 1] && chs2[i - 1] == chs3[i - 1];
        }

        for (int i = 1; i < len1 + 1; i++) {
            dp[i][0] = dp[i - 1][0] && chs1[i - 1] == chs3[i - 1];
        }

        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                dp[i][j] = dp[i - 1][j] && (chs3[i + j - 1] == chs1[i - 1])
                        || dp[i][j - 1] && (chs3[i + j - 1] == chs2[j - 1]);
            }
        }

        return dp[len1][len2];
    }

}
