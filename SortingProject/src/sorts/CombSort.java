package src.sorts;

import src.sorts.CocktailShakerSort;
import src.sorts.QuickSort;

public class CombSort {
    public static void sort(int[] arr) 
    {
        int n = arr.length;
        int gap = n;
        double shrink = 1.3;
        boolean sorted = false;

        while (!sorted) 
        {
            gap = (int) Math.floor(gap / shrink);
            if (gap <= 1)
            {
                gap = 1;
                sorted = true;
            }
            else if (gap == 9 || gap == 10)
            {
                gap = 11; //Rule of 11
            }
            
            for (int i = 0; i + gap < n; i++) 
            {
                if (arr[i] > arr[i + gap]) 
                {
                    swap(arr, i, i + gap);
                    sorted = false;
                }
            }
        }
    }
    private static void swap(int[] arr, int i, int j) 
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}