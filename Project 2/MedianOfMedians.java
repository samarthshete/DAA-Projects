import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MedianOfMedians {
    private static final Random random = new Random();
    private static long operationCount = 0;

    private static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && key < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
                operationCount++;
            }
            arr[j + 1] = key;
        }
    }

    private static int medianOfMedians(int[] arr, int i) {
        if (arr.length <= 5) {
            Arrays.sort(arr);
            return arr[Math.min(i, arr.length - 1)];
        }

        int[] medians = new int[(arr.length + 4) / 5];
        for (int j = 0; j < medians.length; j++) {
            int start = j * 5;
            int end = Math.min(start + 5, arr.length);
            int[] subArray = Arrays.copyOfRange(arr, start, end);
            insertionSort(subArray);
            medians[j] = subArray[subArray.length / 2];
        }

        return medianOfMedians(medians, medians.length / 2);
    }

    private static int[] partition(int[] arr, int pivot) {
        List<Integer> low = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> high = new ArrayList<>();

        for (int num : arr) {
            if (num < pivot) low.add(num);
            else if (num == pivot) equal.add(num);
            else high.add(num);
            operationCount++;
        }

        int[] result = new int[arr.length];
        int index = 0;
        for (int num : low) result[index++] = num;
        for (int num : equal) result[index++] = num;
        for (int num : high) result[index++] = num;

        return new int[]{low.size(), low.size() + equal.size()};
    }

    public static int quickSelect(int[] arr, int k) {
        if (arr.length < 10) {
            Arrays.sort(arr);
            return arr[k];
        }

        int pivot = medianOfMedians(arr, arr.length / 2);
        int[] partitionInfo = partition(arr, pivot);
        int leftSize = partitionInfo[0];
        int middleSize = partitionInfo[1] - leftSize;

        if (k < leftSize) {
            return quickSelect(Arrays.copyOfRange(arr, 0, leftSize), k);
        } else if (k < leftSize + middleSize) {
            return pivot;
        } else {
            return quickSelect(Arrays.copyOfRange(arr, leftSize + middleSize, arr.length), k - leftSize - middleSize);
        }
    }

    public static double theoreticalComplexity(int n) {
        return n; // O(n) complexity
    }

    public static void main(String[] args) {
        int startSize = 100000;
        int maxSize = 1000000;
        int increment = 100000;
        int repeats = 10;

        System.out.printf("%-10s %-25s %-25s %-25s%n", 
                          "Size", "Experimental Time (s)", "Theoretical Time (s)", "Operations");

        double scalingFactor = 0;

        for (int size = startSize; size <= maxSize; size += increment) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = random.nextInt(size);
            }

            int k = size / 2; // Find median

            // Warm-up run
            quickSelect(arr.clone(), k - 1);

            double totalTime = 0;
            long totalOperations = 0;

            for (int r = 0; r < repeats; r++) {
                operationCount = 0;
                long startTime = System.nanoTime();
                int result = quickSelect(arr.clone(), k - 1);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime) / 1e9;
                totalOperations += operationCount;
            }

            double experimentalTime = totalTime / repeats;
            long averageOperations = totalOperations / repeats;

            if (scalingFactor == 0) {
                scalingFactor = experimentalTime / size;
            }

            double theoreticalTime = size * scalingFactor;

            System.out.printf("%-10d, %-25.6f, %-25.6f, %-25d%n", 
                              size, experimentalTime, theoreticalTime, averageOperations);
        }
    }
}