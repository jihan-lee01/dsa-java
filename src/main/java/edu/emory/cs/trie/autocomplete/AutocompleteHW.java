package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        List<String> result = new ArrayList<>();

        TrieNode<List<String>> node = find(prefix);
        if (node == null) return result;

        if (node.getValue() != null)
            for (int i = node.getValue().size() - 1; result.size() < getMax() && i >= 0 ; i--) {
                if (result.size() == getMax()) return result;
                result.add(node.getValue().get(i));
            }

        Deque<TrieNode<List<String>>> queue = new ArrayDeque<>();
        queue.addLast(node);

        while (!queue.isEmpty()) {
            TrieNode<List<String>> current = queue.removeFirst();
            if (current.isEndState()) {
                String word = "";
                TrieNode<List<String>> temp = current;
                while (temp != getRoot()) {
                    word = temp.getKey() + word;
                    temp = temp.getParent();
                }
                if (node.getValue() == null || !node.getValue().contains(word)) {
                    result.add(word);
                    if (result.size() == getMax()) break;
                }
            }

            List<Character> children = new ArrayList<>(current.getChildrenMap().keySet());
            Collections.sort(children);

            for (char ch : children) {
                TrieNode<List<String>> child = current.getChildrenMap().get(ch);
                queue.add(child);
            }
        }
        return result;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        candidate = candidate.trim();

        TrieNode<List<String>> candidateNode = find(candidate);
        if (candidateNode == null) put(candidate, null);
        else if (!candidateNode.isEndState()) candidateNode.setEndState(true);

        TrieNode<List<String>> node = find(prefix);
        if (node == null) {
            put(prefix, null);
            node.setEndState(false);
        }

        List<String> candidates = node.getValue();
        if (candidates == null) candidates = new ArrayList<>();
        candidates.add(candidate);

        node.setValue(candidates);
    }
}
