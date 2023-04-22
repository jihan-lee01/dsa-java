package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        MSTPrim prim = new MSTPrim();
        double minWeight = prim.getMinimumSpanningTree(graph).getTotalWeight();

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();
        List<SpanningTree> trees = new ArrayList<>();

        add(queue, visited, graph, 0);

        getMinimumSpanningTreesAux(queue, visited, trees, tree, minWeight, graph);

        return trees;
    }

    private void getMinimumSpanningTreesAux(PriorityQueue<Edge> queue, Set<Integer> visited, List<SpanningTree> trees, SpanningTree tree, double minWeight, Graph graph) {
        if (tree.size() == graph.size() - 1) {
            if (tree.getTotalWeight() == minWeight) trees.add(tree);
            return;
        }

        Edge edge;
        PriorityQueue<Edge> copy = new PriorityQueue<>(queue);

        while(!queue.isEmpty()) {
            edge = queue.poll();
            copy.poll();
            SpanningTree tmp = new SpanningTree(tree);

            if (!visited.contains(edge.getSource())) {
                tmp.addEdge(edge);
                add(copy, visited, graph, edge.getSource());
                getMinimumSpanningTreesAux(copy, visited, trees, tmp, minWeight, graph);
                visited.remove(edge.getSource());
                if (queue.peek() != null && edge.getWeight() != queue.peek().getWeight()) break;
            }

            if (queue.peek() != null && edge.getWeight() == queue.peek().getWeight()) {
                getMinimumSpanningTreesAux(queue, visited, trees, tree, minWeight, graph);
            }
        }
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }
}
