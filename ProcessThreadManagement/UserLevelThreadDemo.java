import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserLevelThreadDemo {
    public static void main(String[] args) {
        // Create an ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Create CompletableFuture tasks
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 1 is running");
                sleep(1000);
            }
        }, executorService);

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 2 is running");
                sleep(1000);
            }
        }, executorService);

        // Wait for both tasks to complete
        CompletableFuture.allOf(task1, task2).join();

        // Shut down the ExecutorService
        executorService.shutdown();
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
