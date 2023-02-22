package edu.emory.cs.sort.distribution;

public class RadixSortQuiz extends RadixSort{
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sort(array, beginIndex, endIndex, maxBit - 1);
    }

    private void sort(Integer[] array, int beginIndex, int endIndex, int bit) {
        if (bit  < 0) return;

        int[] count = new int[10];

        for (int i = beginIndex; i < endIndex; i++) {
            int key = (array[i] / (int)Math.pow(10, bit)) % 10;
            count[key]++;
            buckets.get(key).add(array[i]);
        }

        int index = beginIndex;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[index++] = buckets.get(i).removeFirst();
            }
        }

        index = beginIndex;
        for (int i = 0; i < 10; i++) {
            int bucketBeginIndex = index;
            int bucketEndIndex = index + count[i];
            sort(array, bucketBeginIndex, bucketEndIndex, bit - 1);
            index = bucketEndIndex;
        }
    }
}
