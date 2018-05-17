package newcoder;

import java.util.ArrayList;
import java.util.List;

public class Test222 {

    public static int solution(int n, int K) {
        List<Integer> group = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            group.add(i);
        }
        int number = 0;
        //圈里的人循环报数
        for (int i = 0; i < n; i++) {
            if (number == K) {//当圈里第i+1个人报道的数是3
                group.remove(i);//圈里第i+1个人退出圈子
                i--;//下一个报数的人在数组列表中的下标值
                n--;//圈子的总人数减少1
                number = 0;//通过number++，下一个人报的数是1
            }
            number++;  //报数时，每次加1
            if (i == n - 1) {//当所有的人报完一圈
                i = -1;//下一次从圈里的第一个人报，通过for循环的i++,对应数组列表下标值为0
            }
            if (n == 1) {//如果整个圈子最后只剩下一人
                break;
            }
        }
        return group.get(0);
    }

    public static void main(String[] args) {

        System.out.println(solution(15, 20));

    }

}