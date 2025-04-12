package src;

import src.sorts.*;
import java.io.*;
import java.util.*;

public class Main {
    static final int[] SIZES = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
    static final int NUM_RUNS = 10;
    static final String[] TYPES = {"Random", "Sorted", "Reversed", "PartiallySorted"};

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("src/data/results.csv");
        writer.println("Algorithm, InputType, InputSize, AvgTimeMillis");

        for (int size : SIZES)
        {
            for (String type : TYPES)
            {
                int[] original = generateInput(size, type);
                runAllSorts(original, type, size, writer);
            }
        }

        writer.close();
        System.out.println("Data Saved to results.csv.");
    }

    static void runAllSorts(int[] input, String type, int size, PrintWriter writer) 
    {
        Map<String, RunnableSort> algorithms = Map.of(
            "BubbleSort", BubbleSort::sort,
            "SelectionSort", SelectionSort::sort,
            "InsertionSort", InsertionSort::sort,
            "MergeSort", MergeSort::sort,
            "HeapSort", HeapSort::sort,
            "QuickSort", QuickSort::sort,
            "CocktailShakerSort", CocktailShakerSort::sort,
            "CombSort", CombSort::sort,
            "TournamentSort", TournamentSort::sort,
            "IntroSort", IntroSort::sort,
            "TimSort", TimSort::sort,
            "LibrarySort", LibrarySort::sort
        );
        
        for (Map.Entry<String, RunnableSort> entry : algorithms.entrySet()) {
            long total = 0;
            for (int i = 0; i < NUM_RUNS; i++) {
                int[] copy = Arrays.copyOf(input, input.length);
                long start = System.nanoTime();
                entry.getValue().sort(copy);
                long end = System.nanoTime();
                total += (end - start);
            }
            double avgMillis = total / (NUM_RUNS * 1_000_000.0);
            writer.printf("%s,%s,%d,%.4f\n", entry.getKey(), type, size, avgMillis);
        }
    }

    static int[] generateInput(int size, String type) {
        Random rand = new Random();
        int[] arr = new int[size];

        switch (type) {
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reversed":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "PartiallySorted":
                for (int i = 0; i < size; i++) arr[i] = (i % 5 == 0) ? rand.nextInt(size) : i;
                break;
            default: // Random
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(size);
        }
        return arr;
    }

    // Functional interface for sorting methods
    @FunctionalInterface
    interface RunnableSort {
        void sort(int[] array);
    }
}