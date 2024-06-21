package problem;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import util.ThreadUtil;

public class P1114PrintInOrder {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        Foo foo = new Foo();
        List<Integer> list = new Vector<>();
        List<Runnable> tasks = Arrays.asList(
            ThreadUtil.asRunnable(() -> foo.first(() -> {System.out.println("first");list.add(1); })),
            ThreadUtil.asRunnable(() -> foo.second(() -> {System.out.println("second");list.add(2); })),
            ThreadUtil.asRunnable(() -> foo.third(() -> {System.out.println("third");list.add(3); }))
        );
        ThreadUtil.startThreadsInOrder(tasks, new int[]{1,0,2}).get();
        Assert.assertEquals(Arrays.asList(1,2,3), list);
    }
}
class Foo {
    private final AtomicInteger integer;

    public Foo() {
        this.integer = new AtomicInteger();
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        integer.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (integer.get() != 1);
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        integer.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (integer.get() != 2);
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}