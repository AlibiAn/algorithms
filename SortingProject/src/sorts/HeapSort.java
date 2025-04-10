package src.sorts;

public class HeapSort {
    public static void sort(int[] arr) {
        int heap_size = arr.length;
        build_Max_Heap(arr, heap_size);

        for (int i = heap_size - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heap_size--;
            max_Heapify(arr, 0, heap_size);
        }
    }

    public static void build_Max_Heap(int[] arr, int heap_size) {
        for (int i = heap_size / 2 - 1; i >= 0; i--) {
            max_Heapify(arr, i, heap_size);
        }
    }

    public static void max_Heapify(int[] arr, int i, int heap_size) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < heap_size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < heap_size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            max_Heapify(arr, largest, heap_size);
        }
    }
}
