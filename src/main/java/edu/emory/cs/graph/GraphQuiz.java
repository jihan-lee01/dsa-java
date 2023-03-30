package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.*;

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        Set<List<Integer>> cycles = new HashSet<>();
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while(!notVisited.isEmpty())
            numberOfCyclesAux(notVisited.poll(), notVisited, new HashSet<>(), new ArrayList<>(), cycles);

        return cycles.size();
    }

    private void numberOfCyclesAux(int target, Deque<Integer> notVisited, Set<Integer> visited, List<Integer> path, Set<List<Integer>> cycles) {
        notVisited.remove(target);
        visited.add(target);
        path.add(target);

        for (Edge edge : getIncomingEdges(target)) {
            int source = edge.getSource();
            if (visited.contains(source)) {
                List<Integer> cycle = new ArrayList<>();
                int index = path.indexOf(source);
                for (int i = path.size() - 1; i >= index + 1; i--) {
                    cycle.add(path.get(i));
                }
                cycle.add(source);
                cycles.add(cycleAux(cycle));
            }
            else {
                numberOfCyclesAux(source, notVisited, new HashSet<>(visited), new ArrayList<>(path), cycles);
            }
        }

        path.remove(path.size() - 1);
    }

    private List<Integer> cycleAux(List<Integer> cycle) {
        int minIndex = 0;
        for (int i = 1; i < cycle.size(); i++) {
            if (cycle.get(i) < cycle.get(minIndex)) {
                minIndex = i;
            }
        }
        List<Integer> cycleHelper = new ArrayList<>(cycle.size());
        for (int i = 0; i < cycle.size(); i++) {
            cycleHelper.add(cycle.get((minIndex + i) % cycle.size()));
        }
        return cycleHelper;
    }
}
