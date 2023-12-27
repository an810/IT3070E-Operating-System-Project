import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolingDemo {
    public static void main(String[] args) {
        // Create a thread pool with 2 threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit tasks to the thread pool
        for (int i = 0; i < 5; i++) {
            Runnable task = new MyTask("Task " + (i + 1));
            executorService.submit(task);
        }

        // Shut down the thread pool
        executorService.shutdown();
    }
}
class MyTask implements Runnable {
    private String name;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " is running in thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate some task execution time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}