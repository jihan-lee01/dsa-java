package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        return getAugmentingPathsAux(graph, new Subgraph(), source, target);
    }

    private Set<Subgraph> getAugmentingPathsAux(Graph graph, Subgraph sub, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();

        if (source == target) {
            augmentingPaths.add(sub);
            return augmentingPaths;
        }

        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;

            tmp = new Subgraph(sub);
            tmp.addEdge(edge);

            Set<Subgraph> paths = getAugmentingPathsAux(graph, tmp, source, edge.getSource());

            augmentingPaths.addAll(paths);
        }

        return augmentingPaths;
    }
}
