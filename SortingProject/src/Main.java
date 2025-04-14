package src;

import src.sorts.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static final int[] SIZES = {1000, 5000, 10000, 100000, 500000, 1000000};
    static final String[] TYPES = {"Random", "Sorted", "Reversed", "PartiallySorted"};
    static final int NUM_RUNS = 10;
    private static final AtomicInteger completed = new AtomicInteger(0);
    private static final int TOTAL_TASKS = SIZES.length * TYPES.length * 12 * NUM_RUNS;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newWorkStealingPool(12);
        
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("memory_results.csv")))) {
            writer.println("Algorithm,InputType,InputSize,AvgTimeMillis,AvgMemoryMB,ValidRuns,Stable");
            
            for (int size : SIZES) {
                for (String type : TYPES) {
                    int[] original = generateInput(size, type);
                    for (String algo : getAlgorithms()) {
                        executor.submit(() -> processAlgorithm(algo, original, size, type, writer));
                    }
                }
            }
            
            executor.shutdown();
            scheduleProgressUpdates();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\nData collection complete!");
    }

    private static List<String> getAlgorithms() {
        return List.of(
            "BubbleSort", "SelectionSort", "InsertionSort", "MergeSort",
            "HeapSort", "QuickSort", "CocktailShakerSort", "CombSort",
            "TournamentSort", "IntroSort", "TimSort", "LibrarySort"
        );
    }

    private static void processAlgorithm(String algo, int[] original, int size, 
                                       String type, PrintWriter writer) {
        try {
            long totalTime = 0;
            long totalMemory = 0;
            int validRuns = 0;
            
            for (int i = 0; i < NUM_RUNS; i++) {
                int[] copy = Arrays.copyOf(original, original.length);
                
                System.gc();
                long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                
                long start = System.nanoTime();
                sortWithAlgorithm(algo, copy);
                long end = System.nanoTime();
                
                long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long memUsed = Math.max(0, memAfter - memBefore);
                
                if (validate(copy, original)) {
                    totalTime += (end - start);
                    totalMemory += memUsed;
                    validRuns++;
                }
                completed.incrementAndGet();
            }
            
            if (validRuns > 0) {
                double avgTime = totalTime / (validRuns * 1_000_000.0);
                double avgMemory = (totalMemory / (1024.0 * 1024.0)) / validRuns;
                boolean isStable = isAlgorithmStable(algo);
                
                synchronized(writer) {
                    writer.printf("%s,%s,%d,%.4f,%.2f,%d,%b%n", 
                        algo, type, size, avgTime, avgMemory, validRuns, isStable);
                    writer.flush();
                }
            }
        } catch (Exception e) {
            System.err.printf("%s failed for %s %d: %s%n", algo, type, size, e.getMessage());
        }
    }

    private static boolean isAlgorithmStable(String algo) {
        return switch (algo) {
            case "MergeSort", "InsertionSort", "TimSort", "LibrarySort", "CocktailShakerSort" -> true;
            default -> false;
        };
    }

    private static void sortWithAlgorithm(String algo, int[] arr) throws Exception {
        switch (algo) {
            case "BubbleSort" -> BubbleSort.sort(arr);
            case "SelectionSort" -> SelectionSort.sort(arr);
            case "InsertionSort" -> InsertionSort.sort(arr);
            case "MergeSort" -> MergeSort.sort(arr);
            case "HeapSort" -> HeapSort.sort(arr);
            case "QuickSort" -> QuickSort.sort(arr);
            case "CocktailShakerSort" -> CocktailShakerSort.sort(arr);
            case "CombSort" -> CombSort.sort(arr);
            case "TournamentSort" -> TournamentSort.sort(arr);
            case "IntroSort" -> IntroSort.sort(arr);
            case "TimSort" -> TimSort.sort(arr);
            case "LibrarySort" -> LibrarySort.sort(arr);
        }
    }

    private static boolean validate(int[] sorted, int[] original) {
        if (sorted.length != original.length) return false;
        
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i-1] > sorted[i]) return false;
        }
        
        int[] count = new int[original.length];
        for (int num : original) count[num]++;
        for (int num : sorted) {
            if (--count[num] < 0) return false;
        }
        return true;
    }

    private static int[] generateInput(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random();
        
        switch (type) {
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reversed":
                for (int i = 0; i < size; i++) arr[i] = size - i - 1;
                break;
            case "PartiallySorted":
                for (int i = 0; i < size; i++)
                    arr[i] = (i % 5 == 0) ? rand.nextInt(size) : i;
                break;
            default:
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(size);
        }
        return arr;
    }

    private static void scheduleProgressUpdates() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            int done = completed.get();
            double progress = (done * 100.0) / TOTAL_TASKS;
            System.out.printf("\rProgress: %.1f%% (%d/%d)", progress, done, TOTAL_TASKS);
        }, 1, 1, TimeUnit.SECONDS);
    }
}