package problem;

import java.util.Arrays;
import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[]{11,4, 3, 2, 4, 5, 4, 7, 1, 2, 8, 0};
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length);
    }

    private static void quickSort(int[] arr, int start, int end) {
        if (start + 1 >= end) {
            return;
        }
        int mid = partition(arr, start, end);
        quickSort(arr, start, mid);
        quickSort(arr, mid + 1, end);
    }

    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[start];
        int index = start + 1; //下一个小元素存放位置
        for (int i = index; i < end; i++) {
            if (arr[i] < pivot) {
                swap(arr, index, i);
                index++;
            }
        }
        index--;
        swap(arr, start, index);
        return index;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
