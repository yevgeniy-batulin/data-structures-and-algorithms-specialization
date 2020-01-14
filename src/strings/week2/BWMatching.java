package strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
    Implement multiple pattern matching using Burrows-Wheeler Transform.
 */
public class BWMatching {
    // Preprocess the Burrows-Wheeler Transform bwt of some text
    // and compute as a result:
    //   * starts - for each character C in bwt, starts[C] is the first position
    //       of this character in the sorted array of
    //       all characters of the text.
    //   * occ_count_before - for each character C in bwt and each position P in bwt,
    //       occurenceCount[C][P] is the number of occurrences of character C in bwt
    //       from position 0 to position P inclusive.
    private void preprocessBWT(String bwt, Map<Character, Integer> starts, Map<Character, List<Integer>> occurenceCount) {
        char[] firstColumn = bwt.toCharArray();
        Arrays.sort(firstColumn);
        for (int i = 0; i < firstColumn.length; i++) {
            if (starts.size() < 5 && !starts.containsKey(firstColumn[i])) {
                starts.put(firstColumn[i], i);
            }
        }

        List<Integer> zeros = Collections.nCopies(bwt.length() + 1, 0);
        occurenceCount.put('$', new ArrayList<>(zeros));
        occurenceCount.put('A', new ArrayList<>(zeros));
        occurenceCount.put('C', new ArrayList<>(zeros));
        occurenceCount.put('G', new ArrayList<>(zeros));
        occurenceCount.put('T', new ArrayList<>(zeros));

        for (int i = 1; i < bwt.length() + 1; i++) {
            char symbol = bwt.charAt(i - 1);
            for (Map.Entry<Character, List<Integer>> entry : occurenceCount.entrySet()) {
                if (entry.getKey() == symbol)
                    entry.getValue().set(i, entry.getValue().get(i - 1) + 1);
                else
                    entry.getValue().set(i, entry.getValue().get(i - 1));
            }
        }
    }

    // Compute the number of occurrences of string pattern in the text
    // given only Burrows-Wheeler Transform bwt of the text and additional
    // information we get from the preprocessing stage - starts and occurenceCount.
    private int countOccurrences(String pattern, String bwt, Map<Character, Integer> starts, Map<Character, List<Integer>> occurenceCount) {
        int top = 0;
        int bottom = bwt.length() - 1;
        int symbolIndex = pattern.length() - 1;
        while (top <= bottom) {
            if (symbolIndex >= 0) {
                char symbol = pattern.charAt(symbolIndex);
                symbolIndex--;
                int bottomOccurences = occurenceCount.get(symbol).get(bottom + 1);
                int topOccurences = occurenceCount.get(symbol).get(top);
                if (bottomOccurences > topOccurences) {
                    top = starts.get(symbol) + topOccurences;
                    bottom = starts.get(symbol) + bottomOccurences - 1;
                } else {
                    return 0;
                }
            } else {
                return bottom - top + 1;
            }
        }
        return 0;
    }

    static public void main(String[] args) throws IOException {
        new BWMatching().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        // Start of each character in the sorted list of characters of bwt,
        // see the description in the comment about function preprocessBWT
        Map<Character, Integer> starts = new HashMap<>();
        // Occurrence counts for each character and each position in bwt,
        // see the description in the comment about function preprocessBWT
        Map<Character, List<Integer>> occurenceCount = new HashMap<>();
        // Preprocess the BWT once to get starts and occ_count_before.
        // For each pattern, we will then use these precomputed values and
        // spend only O(|pattern|) to find all occurrences of the pattern
        // in the text instead of O(|pattern| + |text|).
        preprocessBWT(bwt, starts, occurenceCount);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = countOccurrences(patterns[i], bwt, starts, occurenceCount);
        }
        print(result);
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