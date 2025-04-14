package src.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibrarySort {
    private static final int GAP = Integer.MIN_VALUE;
    
    public static void sort(int[] arr) 
    {
        List<Integer> sorted = librarySort(arr);
        for(int i = 0; i < arr.length; i++) 
        {
            arr[i] = sorted.get(i);
        }
    }

    private static List<Integer> librarySort(int[] input) 
    {
        if(input.length == 0) return new ArrayList<>();
        int n = input.length;
        int size = nextPowerOfTwo(n) * 2;
        Integer[] S = new Integer[size];
        Arrays.fill(S, GAP);
        
        S[size/2] = input[0];
        int numElements = 1;
        int level = 1;

        while((1 << (level-1)) <= numElements) 
        {
            int segment = 1 << (level-1);
            int start = (size/2) - segment;
            int end = (size/2) + segment;
            rebalance(S, start, end);

            int insertStart = 1 << (level-1);
            int insertEnd = Math.min(1 << level, n);
            
            for(int i = insertStart; i < insertEnd; i++) 
            {
                if(i >= input.length) break;
                int val = input[i];
                int pos = binarySearch(S, val, 0, size-1);
                int insertPos = findFreeSlot(S, pos);
                
                if(insertPos != -1)
                 {
                    shiftAndInsert(S, insertPos, val);
                    numElements++;
                }
            }
            level++;
        }
        
        return compact(S);
    }

    private static void rebalance(Integer[] S, int start, int end) 
    {
        List<Integer> elements = new ArrayList<>();
        for(int i = start; i <= end; i++) 
        {
            if(S[i] != GAP) elements.add(S[i]);
        }
        
        Arrays.fill(S, start, end+1, GAP);
        int newSize = end - start + 1;
        int spacing = newSize / (elements.size() + 1);
        
        for(int i = 0; i < elements.size(); i++) 
        {
            int pos = start + spacing * (i + 1);
            S[pos] = elements.get(i);
        }
    }

    private static int binarySearch(Integer[] S, int target, int low, int high) 
    {
        while(low <= high) 
        {
            int mid = (low + high) >>> 1;
            int actualMid = findNearestElement(S, mid);
            
            if(actualMid == -1) return low;
            if(S[actualMid] == target) return actualMid;
            
            if(S[actualMid] < target) 
            {
                low = Math.max(mid, actualMid) + 1;
            } else 
            {
                high = Math.min(mid, actualMid) - 1;
            }
        }
        return low;
    }

    private static int findNearestElement(Integer[] S, int pos) 
    {
        int left = pos;
        int right = pos;
        while(left >= 0 || right < S.length) 
        {
            if(left >= 0 && S[left] != GAP) return left;
            if(right < S.length && S[right] != GAP) return right;
            left--;
            right++;
        }
        return -1;
    }

    private static int findFreeSlot(Integer[] S, int pos) 
    {
        for(int i = pos; i < S.length; i++) 
        {
            if(S[i] == GAP) return i;
        }
        for(int i = pos-1; i >= 0; i--) 
        {
            if(S[i] == GAP) return i;
        }
        return -1;
    }

    private static void shiftAndInsert(Integer[] S, int pos, int val) 
    {
        int freeSpot = pos;
        while(freeSpot < S.length && S[freeSpot] != GAP) freeSpot++;
        
        if(freeSpot == S.length) 
        {
            for(int i = pos; i > 0; i--) S[i] = S[i-1];
        } else 
        {
            for(int i = freeSpot; i > pos; i--) S[i] = S[i-1];
        }
        S[pos] = val;
    }

    private static List<Integer> compact(Integer[] S) 
    {
        List<Integer> result = new ArrayList<>();
        for(Integer num : S) 
        {
            if(num != GAP) result.add(num);
        }
        return result;
    }

    private static int nextPowerOfTwo(int n) 
    {
        if(n == 0) return 1;
        return Integer.highestOneBit(n) << 1;
    }
}