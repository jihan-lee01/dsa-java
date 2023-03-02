package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {
    private static final int INSERTION_SORT_THRESHOLD = 10;

    @Override
    public T[] sort(T[][] input) {
        for (T[] array : input) {
            if (isSorted(array)) continue;
            else if (isReversed(array)) {
                reverse(array);
            } else {
                if (array.length <= INSERTION_SORT_THRESHOLD) {
                    InsertionSort<T> engine = new InsertionSort<>();
                    engine.sort(array, 0, array.length);
                } else {
                    IntroSort<T> engine = new IntroSort<T>(new ShellSortKnuth<>());
                    engine.sort(array, 0, array.length);
                }
            }
        }

        return mergeSubarrays(input);
    }

    protected boolean isSorted(T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    protected boolean isReversed(T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[i - 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    protected void reverse(T[] array) {
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            T tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    protected T[] mergeSubarrays(T[][] input) {
        int k = input.length;
        while (k > 1) {
            int m = (k + 1) / 2;
            for (int i = 0; i < k / 2; i++) {
                input[i] = merge(input[i], input[i + m]);
            }
            k = m;
        }

        return input[0];
    }

    @SuppressWarnings("unchecked")
    private T[] merge(T[] sortedArr, T[] input) {
        T[] mergedArr = (T[]) Array.newInstance(sortedArr[0].getClass(), sortedArr.length + input.length);

        int i = 0, j = 0, k = 0;
        while (i < sortedArr.length && j < input.length) {
            if (sortedArr[i].compareTo(input[j]) <= 0) {
                mergedArr[k++] = sortedArr[i++];
            } else {
                mergedArr[k++] = input[j++];
            }
        }

        while (i < sortedArr.length) {
            mergedArr[k++] = sortedArr[i++];
        }

        while (j < input.length) {
            mergedArr[k++] = input[j++];
        }

        return mergedArr;
    }
}
