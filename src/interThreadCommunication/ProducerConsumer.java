package interThreadCommunication;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(5, 0);

        Thread producer = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}

class Worker {
    private int sequence = 0;
    private final Integer top;
    private final Integer bottom;
    private final List<Integer> container;
    private final Object lock = new Object();

    public Worker(Integer top, Integer bottom) {
        this.top = top;
        this.bottom = bottom;
        this.container = new ArrayList<>();
    }

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == top) {
                    System.out.println("Container full, waiting for items to be removed...");
                    lock.wait();
                } else {
                    System.out.println(sequence + " Added to container");
                    container.add(sequence++);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == bottom) {
                    System.out.println("Container empty, waiting for the items to be added...");
                    lock.wait();
                } else {
                    System.out.println(container.removeFirst() + " removed from the container");
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }
}


// 🧵 Producer-Consumer Problem: A Quick Recap
// Producer thread(s) generate data and place it in a shared buffer.
// Consumer thread(s) take data from the buffer and process it.
// The shared buffer has limited capacity, making synchronization essential.

// ⚠️ Core Challenges

// 1. Race Conditions
// When multiple threads access and modify shared data concurrently without proper synchronization, inconsistent or corrupted data may result.

// 📍 Example:
// Producer writes a value to buffer.
// Before it marks the buffer as full, the Consumer reads it — possibly reading invalid or incomplete data.
// 🛠️ Solution: Use synchronization (e.g., synchronized, Lock) to make operations atomic.

// 2. Buffer Overflow (Overproduction)
// If the producer is faster than the consumer and the buffer is full, it must wait, or data will be lost or overwritten.
// 🛠️ Solution:
// Producer must check if the buffer is full, and if so, call wait() or block until there's space.

// 3. Buffer Underflow (Overconsumption)
// If the consumer is faster than the producer and the buffer is empty, it must wait — otherwise, it reads invalid data or throws exceptions.
// 🛠️ Solution:
// Consumer must wait if buffer is empty and be notified only when data is available.

// 4. Thread Synchronization
// Proper coordination is needed to avoid:
// Two producers writing at the same time.
// One consumer reading while another is removing the same item.
// Deadlocks and starvation.
// 🛠️ Solution:
// Use mutexes, monitors, or higher-level concurrency primitives.
// Carefully structure wait()/notify() inside synchronized blocks.

// 5. Deadlock
// Occurs when:
// Producer is waiting for consumer to consume,
// And consumer is waiting for producer to produce,
// But neither releases the lock.
// 🛠️ Solution:
// Avoid nested locks and always use while(condition) wait() instead of if().

// 6. Spurious Wakeups
// Threads waiting on wait() might wake up even without a notify() due to JVM optimizations.
// 🛠️ Solution:
// Always check the condition in a while loop, not if.

// 7. Fairness & Starvation
// If notify() always wakes the same thread (e.g., one consumer), other threads (e.g., producers) may starve.
// 🛠️ Solution:
// Use notifyAll() when fairness is needed, or use BlockingQueue which handles fairness better.

// 8. Busy Waiting (Without Proper Waiting)
// Inefficient designs might make the producer/consumer loop continuously checking the buffer in a tight loop.
// 🛠️ Solution:
// Use proper blocking mechanisms (wait(), sleep(), or BlockingQueue), not manual spinlocks.

// 9. Difficulty Scaling with Multiple Producers/Consumers
// As more threads are added, coordination becomes harder.
// Bugs like deadlocks, race conditions, and lost signals become more likely.
// 🛠️ Solution:
// Use well-tested concurrent data structures like BlockingQueue, ConcurrentLinkedQueue, or ReentrantLock + Condition.

// ✅ Summary Table
// Challenge	        Cause	                           Solution
// Race Condition	    Concurrent unsynchronized access   Use locks/synchronized
// Buffer Overflow	    Producer too fast, buffer full	   Wait when buffer full
// Buffer Underflow	    Consumer too fast, buffer empty	   Wait when buffer empty
// Deadlock	            Circular waiting	               Avoid nested locking, use while
// Spurious Wakeups	    JVM optimization	               Use while not if
// Starvation	        Unfair scheduling or wrong notify  Use notifyAll() or fair locks
// Busy Waiting	        Manual spin checks	               Use wait(), sleep(), or blocking queues
// Scaling Issues	    Many threads and shared resources  Use concurrent collections