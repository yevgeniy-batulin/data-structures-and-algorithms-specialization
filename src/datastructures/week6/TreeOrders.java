package datastructures.week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
    You are given a rooted binary tree. Build and output its in-order, pre-order and post-order traversals.
 */
public class TreeOrders {
    public static class Solver {
        int n;
        int[] key;
        int[] left;
        int[] right;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }

        private List<Integer> inOrder() {
            return inOrderInternal(0, new ArrayList<>(n));
        }

        // creating a new list on each recursive call really drops running time of the algorithm
        private List<Integer> inOrderInternal(int index, List<Integer> result) {
            if (left[index] != -1)
                inOrderInternal(left[index], result);
            result.add(key[index]);
            if (right[index] != -1)
                inOrderInternal(right[index], result);
            return result;
        }

        private List<Integer> preOrder() {
            return preOrderInternal(0, new ArrayList<>(n));
        }

        private List<Integer> preOrderInternal(int index, List<Integer> result) {
            result.add(key[index]);
            if (left[index] != -1)
                preOrderInternal(left[index], result);
            if (right[index] != -1)
                preOrderInternal(right[index], result);
            return result;
        }

        private List<Integer> postOrder() {
            return postOrderInternal(0, new ArrayList<>(n));
        }

        private List<Integer> postOrderInternal(int index, List<Integer> result) {
            if (left[index] != -1)
                postOrderInternal(left[index], result);
            if (right[index] != -1)
                postOrderInternal(right[index], result);
            result.add(key[index]);
            return result;
        }
    }

    public static void main(String[] args) {
        new Thread(null, () -> {
            try {
                new TreeOrders().run();
            } catch (IOException ignored) {
            }
        }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        StringBuilder result = new StringBuilder();
        for (Integer integer : x) {
            result.append(integer).append(" ");
        }
        System.out.println(result);
        System.out.println();
    }

    private void run() throws IOException {
        Solver solver = new Solver();
        solver.read();
        print(solver.inOrder());
        print(solver.preOrder());
        print(solver.postOrder());
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