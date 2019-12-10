package course2.week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SetRangeSum {
    private static final int MODULO = 1000000001;

    private BufferedReader reader;
    private PrintWriter writer;
    private StringTokenizer tokenizer;
    private Vertex root = null;

    static class Vertex {
        int key;
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private void update(Vertex vertex) {
        if (vertex == null) return;
        vertex.sum = vertex.key + (vertex.left != null ? vertex.left.sum : 0) + (vertex.right != null ? vertex.right.sum : 0);
        if (vertex.left != null) {
            vertex.left.parent = vertex;
        }
        if (vertex.right != null) {
            vertex.right.parent = vertex;
        }
    }

    private void smallRotation(Vertex vertex) {
        Vertex parent = vertex.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = vertex.parent.parent;
        if (parent.left == vertex) {
            Vertex m = vertex.right;
            vertex.right = parent;
            parent.left = m;
        } else {
            Vertex m = vertex.left;
            vertex.left = parent;
            parent.right = m;
        }
        update(parent);
        update(vertex);
        vertex.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = vertex;
            } else {
                grandparent.right = vertex;
            }
        }
    }

    private void bigRotation(Vertex vertex) {
        if (vertex.parent.left == vertex && vertex.parent.parent.left == vertex.parent) {
            // Zig-zig
            smallRotation(vertex.parent);
            smallRotation(vertex);
        } else if (vertex.parent.right == vertex && vertex.parent.parent.right == vertex.parent) {
            // Zig-zig
            smallRotation(vertex.parent);
            smallRotation(vertex);
        } else {
            // Zig-zag
            smallRotation(vertex);
            smallRotation(vertex);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    private Vertex splay(Vertex vertex) {
        if (vertex == null) return null;
        while (vertex.parent != null) {
            if (vertex.parent.parent == null) {
                smallRotation(vertex);
                break;
            }
            bigRotation(vertex);
        }
        return vertex;
    }

    static class VertexPair {
        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    private VertexPair find(Vertex root, int key) {
        Vertex vertex = root;
        Vertex last = root;
        Vertex next = null;
        while (vertex != null) {
            if (vertex.key >= key && (next == null || vertex.key < next.key)) {
                next = vertex;
            }
            last = vertex;
            if (vertex.key == key) {
                break;
            }
            if (vertex.key < key) {
                vertex = vertex.right;
            } else {
                vertex = vertex.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    private VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    private Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }

    private void insert(int key) {
        Vertex left;
        Vertex right;
        Vertex newVertex = null;
        VertexPair leftRight = split(root, key);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != key) {
            newVertex = new Vertex(key, key, null, null, null);
        }
        root = merge(merge(left, newVertex), right);
    }

    private void erase(int key) {
        if (!find(key)) return;
        root = merge(root.left, root.right);
        if (root != null) root.parent = null;
    }

    private boolean find(int key) {
        VertexPair vertexPair = find(root, key);
        root = vertexPair.right;
        return vertexPair.left != null && vertexPair.left.key == key;
    }

    private long sum(int from, int to) {
        if (from > to) {
            return 0;
        }

        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        long answer = 0;
        if (middle != null) {
            answer = middle.sum;
            left = merge(left, middle);
        }
        root = merge(left, right);
        return answer;
    }

    private void solve() {
        int n = nextInt();
        int lastSumResult = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+': {
                    int x = nextInt();
                    insert((x + lastSumResult) % MODULO);
                }
                break;
                case '-': {
                    int x = nextInt();
                    erase((x + lastSumResult) % MODULO);
                }
                break;
                case '?': {
                    int x = nextInt();
                    writer.println(find((x + lastSumResult) % MODULO) ? "Found" : "Not found");
                }
                break;
                case 's': {
                    int from = nextInt();
                    int to = nextInt();
                    long result = sum((from + lastSumResult) % MODULO, (to + lastSumResult) % MODULO);
                    writer.println(result);
                    lastSumResult = (int) (result % MODULO);
                }
            }
        }
    }

    private SetRangeSum() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        solve();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
    }

    private String nextToken() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (Exception e) {
                return null;
            }
        }
        return tokenizer.nextToken();
    }

    private int nextInt() {
        return Integer.parseInt(nextToken());
    }

    private char nextChar() {
        return nextToken().charAt(0);
    }
}