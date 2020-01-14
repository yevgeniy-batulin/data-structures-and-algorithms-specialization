package strings.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
	Find all occurrences of a collection of patterns in a text.
 */
public class TrieMatching implements Runnable {
    private List<Integer> solve(String text, List<String> patterns) {
        List<Node> trie = buildTrie(patterns);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            int position = suffixMatches(text, trie, i);
            if (position != -1) result.add(position);
        }

        return result;
    }

    private int suffixMatches(String text, List<Node> trie, int startingIndex) {
        int currentNode = 0;
        int nextIndex = startingIndex;
        while (true) {
            if (trie.get(currentNode).isLeaf())
                return startingIndex;
            else if (nextIndex < text.length() && trie.get(currentNode).next[letterToIndex(text.charAt(nextIndex))] != -1)
                currentNode = trie.get(currentNode).next[letterToIndex(text.charAt(nextIndex++))];
            else
                return -1;
        }
    }

    private List<Node> buildTrie(List<String> patterns) {
        List<Node> trie = new ArrayList<>();
        trie.add(new Node());
        int counter = 0;
        for (String pattern : patterns) {
            int currentNode = 0;
            for (int i = 0; i < pattern.length(); i++) {
                char currentSymbol = pattern.charAt(i);
                if (trie.size() <= currentNode) {
                    Node node = new Node();
                    node.next[letterToIndex(currentSymbol)] = ++counter;
                    trie.add(node);
                    currentNode = counter;
                } else if (trie.get(currentNode).next[letterToIndex(currentSymbol)] == -1) {
                    trie.get(currentNode).next[letterToIndex(currentSymbol)] = ++counter;
                    trie.add(new Node());
                    currentNode = counter;
                } else {
                    currentNode = trie.get(currentNode).next[letterToIndex(currentSymbol)];
                }
            }
        }

        return trie;
    }

    private int letterToIndex(char letter) {
        switch (letter) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            default:
                assert false;
                return Node.NA;
        }
    }

    static class Node {
        private static final int LETTERS = 4;
        static final int NA = -1;
        int[] next;

        Node() {
            next = new int[LETTERS];
            Arrays.fill(next, NA);
        }

        private boolean isLeaf() {
            return Arrays.stream(next).allMatch(it -> it == -1);
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String text = in.readLine();
            int n = Integer.parseInt(in.readLine());
            List<String> patterns = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                patterns.add(in.readLine());
            }

            List<Integer> answer = solve(text, patterns);

            for (int j = 0; j < answer.size(); j++) {
                System.out.print("" + answer.get(j));
                System.out.print(j + 1 < answer.size() ? " " : "\n");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new TrieMatching()).start();
    }
}