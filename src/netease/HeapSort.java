package netease;


import java.util.Arrays;

public class HeapSort {
    private static void adjustHeap(int[] a, int i, int len) {

        int father = a[i];
        for (int j = i * 2; j < len; j *= 2) {
            if (j < len && a[j] < a[j + 1])
                ++j;
            if (a[j] <= father) {
                break;
            }
            a[i] = a[j];
            i = j;
        }
        a[i] = father;
    }

    public static void main(String[] args) {
        int a[] = {51, 46, 20, 18, 65, 97, 82, 30, 77, 50};
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeap(a, i, a.length - 1);
        }
        for (int i = a.length - 1; i >= 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            System.out.println(i);
            System.out.println(Arrays.toString(a));
            adjustHeap(a, 0, i - 1);
        }
    }
}
