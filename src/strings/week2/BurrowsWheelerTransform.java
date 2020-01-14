package strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
    Construct the Burrows–Wheeler transform of a string.
 */
public class BurrowsWheelerTransform {
    private String bwt(String text) {
        List<String> strings = new ArrayList<>(text.length());
        strings.add(text);
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = 0; i < text.length() - 1; i++) {
            stringBuilder.append(stringBuilder.charAt(0));
            stringBuilder.delete(0, 1);
            strings.add(stringBuilder.toString());
        }
        Collections.sort(strings);
        StringBuilder result = new StringBuilder(text.length());
        for (String string : strings) {
            result.append(string.substring(string.length() - 1));
        }
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(bwt(text));
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