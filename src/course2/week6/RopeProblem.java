package course2.week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

class RopeProblem {
    static class Rope {
        Vertex root;

        Rope(String s) {
            root = create(s);
        }

        void process(int i, int j, int k) {
            VertexPair leftMid = split(root, i);
            VertexPair midRight = split(leftMid.right, j - i + 2);
            Vertex mid = midRight.left;
            root = merge(leftMid.left, midRight.right);
            VertexPair leftRight = split(root, k + 1);
            root = merge(leftRight.left, mid);
            root = merge(root, leftRight.right);
        }

        String result() {
            StringBuilder stringBuilder = new StringBuilder();
            Vertex node = root;
            Stack<Vertex> stack = new Stack<>();
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            // traverse the tree
            while (stack.size() > 0) {
                // visit the top node
                node = stack.pop();
                stringBuilder.append(node.letter);
                if (node.right != null) {
                    node = node.right;

                    // the next node to be visited is the leftmost
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
            }
            return stringBuilder.toString();
        }

        private Vertex create(String string) {
            Vertex root = null;
            Vertex prev = null;
            int length = string.length();
            for (int i = 0; i < length; i++) {
                Vertex v = new Vertex(length - i, string.charAt(i), null, null, prev);
                if (prev == null) {
                    root = v;
                } else {
                    prev.right = v;
                }
                prev = v;
            }
            return root;
        }
    }

    public static void main(String[] args) throws IOException {
        new RopeProblem().run();
    }

    private void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i + 1, j + 1, k);
        }
        out.println(rope.result());
        out.close();
    }

    static class Vertex {
        int size;
        char letter;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int size, char letter, Vertex left, Vertex right, Vertex parent) {
            this.size = size;
            this.letter = letter;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private static void update(Vertex v) {
        if (v == null) return;
        v.size = 1 + (v.left != null ? v.left.size : 0) + (v.right != null ? v.right.size : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    private static void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    private static void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    private static Vertex splay(Vertex vertex) {
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

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    private static VertexPair find(Vertex root, int target) {
        if (root.size < target) return null;
        Vertex vertex = root;
        Vertex last = vertex;
        Vertex found = null;

        while (vertex != null) {
            last = vertex;
            int leftSum = 0;
            if (vertex.left != null) {
                leftSum = vertex.left.size;
            }
            if (target == leftSum + 1) {
                found = vertex;
                break;
            } else if (target > leftSum) {
                vertex = vertex.right;
                target -= leftSum + 1;
            } else {
                vertex = vertex.left;
            }
        }
        root = splay(last);
        return new VertexPair(found, root);
    }

    private static VertexPair split(Vertex root, int key) {
        if (root == null) return new VertexPair(null, null);
        VertexPair result = new VertexPair();

        VertexPair findAndRoot = find(root, key);
        if (findAndRoot != null) {
            root = findAndRoot.right;
            result.right = findAndRoot.left;
        }
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

    private static Vertex merge(Vertex left, Vertex right) {
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

    static class VertexPair {
        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "{" + left + "} + {" + right + "}";
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