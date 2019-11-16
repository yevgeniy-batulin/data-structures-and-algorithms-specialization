import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
    You are given a description of a rooted tree. Your task is to compute and output its height.
    The first line contains the number of nodes 𝑛.
    The second line contains 𝑛 integer numbers from −1 to 𝑛 − 1 — parents of nodes.
    If the 𝑖-th one of them (0 ≤ 𝑖 ≤ 𝑛 − 1) is −1, node 𝑖 is the root, otherwise it’s 0-based index of the parent of 𝑖-th node.
    It is guaranteed that there is exactly one root. It is guaranteed that the input represents a tree.
 */
public class TreeHeight {
    static class Node {
        List<Integer> childIndexes;

        Node() {
            this.childIndexes = new ArrayList<>();
        }

        void addChild(Integer childIndex) {
            childIndexes.add(childIndex);
        }
    }

    private int[] read() throws IOException {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = in.nextInt();
        }
        return parent;
    }

    private int computeHeight(int[] parent) {
        Node[] nodes = new Node[parent.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }

        int rootIndex = 0;
        for (int childIndex = 0; childIndex < parent.length; childIndex++) {
            int parentIndex = parent[childIndex];
            if (parentIndex == -1) {
                rootIndex = childIndex;
            } else {
                nodes[parentIndex].addChild(childIndex);
            }
        }

        return iterativeHeight(nodes, rootIndex);
    }

    private int iterativeHeight(Node[] nodes, int rootIndex) {
        int height = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(rootIndex);
        while (true) {
            int nodeCount = queue.size();
            if (nodeCount == 0) return height;
            height++;

            while (nodeCount > 0) {
                Integer poll = queue.poll();
                if (!nodes[poll].childIndexes.isEmpty()) {
                    queue.addAll(nodes[poll].childIndexes);
                }
                nodeCount--;
            }
        }
    }

    private int recursiveHeight(Node[] nodes, int index) {
        if (nodes[index].childIndexes.isEmpty()) return 1;

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nodes[index].childIndexes.size(); i++) {
            int height = 1 + recursiveHeight(nodes, nodes[index].childIndexes.get(i));
            if (height > max) max = height;
        }
        return max;
    }

    public static void main(String[] args) throws IOException {
        TreeHeight tree = new TreeHeight();
        int[] parent = tree.read();
        System.out.println(tree.computeHeight(parent));
    }
}

class FastScanner {
    private StringTokenizer tok = new StringTokenizer("");
    private BufferedReader in;

    FastScanner() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    private String next() throws IOException {
        while (!tok.hasMoreElements())
            tok = new StringTokenizer(in.readLine());
        return tok.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}