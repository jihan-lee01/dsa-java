package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    /**
     * @param k1 the index of the first key in {@link #keys}.
     * @param k2 the index of the second key in {@link #keys}.
     * @return a negative integer, zero, or a positive integer as the first key is
     * less than, equal to, or greater than the second key.
     */
    private int compare(int k1, int k2) { return priority.compare(keys.get(k1), keys.get(k2)); }

    @Override
    public int size() { return keys.size(); }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size() - 1);
    }

    private void swim(int k) {
        for(; 0 < k && compare((k - 1) / 3, k) < 0; k = (k - 1) / 3)
            Collections.swap(keys, (k - 1) / 3, k);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 0, size() - 1);
        T max = keys.remove(size() - 1);
        sink();
        return max;
    }

    private void sink() {
        int k = 0;
        int c = 3 * k;
        while (c <= size() - 1) {
            int maxChild = c;
            if (c + 1 <= size() - 1 && compare(c + 1, maxChild) > 0) maxChild = c + 1;
            if (c + 2 <= size() - 1 && compare(c + 2, maxChild) > 0) maxChild = c + 2;
            if (c + 3 <= size() - 1 && compare(c + 3, maxChild) > 0) maxChild = c + 3;
            if (compare(k, maxChild) >= 0) break;
            Collections.swap(keys, k, maxChild);
            k = maxChild;
            c = 3 * k;
        }
    }
}
