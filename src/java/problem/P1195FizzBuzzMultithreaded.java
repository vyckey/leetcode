package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

import org.junit.Assert;
import org.junit.Test;

import util.ThreadUtil;

public class P1195FizzBuzzMultithreaded {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int n = 30;
        List<Object> list = new Vector<>();
        FizzBuzz fizzBuzz = new FizzBuzz(n);
        List<Runnable> tasks = Arrays.asList(
            ThreadUtil.asRunnable(() -> fizzBuzz.fizz(() -> {System.out.println("fizz");list.add("fizz"); })),
            ThreadUtil.asRunnable(() -> fizzBuzz.buzz(() -> {System.out.println("buzz");list.add("buzz"); })),
            ThreadUtil.asRunnable(() -> fizzBuzz.fizzbuzz(() -> {System.out.println("fizzbuzz");list.add("fizzbuzz"); })),
            ThreadUtil.asRunnable(() -> fizzBuzz.number(i -> {System.out.println(i);list.add(i); }))
        );
        ThreadUtil.startThreadsInOrder(tasks).get();

        List<Object> expect = new ArrayList<>(n << 1);
        for (int i = 1; i <= n; i++) {
            if ((i % 3) == 0) {
                expect.add((i % 5) == 0 ? "fizzbuzz" : "fizz");
            } else {
                expect.add((i % 5) == 0 ? "buzz" : i);
            }
        }
        Assert.assertEquals(expect, list);
    }
}
class FizzBuzz {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final int n;
    private volatile int next = 1;
    /**
     * number=0, fizz=1, buzz=2, fizzbuzz=3
     */
    private volatile int role = 0;

    public FizzBuzz(int n) {
        this.n = n;
    }

    private int nextRole() {
        if (next % 3 == 0) {
            return next % 5 == 0 ? 3 : 1;
        }
        return next % 5 == 0 ? 2 : 0;
    }

    private void run(int r, Runnable runnable) throws InterruptedException {
        while (next <= n) {
            lock.lock();
            try {
                while (role != r && next <= n) {
                    condition.await();
                }
                if (next <= n) {
                    runnable.run();
                }
                next++;
                role = nextRole();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        run(1, printFizz);
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        run(2, printBuzz);
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        run(3, printFizzBuzz);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        run(0, () -> printNumber.accept(next));
    }
}