package src.sorts;

public class CocktailShakerSort {
    public static void sort(int[] arr)
    {
        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;

        while (swapped) {
            swapped = false;

            for (int i = start; i < end; i++) 
            {
                if (arr[i] > arr[i + 1])
                {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) break;

            swapped = false;
            end--;

            for (int i = end - 1; i >= start; i--)
            {
                if (arr[i] > arr[i + 1])
                {
                    swap(arr, i, i+1);
                    swapped = true;
                }
            }
            start++;
        }
    }

    public static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}