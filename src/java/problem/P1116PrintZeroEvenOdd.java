package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

import org.junit.Assert;
import org.junit.Test;

import util.ThreadUtil;

public class P1116PrintZeroEvenOdd {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int n = 9;
        List<Integer> list = new Vector<>();
        ZeroEvenOdd4 zeroEvenOdd = new ZeroEvenOdd4(n);
        List<Runnable> tasks = Arrays.asList(
            ThreadUtil.asRunnable(() -> zeroEvenOdd.zero(i -> {System.out.println(i);list.add(i); })),
            ThreadUtil.asRunnable(() -> zeroEvenOdd.odd(i -> {System.out.println(i);list.add(i); })),
            ThreadUtil.asRunnable(() -> zeroEvenOdd.even(i -> {System.out.println(i);list.add(i); }))
        );
        ThreadUtil.startThreadsInOrder(tasks).get();

        List<Integer> expect = new ArrayList<>(n << 1);
        for (int i = 0; i < n; i++) {
            expect.add(0);
            expect.add(i + 1);
        }
        Assert.assertEquals(expect, list);
    }

}
/**
 * 使用 {@link Object#wait()} 和 {@link Object#notifyAll()}
 */
class ZeroEvenOdd {
    private final Object lock = new Object();
    /**
     * zero-0,2 odd-1 even-3
     */
    private volatile int role = 0;
    private int n;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized(lock) {
                while (role != 0 && role != 2) {
                    lock.wait();
                }
                printNumber.accept(0);
                role += 1;
                lock.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized(lock) {
                while (role != 3) {
                    lock.wait();
                }
                printNumber.accept(i);
                role = 0;
                lock.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized(lock) {
                while (role != 1) {
                    lock.wait();
                }
                printNumber.accept(i);
                role = 2;
                lock.notifyAll();
            }
        }
    }
}

/**
 * 使用 {@link ReentrantLock} 和 {@link Condition}
 */
class ZeroEvenOdd2 {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile int role = 0;
    private int n;
    
    public ZeroEvenOdd2(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            try {
                lock.lock();
                while (role != 0 && role != 2) {
                    condition.await();
                }
                printNumber.accept(0);
                role += 1;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            try {
                lock.lock();
                while (role != 3) {
                    condition.await();
                }
                printNumber.accept(i);
                role = 0;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            try {
                lock.lock();
                while (role != 1) {
                    condition.await();
                }
                printNumber.accept(i);
                role = 2;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
/**
 * 使用 {@link Thread#yield()}
 */
class ZeroEvenOdd3 {
    private volatile int role = 0;
    private int n;
    
    public ZeroEvenOdd3(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (role != 0 && role != 2) {
                Thread.yield();
            }
            printNumber.accept(0);
            role += 1;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (role != 3) {
                Thread.yield();
            }
            printNumber.accept(i);
            role = 0;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while (role != 1) {
                Thread.yield();
            }
            printNumber.accept(i);
            role = 2;
        }
    }
}

/**
 * 使用 {@link Semaphore}
 */
class ZeroEvenOdd4 {
    private final Semaphore zeroSemaphore = new Semaphore(1);
    private final Semaphore oddSemaphore = new Semaphore(0);
    private final Semaphore evenSemaphore = new Semaphore(0);
    private volatile boolean printEven = false;
    private int n;
    
    public ZeroEvenOdd4(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSemaphore.acquire();
            printNumber.accept(0);
            if (printEven) {
                evenSemaphore.release();
            } else {
                oddSemaphore.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSemaphore.acquire();
            printNumber.accept(i);
            printEven = false;
            zeroSemaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSemaphore.acquire();
            printNumber.accept(i);
            printEven = true;
            zeroSemaphore.release();
        }
    }
}