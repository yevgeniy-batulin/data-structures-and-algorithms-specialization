package strings.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    Construct the suffix array of a string.
 */
public class SuffixArrayLong {
    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    private int[] computeSuffixArray(String text) {
        int[] order = sortCharacters(text);
        int[] classes = computeCharClasses(text, order);
        int currentSuffixLength = 1;
        while (currentSuffixLength < text.length()) {
            order = sortDoubled(text, currentSuffixLength, order, classes);
            classes = updateClasses(order, classes, currentSuffixLength);
            currentSuffixLength *= 2;
        }
        return order;
    }

    private int[] sortCharacters(String string) {
        int[] order = new int[string.length()];
        int[] count = new int[5];
        for (int i = 0; i < string.length(); i++) {
            int letterIndex = letterToIndex(string.charAt(i));
            count[letterIndex]++;
        }
        for (int i = 1; i < count.length; i++)
            count[i] = count[i - 1] + count[i];

        for (int i = string.length() - 1; i >= 0; i--) {
            int letterIndex = letterToIndex(string.charAt(i));
            count[letterIndex]--;
            order[count[letterIndex]] = i;
        }
        return order;
    }

    private int letterToIndex(char letter) {
        switch (letter) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                return -1;
        }
    }

    private int[] computeCharClasses(String string, int[] order) {
        int[] classes = new int[string.length()];
        classes[order[0]] = 0;
        for (int i = 1; i < string.length(); i++) {
            if (string.charAt(order[i]) != string.charAt(order[i - 1]))
                classes[order[i]] = classes[order[i - 1]] + 1;
            else
                classes[order[i]] = classes[order[i - 1]];
        }
        return classes;
    }

    private int[] sortDoubled(String string, int currentSuffixLength, int[] order, int[] classes) {
        int[] newOrder = new int[string.length()];
        int[] count = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            count[classes[i]]++;
        }
        for (int i = 1; i < count.length; i++)
            count[i] = count[i - 1] + count[i];

        for (int i = string.length() - 1; i >= 0; i--) {
            int start = (order[i] - currentSuffixLength + string.length()) % string.length();
            int startClass = classes[start];
            count[startClass]--;
            newOrder[count[startClass]] = start;
        }
        return newOrder;
    }

    private int[] updateClasses(int[] order, int[] classes, int currentSuffixLength) {
        int length = order.length;
        int[] newClasses = new int[length];
        newClasses[order[0]] = 0;
        for (int i = 1; i < length; i++) {
            int current = order[i];
            int previous = order[i - 1];
            int middle = current + currentSuffixLength % length;
            int middlePrevious = previous + currentSuffixLength % length;
            if (classes[current] != classes[previous] || classes[middle] != classes[middlePrevious])
                newClasses[current] = newClasses[previous] + 1;
            else
                newClasses[current] = newClasses[previous];
        }
        return newClasses;
    }

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] ints) {
        for (int i : ints) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
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