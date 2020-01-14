package strings.week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
    Construct a trie from a collection of patterns.
 */
public class Trie {
    private List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<>();
        int counter = 0;
        for (String pattern : patterns) {
            int currentNode = 0;
            for (int j = 0; j < pattern.length(); j++) {
                char currentSymbol = pattern.charAt(j);
                if (trie.size() <= currentNode) {
                    Map<Character, Integer> map = new HashMap<>();
                    map.put(currentSymbol, ++counter);
                    currentNode = counter;
                    trie.add(map);
                } else if (trie.get(currentNode).get(currentSymbol) == null) {
                    trie.get(currentNode).put(currentSymbol, ++counter);
                    trie.add(Collections.emptyMap());
                    currentNode = counter;
                } else {
                    currentNode = trie.get(currentNode).get(currentSymbol);
                }
            }
        }

        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    private void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }

    private void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    static class FastScanner {
        StringTokenizer tokenizer = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tokenizer.hasMoreElements())
                tokenizer = new StringTokenizer(in.readLine());
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}