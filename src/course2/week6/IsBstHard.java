package course2.week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    You are given a binary tree with integers as its keys.
    You need to test whether it is a correct binary search tree.
    The definition of the binary search tree is the following:
    for any node of the tree, if its key is 𝑥, then for any node in its left subtree its key must be strictly less than 𝑥,
    and for any node in its right subtree its key must be greater than or equal to 𝑥.
 */
public class IsBstHard {
    static class Node {
        int key;
        int left;
        int right;

        Node(int key, int left, int right) {
            this.left = left;
            this.right = right;
            this.key = key;
        }
    }

    static class Solver {
        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            if (tree.length == 0)
                return true;
            else
                return isBinaryInternal(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        boolean isBinaryInternal(int index, int lower, int upper) {
            if (tree[index].key < lower || tree[index].key + 1 > upper) return false;

            boolean left = true;
            if (tree[index].left != -1)
                left = isBinaryInternal(tree[index].left, lower, tree[index].key);

            boolean right = true;
            if (left && tree[index].right != -1)
                right = isBinaryInternal(tree[index].right, tree[index].key, upper);

            return left && right;
        }

        private void run() throws IOException {
            Solver tree = new Solver();
            tree.read();
            if (tree.isBinarySearchTree()) {
                System.out.println("CORRECT");
            } else {
                System.out.println("INCORRECT");
            }
        }

        static class FastScanner {
            StringTokenizer tok = new StringTokenizer("");
            BufferedReader in;

            FastScanner() {
                in = new BufferedReader(new InputStreamReader(System.in));
            }

            String next() throws IOException {
                while (!tok.hasMoreElements())
                    tok = new StringTokenizer(in.readLine());
                return tok.nextToken();
            }

            int nextInt() throws IOException {
                return Integer.parseInt(next());
            }
        }
    }

    public static void main(String[] args) {
        new Thread(null, () -> {
            try {
                new Solver().run();
            } catch (IOException ignored) {
            }
        }, "1", 1 << 26).start();
    }

}