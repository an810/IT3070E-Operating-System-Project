import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    private static final int TASK_COUNT = 20;
    private static final int THREAD_POOL_SIZE = 4;

    public static void main(String[] args) {
        // Conventional approach
        long startConventional = System.currentTimeMillis();
        runConventionalTasks();
        long endConventional = System.currentTimeMillis();
        System.out.println("Conventional approach took: " + (endConventional - startConventional) + " ms");

        // Thread pool approach
        long startThreadPool = System.currentTimeMillis();
        runThreadPoolTasks();
        long endThreadPool = System.currentTimeMillis();
        System.out.println("Thread pool approach took: " + (endThreadPool - startThreadPool) + " ms");
    }

    private static void runConventionalTasks() {
        for (int i = 0; i < TASK_COUNT; i++) {
            performTaskWithDelay(i);
        }
    }

    private static void runThreadPoolTasks() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int i = 0; i < TASK_COUNT; i++) {
            final int taskNumber = i;
            executorService.submit(() -> performTaskWithDelay(taskNumber));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private static void performTaskWithDelay(int taskNumber) {
        // Print information about the current thread before the delay
        System.out.println("Task " + taskNumber + " started by thread: " + Thread.currentThread().getName());

        // Simulate a network call or I/O operation
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Print information about the current thread after the delay
        System.out.println("Task " + taskNumber + " completed by thread: " + Thread.currentThread().getName());
    }

}