package problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

public class ProducerConsumerTest {
    private static final ExecutorService THREAD_POOL = Executors.newScheduledThreadPool(4);
    private final AtomicInteger integer = new AtomicInteger(1);
    
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int capacity = 10;
        ProducerConsumer<Integer> producerConsumer = new ProducerConsumer2<>(capacity);
        List<Runnable> tasks = Arrays.asList(newProducer(
            producerConsumer, "1"), newProducer(producerConsumer, "2"),
            newConsumer(producerConsumer, "1"), newConsumer(producerConsumer, "2"), newConsumer(producerConsumer, "3")
        );
        startThreadsInOrder(tasks).get();
    }

    private Runnable newProducer(ProducerConsumer<Integer> producerConsumer, String name) {
        return asRunnable(() -> producerConsumer.produce(() -> {
            System.out.println("producer " + name + ": produce data " + integer.get());
            return integer.getAndIncrement();
        }));
    }

    private Runnable newConsumer(ProducerConsumer<Integer> producerConsumer, String name) {
        return asRunnable(() -> producerConsumer.consume(i -> {
            System.out.println("consumer" + name + ": consume data " + i);
        }));
    }

    private static CompletableFuture<Void> startThreadsInOrder(List<Runnable> tasks) {
        CompletableFuture<Void>[] futures = new CompletableFuture[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            final Runnable task = tasks.get(i);
            CompletableFuture<Void> future = CompletableFuture.runAsync(task, THREAD_POOL);
            futures[i] = future;
        }
        return CompletableFuture.allOf(futures);
    }

    private static Runnable asRunnable(Executable executable) {
        return () -> {
            try {
                executable.execute();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    @FunctionalInterface
    public interface Executable {
        void execute() throws Exception;
    }
}
abstract class ProducerConsumer<T> {
    protected final int capacity;

    protected ProducerConsumer(int capacity) {
        this.capacity = capacity;
    }

    public abstract void produce(Supplier<T> supplier) throws InterruptedException;

    public abstract void consume(Consumer<T> consumer) throws InterruptedException;
}

class ProducerConsumer1<T> extends ProducerConsumer<T> {
    private final Object lock = new Object();
    private final Queue<T> queue = new LinkedList<>();
    private volatile int size = 0;

    public ProducerConsumer1(int capacity) {
        super(capacity);
    }

    @Override
    public void produce(Supplier<T> supplier) throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            synchronized (lock) {
                while (size >= capacity) {
                    lock.wait();
                }
                queue.offer(supplier.get());
                ++size;
                lock.notifyAll();
            }
        }
    }

    @Override
    public void consume(Consumer<T> consumer) throws InterruptedException {
        while (true) {
            Thread.sleep(1200);
            synchronized (lock) {
                while (size <= 0) {
                    lock.wait();
                }
                consumer.accept(queue.poll());
                --size;
                lock.notifyAll();
            }
        }
    }
}

class ProducerConsumer2<T> extends ProducerConsumer<T> {
    private final Semaphore mutex;
    private final Semaphore size;
    private final Semaphore empty;
    private final Queue<T> queue = new LinkedList<>();

    public ProducerConsumer2(int capacity) {
        super(capacity);
        this.mutex = new Semaphore(1);
        this.size = new Semaphore(capacity);
        this.empty = new Semaphore(0);
    }

    @Override
    public void produce(Supplier<T> supplier) throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            try {
                mutex.acquire();
                size.acquire();
                queue.offer(supplier.get());
            } finally {
                empty.release();
                mutex.release();
            }
        }
    }

    @Override
    public void consume(Consumer<T> consumer) throws InterruptedException {
        while (true) {
            Thread.sleep(1200);
            try {
                mutex.acquire();
                empty.acquire();
                consumer.accept(queue.poll());
            } finally {
                size.release();
                mutex.release();
            }
        }
    }
}