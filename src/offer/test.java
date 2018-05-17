package offer;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by wiyee on 2018/3/23.
 */
public class test {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Set<Integer> countset = new HashSet<>();
        int stop = n / 2;
        for (int i = 1; i <= stop; i++) {
            int result = (i) ^ (n - i);
            countset.add(result);
        }
        System.out.println(countset.size());
    }
}
