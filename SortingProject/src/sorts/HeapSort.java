package src.sorts;

public class HeapSort {

    public static void sort(int[] arr) 
    {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int low, int high) 
    {
        int heap_size = high - low + 1;
        int offset = low;

        build_Max_Heap(arr, offset, heap_size);

        for (int i = heap_size - 1; i > 0; i--) 
        {
            int temp = arr[offset];
            arr[offset] = arr[offset + i];
            arr[offset + i] = temp;

            max_Heapify(arr, offset, 0, i);
        }
    }

    private static void build_Max_Heap(int[] arr, int offset, int heap_size) 
    {
        for (int i = heap_size / 2 - 1; i >= 0; i--) 
        {
            max_Heapify(arr, offset, i, heap_size);
        }
    }

    private static void max_Heapify(int[] arr, int offset, int i, int heap_size) 
    {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < heap_size && arr[offset + left] > arr[offset + largest]) 
        {
            largest = left;
        }

        if (right < heap_size && arr[offset + right] > arr[offset + largest]) 
        {
            largest = right;
        }

        if (largest != i) 
        {
            int temp = arr[offset + i];
            arr[offset + i] = arr[offset + largest];
            arr[offset + largest] = temp;

            max_Heapify(arr, offset, largest, heap_size);
        }
    }
}
