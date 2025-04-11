import src.sorts;
import src.sorts.InsertionSort;

public class TimSort 
{
    static final int RUN = 32;

    public static void timSort(int[] arr) 
    {
        int n = arr.length;
    

    for (int i = 0; i < n; i += RUN)
    {
        int right = Math.min(i + RUN - 1, n - 1);
        InsertionSort.sort(arr, i, right);
    }
    
    for (int size = RUN; size < n; size *= 2) 
    {
        for (int left = 0; left < n; left += 2 * size) 
        {
            int mid = left + size - 1;
            int right = Math.min(left + 2 * size -1, n - 1);
            if (mid < right)
            {
                MergeSort.merge(arr, left, mid ,right);
            }
        }
    }
}
   
}