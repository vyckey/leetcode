package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class P380InsertDeleteGetRandomO1 {
    @Test
    public void test() {
        RandomizedSet obj = new RandomizedSet();
        Assert.assertTrue(obj.insert(10));
        Assert.assertFalse(obj.remove(1));
        Assert.assertEquals(10, obj.getRandom());
        Assert.assertTrue(obj.remove(10));
        Assert.assertFalse(obj.remove(10));

        Assert.assertTrue(obj.insert(1));
        Assert.assertTrue(obj.insert(2));
        Assert.assertTrue(obj.insert(3));
        Assert.assertTrue(obj.insert(4));
        Assert.assertTrue(obj.insert(5));
        Assert.assertTrue(obj.insert(6));

        Assert.assertTrue(1 <= obj.getRandom() && obj.getRandom() <= 6);

        Assert.assertTrue(obj.remove(4));
        Assert.assertTrue(obj.getRandom() != 4);
        // 更多地需要统计随机数的分布情况
    }
    
}
class RandomizedSet {
    private final Map<Integer, Integer> hashtable;
    private final List<Integer> elements;
    private final Random random;

    public RandomizedSet() {
        this.hashtable = new HashMap<>();
        this.elements = new ArrayList<>();
        this.random = new Random();
    }
    
    public boolean insert(int val) {
        if (hashtable.containsKey(val)) {
            return false;
        }
        int index = elements.size();
        hashtable.put(val, index);
        elements.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!hashtable.containsKey(val)) {
            return false;
        }
        int index = hashtable.get(val);
        int newIndex = elements.size() - 1;
        // 交换待删除元素和最后一个元素
        if (index != newIndex) {
            hashtable.put(elements.get(newIndex), index);
            elements.set(index, elements.get(newIndex));
        }
        hashtable.remove(val);
        elements.remove(newIndex);
        return true;
    }
    
    public int getRandom() {
        int randomIdx = random.nextInt(elements.size());
        return elements.get(randomIdx);
    }
}