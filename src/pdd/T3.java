package pdd;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class T3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int userIndex = sc.nextInt();
        HashSet<Integer> hs = new HashSet<Integer>();
        if (N < 1 || N > 100) {
            System.out.println(0);
            return;
        }
        String[] friend = new String[N];
        int maxUser = 0;
        int index = 0;
        sc.nextLine();
        for (int i = 0; i < N; i++) {
            friend[i] = sc.nextLine();
        }
        for (String num : friend[userIndex].split(" ")) {
            hs.add(Integer.valueOf(num));
        }
        for (int i = N - 1; i >= 0; i--) {
            int friendTotal = 0;
            if (i != userIndex)
                for (String n : friend[i].split(" "))
                    if (hs.contains(Integer.valueOf(n))) friendTotal++;
            if (friendTotal >= maxUser) {
                maxUser = friendTotal;
                index = i;
            }
        }
        if (maxUser == 0)
            System.out.println(-1);
        else
            System.out.println(index);
    }

}
