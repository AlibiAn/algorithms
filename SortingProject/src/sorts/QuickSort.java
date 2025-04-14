package src.sorts;

public class QuickSort {
    public static void sort(int[] arr) 
    {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) 
    {
        if (low < high) {
            int pi = partition(arr, low, high);
            if (pi > low) quickSort(arr, low, pi - 1);
            if (pi < high) quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) 
    {
        int pivot = medianOfThree(arr, low, high);
        int i = low - 1;
        
        for(int j = low; j < high; j++) 
        {
            if(arr[j] <= pivot) 
            {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static int medianOfThree(int[] arr, int low, int high) 
    {
        int mid = low + (high - low)/2;
        if(arr[low] > arr[mid]) swap(arr, low, mid);
        if(arr[low] > arr[high]) swap(arr, low, high);
        if(arr[mid] > arr[high]) swap(arr, mid, high);
        swap(arr, mid, high);
        return arr[high];
    }

    private static void swap(int[] arr, int i, int j) 
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}