package problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class P239SlidingWindowMaximum {
    @Test
    public void test1() {
        Assert.assertArrayEquals(new int[]{3}, maxSlidingWindow(new int[]{3}, 1));
        Assert.assertArrayEquals(new int[]{3,3,5,5,6,7}, maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(new int[]{6,6,-2}, maxSlidingWindow(new int[]{-12, 6, -2, -9, -11,     -13, -6},5));
    }

    @Test
    public void test2() {
        Random random = new Random();
        for (int c = 0; c < 100; c++) {
            int[] nums = new int[random.nextInt(1, 10^5)];
            for (int i = 0; i < nums.length; ++i) {
                nums[i] = random.nextInt(-10^4, 10^4);
            }
            for (int i = 0; i < 10; i++) {
                int k = (nums.length > 2) ? random.nextInt(1, nums.length - 1) : 1;
                System.out.println(Arrays.toString(nums));
                int[] maximums1 = maxSlidingWindowNormal(nums, k);
                int[] maximums2 = maxSlidingWindow(nums, k);
                System.out.println(k+":"+Arrays.toString(maximums1));
                Assert.assertArrayEquals(maximums1, maximums2);
                System.out.println("\n");
            }
        }
    }

    /**
     * 使用优先级队列插入和删除
     */
    public int[] maxSlidingWindowNormal(int[] nums, int k) {
        int[] maximums = new int[nums.length - k + 1];
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                queue.add(nums[i]);
                continue;
            }
            queue.add(nums[i]);
            maximums[j++] = queue.peek();
            queue.remove(nums[i - k + 1]);
        }
        return maximums;
    }

    /**
     * 单调队列
     * 堆头保存最大元素，没新加一个元素，就删除掉队尾比新元素小的，然后加到队尾
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] maximums = new int[nums.length + 1 - k];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(nums[i]);
        }
        maximums[0] = deque.peekFirst();

        for (int i = k; i < nums.length; i++) {
            if (nums[i - k] == deque.peekFirst()) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(nums[i]);
            maximums[i + 1 - k] = deque.peekFirst();
        }
        return maximums;
    }

    /**
     * 对元素每k个进行分组，计算其最大前缀和最大后缀
     * maximums[i*k]=max(nums[i*k], suffixMax[k-1])
     * maximums[i*k+j]=max(suffixMax[k-j], prefixMax[j])
     */
    public int[] maxSlidingWindowGroup(int[] nums, int k) {
        int[] prefixMaxArr = new int[k], suffixMaxArr = new int[k];
        prefixMaxArr[0] = suffixMaxArr[0] = Integer.MIN_VALUE;

        int[] maximums = new int[nums.length - k + 1];
        for (int i = 0; i + k <= nums.length; i += k) {
            int m = i + k;
            boolean check = m + k > nums.length;

            for (int j = 0; j < k - 1; ++j) {
                suffixMaxArr[j + 1] = Math.max(suffixMaxArr[j], nums[m - j - 1]);
                if (!check || m + j < nums.length) {
                    prefixMaxArr[j + 1] = Math.max(prefixMaxArr[j], nums[m + j]);
                }
            }

            maximums[i] = Math.max(nums[i], suffixMaxArr[k - 1]);
            for (int j = 1; j < k; ++j) {
                if (check && i + j >= nums.length - k + 1) {
                    break;
                }
                maximums[i + j] = Math.max(suffixMaxArr[k - j], prefixMaxArr[j]);
            }
        }
        return maximums;
    }
}
