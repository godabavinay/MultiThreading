package interThreadCommunication;

//🧵 wait() and notify() are core tools in inter-thread communication in Java.

//📦 What is Inter-Thread Communication?
//It’s when multiple threads coordinate or communicate with each other using shared objects — often to:
//Wait for a condition to be met (like a buffer being filled).
//Signal other threads to continue execution.

public class WaitNotifyExample {
    public static final Object LOCK = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(WaitNotifyExample::two);

        thread1.start();
        thread2.start();
    }

    public static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Method 1 started executing...");
            LOCK.wait();
            System.out.println("Back to method 1!!!");
        }
    }

    public static void two() {
        synchronized (LOCK) {
            System.out.println("Method 2 started executing...");
            LOCK.notify();
            System.out.println("Method 2 executing even after notifying!");
        }
    }
}
