package src.sorts;

public class IntroSort {
    private static final int SIZE_THRESHOLD = 16;

    public static void sort(int[] arr) 
    {
        int depthLimit = 2 * (int) (Math.log(arr.length) / Math.log(2));
        introSort(arr, 0, arr.length - 1, depthLimit);
    }

    private static void introSort(int[] arr, int low, int high, int depthLimit) 
    {
        int size = high - low + 1;
        if (size <= SIZE_THRESHOLD) 
        {
            InsertionSort.sort(arr, low, high);
        } else if (depthLimit == 0) 
        {
            HeapSort.sort(arr, low, high);
        } else 
        {
            int pivot = partition(arr, low, high);
            introSort(arr, low, pivot - 1, depthLimit - 1);
            introSort(arr, pivot + 1, high, depthLimit - 1);
        }
    }

    private static int partition(int[] arr, int low, int high) 
    {
        int mid = low + (high - low) / 2;
        if (arr[mid] < arr[low]) swap(arr, low, mid);
        if (arr[high] < arr[low]) swap(arr, low, high);
        if (arr[mid] < arr[high]) swap(arr, mid, high);
        int pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) 
        {
            if (arr[j] <= pivot) 
            {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) 
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}