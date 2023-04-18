package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import org.junit.jupiter.api.Test;

public class MinimumSpanningTreeTest {
    @Test
    public void testUndirected() {
        Graph graph = new Graph(5);
        graph.setUndirectedEdge(0, 1, 5 - 2);
        graph.setUndirectedEdge(0, 2, 5 - 1);
        graph.setUndirectedEdge(0, 3, 5 - 4);
        graph.setUndirectedEdge(0, 4, 5 - 3);
        graph.setUndirectedEdge(1, 2, 5 - 3);
        graph.setUndirectedEdge(2, 3, 5 - 1);
        graph.setUndirectedEdge(3, 4, 5 - 2);

        MST prim = new MSTPrim();
        MST kruskal = new MSTKruskal();
        SpanningTree tree;

        tree = prim.getMinimumSpanningTree(graph);
        System.out.println(tree);
        System.out.println(tree.getTotalWeight());

        tree = kruskal.getMinimumSpanningTree(graph);
        System.out.println(tree);
        System.out.println(tree.getTotalWeight());
    }
}
