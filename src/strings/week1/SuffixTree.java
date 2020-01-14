package strings.week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
    Construct the suffix tree of a string.
 */
public class SuffixTree {
    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding substrings of the text) in any order.
    private List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<>();
        Node root = buildTree(text);
        buildResult(root, result);
        return result;
    }

    private Node buildTree(String text) {
        Node root = new Node();
        Node wholeTextNode = new Node();
        wholeTextNode.text = text;
        root.children.add(wholeTextNode);

        for (int i = 1; i < text.length(); i++) {
            fillTreeWithSubstring(root, new StringBuilder(text.substring(i)));
        }
        return root;
    }

    private void fillTreeWithSubstring(Node currentNode, StringBuilder string) {
        while (string.length() != 0) {
            CommonPrefix commonPrefix = findCommonPrefix(currentNode, string);
            if (commonPrefix.length == 0) {
                // no common prefix found
                addChild(currentNode, string.toString());
                string.delete(0, string.length());
            } else {
                currentNode = commonPrefix.node;
                if (currentNode.text.length() == commonPrefix.length) {
                    // common prefix is a full node text so just delete the common prefix and go below to children
                    string.delete(0, commonPrefix.length);
                } else {
                    // split current node and add new node to it
                    splitNode(currentNode, commonPrefix);
                    addChild(currentNode, string.substring(commonPrefix.length));
                    string.delete(0, string.length());
                }
            }
        }
    }

    private void addChild(Node node, String text) {
        Node newNode = new Node();
        newNode.text = text;
        node.children.add(newNode);
    }

    private void splitNode(Node currentNode, CommonPrefix commonPrefix) {
        Node node = new Node();
        node.text = currentNode.text.substring(commonPrefix.length);
        node.children.addAll(currentNode.children);
        currentNode.text = currentNode.text.substring(0, commonPrefix.length);
        currentNode.children.clear();
        currentNode.children.add(node);
    }

    private CommonPrefix findCommonPrefix(Node node, StringBuilder string) {
        CommonPrefix result = new CommonPrefix();

        for (Node child : node.children) {
            int commonLength = 0;
            for (int i = 0; i < string.length() && i < child.text.length(); i++) {
                if (child.text.charAt(i) == string.charAt(i)) commonLength++;
                else break;
            }
            if (commonLength > result.length) {
                result.length = commonLength;
                result.node = child;
            }
        }

        return result;
    }

    static class Node {
        String text;
        List<Node> children = new ArrayList<>();
    }

    static class CommonPrefix {
        int length;
        Node node;
    }

    private void buildResult(Node node, List<String> result) {
        if (node.text != null) {
            result.add(node.text);
        }
        for (Node value : node.children) {
            buildResult(value, result);
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> strings) {
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
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