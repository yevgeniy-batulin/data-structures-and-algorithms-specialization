package graphs.week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/*
    Compute the number of strongly connected components of a given directed graph with 𝑛 vertices and 𝑚 edges.
 */
public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(List<List<Integer>> adjacent) {
        List<List<Integer>> reverseGraph = getReverseGraph(adjacent);
        int[] postOrder = depthFirstSearchWithPostOrder(reverseGraph);
        Integer[] verticesSortedByPostOrder = getVerticesSortedByPostOrder(adjacent, postOrder);
        return getNumberOfStronglyConnectedComponents(adjacent, verticesSortedByPostOrder);
    }

    private static List<List<Integer>> getReverseGraph(List<List<Integer>> adjacent) {
        List<List<Integer>> result = new ArrayList<>(adjacent.size());
        for (int i = 0; i < adjacent.size(); i++) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < adjacent.size(); i++) {
            for (Integer value : adjacent.get(i)) {
                result.get(value).add(i);
            }
        }
        return result;
    }

    private static int[] depthFirstSearchWithPostOrder(List<List<Integer>> adjacent) {
        boolean[] visited = new boolean[adjacent.size()];
        int counter = 0;
        int[] postOrder = new int[adjacent.size()];
        for (int i = 0; i < adjacent.size(); i++) {
            if (!visited[i]) {
                counter = exploreWithPostOrder(adjacent, i, visited, postOrder, counter);
            }
        }
        return postOrder;
    }

    private static int exploreWithPostOrder(List<List<Integer>> adjacent, int current, boolean[] visited, int[] postOrder, int counter) {
        visited[current] = true;
        for (Integer neighbour : adjacent.get(current)) {
            if (!visited[neighbour]) {
                counter = exploreWithPostOrder(adjacent, neighbour, visited, postOrder, counter + 1);
            }
        }
        postOrder[current] = ++counter;
        return counter;
    }

    private static Integer[] getVerticesSortedByPostOrder(List<List<Integer>> adjacent, int[] postOrder) {
        Integer[] verticesSortedByPostOrder = new Integer[adjacent.size()];
        for (int i = 0; i < verticesSortedByPostOrder.length; i++) {
            verticesSortedByPostOrder[i] = i;
        }
        Arrays.sort(verticesSortedByPostOrder, Comparator.comparingInt(i -> postOrder[i]));
        return verticesSortedByPostOrder;
    }

    private static int getNumberOfStronglyConnectedComponents(List<List<Integer>> adjacent, Integer[] postOrder) {
        int result = 0;
        boolean[] visited = new boolean[adjacent.size()];
        for (int i = postOrder.length - 1; i >= 0; i--) {
            if (!visited[postOrder[i]]) {
                explore(adjacent, postOrder[i], visited);
                result++;
            }
        }
        return result;
    }

    private static void explore(List<List<Integer>> adjacent, int current, boolean[] visited) {
        visited[current] = true;
        for (Integer integer : adjacent.get(current)) {
            if (!visited[integer])
                explore(adjacent, integer, visited);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj.get(x - 1).add(y - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}