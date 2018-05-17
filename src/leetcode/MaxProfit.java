package leetcode;

/**
 * Created by wiyee on 2018/4/10.
 * <p>
 * Say you have an array for which the i th element is the price of a given stock on day i.
 * <p>
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 */
public class MaxProfit {
    public static void main(String[] args) {
        int[] pri = new int[]{2, 1, 2, 0, 1};
        System.out.println(maxProfit(pri));
    }

    public static int maxProfit(int[] prices) {
        if (prices.length < 1) {
            return 0;
        }
        int result = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min)
                min = prices[i];
            else
                result = Math.max(prices[i] - min, result);
        }
        return result;
    }
}
