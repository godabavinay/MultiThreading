package basicMultiThreading;

public class ThreadPriorityExample {
    public static void main (String[] args) {
        System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getPriority());

        Thread one = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getPriority());
        });

        Thread two = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getPriority());
        });

        Thread three = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getPriority());
        });

        one.setPriority(Thread.MAX_PRIORITY);
        two.setPriority(Thread.NORM_PRIORITY);
        three.setPriority(Thread.MIN_PRIORITY);

        one.start();
        two.start();
        three.start();
    }
}
