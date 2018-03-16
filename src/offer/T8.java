package offer;

/**
 * Created by wiyee on 2018/3/7.
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。 NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 * 空间限制，不能使用遍历方法，需要时间复杂度需小于O(n)
 */
public class T8 {
    public int minNumberInRotateArray(int[] array) {
        if (array.length == 0)
            return 0;
        int low = 0;
        int high = array.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (array[mid] > array[high]) {
                low = mid + 1;
            } else if (array[mid] == array[high]) {
                high = high - 1;
            } else {
                high = mid;
            }
        }
        return array[low];
    }

    public static void main(String[] args) {
        int[] array = new int[]{};
        T8 t8 = new T8();
        int i = t8.minNumberInRotateArray(array);
        System.out.println(i);
    }
}
