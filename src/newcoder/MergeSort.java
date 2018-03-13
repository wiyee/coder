package newcoder;

import java.util.Arrays;

/**
 * Created by wiyee on 2018/3/6.
 */
public class MergeSort {
    private void sort(int[] array,int[] tmp,int left,int right){
        if (left < right) {
            int mid = (right + left) / 2;
            sort(array, tmp, left, mid);
            sort(array, tmp, mid+1, right);
            merge(array, tmp, left, mid, right);
        }
    }
    private void merge(int[] array,int[] tmp, int left, int mid, int right){
        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= right){
            if (array[i]<=array[j])
                tmp[index++] = array[i++];
            else
                tmp[index++] = array[j++];
        }
        while (i<=mid){
            tmp[index++]=array[i++];
        }
        while(j<=right){
            tmp[index++] = array[j++];
        }
        index = 0;
        while (left<=right)
            array[left++] = tmp[index++];
    }

    public static void main(String[] args) {

        int[] array = new int[]{6,5,4,3,2,1};
        int[] tmp = new int[array.length]; //排序前先建好一个大小等于原数组长度的临时数组，避免递归中频繁开辟空间
        new MergeSort().sort(array,tmp,0,array.length-1);
        System.out.println(Arrays.toString(array));
    }
}
