package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.replaceAll("\\s", "");
        List<String> result = new ArrayList<>();

        TrieNode<List<String>> node = getRoot();

        for (char ch : prefix.toCharArray()) {
            if (node.getChildrenMap().containsKey(ch))
                node = node.getChildrenMap().get(ch);
            else return result;
        }

        if (node.getValue() != null)
            for (int i = node.getValue().size() - 1; result.size() < getMax() && i >= 0 ; i--)
                result.add(node.getValue().get(i));

        Deque<TrieNode<List<String>>> queue = new ArrayDeque<>();
        queue.addLast(node);


        while (!queue.isEmpty()) {
            TrieNode<List<String>> current = queue.removeFirst();
            if (result.size() == getMax()) break;
            if (current.isEndState()) {
                String word = "";
                TrieNode<List<String>> temp = current;
                while (temp != getRoot()) {
                    word = temp.getKey() + word;
                    temp = temp.getParent();
                }
                if (node.getValue() == null || !node.getValue().contains(word))
                    result.add(word);
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
        prefix = prefix.replaceAll("\\s", "");
        TrieNode<List<String>> node = getRoot();

        for (char ch : prefix.toCharArray()) {
            if (node.getChildrenMap().containsKey(ch))
                node = node.getChildrenMap().get(ch);
            else break;
        }

        List<String> candidates = node.getValue();
        if (candidates == null) candidates = new ArrayList<>();
        candidates.add(candidate);

        node.setValue(candidates);
    }
}
