package netease;


import java.util.Scanner;

/**
 * Created by wiyee on 2018/5/17.
 * 小Q和牛博士合唱一首歌曲,这首歌曲由n个音调组成,每个音调由一个正整数表示。
 * 对于每个音调要么由小Q演唱要么由牛博士演唱,对于一系列音调演唱的难度等于所有相邻音调变化幅度之和, 例如一个音调序列是8, 8, 13, 12, 那么它的难度等于|8 - 8| + |13 - 8| + |12 - 13| = 6(其中||表示绝对值)。
 * 现在要对把这n个音调分配给小Q或牛博士,让他们演唱的难度之和最小,请你算算最小的难度和是多少。
 * 如样例所示: 小Q选择演唱{5, 6}难度为1, 牛博士选择演唱{1, 2, 1}难度为2,难度之和为3,这一个是最小难度和的方案了。
 * 输入描述:
 * 输入包括两行,第一行一个正整数n(1 ≤ n ≤ 2000) 第二行n个整数v[i](1 ≤ v[i] ≤ 10^6), 表示每个音调。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数,表示小Q和牛博士演唱最小的难度和是多少。
 * <p>
 * 输入例子1:
 * 5
 * 1 5 6 2 1
 * <p>
 * 输出例子1:
 * 3
 */
public class Chorus {

    /**
     * // 回溯法只通过了60%
     * public static int min = Integer.MAX_VALUE;
     * public static void main(String[] args) {
     * Scanner scanner = new Scanner(System.in);
     * while (scanner.hasNext()){
     * int n = scanner.nextInt();
     * int[] v = new int[n];
     * for (int i = 0; i < n; i ++){
     * v[i] = scanner.nextInt();
     * }
     * min = Integer.MAX_VALUE;
     * dfs(v,0,n,new ArrayList<>(), new ArrayList<>());
     * System.out.println(min);
     * <p>
     * }
     * }
     * public static void dfs(int[] v, int index, int n, ArrayList<Integer> A, ArrayList<Integer> B){
     * if (calDifficulty(A) + calDifficulty(B) > min)
     * return;
     * if (index == n){
     * int sum =calDifficulty(A) + calDifficulty(B);
     * if (sum < min){
     * min = sum;
     * return;
     * }
     * }
     * if (index > n )
     * return;
     * if (index < n){
     * A.add(v[index]);
     * dfs(v,index+1,n,A,B);
     * A.remove(A.size() - 1);
     * B.add(v[index]);
     * dfs(v,index+1,n,A,B);
     * B.remove(B.size() - 1);
     * }
     * <p>
     * }
     * <p>
     * public static int calDifficulty(ArrayList<Integer> A){
     * int count = 0;
     * for (int i = 1; i < A.size(); i ++){
     * count += Math.abs(A.get(i) - A.get(i-1));
     * }
     * return count;
     * }
     **/

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] v = new int[n];
            for (int i = 0; i < n; i++) {
                v[i] = in.nextInt();
            }
            if (n < 3)
                System.out.println(0);
            else {
                int[][] dp = new int[n][n];
                int[] cost = new int[n];
                dp[0][0] = 0 - Math.abs(v[1] - v[0]);
                for (int i = 1; i < n; i++) {
                    cost[i] = cost[i - 1] + Math.abs(v[i] - v[i - 1]);
                    dp[i][i - 1] = cost[i - 1];
                    for (int j = 0; j < i - 1; j++) {
                        dp[i][j] = dp[i - 1][j] + cost[i] - cost[i - 1];
                        dp[i][i - 1] = Math.min(dp[i][i - 1], dp[i - 1][j] + Math.abs(v[i] - v[i - 1]));
                    }
                }
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < n - 1; j++) {
                    min = Math.min(min, dp[n - 1][j]);
                }
                System.out.println(min);
            }
        }
    }
}
