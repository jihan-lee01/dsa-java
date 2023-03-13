package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        List<Entity> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            TrieNode<Integer> node = getRoot();
            int j = i;

            while (j < input.length() && node.getChild(input.charAt(j)) != null) {
                node = node.getChild(input.charAt(j));
                j++;
                if (node.isEndState()) {
                    result.add(new Entity(i, j, node.getValue()));
                }
            }
        }

        return result;
    }
}
