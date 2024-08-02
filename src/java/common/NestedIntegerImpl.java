package common;

import java.util.List;

class NestedIntegerImpl implements NestedInteger {
    private final boolean isInt;
    private final Integer intVal;
    private final List<NestedInteger> nestedInts;

    NestedIntegerImpl(Integer value) {
        this.isInt = true;
        this.intVal = value;
        this.nestedInts = null;
    }

    NestedIntegerImpl(List<NestedInteger> nestedInts) {
        this.isInt = false;
        this.intVal = null;
        this.nestedInts = nestedInts;
    }

    @Override
    public boolean isInteger() {
        return isInt;
    }

    @Override
    public Integer getInteger() {
        if (!isInt) {
            throw new UnsupportedOperationException("not a int value");
        }
        return intVal;
    }

    @Override
    public List<NestedInteger> getList() {
        if (isInt) {
            throw new UnsupportedOperationException("not a nested list value");
        }
        return nestedInts;
    }

    @Override
    public String toString() {
        if (isInt) {
            return "" + intVal;
        }
        return nestedInts.toString();
    }
}