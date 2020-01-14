package strings.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
	Find the shortest substring of one string that does not appear in another string.
 */
public class NonSharedSubstring implements Runnable {
    private String solve(String first, String second) {
        Node root = buildTree(first + "#" + second + "$");
        markEligibleNodes(root);

        List<String> answers = new ArrayList<>();
        possibleAnswers(root, "", answers);

        String result = answers.get(0);
        for (int i = 1; i < answers.size(); i++) {
            if (result.length() > answers.get(i).length()) result = answers.get(i);
        }

        return result;
    }

    private void possibleAnswers(Node node, String currentPath, List<String> result) {
        if (node.eligible && !node.text.startsWith("#")) {
            result.add(currentPath + node.text.substring(0, 1));
        }
        for (Node child : node.children) {
            possibleAnswers(child, currentPath + (node.text == null ? "" : node.text), result);
        }
    }

    private void markEligibleNodes(Node node) {
        //mark leaves
        if (node.text != null && node.text.contains("#") && node.children.isEmpty()) node.eligible = true;

        for (Node child : node.children) markEligibleNodes(child);

        //mark non-leaves
        if (!node.children.isEmpty() && node.children.stream().allMatch(it -> it.eligible)) node.eligible = true;
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
        boolean eligible;
        List<Node> children = new ArrayList<>();
    }

    static class CommonPrefix {
        int length;
        Node node;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String p = in.readLine();
            String q = in.readLine();

            String ans = solve(p, q);

            System.out.println(ans);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new NonSharedSubstring()).start();
    }
}