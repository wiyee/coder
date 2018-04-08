package netease;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class FindWork {
    /**
     * 为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。牛牛的小伙伴太多了，于是他只好把这个任务交给了你。
     * 输入描述:
     * 每个输入包含一个测试用例。
     * 每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
     * 接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
     * 接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
     * 保证不存在两项工作的报酬相同。
     * <p>
     * 输出描述:
     * 对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。
     * <p>
     * 输入例子1:
     * 3 3
     * 1 100
     * 10 1000
     * 1000000000 1001
     * 9 10 1000000000
     * <p>
     * 输出例子1:
     * 100
     * 1000
     * 1001
     */
    public static void main(String[] args) {
        int n, m;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[][] work = new int[n][2];
        int[][] person = new int[m][2];
        for (int i = 0; i < n; i++) {
            work[i][1] = scanner.nextInt();
            work[i][0] = scanner.nextInt();
        }
        //排序
        Arrays.sort(work, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        for (int i = 0; i < m; i++) {
            person[i][0] = scanner.nextInt();
            person[i][1] = i;
        }
        Arrays.sort(person, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        int[] result = new int[m];
        int workIndex = 0;
        int personIndex = 0;
        while (workIndex < n && personIndex < m) {
            if (person[personIndex][0] >= work[workIndex][1]) {
                result[person[personIndex][1]] = work[workIndex][0];
                personIndex++;
            } else {
                workIndex++;
            }
        }
        for (int i : result) {
            System.out.println(i);
        }
    }
}
