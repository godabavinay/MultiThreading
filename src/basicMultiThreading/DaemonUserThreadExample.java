package basicMultiThreading;

public class DaemonUserThreadExample {
    public static void main (String[] args) {
        Thread bgThread = new Thread(new BgThread());
        Thread userThread = new Thread(new UserThread());
        Thread bgThread2 = new Thread(() -> {
           System.out.println("BgThread created using Thread class");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("BgThread created using Thread class executed");
        });

        bgThread.setDaemon(true);
        bgThread2.setDaemon(true);
        bgThread.start();
        userThread.start();
        bgThread2.start();
    }
}

// Daemon threads are the threads that run in background once the user threads are executed JVM stops the daemon threads.

class BgThread implements Runnable {
    @Override
    public void run() {
        int count = 0;

        while (count < 500) {
            System.out.println("BgThread: " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
        }

        System.out.println("BgThread executed");
    }
}

class UserThread implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("User Thread started");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User Thread executed");
    }
}
