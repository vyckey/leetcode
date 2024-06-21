package util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static final ExecutorService THREAD_POOL = Executors.newScheduledThreadPool(4);

    public static Runnable asRunnable(Executable executable) {
        return () -> {
            try {
                executable.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static CompletableFuture<Void> startThreadsInOrder(List<Runnable> tasks) {
        int[] orders = new int[tasks.size()];
        for (int i = 0; i < orders.length; i++) {
            orders[i] = i;
        }
        return startThreadsInOrder(tasks, orders);
    }

    public static CompletableFuture<Void> startThreadsInOrder(List<Runnable> tasks, int[] orders) {
        if (tasks.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        Set<Integer> orderSet = new HashSet<>(orders.length);
        for (int order : orders) {
            if (order < 0 || order >= tasks.size() || !orderSet.add(order)) {
                throw new IllegalArgumentException("invalid orders " + orders);
            }
        }
        CompletableFuture<Void>[] futures = new CompletableFuture[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            final int order = orders[i];
            final Runnable task = tasks.get(order);
            CompletableFuture<Void> future = CompletableFuture.runAsync(task, THREAD_POOL);
            futures[i] = future;
        }
        return CompletableFuture.allOf(futures);
    }

}
