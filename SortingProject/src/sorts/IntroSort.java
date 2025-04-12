package src.sorts;

public class IntroSort {
    public static void sort(int[] arr) 
    {
        int maxDepth = 2 * (int) Math.floor(Math.log(arr.length) / Math.log(2));
        introsort(arr, 0, arr.length - 1, maxDepth);
    }

    private static void introsort(int[] arr, int left, int right, int maxDepth)
    {
        int n = right - left + 1;

        if (n < 16)
        {
            InsertionSort.sort(arr, left, right);
        }
        else if (maxDepth == 0)
        {
            HeapSort.sort(arr, left, right);
        }
        else 
        {
            int p = QuickSort.partition(arr, left, right);
            introsort(arr, left, p - 1, maxDepth - 1);
            introsort(arr, p + 1, right, maxDepth - 1);
        }
    }
}