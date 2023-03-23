package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHWExtra extends Autocomplete<List<Map.Entry<String, Integer>>> {
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.replaceAll("\\s", "");
        List<String> result = new ArrayList<>();

        TrieNode<List<Map.Entry<String, Integer>>> node = getRoot();

        for (char ch : prefix.toCharArray()) {
            if (node.getChildrenMap().containsKey(ch))
                node = node.getChildrenMap().get(ch);
            else return result;
        }

        if (node.getValue() != null) {
            List<Map.Entry<String, Integer>> candidates = node.getValue();
            for (int i = 0; i < Math.min(getMax(), candidates.size()); i++)
                result.add(candidates.get(i).getKey());
        }

        Deque<TrieNode<List<Map.Entry<String, Integer>>>> queue = new ArrayDeque<>();
        queue.addLast(node);

        while (!queue.isEmpty()) {
            TrieNode<List<Map.Entry<String, Integer>>> current = queue.removeFirst();
            if (result.size() == getMax()) break;
            if (current.isEndState()) {
                String word = "";
                TrieNode<List<Map.Entry<String, Integer>>> temp = current;
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
                TrieNode<List<Map.Entry<String, Integer>>> child = current.getChildrenMap().get(ch);
                queue.add(child);
            }
        }
        return result;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.replaceAll("\\s", "");
        TrieNode<List<Map.Entry<String, Integer>>> node = getRoot();

        for (char ch : prefix.toCharArray()) {
            if (node.getChildrenMap().containsKey(ch))
                node = node.getChildrenMap().get(ch);
            else break;
        }

        List<Map.Entry<String, Integer>> candidates = node.getValue();
        if (candidates == null) candidates = new ArrayList<>();

        Map.Entry<String, Integer> candidateWord = null;
        for (Map.Entry<String, Integer> entry : candidates) {
            if (entry.getKey().equals(candidate)) {
                candidateWord = entry;
                break;
            }
        }

        if (candidateWord != null) {
            candidates.remove(candidateWord);
            candidateWord.setValue(candidateWord.getValue() + 1);
        } else {
            candidateWord = new AbstractMap.SimpleEntry<>(candidate, 1);
        }

        candidates.add(candidateWord);
        candidates.sort(getComparator(candidates));

        node.setValue(candidates);
    }

    private Comparator<Map.Entry<String, Integer>> getComparator(List<Map.Entry<String, Integer>> candidates) {
        return (o1, o2) -> {
            int cmp = Integer.compare(o2.getValue(), o1.getValue());
            if (cmp == 0) {
                cmp = Integer.compare(candidates.indexOf(o2), candidates.indexOf(o1));
            }
            if (cmp == 0) {
                cmp = o1.getKey().compareTo(o2.getKey());
            }
            return cmp;
        };
    }
}
