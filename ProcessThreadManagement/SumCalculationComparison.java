import java.util.concurrent.*;

public class SumCalculationComparison {

    public static void main(String[] args) {
        int n = 100000000;

        // Calculate sum using a single thread
        long startTimeSingleThread = System.currentTimeMillis();
        long sumSingleThread = calculateSumSingleThread(n);
        long endTimeSingleThread = System.currentTimeMillis();

        System.out.println("Sum calculated using a single thread: " + sumSingleThread);
        System.out.println("Time taken with a single thread: " + (endTimeSingleThread - startTimeSingleThread) + " ms\n");

        // Calculate sum using a thread pool
        long startTimeThreadPool = System.currentTimeMillis();
        long sumThreadPool = calculateSumThreadPool(n);
        long endTimeThreadPool = System.currentTimeMillis();

        System.out.println("Sum calculated using thread pooling: " + sumThreadPool);
        System.out.println("Time taken with thread pooling: " + (endTimeThreadPool - startTimeThreadPool) + " ms");
    }

    private static long calculateSumSingleThread(int n) {
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    private static long calculateSumThreadPool(int n) {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available processors
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        int batchSize = n / numThreads;
        int remainder = n % numThreads;

        // Create a list to store Future<Long> objects
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService);

        // Submit tasks to the thread pool
        int start = 1;
        for (int i = 0; i < numThreads; i++) {
            int end = start + batchSize - 1;
            if (i == numThreads - 1) {
                end += remainder; // Distribute the remainder to the last thread
            }

            // Submit a Callable task to compute the sum in the given range
            int finalStart = start;
            int finalEnd = end;
            completionService.submit(() -> calculatePartialSum(finalStart, finalEnd));

            start = end + 1;
        }

        // Retrieve results from completed tasks
        long totalSum = 0;
        try {
            for (int i = 0; i < numThreads; i++) {
                Future<Long> future = completionService.take();
                totalSum += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shut down the thread pool
        executorService.shutdown();

        return totalSum;
    }

    private static long calculatePartialSum(int start, int end) {
        long partialSum = 0;
        for (int i = start; i <= end; i++) {
            partialSum += i;
        }
        return partialSum;
    }
}
