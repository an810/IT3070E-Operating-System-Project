import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolVsNoThreadPool {

    private static final int NUM_TASKS = 1000; // Number of independent tasks

    public static void main(String[] args) {
        System.out.println("Without Thread Pool:");
        runWithoutThreadPool();

        System.out.println("\nWith Thread Pool:");
        runWithThreadPool();
    }

    private static void runWithoutThreadPool() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_TASKS; i++) {
            performTask(i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken without thread pool: " + (endTime - startTime) + " ms");
    }

    private static void runWithThreadPool() {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < NUM_TASKS; i++) {
            final int taskNumber = i;
            executorService.submit(() -> performTask(taskNumber));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Wait for all tasks to complete
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with thread pool: " + (endTime - startTime) + " ms");
    }

    private static void performTask(int taskNumber) {
        // Simulate some independent task
        double result = Math.sqrt(taskNumber * 1000);
        System.out.println("Task " + taskNumber + " completed. Result: " + result);
    }
}

