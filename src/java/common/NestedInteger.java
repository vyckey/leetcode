package common;

import java.util.Arrays;
import java.util.List;

public interface NestedInteger {
    /**
     * @return true if this NestedInteger holds a single integer, rather than a nested list.
     */
    public boolean isInteger();
    
    /**
     * @return the single integer that this NestedInteger holds, if it holds a single integer. Return null if this NestedInteger holds a nested list
     */
    public Integer getInteger();

    /**
     * @return the nested list that this NestedInteger holds, if it holds a nested list. Return empty list if this NestedInteger holds a single integer
     */
    public List<NestedInteger> getList();

    public static NestedInteger of(Integer value) {
        return new NestedIntegerImpl(value);
    }

    public static NestedInteger of(List<NestedInteger> value) {
        return new NestedIntegerImpl(value);
    }

    public static NestedInteger of(NestedInteger... value) {
        return new NestedIntegerImpl(Arrays.asList(value));
    }
}
