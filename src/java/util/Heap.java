package util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Heap<E> extends AbstractList<E> {
    private final List<E> elements;
    private final Comparator<E> comparator;

    protected Heap(List<E> elements, Comparator<E> comparator) {
        this.elements = elements;
        this.comparator = comparator;
        build();
    }

    public static <E> Heap<E> heap(List<E> elements, Comparator<E> comparator) {
        return new Heap<>(elements, comparator);
    }

    public static <E extends Comparable<E>> Heap<E> minHeap(List<E> elements) {
        return new Heap<>(elements, Comparator.naturalOrder());
    }

    public static <E extends Comparable<E>> Heap<E> maxHeap(List<E> elements) {
        return new Heap<>(elements, Comparator.reverseOrder());
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    private int compare(int i, int j) {
        return comparator.compare(get(i), get(j));
    }

    private void build() {
        // 从非叶子节点进行堆化
        for (int i = (size() - 2) >> 1; i >=0; i--) {
            heapify(i);
        }
    }

    /**
     * 从{@code nodeIdx}位置自顶向下进行堆化
     * 
     * @param nodeIdx 开始位置
     */
    private void heapify(int nodeIdx) {
        boolean again = true;
        while (again) {
            int leftIdx = (nodeIdx << 1) + 1, rightIdx = leftIdx + 1;
            if (rightIdx < size() && compare(leftIdx, rightIdx) > 0) {
                if (compare(nodeIdx, rightIdx) > 0) {
                    // 右孩子更小
                    swap(nodeIdx, rightIdx);
                    nodeIdx = rightIdx;
                } else {
                    again = false;
                }
            } else if (leftIdx < size() && compare(nodeIdx, leftIdx) > 0) {
                // 左孩子更小
                swap(nodeIdx, leftIdx);
                nodeIdx = leftIdx;
            } else {
                again = false;
            }
        }
    }

    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removed = get(0);
        if (size() > 1) {
            elements.set(0, elements.removeLast());
            heapify(0);
        } else {
            elements.remove(0);
        }
        return removed;
    }

    @Override
    public boolean add(E element) {
        elements.add(element);
        // 自低向上堆化
        int childIdx = size() - 1;
        while (childIdx > 0) {
            int nodeIdx = (childIdx - 1) >> 1;
            if (compare(nodeIdx, childIdx) > 0) {
                swap(childIdx, nodeIdx);
                childIdx = nodeIdx;
            } else {
                break;
            }
        }
        return true;
    }

    private void swap(int i, int j) {
        E e = get(i);
        elements.set(i, get(j));
        elements.set(j, e);
    }

    public static <E> void sort(List<E> elements, Comparator<E> comparator) {
        Heap<E> heap = new Heap<>(new ArrayList<>(elements), comparator.reversed());
        int i = elements.size() - 1;
        while (!heap.isEmpty()) {
            elements.set(i--, heap.remove());
        }
    }

}
