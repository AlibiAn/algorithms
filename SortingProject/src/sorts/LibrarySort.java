package src.sorts;
import java.util.*;

public class LibrarySort {

    static final int GAP = Integer.MIN_VALUE;

    public static List<Integer> librarySort(List<Integer> input) {
        int n = input.size();
        int size = 2 * n;
        Integer[] S = new Integer[size];
        Arrays.fill(S, GAP);

        S[n] = input.get(0);
        int numInserted = 1;

        int round = 1;
        while ((1 << round) <= n) {
            int begin = n - (1 << (round - 1)) + 1;
            int end = n + (1 << (round - 1)) - 1;

            rebalance(S, begin, end);

            int insertStart = 1 << (round - 1);
            int insertEnd = Math.min(1 << round, n);

            for (int i = insertStart; i < insertEnd; i++) {
                int val = input.get(i);
                int pos = binarySearchWithGaps(S, val, begin, end * 2);

                int insertPos = findGapRight(S, pos);
                if (insertPos == -1) throw new RuntimeException("No gap found!");

                shiftRight(S, pos, insertPos);
                S[insertPos] = val;
                numInserted++;
            }

            round++;
        }

        return compact(S);
    }

    private static void rebalance(Integer[] A, int begin, int end) {
        int r = end;
        int w = end * 2;

        while (r >= begin) {
            A[w] = A[r];
            A[w - 1] = GAP;
            r--;
            w -= 2;
        }
    }

    private static int binarySearchWithGaps(Integer[] A, int key, int lo, int hi) {
        List<Integer> values = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = lo; i <= hi && i < A.length; i++) {
            if (A[i] != GAP) {
                values.add(A[i]);
                indices.add(i);
            }
        }

        int idx = Collections.binarySearch(values, key);
        if (idx < 0) idx = -idx - 1;
        if (idx >= indices.size()) return indices.isEmpty() ? lo : indices.get(indices.size() - 1);
        return indices.get(idx);
    }

    private static int findGapRight(Integer[] A, int from) {
        for (int i = from; i < A.length; i++) {
            if (A[i] == GAP) return i;
        }
        return -1;
    }

    private static void shiftRight(Integer[] A, int from, int to) {
        for (int i = to; i > from; i--) {
            A[i] = A[i - 1];
        }
    }

    private static List<Integer> compact(Integer[] A) {
        List<Integer> result = new ArrayList<>();
        for (Integer x : A) {
            if (x != GAP) result.add(x);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(7, 3, 10, 2, 8, 1, 4, 6, 9, 5);
        List<Integer> sorted = librarySort(input);
        System.out.println(sorted);
    }
}
