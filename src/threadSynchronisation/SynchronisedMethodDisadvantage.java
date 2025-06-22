package threadSynchronisation;

//🔐 If a thread acquires an intrinsic lock on an object:
//It means the thread has exclusive access to the monitor (lock) associated with that specific object — not the entire object in every context, but rather:

//✅ What it does mean:
//The thread has acquired the lock on the object used in the synchronized statement or method.

//Other threads cannot enter:
//Any other synchronized method of that object.
//Any synchronized block that locks on the same object.
//Non-synchronized methods are not affected — they can still be called by other threads concurrently.

//❌ What it does NOT mean:
//It does not lock all code in the object — only the parts explicitly marked as synchronized.
//It does not prevent access to non-synchronized methods or fields.
//It does not lock other objects, even if they reference the same data.

public class SynchronisedMethodDisadvantage {
    private static int counter1 = 0;
    private static int counter2 = 0;

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

    public synchronized static void increment1() {
        counter1++;
    }

    public synchronized static void increment2() {
        counter2++;
    }
}
