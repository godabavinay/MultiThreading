package executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            for (int i = 1; i <= 5; i++) {
                service.execute(new Task(i));
            }
        }
    }
}

class Task implements Runnable {
    private final int taskID;

    public Task(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void run() {
        System.out.println("Task " +  taskID + " executed by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
