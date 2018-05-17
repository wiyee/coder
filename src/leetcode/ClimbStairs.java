package leetcode;

/**
 * Created by wiyee on 2018/4/24.
 */
public class ClimbStairs {
    public static int count = 0;

    public static void main(String[] args) {
        System.out.println(new ClimbStairs().climbStairs(5));
    }

    public int climbStairs(int n) {
        int dp[] = new int[n + 1];
        dp[1] = 1;
        dp[0] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
//    // 递归超时
//    public int climbStairs(int n) {
//        dfs(0,n);
//        return count;
//    }
//    private void dfs(int index,int sum){
//        if (index == sum)
//            count ++;
//        if (index <= sum - 2){
//            dfs(index + 1, sum);
//            dfs(index + 2, sum);
//        } else if (index == sum - 1){
//            count ++;
//        }
//    }
}
