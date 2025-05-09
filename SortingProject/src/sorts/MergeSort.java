package src.sorts;

public class MergeSort {
    public static void sort(int[] arr) 
    {
        if(arr == null || arr.length == 0) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int l, int r) 
    {
        if(l < r) {
            int m = l + (r - l)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static void merge(int[] arr, int l, int m, int r) 
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;
        while(i < n1 && j < n2) {
            if(L[i] <= R[j]) 
            {
                arr[k] = L[i];
                i++;
            } else 
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while(i < n1) arr[k++] = L[i++];
        while(j < n2) arr[k++] = R[j++];
    }
}