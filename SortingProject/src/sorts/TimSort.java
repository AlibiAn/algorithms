package src.sorts;

import java.util.Arrays;

public class TimSort {
    static final int MIN_MERGE = 32;

    public static void sort(int[] arr) 
    {
        int n = arr.length;
        if(n < 2) return;

        for(int i = 0; i < n; i += MIN_MERGE) 
        {
            int end = Math.min(i + MIN_MERGE - 1, n - 1);
            insertionSort(arr, i, end);
        }

        int size = MIN_MERGE;
        while(size < n) 
        {
            for(int left = 0; left < n; left += 2 * size) 
            {
                int mid = Math.min(left + size - 1, n - 1);
                int right = Math.min(left + 2 * size - 1, n - 1);
                if(mid < right) merge(arr, left, mid, right);
            }
            size *= 2;
        }
    }

    private static void insertionSort(int[] arr, int left, int right) 
    {
        for(int i = left + 1; i <= right; i++) 
        {
            int key = arr[i];
            int j = i - 1;
            while(j >= left && arr[j] > key) 
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void merge(int[] arr, int l, int m, int r) 
    {
        int len1 = m - l + 1;
        int len2 = r - m;
        
        int[] left = Arrays.copyOfRange(arr, l, m + 1);
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;
        while(i < len1 && j < len2) 
        {
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while(i < len1) arr[k++] = left[i++];
        while(j < len2) arr[k++] = right[j++];
    }
}