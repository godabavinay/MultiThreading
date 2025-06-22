package threadSynchronisation;

//Condition	                            Behavior
//Same lock object	                    Only one thread enters at a time
//Different lock objects	            Both threads can proceed in parallel
//Lock object is this in both cases	    Threads block each other

public class SynchronisedBlockExample {
    private static int counter1 = 0;
    private static int counter2 = 0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment1();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment2();
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.println("Counter values: " + counter1 + " --- " + counter2);
    }

    public static void increment1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    public static void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }
}
