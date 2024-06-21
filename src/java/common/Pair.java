package common;

import java.util.Map;

public abstract class Pair<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    protected Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public K getLeft() {
        return getKey();
    }

    public V getRight() {
        return getValue();
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public static <K, V> Pair<K, V> immutable(K key, V value) {
        return new Pair<K,V>(key, value) {
            @Override
            public void setKey(K key) {
                throw new UnsupportedOperationException();
            }

            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
