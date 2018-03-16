package offer;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/2/28.
 * 计算一年中的第几天
 */
public class WhatDay {
    public static int[] monthDay = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int year = sc.nextInt();
            int month = sc.nextInt();
            int day = sc.nextInt();
            if (month > 12 || month < 0 || day > 31 || day < 0) {
                System.exit(0);
            }
            System.out.println(calculateDayOfYear(year, month, day));
        }
    }

    public static int calculateDayOfYear(int year, int month, int day) {
        int a = 0;
        if (day > monthDay[month - 1] && month != 2) {
            System.exit(0);
        } else if (month == 2 && day > 27) {
            if (!(isLeapYear(year) && day == 28))
                System.exit(0);
        }
        for (int i = 0; i < month - 1; i++) {
            a += monthDay[i];
        }
        if (isLeapYear(year))
            a++;
        return a + day;
    }

    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else
            return false;
    }


}
