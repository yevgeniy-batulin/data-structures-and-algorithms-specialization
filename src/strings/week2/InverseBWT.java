package strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
    Reconstruct a string from its Burrows–Wheeler transform.
 */
public class InverseBWT {
    private String inverseBWT(String bwt) {
        int[] countArray = new int[5];
        for (int i = 0; i < bwt.length(); i++) {
            countArray[letterToIndex(bwt.charAt(i))]++;
        }

        int[] rankArray = Arrays.copyOf(countArray, countArray.length);
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] = countArray[i - 1] + countArray[i];
        }

        int[] lastColumnToFirstColumnArray = new int[bwt.length()];
        for (int i = 0; i < lastColumnToFirstColumnArray.length; i++) {
            int letterToIndex = letterToIndex(bwt.charAt(i));
            lastColumnToFirstColumnArray[i] = countArray[letterToIndex] - rankArray[letterToIndex];
            rankArray[letterToIndex]--;
        }

        StringBuilder result = new StringBuilder(bwt.length());
        result.append('$');
        int i = 0;
        while (bwt.charAt(i) != '$') {
            result.append(bwt.charAt(i));
            i = lastColumnToFirstColumnArray[i];
        }

        return result.reverse().toString();
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

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
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