package strings.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
    Find all occurrences of a pattern in a string.
 */
public class KnuthMorrisPratt {
    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    private List<Integer> findPattern(String pattern, String text) {
        if (pattern.length() > text.length()) return Collections.emptyList();

        String string = pattern + '$' + text;
        int[] prefixArray = computePrefixArray(string);
        ArrayList<Integer> result = new ArrayList<>();
        int patternLength = pattern.length();
        for (int i = patternLength + 1; i < string.length(); i++) {
            if (prefixArray[i] == patternLength) result.add(i - 2 * patternLength);
        }
        return result;
    }

    // Prefix function of a string P is a function s(i)
    // that for each i returns the length of the longest border of the prefix P[0..i]
    private int[] computePrefixArray(String string) {
        int[] result = new int[string.length()];
        result[0] = 0;
        int border = 0;
        for (int i = 1; i < string.length(); i++) {
            while (border > 0 && string.charAt(i) != string.charAt(border))
                border = result[border - 1];
            if (string.charAt(i) == string.charAt(border))
                border = border + 1;
            else
                border = 0;
            result[i] = border;
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> integers) {
        for (int i : integers) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
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
    }
}