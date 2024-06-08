package problem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

class SlowMedianFinder {
    private final List<Integer> nums = new ArrayList<>();

    public SlowMedianFinder() {
    }
    
    public void addNum(int num) {
        int left = 0, right = nums.size();
        // 1 3 4
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (num < nums.get(mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (nums.isEmpty() || num < nums.get(left)) {
            nums.add(left, num);
        } else {
            nums.add(left + 1, num);
        }
    }
    
    public double findMedian() {
        int mid = nums.size() >> 1;
        if ((nums.size() & 0x1) == 1) {
            return (double) nums.get(mid);
        }
        return ((double) nums.get(mid - 1) + nums.get(mid)) / 2.0;
    }
}

public class P295FindMedianFromDataStream {

    @Test
    public void test() {
        SlowMedianFinder medianFinder1 = new SlowMedianFinder();
        MedianFinder medianFinder2 = new MedianFinder();
        final Random random = new Random();

        final int opcnt = random.nextInt(1000, 100000);
        for (int i = 0; i < opcnt; i++) {
            if (random.nextBoolean() && i > 0) {
                Assert.assertEquals(medianFinder1.findMedian(), medianFinder2.findMedian(),  10^-5);
            } else {
                int num = random.nextInt(-10^5, 10^5);
                System.out.print(num + ",");
                medianFinder1.addNum(num);
                medianFinder2.addNum(num);
            }
        }
    }

}
/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
class MedianFinder {
    /**
     * 保存小于等于中位数的元素
     */
    private final PriorityQueue<Integer> maxQueue;
    /**
     * 保存大于等于中位数的元素
     */
    private final PriorityQueue<Integer> minQueue;

    public MedianFinder() {
        this.maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
        this.minQueue = new PriorityQueue<>(Integer::compareTo);
    }
    
    public void addNum(int num) {
        if (maxQueue.isEmpty()) {
            minQueue.add(num);
            // maxQueue.add(minQueue.poll());
        } else if (minQueue.isEmpty()) {
            maxQueue.add(num);
            // minQueue.add(maxQueue.poll());
        } else if (num <= maxQueue.peek()) {
            maxQueue.add(num);
        } else {
            minQueue.add(num);
        }
        if (maxQueue.size() - minQueue.size() > 1) {
            minQueue.add(maxQueue.poll());
        } else if (minQueue.size() - maxQueue.size() > 1) {
            maxQueue.add(minQueue.poll());
        }
    }
    
    public double findMedian() {
        double left = maxQueue.isEmpty() ? 0.0 : maxQueue.peek();
        double right = minQueue.isEmpty() ? 0.0 : minQueue.peek();
        if (maxQueue.size() == minQueue.size()) {
            return (left + right) / 2.0;
        }
        return maxQueue.size() > minQueue.size() ? left : right;
    }
}
