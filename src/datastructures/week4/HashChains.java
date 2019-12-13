package datastructures.week4;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
    In this problem you will implement a hash table using the chaining scheme.
 */
public class HashChains {
    private FastScanner in;
    private PrintWriter out;
    private List<Deque<String>> hashTable;
    private int bucketCount;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        hashTable = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            hashTable.add(new LinkedList<>());
        }
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                Deque<String> chain = getChain(query.string);
                if (!chain.contains(query.string)) {
                    chain.addFirst(query.string);
                }
                break;
            case "del":
                getChain(query.string).remove(query.string);
                break;
            case "find":
                out.println(getChain(query.string).contains(query.string) ? "yes" : "no");
                break;
            case "check":
                out.println(String.join(" ", hashTable.get(query.index)));
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    private Deque<String> getChain(String string) {
        int hash = hash(string);
        return hashTable.get(hash);
    }

    private int hash(String string) {
        long hash = 0;
        int prime = 1000000007;
        int multiplier = 263;
        for (int i = string.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + string.charAt(i)) % prime;
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String string = in.next();
            return new Query(type, string);
        } else {
            int index = in.nextInt();
            return new Query(type, index);
        }
    }

    static class Query {
        String type;
        String string;
        int index;

        Query(String type, String string) {
            this.type = type;
            this.string = string;
        }

        Query(String type, int index) {
            this.type = type;
            this.index = index;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}