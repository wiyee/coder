package leetcode;

/**
 * Created by wiyee on 2018/4/25.
 * There are N children standing in a line. Each child is assigned a rating value.
 * <p>
 * You are giving candies to these children subjected to the following requirements:
 * <p>
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 * What is the minimum candies you must give?
 */
public class Candy {
    public static void main(String[] args) {
        int[] ratings = new int[]{10, 12, 1, 5, 64, 87};
        System.out.println(candy(ratings));
    }

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] dp = new int[n];
        int result = 0;
        dp[0] = 1;
        for (int i = 0; i < n - 1; i++) {
            if (ratings[i + 1] > ratings[i]) {
                dp[i + 1] = dp[i] + 1;
            } else
                dp[i + 1] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i + 1] < ratings[i]) {
                dp[i] = Math.max(dp[i], dp[i + 1] + 1);
            }
        }
        for (int i : dp)
            result += i;
        return result;
    }
}
