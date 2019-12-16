package graphs.week2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Compute a topological ordering of a given directed acyclic graph (DAG) with 𝑛 vertices and 𝑚 edges.
 */
public class TopologicalSort {
    private static List<Integer> sort(List<List<Integer>> adjacent) {
        boolean[] visited = new boolean[adjacent.size()];
        List<Integer> order = new ArrayList<>(adjacent.size());
        for (int i = 0; i < adjacent.size(); i++) {
            if (!visited[i])
                depthFirstSearch(adjacent, i, visited, order);
        }
        return order;
    }

    private static void depthFirstSearch(List<List<Integer>> adj, int current, boolean[] visited, List<Integer> order) {
        for (Integer neighbour : adj.get(current)) {
            if (!visited[neighbour]) {
                depthFirstSearch(adj, neighbour, visited, order);
            }
        }

        order.add(current);
        visited[current] = true;
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
        List<Integer> order = sort(adj);
        for (int i = order.size() - 1; i >= 0; i--) {
            System.out.print((order.get(i) + 1) + " ");
        }
    }
}