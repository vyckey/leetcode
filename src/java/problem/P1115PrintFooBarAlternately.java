package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;
import org.junit.Test;

import util.ThreadUtil;

public class P1115PrintFooBarAlternately {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int n = 10;
        List<Integer> list = new Vector<>();
        FooBar6 fooBar = new FooBar6(n);
        List<Runnable> tasks = Arrays.asList(
            ThreadUtil.asRunnable(() -> fooBar.foo(() -> {System.out.println("foo");list.add(1); })),
            ThreadUtil.asRunnable(() -> fooBar.bar(() -> {System.out.println("bar");list.add(2); }))
        );
        ThreadUtil.startThreadsInOrder(tasks).get();

        List<Integer> expect = new ArrayList<>(n << 1);
        for (int i = 0; i < n; i++) {
            expect.add(1);
            expect.add(2);
        }
        Assert.assertEquals(expect, list);
    }

}
/**
 * 使用synchronized、Object#wait、Object#notify
 */
class FooBar {
    private final Object lock = new Object();
    private volatile boolean first = true;
    private final int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized(lock) {
                if (!first) {
                    lock.wait();
                }
                printFoo.run();
                first = false;
                lock.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized(lock) {
                if (first) {
                    lock.wait();
                }
                printBar.run();
                first = true;
                lock.notify();
            }
        }
    }
}

/**
 * 使用Thread#yield让出CPU
 */
class FooBar2 {
    private volatile boolean first = true;
    private int n;

    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n;) {
            if (first) {
                printFoo.run();
                i++;
                first = false;
            } else {
                Thread.yield();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n;) {
            if (!first) {
                printBar.run();
                i++;
                first = true;
            } else {
                Thread.yield();
            }
        }
    }
}
/**
 * 使用Semaphore实现
 */
class FooBar3 {
    private final Semaphore foo = new Semaphore(1);
    private final Semaphore bar = new Semaphore(0);
    private int n;

    public FooBar3(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
}

/**
 * 使用CyclicBarrier实现
 */
class FooBar4 {
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    private volatile boolean first = true;
    private int n;

    public FooBar4(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!first);
            printFoo.run();
            first = false;
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            printBar.run();
            first = true;
        }
    }
}

/**
 * 使用BlockingQueue实现
 */
class FooBar5 {
    private final BlockingQueue<Integer> fooQueue = new LinkedBlockingDeque<>(1);
    private final BlockingQueue<Integer> barQueue = new LinkedBlockingDeque<>(1);
    private int n;

    public FooBar5(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        fooQueue.offer(0);
        for (int i = 0; i < n; i++) {
            fooQueue.take();
            printFoo.run();
            barQueue.offer(i);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barQueue.take();
            printBar.run();
            fooQueue.offer(i);
        }
    }
}

/**
 * 使用ReentrantLock实现
 */
class FooBar6 {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile boolean first = true;
    private int n;

    public FooBar6(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                if (!first) {
                    condition.await();
                }
                printFoo.run();
                first = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                if (first) {
                    condition.await();
                }
                printBar.run();
                first = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}