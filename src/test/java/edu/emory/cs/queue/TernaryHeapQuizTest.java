package edu.emory.cs.queue;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuizTest extends PriorityQueueTest {
    @Test
    public void testRobustness() {
        List<Integer> keys = List.of(4, 1, 3, 2, 5, 6, 8, 3, 4, 7, 5, 9, 7, 8, 9, 29, 19, 10, 5, 6);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }

    @Test
    public void testRuntime() {
        testRuntime(new BinaryHeap<>(), new TernaryHeapQuiz<>());
    }
}
