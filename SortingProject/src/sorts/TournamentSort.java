package src.sorts;

import java.util.Arrays;

public class TournamentSort {
    public static void sort(int[] arr) 
    {
        int n = arr.length;
        int treeSize = 1;
        while (treeSize < n) treeSize <<= 1;
        int[] tree = new int[2 * treeSize];
        Arrays.fill(tree, Integer.MAX_VALUE);
        System.arraycopy(arr, 0, tree, treeSize, n);
        
        for (int i = treeSize - 1; i > 0; i--) 
        {
            tree[i] = Math.min(tree[2 * i], tree[2 * i + 1]);
        }
        
        for (int i = 0; i < n; i++) 
        {
            arr[i] = tree[1];
            int j = 1;
            while (j < treeSize) 
            {
                j = 2 * j + (tree[2 * j] == arr[i] ? 0 : 1);
            }
            tree[j] = Integer.MAX_VALUE;
            
            while (j > 1) 
            {
                j /= 2;
                tree[j] = Math.min(tree[2 * j], tree[2 * j + 1]);
            }
        }
    }
}