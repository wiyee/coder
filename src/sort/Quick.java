package sort;

import java.util.Arrays;


public class Quick {
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 7, 4, 5, 3, 9, 0};
        System.out.println(Arrays.toString(a));
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a) {
        if (a.length > 0) {
            quickSort(a, 0, a.length - 1);
        }
    }

    private static void quickSort(int[] a, int low, int high) {
        if (low > high)
            return;
        int i = low;
        int j = high;

        int key = a[i];

        while (i < j) {
            while (i < j && a[j] > key) {
                j--;
            }
            while (i < j && a[i] <= key) {
                i++;
            }
            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int p = a[i];
        a[i] = a[low];
        a[low] = p;
        quickSort(a, low, i - 1);
        quickSort(a, i + 1, high);
    }
}
