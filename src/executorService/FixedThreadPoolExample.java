package executorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class FixedThreadPoolExample {
    public static void main(String[] args) {
        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 5; i++) {
                service.execute(new Work(i + 1));
            }
        }
    }
}

class Work implements Runnable {
    public final int taskID;

    public Work(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void run() {
        System.out.println("Executing task " + taskID + " by the thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
