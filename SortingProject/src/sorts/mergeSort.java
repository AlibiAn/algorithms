package src.sorts;

public class mergeSort {
    public static void sort(int[] arr, int left, int right) 
    {
        if (left < right) {
            int mid = (left + right) / 2;

            // Sort left half
            Sort(arr, left, mid);

            // Sort right half
            Sort(arr, mid + 1, right);

            // Merge both halves
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right)
    {
         int n1 = mid - left + 1;
         int n2 = right - mid;
 
         int[] L = new int[n1];
         int[] R = new int[n2];
 
         for (int i = 0; i < n1; ++i)
             L[i] = arr[left + i];
         for (int j = 0; j < n2; ++j)
             R[j] = arr[mid + 1 + j];
 
         int i = 0, j = 0, k = left;
 
         while (i < n1 && j < n2) 
         {
             if (L[i] <= R[j]) {
                 arr[k++] = L[i++];
             } else {
                 arr[k++] = R[j++];
             }
         }
 
         while (i < n1)
             arr[k++] = L[i++];
         while (j < n2)
             arr[k++] = R[j++];
    }
}